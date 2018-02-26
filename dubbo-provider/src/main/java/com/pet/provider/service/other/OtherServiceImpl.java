package com.pet.provider.service.other;

import com.pet.api.other.IOther;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("other")
public class OtherServiceImpl implements IOther{
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public Map<String,Object>queryDailyWords(){
        String sql="SELECT words, DATE_FORMAT(date,'%Y-%m-%d') AS 'date' FROM daily_words ORDER BY id DESC LIMIT 1";
        Map <String,Object> map= new HashMap<>();
        try{
            map=jdbcTemplate.queryForMap(sql);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return map;
    }
}
