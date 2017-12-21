package com.pet.provider.service;

import com.pet.api.LoginService;
import com.pet.api.model.User;
import com.pet.api.model.UserAdmin;
import com.pet.api.model.UserMain;
import com.pet.provider.utils.SqlUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyut on 2017-09-14.
 */
@Service("login")
public class LoginImpl implements LoginService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private SqlUtils sqlUtils;

    private Logger logger = LoggerFactory.getLogger(LoginImpl.class);

    @Override
    public String login(int id) {
        String sql="select username,password from user_main where username = 'lyt'";
      //  String sql="select 1";

        //  Object[] args = new Object[0];
       // int list = jdbcTemplate.queryForInt(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> loan : list) {
                String a = loan.get("username").toString();
                String b = loan.get("password").toString();
                if(a.equals("lyt")&& b.equals("123"))
                {
                    return "登陆成功"+id;
                }

            }
       }
//        if (list > 0) {
//
//                    return "登陆成功"+id;
//
//        }
        return "登陆失败"+id;
    }

    @Override
    public UserAdmin loginAdmin(String userName , String password){
//        String sql="SELECT * FROM admin_user_main WHERE userName = '"+userName+"' AND `password` = '"+password+"'";
        logger.info("进入loginAdmin方法》》》》》");
        String sql="SELECT * FROM admin_user_main WHERE userName = ? AND `password` = ?";
        try{
        UserAdmin userAdmin=jdbcTemplate.queryForObject(sql, new RowMapper<UserAdmin>(){
            @Override
            public UserAdmin mapRow(ResultSet arg0, int arg1) throws SQLException {
                UserAdmin user=new UserAdmin();
                user.setId(arg0.getInt("id"));
                user.setUserName(arg0.getString("userName"));
                user.setPassword(arg0.getString("password"));
                user.setLevel(arg0.getInt("level"));
                return user;
            }
        },new Object[]{userName,password});
        logger.info("获取用户成功"+userAdmin.getUserName());
        return userAdmin;}
        catch (Exception es){
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public UserMain loginUserMain(String userName, String password) {
        logger.info("进入loginAdmin方法》》》》》");
        String sql="SELECT * FROM user_main WHERE userName = ? AND `password` = ?";
        String passwordmd5=DigestUtils.md5Hex(password);
        try{
            UserMain userMain=jdbcTemplate.queryForObject(sql, new RowMapper<UserMain>(){
                @Override
                public UserMain mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserMain user=new UserMain();
                    user.setUserId(arg0.getInt("userId"));
                    user.setUserName(arg0.getString("userName"));
                    user.setPassword(arg0.getString("password"));
                    user.setUserPicId(arg0.getInt("userPicId"));
                    user.setRegisterTime(arg0.getDate("registerTime"));
                    return user;
                }
            },new Object[]{userName,passwordmd5});
            logger.info("获取用户成功"+userMain.getUserName());
            return userMain;}
        catch (Exception es){
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public UserMain exsitUserName(String userName) {
        logger.info("进入exsitUserName方法》》》》》");
        String sql ="SELECT * FROM user_main WHERE username = ?";
        try{
            UserMain userMain=jdbcTemplate.queryForObject(sql, new RowMapper<UserMain>(){
                @Override
                public UserMain mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserMain user=new UserMain();
                    user.setUserId(arg0.getInt("userId"));
                    user.setUserName(arg0.getString("userName"));
                    user.setPassword(arg0.getString("password"));
                    user.setUserPicId(arg0.getInt("userPicId"));
                    user.setRegisterTime(arg0.getDate("registerTime"));
                    return user;
                }
            },new Object[]{userName});
            logger.info("获取用户成功"+userMain.getUserName());
            return userMain;}
        catch (Exception es){
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public Integer register(UserMain userMain) {
        logger.info("进入register方法》》》》》");
        String sql ="INSERT INTO user_main ( userName, password, userPicId, registerTime ) VALUES (?,?,?, NOW())";
       String userpassword= DigestUtils.md5Hex( userMain.getPassword());
        int userId=0;
        try{
            userId=sqlUtils.insertSqlAndReturnKeyId(sql,new Object[]{userMain.getUserName(),userpassword,userMain.getUserPicId()});
            return userId;
        }catch (Exception e)
        {
            logger.info("注册失败");
            return userId;
        }


    }

    @Override
    public void insertVerifyCode(String userVerifyCode, String code) {
        String sql="INSERT INTO verify_code ( userVerifyCode,code) VALUES (?,?) ON DUPLICATE KEY UPDATE code=VALUES(code)";
        jdbcTemplate.update(sql,new Object[]{userVerifyCode,code});
    }

    @Override
    public String queryVerifyCode(String userVerifyCode) {
        String sql="SELECT code FROM verify_code WHERE userVerifyCode="+userVerifyCode;
        String code=null;
        Map map=new HashMap();
        map=jdbcTemplate.queryForMap(sql);
        code=map.get("code").toString();
        return code;
    }
}
