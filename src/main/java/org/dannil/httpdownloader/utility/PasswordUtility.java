package org.dannil.httpdownloader.utility;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public final class PasswordUtility {

	//
	// The following constants may be changed without breaking existing hashes
	//

	// Size of the salt in bytes (1 byte = 8 bits)
	private static final int SALT_BYTE_SIZE = 64;

	// Size of the hash in bytes (1 byte = 8 bits)
	private static final int HASH_BYTE_SIZE = 64;

	// Hardcoded value for the number of iterations the password-based key
	// derivation function should run; the higher
	// the number, the better the security will be, but a significant
	// performance hit will be noticed with very high
	// numbers ( > 150 000 )
	private static final int PBKDF2_ITERATIONS = 102072;

	//
	// The following constants can't be changed without breaking the existing
	// hashes
	//

	// The algorithm for the password-based key derivative function engine
	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// The index for the specific parts of the password, going from left to
	// right
	private static final int ITERATION_INDEX = 0;
	private static final int SALT_INDEX = 1;
	private static final int PBKDF2_INDEX = 2;

	/**
	 * Private constructor to make the class a true singleton
	 */
	private PasswordUtility() {
		throw new AssertionError();
	}

	/**
	 * 
	 * Return a new password, which is salted and hashed
	 * 
	 * @param password
	 * 			the password to be encrypted
	 * @return
	 * 			the new password
	 * @throws NoSuchAlgorithmException
	 *             if the specified algorithm doesn't exist
	 * @throws NoSuchProviderException
	 *             if the specified provider doesn't exist
	 * @throws InvalidKeySpecException
	 *             if an invalid key is specified
	 */
	public static final String getHashedPassword(String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		// Retrieve a salt to be used in the secret key
		final byte[] salt = getSalt();

		// Generate a new hash with the supplied values
		final byte[] hash = getHash(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);

		// Concatenate the iterations, the salt and the hash
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	/**
	 * 
	 * Validate the attempted password with the stored password.
	 * 
	 * @param attemptedPassword
	 *            the user specified password
	 * @param storedPassword
	 *            the stored password
	 * @return
	 *         true if both passwords are the same, false if not
	 * 
	 * @throws NoSuchAlgorithmException
	 *             if the specified alogirthm doesn't exist
	 * @throws InvalidKeySpecException
	 *             if an invalid key is specified
	 */
	public static final boolean validateHashedPassword(String attemptedPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Split the stored password into parts and retrieve the iterations, the
		// salt and the hash
		final String[] parts = storedPassword.split(":");
		final int iterations = Integer.parseInt(parts[ITERATION_INDEX]);
		final byte[] salt = toByte(parts[SALT_INDEX]);
		final byte[] storedHash = toByte(parts[PBKDF2_INDEX]);

		// Generate a new hash with the previously retrieved parts
		final byte[] attemptHash = getHash(attemptedPassword, salt, iterations, storedHash.length);

		// Compare the newly generated hash with the stored hash and return true
		// if they match
		return slowEquals(storedHash, attemptHash);
	}

	/**
	 * 
	 * Generate a new random salt.
	 * 
	 * @return
	 *         a byte[] with the randomly generated salt
	 * @throws NoSuchAlgorithmException
	 *             if the specified algorithm doesn't exist
	 * @throws NoSuchProviderException
	 *             if the specified provider doesn't exist
	 */
	private static final byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		// Instantiate a new SecureRandom with the supplied algorithm SHA1PRNG
		final SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

		// Generate a salt by retrieving random bytes from SecureRandom class.
		final byte[] salt = new byte[SALT_BYTE_SIZE];
		sr.nextBytes(salt);
		return salt;
	}

	/**
	 * 
	 * Generates a new hash with the help of the password, the salt, the number of iterations and the length in a number of bytes.
	 * 
	 * @param password
	 *            the password
	 * @param salt
	 *            the salt
	 * @param iterations
	 *            the number of iterations
	 * @param bytes
	 *         	  the length in bytes
	 * @return
	 *         a byte[] with the new hash
	 * @throws NoSuchAlgorithmException
	 *             if the specified algorithm doesn't exist
	 * @throws InvalidKeySpecException
	 *             if an invalid key is specified
	 */
	private static final byte[] getHash(String password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Generate a new PBEKey with the supplied password, salt, iterations
		// and the length
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, bytes * 8);

		// Instantiate a new SecureRandom with the supplied algorithm
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);

		return skf.generateSecret(spec).getEncoded();
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method is used so that password hashes can't
	 * be extracted from an online system using a timing attack and then attacked offline.
	 * 
	 * @param a
	 *            the first byte array
	 * @param b
	 *            the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private static final boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++) {
			diff |= a[i] ^ b[i];
		}
		return (diff == 0);
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 * 
	 * @param array
	 *            the byte array to convert
	 * @return an array.length * 2 character string encoding the byte array
	 */
	private static final String toHex(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	/**
	 * Converts a string of hexadecimal characters into a byte array.
	 * 
	 * @param hex
	 *            the hex string
	 * @return the hex string decoded into a byte array
	 */
	private static final byte[] toByte(String hex) {
		return DatatypeConverter.parseHexBinary(hex);
	}

}
