package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Repository.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements AuthenticationProvider {
    private UserMapper userMapper;
    private HashService hashService;
    public AuthService(UserMapper userMapper,HashService hashService)
    {
        this.userMapper=userMapper;
        this.hashService=hashService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();
        User user=userMapper.getUser(username);
        if(user!=null)
        {
            String salt=user.getSalt();
            String hashPassword=hashService.getHashedValue(password,salt);

            if(user.getPassword().equals(hashPassword))
                return new UsernamePasswordAuthenticationToken(username,password,new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if(aClass.equals(UsernamePasswordAuthenticationToken.class))
            return true;
        return false;
    }
}
