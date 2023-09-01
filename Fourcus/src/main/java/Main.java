import group.service.GroupService;
import groupmember.service.GroupMemberService;
import member.service.MemberService;
import studyhour.service.StudyHourService;
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
                    System.out.println("1.마이페이지 2.공부 3.그룹 4.다마고치 5.로그아웃");
                    System.out.print("메뉴를 선택하세요 : ");
                    int select = Integer.parseInt(br.readLine());

                    switch (select) {
                        case 1 -> mypage(br);
                        case 2 -> System.out.println("공부메뉴");
                        case 3 -> group(br);
                        case 4 -> tamagotchi(br);
                        case 5 -> memberService.Logout();
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
        groupService.selectGroup(br);

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

    public static void init() {
        memberService = new MemberService();
        studyHourService = new StudyHourService();
        tamagotchiService = TamagotchiService.getInstance();
        groupService = new GroupService();
        groupMemberService = new GroupMemberService();
        titleService = TitleService.getInstance();
    }

}
