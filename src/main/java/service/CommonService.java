package service;

import entity.Common;
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
            if(findGood(username,com.getId())){
                com.setGood(true);
            }
        }
        return commons;
    }
    public boolean insertCommon(Common common){
        return commonMapper.insertCommon(common);
    }
    public boolean deleteCommon(Integer id){
        return commonMapper.deleteCommon(id);
    }
    public Common findId(Integer id){
        return commonMapper.findId(id);
    }
    public boolean findGood(String username,Integer commonid){
        return commonMapper.findGood(username, commonid);
    }
    public boolean insertGood(String username,Integer commonid){
        return commonMapper.insertGood(username, commonid);
    }
    public boolean updateCommonGood(Integer id){
        return commonMapper.updateCommonGood(id);
    }
    public boolean deleteGood(String username,Integer commonid){
        return commonMapper.deleteGood(username, commonid);
    }
    public boolean unUpdateCommonGood(Integer id){
        return commonMapper.unUpdateCommonGood(id);
    }
}
