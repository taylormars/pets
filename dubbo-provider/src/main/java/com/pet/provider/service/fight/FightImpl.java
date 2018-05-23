package com.pet.provider.service.fight;

import com.pet.api.fight.IFight;
import com.pet.api.model.PetFightInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 11:43 2018-05-21
 * @CreateBY : idea
 */
public class FightImpl implements IFight {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public PetFightInfo queryPetFightInfo(String userId) {
        String sql = "";
        PetFightInfo petFightInfo = new PetFightInfo();
        return null;
    }
}
