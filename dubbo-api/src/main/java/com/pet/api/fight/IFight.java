package com.pet.api.fight;

import com.pet.api.model.PetFightInfo;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 11:41 2018-05-21
 * @CreateBY : idea
 */
public interface IFight {

    PetFightInfo queryPetFightInfo (String userId);
}
