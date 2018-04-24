package com.pet.provider.service.friends;

import com.pet.api.friends.IFriends;
import com.pet.api.other.IOther;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("friends")
public class FriendsServiceImpl implements IFriends{
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryFriendsListByuserId(String userId) {
        String sql = "SELECT fr.friendUserId, ad.petNickName,ad.adoptTime, ad.note FROM friend_relationship AS fr LEFT JOIN adopt AS ad ON fr.friendUserId = ad.userId WHERE fr.userId = "+userId;
        try {
            List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer exsitPetNickName(String petNickName) {
        String sql = "SELECT userId FROM adopt WHERE petNickName = '"+petNickName+"'";
        try {
            Map<String, Object> petmap = jdbcTemplate.queryForMap(sql);
                return (Integer) petmap.get("userId");
        } catch (Exception e) {
            return -1;
        }

    }

    @Override
    public Integer updateFriends(String userId, String friendsId) {
        String sql = "INSERT INTO friend_relationship (userId,friendUserId) VALUES(?,?)";
        try {
            jdbcTemplate.update(sql, new Object[]{userId, friendsId});
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer deleteFriend(String userId, String friendId) {
        String sql = "DELETE FROM friend_relationship WHERE userId = ? AND friendUserId = ? ";
        try {
            jdbcTemplate.update(sql, new Object[]{userId, friendId});
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
