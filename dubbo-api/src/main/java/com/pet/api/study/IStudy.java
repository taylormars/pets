package com.pet.api.study;

public interface IStudy {

    public int updateStudyInfo(String userId,String lesson,Integer status);

    public void insertStudyRecord(String userId,String lesson,String studyTime);

    public  int updateStudyStatus(String userId,Integer statusNum );

}
