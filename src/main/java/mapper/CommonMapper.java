package mapper;

import entity.Common;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {
    List<Common> listCommon();
    boolean insertCommon(Common common);
    boolean findGood(@Param("username")String username,@Param("commomid") Integer commonid);
    boolean deleteCommon(Integer id);
    Common findId(Integer id);
    boolean insertGood(@Param("username")String username,@Param("commomid") Integer commonid);
}
