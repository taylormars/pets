package com.pet.provider.service.study;

import com.pet.api.study.IStudy;
import com.pet.provider.utils.SqlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("study")
public class StudyServiceImpl implements IStudy {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private SqlUtils sqlUtils;
    @Override
    public int updateStudyInfo(String userId, String lesson, Integer status,String benefit,Integer reward) {
//todo 增加属性
        String sql ;
        if(benefit.equals("all")){
            sql = "UPDATE adopt SET "+lesson+" = "+lesson+" +"+status+" , intelligence = intelligence +"+reward+" , swift = swift +"+reward+" , strength = strength +"+reward+ " WHERE userId = "+userId;
        }else {
       sql = "UPDATE adopt SET "+lesson+" = "+lesson+" +"+status+" , "+benefit +"="+ benefit +"+"+reward+" WHERE userId = "+userId;
        }
        try {
            jdbcTemplate.update(sql);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public void insertStudyRecord(String userId, String lesson, String studyTime) {
        String sql = "INSERT INTO education_record (userId,lesson,studyTime,startTime) VALUES(?,?,?,NOW())";
        try {
         jdbcTemplate.update(sql, new Object[]{userId,lesson,studyTime});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int updateStudyStatus(String userId, Integer status,Integer cost,String wC) {
        String sql1 = "UPDATE adopt SET tired= tired -"+status+" , hunger= hunger- "+status+" , clean = clean- "+status+" , health = health- "+status+" WHERE userId = "+userId;
        String sql2 = "UPDATE user_main SET coin = coin - "+cost +" WHERE userId = "+userId;
        try {
            jdbcTemplate.update(sql1);
            if (wC.equals("1")){
                jdbcTemplate.update(sql2);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }



    @Override
    public  int updateWorkInfo(String userId,Integer reword){
        String sql = "UPDATE user_main SET coin = coin + "+reword +" WHERE userId = "+userId;
        try {
            jdbcTemplate.update(sql);
            return 1;
            }catch(Exception e){
                e.printStackTrace();
                return  0 ;
        }
        }

}

