package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Repository.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;
    public UserService(UserMapper userMapper,HashService hashService)
    {
        this.userMapper=userMapper;
        this.hashService=hashService;
    }

    public boolean isUsernameAvailable(String username)
    {
        User user= userMapper.getUser(username);
        if(user!=null)
            return false;
        return true;
    }

    public int createUser(User user)
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.createUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));

    }

    public User getUser(String username)
    {
        return userMapper.getUser(username);
    }
}
