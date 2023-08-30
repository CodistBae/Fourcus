package studyhour.service;

import studyhour.dao.StudyHourDao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StudyHourService {
    private StudyHourDao shDao;

    public StudyHourService() {
        shDao = new StudyHourDao();
    }


    // 시작 버튼 ( 첫 시작 insert or 재시작 update)
    public void addReStart(long subjectId) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (shDao.selectStartTime(subjectId) == null) {
            System.out.println("공부를 시작합니다.");
            shDao.start(currentTime, subjectId);
            System.out.println("시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
        } else {
            System.out.println("공부를 재시작합니다.");
            shDao.reStart(currentTime, subjectId);
            System.out.println("재시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
        }
    }


    // 첫 공부 시간 측정
    public void clickStop(long subjectId){
        LocalDateTime currentTime = LocalDateTime.now();

        // 종료시간 update
        shDao.stop(currentTime,subjectId);

        // studyTime으로 CumulativeTime update하기
        Duration du = Duration.between(shDao.selectStopTime(subjectId), shDao.selectStartTime(subjectId));
        long studyTime = du.toSeconds();
        shDao.updateCumulativeTime(studyTime, subjectId);

        // MaxFocusTime 비교해서 크면 변경 작으면 변경x
        if(studyTime >= shDao.selectMax(subjectId)){
            shDao.updateMax(studyTime,subjectId);
        }
    }


    // 공부 종료 버튼 => 종료 - 시작 해서 공부 시간 출력 + 저장




    //


}
