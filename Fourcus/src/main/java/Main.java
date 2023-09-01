import groupmember.service.GroupMemberService;
import member.service.MemberService;
import studyhour.service.StudyHourService;
import tamagotchi.service.TamagotchiService;
import title.service.TitleService;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    static MemberService memberService;
    static StudyHourService studyHourService;
    static TamagotchiService tamagotchiService;
    static GroupMemberService groupMemberService;
    static TitleService titleService;

    public static void main(String[] args) throws IllegalStateException {
        init();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            if (Objects.isNull(MemberService.loginId)) { //비로그인 상태
                System.out.println("1.회원가입 2.로그인 3.종료");
                System.out.print("메뉴를 선택하세요 : ");
                int select = sc.nextInt();

                switch (select) {
                    case 1 -> memberService.join(sc);
                    case 2 -> memberService.Login(sc);
                    case 3 -> {
                        System.out.println("프로그램 종료");
                        flag = false;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + select);
                }

            } else { //로그인 상태
                System.out.println("1.마이페이지 2.공부 3.그룹 4.다마고치 5.로그아웃");
                System.out.print("메뉴를 선택하세요 : ");
                int select = sc.nextInt();

                switch (select) {
                    case 1 -> mypage(sc);
                    case 2 -> System.out.println("공부메뉴");
                    case 3 -> System.out.println("그룹메뉴");
                    case 4 -> tamagotchi(sc);
                    case 5 -> memberService.Logout();
                    default -> throw new IllegalStateException("Unexpected value: " + select);
                }
            }
        }
    }

    public static void mypage(Scanner sc) {
        System.out.println("1.닉네임 변경 2.비밀번호 변경 3.칭호 4.카테고리 변경");
        System.out.print("메뉴를 선택하세요 : ");
        int select = sc.nextInt();
        switch (select) {
            case 1 -> memberService.updateNickname(sc);
            case 2 -> memberService.updatePwd(sc);
            case 3 -> title(sc);
            case 4 -> memberService.updateCategory(sc);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void title(Scanner sc) {
        System.out.println("칭호");
        System.out.println("1.현재 보유중인 칭호  2.칭호 번경");
        System.out.print("메뉴를 선택하세요 : ");
        int select = sc.nextInt();
        switch (select) {
            case 1 -> titleService.getTitleList();
            case 2 -> titleService.selectTitle(sc);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void tamagotchi(Scanner sc) {
        System.out.println("다마고치");
        System.out.println("1.상태보기  2.이름 변경  3.타입 변경");
        System.out.print("메뉴를 선택하세요 : ");
        int select = sc.nextInt();
        switch (select) {
            case 1 -> tamagotchiService.getTamagotchi();
            case 2 -> tamagotchiService.nickNameChange(sc);
            case 3 -> tamagotchiService.typeChange(sc);
            default -> throw new IllegalStateException("Unexpected value: " + select);
        }
    }

    public static void init() {
        memberService = new MemberService();
        studyHourService = new StudyHourService();
        tamagotchiService = new TamagotchiService();
        groupMemberService = new GroupMemberService();
        titleService = new TitleService();
    }

}
