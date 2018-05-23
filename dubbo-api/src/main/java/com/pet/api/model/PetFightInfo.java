package com.pet.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 11:27 2018-05-21
 * @CreateBY : idea
 */
public class PetFightInfo implements Serializable {
    private String  userId;
    private Integer speed;
    private Integer hp;
    private Integer attack;
    private Integer skill;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSpeed () {
        return speed ;
    }

    public void setSpeed (Integer speed ) {
        this.speed  = speed ;
    }

    public Integer getHp () { return hp ; }

    public void setHp(Integer hp ) {
        this.hp  = hp ;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
        this.skill = skill;
    }


    @Override
    public String toString() {
        return "petfightinfo {userId=" + userId + ", speed=" + speed + ", hp=" + hp + ", attack=" + attack + ", skill=" + skill +
                "}";
    }



}
