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
    public int updateStudyInfo(String userId, String lesson, Integer status) {
//todo 增加属性
        String sql = "UPDATE adopt SET "+lesson+" = "+lesson+" +"+status+" WHERE userId = "+userId;
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
    public int updateStudyStatus(String userId, Integer status) {
        String sql = "UPDATE adopt SET tired= tired -"+status+" , hunger= hunger- "+status+" WHERE userId = "+userId;
        try {
            jdbcTemplate.update(sql);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
