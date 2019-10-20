package mapper;
import entity.User;
import org.apache.ibatis.annotations.Param;

public interface SignUpMapper {
    User signUpCheck(String username);
    boolean signUp(@Param("username") String username, @Param("password") String password);

}
