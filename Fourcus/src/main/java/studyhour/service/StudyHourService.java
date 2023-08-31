package studyhour.service;

import member.service.MemberService;
import studyhour.dao.StudyHourDao;
import subject.dao.Subjectdao;
import subject.service.SubjectService;
import subject.vo.Subject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StudyHourService {
    private StudyHourDao shDao;
    private Subjectdao subjectdao;
    private static Long subjectId;


    public StudyHourService() {
        subjectdao = new Subjectdao();
        shDao = new StudyHourDao();
        subjectId = null;
    }


    // 시작 버튼 ( 첫 시작 insert or 재시작 update)
    public void addStart(Scanner sc) {
        // 과목 리스트 보여주기
        List<Subject> list = subjectdao.selectAll(MemberService.loginId);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " " + list.get(i));
        }
        // 과목 번호 가져오기
        System.out.println("공부할 과목 번호를 입력하세요.");
        int num = sc.nextInt();
        // num <0 일떄도 예외처리
        if (num > list.size() - 1) {
            System.out.println("잘못 입력하셨습니다.");
        } else {
            subjectId = list.get(num).getId();
            LocalDateTime currentTime = LocalDateTime.now();

            if (Objects.nonNull(shDao.selectStartTime(subjectId))) {
                System.out.println("공부를 재시작합니다.");
                shDao.reStart(currentTime, subjectId);
                System.out.println("재시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
            } else {
                System.out.println("공부를 시작합니다.");
                shDao.start(currentTime, subjectId);
                System.out.println("시작 시간 : " + java.sql.Timestamp.valueOf(currentTime));
            }
        }
    }


    // 종료 버튼 클릭 => 누적시간 += 종료-시작 , 종료-시작을 max 값과 비교, 날짜 비교하고 마지막에 subjectId=null
    public void clickStop() {
        LocalDateTime currentTime = LocalDateTime.now();

        // 종료시간 update
        shDao.stop(currentTime, subjectId);
        System.out.println("종료 시간 : " + java.sql.Timestamp.valueOf(currentTime));

        // 날짜 비교 => 시작 시간 가져와서 비교하기
        LocalDate ldStartTime = shDao.selectStartTime(subjectId).toLocalDate();
        LocalDate ldStopTime = currentTime.toLocalDate();

        // 같은 날짜 = start or restart 인지 확인해서
        if (ldStartTime == ldStopTime) {
            // restartTime == null 이면 첫 시작이라는 말이니까 selectStartTime
            if (shDao.selectRestartTime(subjectId) == null) {
                // 공부 시간 계산 ( 초로 저장 )
                Duration du = Duration.between(currentTime, shDao.selectStartTime(subjectId));
                long studyTime = du.toSeconds();

                // 누적 시간 업데이트
                long newCumulativeTime = studyTime + shDao.selectCumulativeTime(subjectId);
                shDao.updateCumulativeTime(newCumulativeTime, subjectId);

                // 최대 집중 시간 비교
                if (studyTime >= shDao.selectMax(subjectId)) {
                    shDao.updateMax(studyTime, subjectId);
                }
            } else {
                // 공부 시간 계산 ( 초로 저장 )
                Duration du = Duration.between(currentTime, shDao.selectRestartTime(subjectId));
                long studyTime = du.toSeconds();

                // 누적 시간 업데이트
                long newCumulativeTime = studyTime + shDao.selectCumulativeTime(subjectId);
                shDao.updateCumulativeTime(newCumulativeTime, subjectId);

                // 최대 집중 시간 비교
                if (studyTime >= shDao.selectMax(subjectId)) {
                    shDao.updateMax(studyTime, subjectId);
                }
            }
            // 과목 아이디 리셋
        //    shDao.stop(null, subjectId); 종료시간은 업데이트하니까 안해도 될 듯 .. ?
            subjectId = null;


        } else {  // 시작 버튼 누른 날짜와 종료 버튼 누른 날짜가 다름 => 0시를 기준으로 나눠서 저장해야함
            // 0시 standardTime 설정
            // endLocalDate 에 restartTime 의 날짜를 가져오지 않아도 괜찮음 !
            LocalDate endLocalDate = ldStartTime.plusDays(1);
            LocalTime endLocalTime = LocalTime.of(0, 0, 0);
            LocalDateTime standardTime = LocalDateTime.of(endLocalDate, endLocalTime);

            // 첫 start 버튼 누름
            if (shDao.selectRestartTime(subjectId) == null) {
                // 오늘까지의 공부시간 저장 ( 0시 - 시작시간 )
                Duration todayDu = Duration.between(standardTime, shDao.selectStartTime(subjectId));
                long todayStudyTime = Math.abs(todayDu.toSeconds()); // 절대값

                // 누적 시간 업데이트
                long newCumulativeTime = todayStudyTime + shDao.selectCumulativeTime(subjectId);
                shDao.updateCumulativeTime(todayStudyTime, subjectId);

                // 최대 집중 시간 비교
                if (todayStudyTime >= shDao.selectMax(subjectId)) {
                    shDao.updateMax(todayStudyTime, subjectId);
                }

                // 0시 지난 시간 다음 날에 저장
                Duration nextDu = Duration.between(currentTime, standardTime);
                long nextStudyTime = Math.abs(nextDu.toSeconds());

                // insert로 새로운 날짜에 시작시간, 누적시간, 종료시간 저장
                shDao.start(standardTime, subjectId);

            }


/*
        // studyTime으로 CumulativeTime update하기
        Duration du = Duration.between(shDao.selectStopTime(subjectId), shDao.selectStartTime(subjectId));
        long studyTime = du.toSeconds();
        shDao.updateCumulativeTime(studyTime, subjectId);

        // MaxFocusTime 비교해서 크면 변경 작으면 변경x
        if(studyTime >= shDao.selectMax(subjectId)){
            shDao.updateMax(studyTime,subjectId);
        }*/
        }


        // 공부 종료 버튼 => 종료 - 시작 해서 공부 시간 출력 + 저장


        //


    }
}