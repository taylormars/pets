package com.pet.api.friends;

import java.util.List;
import java.util.Map;

public interface IFriends {

    List<Map<String,Object>> queryFriendsListByuserId(String  userId);

   Integer exsitPetNickName(String petNickName);

    Integer updateFriends(String userId, String FriendsId);

    Integer deleteFriend(String userId, String  friendId);


}
