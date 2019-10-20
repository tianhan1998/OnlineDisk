package mapper;

import entity.User;
import org.apache.ibatis.annotations.Param;

public interface SignInMapper {
    User signIn(@Param("username")String username,@Param("password")String password);
}

