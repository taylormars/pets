package com.pet.provider.service.petInfo;

import com.pet.api.petInfo.IPetInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("petInfo")
public class PetInfoImpl implements IPetInfo {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> queryeducation(String petId) {
        String sql = "SELECT * FROM education_info WHERE petId =?";
        Map map = new HashMap();
        try {
            map = jdbcTemplate.queryForMap(sql, new Object[]{petId});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    @Override
    public void updatePetInfo() {
        try {
            String sql = "UPDATE adopt SET  hunger = CASE WHEN hunger - 4 > 0 THEN hunger - 4 WHEN hunger - 4 <= 0 THEN 0 END, clean = CASE WHEN clean - 4 > 0 THEN clean - 4 WHEN clean - 4 <= 0 THEN 0 END, health = CASE WHEN health - 4 > 0 THEN health - 4 WHEN health - 4 <= 0 THEN 0 END, mood = CASE WHEN mood - 4 > 0 THEN mood - 4 WHEN mood - 4 <= 0 THEN 0 END, tired = CASE WHEN tired - 4 > 0 THEN tired - 4 WHEN tired - 4 <= 0 THEN 0 END";
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
