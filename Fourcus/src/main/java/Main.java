import group.service.GroupService;
import groupmember.service.GroupMemberService;
import member.service.MemberService;
import studyhour.service.StudyHourService;
import subject.service.SubjectService;
import tamagotchi.service.TamagotchiService;
import title.service.TitleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {
    static MemberService memberService;
    static StudyHourService studyHourService;
    static TamagotchiService tamagotchiService;
    static GroupService groupService;
    static GroupMemberService groupMemberService;
    static TitleService titleService;
    static SubjectService subjectService;
    static boolean bl;

    public static void main(String[] args) throws IllegalStateException {
        init();
        try (BufferedReader br  = new BufferedReader(new InputStreamReader(System.in))) {
            boolean flag = true;

            while (flag) {
                if (Objects.isNull(MemberService.loginId)) { //비로그인 상태
                    System.out.println("1.회원가입 2.로그인 3.종료");
                    System.out.print("메뉴를 선택하세요 : ");
                    int select = Integer.parseInt(br.readLine());

                    switch (select) {
                        case 1 -> memberService.join(br);
                        case 2 -> memberService.Login(br);
                        case 3 -> {
                            System.out.println("프로그램 종료");
                            flag = false;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + select);
                    }

                } else { //로그인 상태
                    System.out.println("1.마이페이지 2.공부 3.그룹 4.다마고치 5.로그아웃 6.회원탈퇴");
                    System.out.print("메뉴를 선택하세요 : ");
                    int select = Integer.parseInt(br.readLine());

                    switch (select) {
                        case 1 -> mypage(br);
                        case 2 -> study(br);
                        case 3 -> group(br);
                        case 4 -> tamagotchi(br);
                        case 5 -> memberService.Logout();
                        case 6 -> memberService.delMember();
                        default -> throw new IllegalStateException("Unexpected value: " + select);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mypage(BufferedReader br) throws IOException {
        System.out.println("마이페이지");
        System.out.println("1.닉네임 변경 2.비밀번호 변경 3.칭호 4.정보확인");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> memberService.updateNickname(br);
            case 2 -> memberService.updatePwd(br);
            case 3 -> title(br);
            case 4 -> memberService.printMember();
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void title(BufferedReader br) throws IOException {
        System.out.println("칭호");
        System.out.println("1.현재 보유중인 칭호  2.칭호 번경");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> titleService.getTitleList();
            case 2 -> titleService.selectTitle(br);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void group(BufferedReader br) throws IOException{
        System.out.println("그룹");
        System.out.println("1.그룹 생성 2.그룹 검색 3.그룹 관리");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> groupService.createGroup(br);
            case 2 -> groupService.searchGroup(br);
            case 3 -> groupManage(br);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }
    
    public static void groupManage(BufferedReader br) throws IOException{
        System.out.println("그룹 관리");
        boolean bl = groupService.selectGroup(br);

        if(bl) {

            System.out.println("1.그룹명 수정 2.공지사항 3.그룹원 관리 4.그룹 삭제");
            System.out.print("메뉴를 선택하세요 : ");
            int select = Integer.parseInt(br.readLine());

            switch (select) {
                case 1 -> groupService.updateGroupName(br);
                case 2 -> groupService.Notice(br);
                case 3 -> groupMemberManage(br);
                case 4 -> groupService.deleteGroup(br);
                default -> throw new IllegalStateException("Unexpected value: " + select);
            }
        } else{
            group(br);
        }
    }

    public static void groupMemberManage(BufferedReader br) throws IOException {
        System.out.println("그룹원 관리");

        System.out.println("1.그룹원 확인 2.그룹원 프로필 3.그룹장 메뉴");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());

        switch (select){
            case 1 -> groupMemberService.printMyGroupMember();
            case 2 -> groupMemberService.printMyGroupMemberProfile(br);
            case 3 -> groupMasterMenu(br);
        }
    }
    public static void groupMasterMenu(BufferedReader br) throws IOException{
        System.out.println("그룹장 메뉴");

        System.out.println("1.그룹멤버 추가 2. 그룹멤버 추방");
        int select = Integer.parseInt(br.readLine());

        switch (select){
            case 1 -> groupMemberService.addGroupMember(br);
            case 2 -> groupMemberService.delGroupMember(br);
        }
    }

    public static void tamagotchi(BufferedReader br) throws IOException {
        System.out.println("다마고치");
        System.out.println("1.상태보기  2.이름 변경  3.타입 변경");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> tamagotchiService.getTamagotchi();
            case 2 -> tamagotchiService.nickNameChange(br);
            case 3 -> tamagotchiService.typeChange(br);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void study(BufferedReader br) throws IOException {
        System.out.println("공부");
        System.out.println("1.과목 관리  2.공부  3.누적 시간  4.최대 집중 시간");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> subject(br);
            case 2 -> studyMenu(br);
            case 3 -> studyHourService.printCumulativeTime(br);
            case 4 -> studyHourService.printTodayMaxTime();
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void studyMenu(BufferedReader br) throws IOException {
        System.out.println("공부메뉴");
        System.out.println("1.공부시작  2.공부종료");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> {
                if(bl){
                    System.out.println("먼저 공부를 종료하세요.");
                } else {
                    studyHourService.addStart(br);
                    bl = true;
                }
            }
            case 2 -> {
                studyHourService.clickStop();
                bl = false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void subject(BufferedReader br) throws IOException {
        System.out.println("과목");
        System.out.println("1.과목 목록  2.과목 추가  3.과목 수정  4.과목 삭제");
        System.out.print("메뉴를 선택하세요 : ");
        int select = Integer.parseInt(br.readLine());
        switch (select) {
            case 1 -> subjectService.printAll();
            case 2 -> subjectService.insertSubject(br);
            case 3 -> subjectService.editSubjectName(br);
            case 4 -> subjectService.delSubject(br);
        }
    }

    public static void init() {
        memberService = new MemberService();
        studyHourService = new StudyHourService();
        tamagotchiService = TamagotchiService.getInstance();
        groupService = new GroupService();
        groupMemberService = new GroupMemberService();
        titleService = TitleService.getInstance();
        subjectService = new SubjectService();
        bl = false;
    }

}
