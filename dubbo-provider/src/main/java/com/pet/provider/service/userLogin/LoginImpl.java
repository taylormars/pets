package com.pet.provider.service.userLogin;

import com.pet.api.model.AdoptInfo;
import com.pet.api.model.EducationInfo;
import com.pet.api.userLogin.LoginService;
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
        String sql = "select username,password from user_main where username = 'lyt'";
        //  String sql="select 1";

        //  Object[] args = new Object[0];
        // int list = jdbcTemplate.queryForInt(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> loan : list) {
                String a = loan.get("username").toString();
                String b = loan.get("password").toString();
                if (a.equals("lyt") && b.equals("123")) {
                    return "登陆成功" + id;
                }

            }
        }
//        if (list > 0) {
//
//                    return "登陆成功"+id;
//
//        }
        return "登陆失败" + id;
    }

    @Override
    public UserAdmin loginAdmin(String userName, String password) {
//        String sql="SELECT * FROM admin_user_main WHERE userName = '"+userName+"' AND `password` = '"+password+"'";
        logger.info("进入loginAdmin方法》》》》》");
        String sql = "SELECT * FROM admin_user_main WHERE userName = ? AND `password` = ?";
        try {
            UserAdmin userAdmin = jdbcTemplate.queryForObject(sql, new RowMapper<UserAdmin>() {
                @Override
                public UserAdmin mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserAdmin user = new UserAdmin();
                    user.setId(arg0.getInt("id"));
                    user.setUserName(arg0.getString("userName"));
                    user.setPassword(arg0.getString("password"));
                    user.setLevel(arg0.getInt("level"));
                    return user;
                }
            }, new Object[]{userName, password});
            logger.info("获取用户成功" + userAdmin.getUserName());
            return userAdmin;
        } catch (Exception es) {
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public UserMain loginUserMain(String userName, String password) {
        logger.info("进入loginUserMain方法》》》》》");
        String sql = "SELECT * FROM user_main WHERE userName = ? AND `password` = ?";
        String passwordmd5 = DigestUtils.md5Hex(password);
        try {
            UserMain userMain = jdbcTemplate.queryForObject(sql, new RowMapper<UserMain>() {
                @Override
                public UserMain mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserMain user = new UserMain();
                    user.setUserId(arg0.getInt("userId"));
                    user.setUserName(arg0.getString("userName"));
                    user.setPassword(arg0.getString("password"));
                    user.setUserPicId(arg0.getInt("userPicId"));
                    user.setRegisterTime(arg0.getDate("registerTime"));
                    return user;
                }
            }, new Object[]{userName, passwordmd5});
            logger.info("获取用户成功" + userMain.getUserName());
            return userMain;
        } catch (Exception es) {
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public UserMain exsitUserName(String userName) {
        logger.info("进入exsitUserName方法》》》》》");
        String sql = "SELECT * FROM user_main WHERE username = ?";
        try {
            UserMain userMain = jdbcTemplate.queryForObject(sql, new RowMapper<UserMain>() {
                @Override
                public UserMain mapRow(ResultSet arg0, int arg1) throws SQLException {
                    UserMain user = new UserMain();
                    user.setUserId(arg0.getInt("userId"));
                    user.setUserName(arg0.getString("userName"));
                    user.setPassword(arg0.getString("password"));
                    user.setUserPicId(arg0.getInt("userPicId"));
                    user.setRegisterTime(arg0.getDate("registerTime"));
                    return user;
                }
            }, new Object[]{userName});
            logger.info("获取用户成功" + userMain.getUserName());
            return userMain;
        } catch (Exception es) {
            logger.info("获取用户失败");
            return null;
        }
    }

    @Override
    public Integer register(UserMain userMain) {
        logger.info("进入register方法》》》》》");
        String sql = "INSERT INTO user_main ( userName, password, userPicId, registerTime ,coin , diamond) VALUES (?,?,?, NOW(),?,?)";
        String userpassword = DigestUtils.md5Hex(userMain.getPassword());
        int userId = 0;
        try {
            userId = sqlUtils.insertSqlAndReturnKeyId(sql, new Object[]{userMain.getUserName(), userpassword, userMain.getUserPicId(), userMain.getCoin(), userMain.getDiamond()});
            return userId;
        } catch (Exception e) {
            logger.info("注册失败");
            return userId;
        }


    }

    @Override
    public void insertVerifyCode(String userVerifyCode, String code) {
        String sql = "INSERT INTO verify_code ( userVerifyCode,code) VALUES (?,?) ON DUPLICATE KEY UPDATE code=VALUES(code)";
        jdbcTemplate.update(sql, new Object[]{userVerifyCode, code});
    }

    @Override
    public String queryVerifyCode(String userVerifyCode) {
        String sql = "SELECT code FROM verify_code WHERE userVerifyCode=" + userVerifyCode;
        String code = null;
        Map map = new HashMap();
        map = jdbcTemplate.queryForMap(sql);
        code = map.get("code").toString();
        return code;
    }


    @Override
    public Map<String, Object> queryLoadingInfo(String userId) {
        String sql = "SELECT IFNULL(ap.petKindId, 0) AS petkindid, um.userName, um.coin, um.diamond, um.userPicId, COUNT(ap.petId) AS isNew ,ap.petStatus ,ap.petNickName FROM adopt ap, user_main um WHERE ap.userId = um.userId AND um.userId = ?";
        Map map = new HashMap();
        try {
            map = jdbcTemplate.queryForMap(sql, new Object[]{userId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryAdoptInfo(String userId) {
        String sql = "SELECT * FROM adopt WHERE userId =?";
        Map map = new HashMap();
        try {
            map = jdbcTemplate.queryForMap(sql, new Object[]{userId});
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
        return map;
    }

    @Override
    public Integer insertAdoptInfo(AdoptInfo adoptInfo) {
        String sql = "INSERT INTO adopt ( userId, petNickName, years, adoptTime, petKindId, petStatus ) VALUES (?,?,0, NOW() ,?,0)";
        int adoptId = 0;
        try {
            adoptId = sqlUtils.insertSqlAndReturnKeyId(sql, new Object[]{adoptInfo.getUserId(), adoptInfo.getPetNickName(), adoptInfo.getPetKindId()});
            return adoptId;
        } catch (Exception e) {
            logger.info("注册失败");
            return adoptId;
        }
    }

    @Override
    public Integer insertEducationInfo(EducationInfo educationInfo) {
        String sql = "INSERT INTO education_info(userId,petId) VALUES(?,?)";
        int educationId = 0;
        try {
            educationId = sqlUtils.insertSqlAndReturnKeyId(sql, new Object[]{educationInfo.getUserId(), educationInfo.getPetId()});
            return educationId;
        } catch (Exception e) {
            logger.info("注册失败");
            return educationId;
        }
    }

    @Override
    public Integer inserLoginRecord(String userId, String ip) {
        String sql = "INSERT INTO login_record (userId,ip,loginTime) VALUES(?,?,NOW())";
        int loginRecord = 0;
        try {
            loginRecord = sqlUtils.insertSqlAndReturnKeyId(sql, new Object[]{userId, ip});
            return loginRecord;
        } catch (Exception e) {
            logger.info("注册失败");
            return loginRecord;
        }
    }
}
