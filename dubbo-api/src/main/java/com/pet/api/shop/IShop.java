package com.pet.api.shop;

import java.util.List;
import java.util.Map;

public interface IShop {

    List<Map<String,Object>> queryShopOnSellProduct();

    Map<String,Object> queryShopInfoByToolId(String toolId);

    Integer updateProductSums(String sums ,String toolId);

    Integer updateUserCoin(String userId,Integer coin);

    Integer insertToolRecord(String userId,String toolId,String sums);

    Map<String,Object> queryCoinByUserId(String userId);

}
