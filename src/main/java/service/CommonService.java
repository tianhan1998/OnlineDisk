package service;

import entity.Common;
import entity.Good;
import mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {
    @Autowired
    CommonMapper commonMapper;
    public List<Common> listCommon(String username){
        List<Common> commons= commonMapper.listCommon();
        for(Common com:commons){
            if(findGood(username,com.getId())!=0){
                com.setGood(true);
            }
        }
        return commons;
    }
    public int insertCommon(Common common){
        return commonMapper.insertCommon(common);
    }
    public int deleteCommon(Integer id){
        return commonMapper.deleteCommon(id);
    }
    public Common findId(Integer id){
        return commonMapper.findId(id);
    }
    public int findGood(String username, Integer commonid){
        return commonMapper.findGood(username, commonid);
    }
    public int insertGood(String username, Integer commonid){
        return commonMapper.insertGood(username, commonid);
    }
    public Good findGoodByGid(Integer gid){
        return commonMapper.findGoodByGid(gid);
    }
    public Good findGoodByNameAndId(String username,Integer common_id){
        return commonMapper.findGoodByNameAndId(username, common_id);
    }
    public int updateCommonGood(Integer id){
        return commonMapper.updateCommonGood(id);
    }
    public int deleteGood(Integer gid){
        return commonMapper.deleteGood(gid);
    }
    public int unUpdateCommonGood(Integer id){
        return commonMapper.unUpdateCommonGood(id);
    }
}
