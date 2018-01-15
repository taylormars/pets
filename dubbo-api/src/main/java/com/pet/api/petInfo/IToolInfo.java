package com.pet.api.petInfo;

import java.util.List;
import java.util.Map;

public interface IToolInfo {

    List<Map<String,Object>> querytoolByuserId(String petId);

    Integer updateToolUse(String userId,String toolId,String sums);

    Map<String,Object> queryToolInfoByToolId(String toolId);

    Integer updatePetInfoByUseTool(String userId,String hunger,String clean,String mood,String tired);

    Integer querToolSumsByToolIdUserId(String userId,String ToolId);
}
