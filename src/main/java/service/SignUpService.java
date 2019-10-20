package service;

import entity.User;
import mapper.SignUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    @Autowired
    SignUpMapper signUpMapper;

    public User signUpCheck(String username){
        return signUpMapper.signUpCheck(username);
    }
    public boolean signUp(String username, String password){
        return signUpMapper.signUp(username, password);
    }
}
