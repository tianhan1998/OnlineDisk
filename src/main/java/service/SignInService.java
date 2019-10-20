package service;

import entity.User;
import mapper.SignInMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInService {
    @Autowired
    SignInMapper sign;
    public User signIn(String username,String password){
        return sign.signIn(username, password);
    }
}
