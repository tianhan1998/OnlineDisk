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
    public List<Common> listCommon(){
        return commonMapper.listCommon();
    }
    public boolean insertCommon(Common common){
        return commonMapper.insertCommon(common);
    }
}
