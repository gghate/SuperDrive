package com.udacity.jwdnd.course1.cloudstorage.Repository;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper extends Mapper {

    @Insert("insert into CREDENTIALS (url,username,key,password,userid) values (#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialid")
    int createCredentials(Credentials credentials);

    @Select("Select * from CREDENTIALS where userid=#{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Delete("Delete from CREDENTIALS where credentialid=#{credentialid}")
    int deleteCredential(Integer credentialid);

    @Select("Select * from CREDENTIALS where credentialid=#{credentialid}")
    Credentials getCredentialById(Integer credentialid);

    @Update("Update CREDENTIALS set url=#{url},username=#{username},password=#{password},key=#{key} where credentialid=${credentialid}")
    int updateCredentials(Credentials credentials);
}
