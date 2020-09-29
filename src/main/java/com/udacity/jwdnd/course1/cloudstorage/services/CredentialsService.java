package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Repository.CredentialsMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;
    public CredentialsService(CredentialsMapper credentialsMapper,EncryptionService encryptionService)
    {
        this.credentialsMapper=credentialsMapper;
        this.encryptionService=encryptionService;
    }

    public Integer createCredentials(Credentials credentials)
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credentials.setKey(encodedKey);
        String encryptedPassword=encryptPassword(credentials.getPassword(),encodedKey);
        credentials.setPassword(encryptedPassword);

        return credentialsMapper.createCredentials(credentials);
    }

    public List<Credentials> getCredentials(Integer userid)
    {
        return credentialsMapper.getCredentials(userid);
    }

    public Credentials getCrendentialsById(Integer credentialid)
    {
        Credentials credential=credentialsMapper.getCredentialById(credentialid);
        credential.setPassword(decryptPassword(credential.getPassword(),credential.getKey()));
        return credential;

    }
    public Integer deleteCredentials(Integer credentialid)
    {
        return credentialsMapper.deleteCredential(credentialid);
    }


    public Integer updateCredentials(Credentials credential)
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        credential.setPassword(encryptPassword(credential.getPassword(),encodedKey ));
        return credentialsMapper.updateCredentials(credential);
    }

    private String encryptPassword(String password,String key)
    {

        return encryptionService.encryptValue(password,key);
    }

    public String decryptPassword(String encryptedPassword,String encodedKey)
    {
       return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }

}
