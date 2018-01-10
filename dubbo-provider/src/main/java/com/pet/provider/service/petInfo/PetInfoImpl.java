package com.pet.provider.service.petInfo;

import com.pet.api.petInfo.IPetInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Service("educationInfo")
public class PetInfoImpl implements IPetInfo{
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public Map<String, Object> queryeducation(String petId) {
        String sql="SELECT * FROM education_info WHERE petId =?";
        Map map=new HashMap();
        try {
            map = jdbcTemplate.queryForMap(sql, new Object[]{petId});
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return map;
    }
}
