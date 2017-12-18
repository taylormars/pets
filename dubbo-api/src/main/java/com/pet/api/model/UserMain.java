package com.pet.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyut on 2017-12-15.
 */
public class UserMain implements Serializable {


        private long userId;
        private String userName;
        private String password;
        private Integer userPicId;
        private Date registerTime;
        public long getUserId() {
            return userId;
        }
        public void setUserId(long userId) {
            this.userId = userId;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public Integer getUserPicId() {
        return userPicId;
    }
         public void setUserPicId(Integer userPicId) {
        this.userPicId = userPicId;
    }
        public Date getRegisterTime(){return registerTime;}
        public void setRegisterTime(Date registerTime){this.registerTime=registerTime;}
        @Override
        public String toString() {
            return "User {userId=" + userId + ", userName=" + userName + ", password=" + password + ", userPicId="+userPicId+", registerTime="+registerTime+"}";
        }

}
