package org.dannil.httpdownloader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public final class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
	private Integer userId;

	@Column(name = "Email")
	private String email;

	@Column(name = "Password")
	private String password;

	@Column(name = "Firstname")
	private String firstname;

	@Column(name = "Lastname")
	private String lastname;

	public Integer getUserId() {
		return this.userId;
	}

	// Commented for safety purposes
	/*
	 * public void setUserID(final Integer userId)
	 * {
	 * this.userId = userId;
	 * }
	 */

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("   E-mail: " + this.email + NEW_LINE);
		result.append("   Password: " + "[OMITTED]" + NEW_LINE);
		result.append("   Firstname: " + this.firstname + NEW_LINE);
		result.append("   Lastname: " + this.lastname + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
