package com.pet.api.petInfo;

import java.util.Map;

public interface IToolInfo {

    Map<String,Object> queryeducation(String petId);

    void updatePetInfo();

}
