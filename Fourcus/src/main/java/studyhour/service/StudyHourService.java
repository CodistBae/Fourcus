package studyhour.service;

import member.service.MemberService;
import studyhour.dao.StudyHourDao;
import studyhour.vo.StudyHour;
import subject.dao.Subjectdao;
import subject.service.SubjectService;
import subject.vo.Subject;
import tamagotchi.service.TamagotchiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class StudyHourService {
    private StudyHourDao shDao;
    private Subjectdao subjectdao;
    private static Long subjectId;
    private static String today;
    private Subjectdao subjectDao;
    private TamagotchiService tamagotchiService;


    public StudyHourService() {
        subjectdao = new Subjectdao();
        shDao = new StudyHourDao();
        subjectDao = new Subjectdao();
        subjectId = null;
        today = null;
        tamagotchiService = TamagotchiService.getInstance();

    }


    // 시작 버튼 ( 첫 시작 insert or 재시작 update)
    public void addStart(BufferedReader br) throws IOException {
        // 과목 리스트 보여주기
        List<Subject> list = subjectdao.selectAll(MemberService.loginId);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " " + list.get(i));
        }
        // 과목 번호 가져오기
        System.out.println("공부할 과목 번호를 입력하세요.");
        int num = Integer.parseInt(br.readLine());
        // num <0 일떄도 예외처리
        if (num < 0 || num > list.size() - 1) {
            System.out.println("잘못 입력하셨습니다.");
        } else {
            subjectId = list.get(num).getId();
            LocalDateTime now = LocalDateTime.now().withNano(0);
            Time currentTime = Time.valueOf(now.toLocalTime());
            today = String.valueOf(now).split("T")[0];

            if (Objects.nonNull(shDao.selectStartTime(today, subjectId))) {
                System.out.println("공부를 재시작합니다.");
                shDao.reStart(currentTime, today, subjectId);
                System.out.println("재시작 시간 : " + currentTime);
            } else {
                System.out.println("공부를 시작합니다.");
                shDao.start(currentTime, today, subjectId);
                System.out.println("시작 시간 : " + currentTime);
            }
        }


    }


    // 종료 버튼 클릭 => 누적시간 += 종료-시작 , 종료-시작을 max 값과 비교, 마지막에 subjectId=null
    public void clickStop() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Time currentTime = Time.valueOf(now.toLocalTime());

        // stopTime == null 이면 첫 시작이라는 말이니까 selectStartTime
        if (shDao.selectStopTime(today, subjectId) == null) {
            // 공부 시간 계산 ( 초로 저장 )
            shDao.stop(currentTime, today, subjectId);
            System.out.println("종료 시간 : " + now.toLocalTime());
            Duration du = Duration.between(now.toLocalTime(), shDao.selectStartTime(today, subjectId).toLocalTime());
            long studyTime = Math.abs(du.toSeconds());
            System.out.print("공부 시간은 ");
            shDao.changeSec(studyTime);
            System.out.println(" 입니다.");

            // 누적 시간 업데이트
            long newCumulativeTime = studyTime + shDao.selectCumulativeTime(today, subjectId);
            shDao.updateCumulativeTime(newCumulativeTime, today, subjectId);

            // 최대 집중 시간 비교
            if (studyTime >= shDao.selectMax(today, subjectId)) {
                shDao.updateMax(studyTime, today, subjectId);
            }
        } else {
            // 종료시간 업데이트
            shDao.stop(currentTime, today, subjectId);
            System.out.println("종료 시간 : " + now.toLocalTime());

            // 재시작한 경우
            Duration du = Duration.between(now.toLocalTime(), shDao.selectRestartTime(today, subjectId).toLocalTime());
            long studyTime = Math.abs(du.toSeconds());
            System.out.print("공부 시간은 ");
            shDao.changeSec(studyTime);
            System.out.println(" 입니다.");

            // 공부 시간을 담아서 MAX 에서 그 값들을 비교해야함


            // 누적 시간 업데이트
            long newCumulativeTime = studyTime + shDao.selectCumulativeTime(today, subjectId);
            shDao.updateCumulativeTime(newCumulativeTime, today, subjectId);

            // 최대 집중 시간 비교
            if (studyTime >= shDao.selectMax(today, subjectId)) {
                shDao.updateMax(studyTime, today, subjectId);
            }
        }
        // 과목 아이디 리셋
        //    shDao.stop(null, subjectId); 종료시간은 업데이트하니까 안해도 될 듯 .. ?
        subjectId = null;
        tamagotchiService.levelUpdate();

    }

    // 누적시간 날짜별 + 과목별 가져오기
    public void printCumulativeTime(BufferedReader br) throws IOException {
        if (today == null) {
            today = String.valueOf(LocalDateTime.now().toLocalDate());
        }
        List<Subject> list = subjectdao.selectAll(MemberService.loginId);
        if (list.isEmpty()) {
            System.out.println("오늘 공부시간이 없습니다.");
        } else {
            System.out.println("====오늘 " + today + " 과목별 누적시간====");
            // 과목 리스트 보여주기
            System.out.println("====과목 리스트====");

            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + " " + list.get(i));
            }
            // 과목 번호 가져오기
            System.out.println(" 과목 번호를 입력하세요.");
            int num = Integer.parseInt(br.readLine());
            if (num < 0 || num > list.size() - 1) {
                System.out.println("잘못 입력하셨습니다.");
            } else {
                Long id = list.get(num).getId();
                String subjectName = subjectDao.select(id).getSubjectName();
                long cumTime = shDao.selectCumulativeTime(today, id);
                System.out.println("====누적 시간====");
                System.out.print(today + " " + subjectName + "의 누적 시간은 ");
                shDao.changeSec(cumTime);
                System.out.println(" 입니다.");
            }
        }


    }

