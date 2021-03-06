package com.pet.api.model;

import java.io.Serializable;

/**
 * Created by liyut on 2017-12-15.
 */
public class UserAdmin implements Serializable {


        private long id;
        private String userName;
        private String password;
        private Integer level;
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
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
        public Integer getLevel() {
        return level;
    }
         public void setLevel(Integer level) {
        this.level = level;
    }
        @Override
        public String toString() {
            return "User {id=" + id + ", userName=" + userName + ", password=" + password + ", level="+level+"]";
        }

}
