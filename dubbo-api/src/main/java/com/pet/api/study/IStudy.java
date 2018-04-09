package com.pet.api.study;

public interface IStudy {

    public int updateStudyInfo(String userId,String lesson,Integer status,String benefit,Integer reward);

    public void insertStudyRecord(String userId,String lesson,String studyTime);

    public  int updateStudyStatus(String userId,Integer statusNum ,Integer cost);

    public int updateWorkInfo(String userId, Integer reword);

}
