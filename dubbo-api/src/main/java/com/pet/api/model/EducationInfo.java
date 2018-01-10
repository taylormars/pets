package com.pet.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyut on 2017-12-15.
 */
public class EducationInfo implements Serializable {


    private Integer userId;
    private Integer eduId;
    private Integer petId;
    private Integer chinese;
    private Integer math;
    private Integer politics;
    private Integer pe;



    public long getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }


    public Integer getChinese() {
        return chinese;
    }

    public void setChinese(Integer chinese) {
        this.chinese = chinese;
    }

    public Integer getMath() {
        return math;
    }

    public void setMath(Integer petKindId) {
        this.math = math;
    }

    public Integer getPolitics() {
        return politics;
    }

    public void setPolitics(Integer politics) {
        this.politics = politics;
    }

    public Integer getPe() {
        return pe;
    }

    public void setPe(Integer pe) {
        this.pe = pe;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }


    @Override
    public String toString() {
        return "educationinfo {eduId=" + eduId + ", userId=" + userId + ", petId=" + petId + ", chinese=" + chinese + ", math=" + math + ",politics="+politics+",pe="+pe+
                "}";
    }

}
