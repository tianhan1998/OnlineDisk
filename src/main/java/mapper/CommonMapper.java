package mapper;

import entity.Common;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {
    List<Common> listCommon();
    boolean insertCommon(Common common);
    boolean findGood(@Param("userid")Integer userid,@Param("commomid") Integer commonid);

}