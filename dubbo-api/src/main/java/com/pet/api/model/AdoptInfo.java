package com.pet.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyut on 2017-12-15.
 */
public class AdoptInfo implements Serializable {


    private long userId;
    private String petNickName;
    private Integer petId;
    private Date adoptTime;
    private Integer petKindId;
    private Integer petStatus;
    private Integer years;
    private Integer level;
    private Integer growth;
    private Integer hunger;
    private Integer clean;
    private Integer health;
    private Integer mood;
    private Integer education;
    private Integer tired;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPetNickName() {
        return petNickName;
    }

    public void setPetNickName(String petNickName) {
        this.petNickName = petNickName;
    }


    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Date getAdoptTime() {
        return adoptTime;
    }

    public void setAdoptTime(Date registerTime) {
        this.adoptTime = adoptTime;
    }

    public Integer getPetKindId() {
        return petKindId;
    }

    public void setPetKindId(Integer petKindId) {
        this.petKindId = petKindId;
    }

    public Integer getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(Integer petStatus) {
        this.petStatus = petStatus;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Integer getHunger() {
        return hunger;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public Integer getClean() {
        return clean;
    }

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }
    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }
    public Integer getTired() {
        return tired;
    }

    public void setTired(Integer years) {
        this.tired = tired;
    }




    @Override
    public String toString() {
        return "adoptInfo {petId=" + petId + ", userId=" + userId + ", petNiceName=" + petNickName + ", years=" + years + ", adoptTime=" + adoptTime + ",petKindId="+petKindId+",petStatus="+petStatus+",level="+level+",growth="+growth+",hunger="+hunger+",clean="+clean+",health="+health+",mood="+mood+",education="+education+",tired="+tired+
                "}";
    }

}
