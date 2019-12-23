package mapper;

import entity.Common;
import entity.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {
    List<Common> listCommon();
    int insertCommon(Common common);
    int findGood(@Param("username")String username, @Param("commonid") Integer commonid);
    int deleteCommon(Integer id);
    Common findId(Integer id);
    int insertGood(@Param("username")String username,@Param("commonid") Integer commonid);
    int updateCommonGood(Integer id);
    int deleteGood(Integer gid);
    int unUpdateCommonGood(Integer id);
    Good findGoodByGid(Integer gid);
    Good findGoodByNameAndId(@Param("username")String username,@Param("common_id")Integer common_id);
}
