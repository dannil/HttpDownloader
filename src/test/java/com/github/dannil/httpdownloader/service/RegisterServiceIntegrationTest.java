package com.github.dannil.httpdownloader.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.repository.UserRepository;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Integration tests for register service
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class RegisterServiceIntegrationTest {

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserById() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        User user = TestUtility.getUser();
        User registered = this.registerService.save(user);

        Optional<User> opFind = this.userRepository.findById(registered.getId());
        User find = opFind.orElse(null);
        
        assertNotEquals(null, find);
    }

    @Test
    public void findUserByEmail() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        User user = TestUtility.getUser();
        this.registerService.save(user);

        User find = this.userRepository.findByEmail(user.getEmail());

        assertNotEquals(null, find);
    }

    @Test
    public void registerUserWithExistingEmail() {
        User user = TestUtility.getUser();
        this.registerService.save(user);

        User fetched = this.registerService.findByEmail(user.getEmail());

        assertNotEquals(null, fetched);
    }

}
