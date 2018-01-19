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
public class ToolInfoImpl implements IToolInfo {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> querytoolByuserId(String userId) {
        String sql = "SELECT tur.toolId, tur.endTime, tur.source, COUNT(tur.toolId) AS 'counts', t.toolName, t.clean, t.hunger, t.mood, t.tired FROM tool_use_record tur, tools t WHERE tur.toolId = t.toolId AND userId = ? AND STATUS = 0 GROUP BY toolId";
        try {
            List<Map<String, Object>> map = jdbcTemplate.queryForList(sql, new Object[]{userId});
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Integer updateToolUse(String userId, String toolId, String sums) {
        String sql = "UPDATE tool_use_record SET status = 1 , endTime = NOW() WHERE userId = ? AND status = 0 AND toolId =? ORDER BY id ASC LIMIT ?";
        try {
            jdbcTemplate.update(sql, new Object[]{userId, toolId, Integer.valueOf(sums)});
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Map<String, Object> queryToolInfoByToolId(String toolId) {

        String sql = "SELECT hunger, clean, mood, tired FROM tools WHERE toolId =?";
        try {
            Map toolInfo = jdbcTemplate.queryForMap(sql, new Object[]{toolId});
            return toolInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updatePetInfoByUseTool(String userId, String hunger, String clean, String mood, String tired) {
        String sql = "UPDATE adopt SET  hunger = CASE WHEN hunger + ? > 0 THEN hunger + ? WHEN hunger + ? <= 0 THEN 0 END, clean = CASE WHEN clean + ? > 0 THEN clean + ? WHEN clean + ? <= 0 THEN 0 END,  mood = CASE WHEN mood + ? > 0 THEN mood + ? WHEN mood + ? <= 0 THEN 0 END, tired = CASE WHEN tired + ? > 0 THEN tired + ? WHEN tired + ? <= 0 THEN 0 END WHERE userId=?";
        try {
            jdbcTemplate.update(sql, new Object[]{Integer.valueOf(hunger), Integer.valueOf(hunger), Integer.valueOf(hunger), Integer.valueOf(clean), Integer.valueOf(clean), Integer.valueOf(clean), Integer.valueOf(mood), Integer.valueOf(mood), Integer.valueOf(mood), Integer.valueOf(tired), Integer.valueOf(tired), Integer.valueOf(tired), Integer.valueOf(userId)});
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer querToolSumsByToolIdUserId(String userId, String toolId) {
        String sql = "SELECT COUNT(toolId) AS 'counts' FROM tool_use_record WHERE userId=? AND toolId=? AND status=0";
        try {
            Map tool = jdbcTemplate.queryForMap(sql, new Object[]{userId, toolId});
            Integer status = Integer.parseInt(tool.get("counts").toString());
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
