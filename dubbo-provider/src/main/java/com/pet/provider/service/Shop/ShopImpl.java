package com.pet.provider.service.Shop;

import com.pet.api.shop.IShop;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("shop")
public class ShopImpl implements IShop {

    @Resource
    private JdbcTemplate jdbcTemplate;    @Override
    public List<Map<String, Object>> queryShopOnSellProduct() {
        String sql ="SELECT * FROM shop WHERE sell = 1";
        try {
            List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map<String,Object> queryShopInfoByToolId(String toolId){
        String sql ="SELECT * FROM shop WHERE sell = 1 AND toolId ="+toolId;
        try {
           Map<String, Object> map = jdbcTemplate.queryForMap(sql);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer updateProductSums(String sums,String toolId){
        String sql = "UPDATE shop SET sums = sums - "+sums+" WHERE toolId = "+toolId;
        try{
            jdbcTemplate.update(sql);
            return 1;
        }catch (Exception e){
            return  0;
        }
    }

    @Override
    public Integer updateUserCoin(String userId,Integer coin){
        String sql = "UPDATE user_main SET coin = coin -"+coin+" WHERE userId= "+userId;
        try{
            jdbcTemplate.update(sql);
            return 1;
        }catch (Exception e){
            return  0;
        }
    }

    @Override
    public Integer insertToolRecord(String userId,String toolId,String sums){
        String sql = "INSERT INTO tool_use_record (toolId,userId,status,source,creatTime) VALUES(?,?,0,3,NOW())";
        try{
            int i = Integer.parseInt(sums);
            for (int j=0;j<i;j++){
            jdbcTemplate.update(sql,new Object[]{toolId,userId});}
            return 1;
        }catch (Exception e){
            return  0;
        }
    }

    @Override
    public Map<String, Object> queryCoinByUserId(String userId) {
        String sql ="SELECT coin FROM user_main WHERE userId="+userId;
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