//    // 누적시간 과목별 가져오기
//    public void printSubjectCumulativeTime(Scanner sc) {
//        System.out.println("====과목별 누적시간====");
//        // 과목 리스트 보여주기
//        System.out.println("====과목 리스트====");
//        List<Subject> list = subjectdao.selectAll(MemberService.loginId);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(i + " " + list.get(i));
//        }
//        // 과목 번호 가져오기
//        System.out.println(" 과목 번호를 입력하세요.");
//        int num = sc.nextInt();
//        if (num < 0 || num > list.size() - 1) {
//            System.out.println("잘못 입력하셨습니다.");
//        } else {
//            long id = list.get(num).getId();
//            String subjectName = subjectDao.select(id).getSubjectName();
//            ArrayList<Long> list2 = shDao.selectSubjectCumulativeTime(id);
//            long cumulativeTime = 0;
//            for (int i = 0; i < list2.size(); i++) {
//                cumulativeTime += list2.get(i);
//            }
//            System.out.print(subjectName + "의 총 누적 시간은 ");
//            shDao.changeSec(cumulativeTime);
//            System.out.println(" 입니다.");
//        }
//    }

//    // 최대 집중 시간 날짜별 + 과목별 가져오기
//    public void printMaxTime() {
//        System.out.println("====오늘 " + today + " 과목별 최대 집중 시간====");
//
//        String subjectName = subjectDao.select(subjectId).getSubjectName();
//        long max =shDao.selectMax(today,subjectId);
//        System.out.print(today+ " " + subjectName + " 의 최대 집중 시간은 ");
//        shDao.changeSec(max);
//        System.out.print(" 입니다.");
//    }

    // 오늘 최대 집중 시간
    public void printTodayMaxTime() {
        if (today == null) {
            today = String.valueOf(LocalDateTime.now().toLocalDate());
        }
        ArrayList<Long> list = shDao.selectMaxAll(today, MemberService.loginId);
        if (list.isEmpty()) {
            System.out.println("오늘 공부시간이 없습니다.");
        } else {
            long max = Collections.max(list);
            System.out.println("====최대 집중 시간====");
            System.out.print(today + " 최대 집중 시간은 ");
            shDao.changeSec(max);
            System.out.println(" 입니다");
        }
    }
}

