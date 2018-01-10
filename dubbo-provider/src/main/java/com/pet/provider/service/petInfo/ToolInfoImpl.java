package com.pet.provider.service.petInfo;

import com.pet.api.petInfo.IPetInfo;
import com.pet.api.petInfo.IToolInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("toolInfo")
public class ToolInfoImpl implements IToolInfo{
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Map<String, Object>> querytoolByuserId(String userId) {
        String sql="SELECT toolId, endTime, source, COUNT(toolId) AS 'counts' FROM tool_use_record WHERE userId = ? AND STATUS = 0 GROUP BY toolId";
        try {
            List<Map<String, Object>> map = jdbcTemplate.queryForList(sql, new Object[]{userId});
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
