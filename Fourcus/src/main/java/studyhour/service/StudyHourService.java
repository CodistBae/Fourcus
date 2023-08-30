package studyhour.service;

import studyhour.dao.StudyHourDao;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StudyHourService {
    private StudyHourDao shDao;

    public StudyHourService() {
        shDao = new StudyHourDao();
    }

    // 공부 시작 버튼
    public void addStart(long subjectId){
        LocalDateTime currentTime = LocalDateTime.now();
        shDao.start(currentTime, subjectId);
        System.out.println("시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
    }

    // 공부 종료 버튼 => 종료 - 시작 해서 공부 시간 출력 + 저장

    // 공부 시간 측정
    public void clickStop(long subjectId){
        LocalTime startLocalTime = shDao.selectStartTime(subjectId).toLocalTime();
        LocalTime stopLocalTime = shDao.selectStopTime(subjectId).toLocalTime();




    }

    // 재시작 버튼
    public void addReStart(long subjectId){
        LocalDateTime currentTime = LocalDateTime.now();
        shDao.start(currentTime, subjectId);
        System.out.println("재시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
    }
    //


    //


}
