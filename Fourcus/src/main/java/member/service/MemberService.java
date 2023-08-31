package member.service;

import member.dao.MemberDao;
import member.vo.Member;
import tamagotchi.dao.TamagotchiDao;
import tamagotchi.vo.Tamagotchi;
import tamagotchi.vo.Type;

import java.util.Scanner;

public class MemberService {
    private final TamagotchiDao tamagotchiDao;
    private MemberDao dao;
    public static Long loginId;

    public MemberService() {
        dao = new MemberDao();
        loginId = null;
        this.tamagotchiDao = TamagotchiDao.getInstance();
    }

    // 회원가입
    public void join(Scanner sc) {
        if (loginId == null) {
            System.out.println("=====회원 가입=====");
            String username = "";
            String nickname = "";
            Member m = null;
            do {
                System.out.print("ID: ");
                username = sc.next();
                m = dao.select(1, username);
            } while (m != null);
            System.out.print("PASSWORD: ");
            String password = sc.next();
            m = null;
            do {
                System.out.print("닉네임: ");
                nickname = sc.next();
                m = dao.select(2, nickname);
            } while (m != null);
            System.out.print("email: ");
            String email = sc.next();
            System.out.println("1.학생 2.취준생 3.자격증준비");
            System.out.print("카테고리: ");
            Long category_id = sc.nextLong();

            dao.insert(new Member(username, password, nickname, email, category_id));
            tamagotchiDao.insert(
                    Tamagotchi.builder()
                            .id(1) // 사용안함
                            .memberId(1) // 사용안함
                            .tamagotchiName("이름을 바꿔줘욤")
                            .level(1)
                            .type(Type.tree)
                            .build()
                    , username
            );

        } else {
            System.out.println("로그아웃 후 이용해주세요.");
        }
    }

    // 회원 검색
    public void searchMember(Scanner sc) {
        System.out.println("=====회원 검색=====");
        System.out.print("검색할 회원 닉네임: ");
        String nickname = sc.next();
        System.out.println(dao.select(2, nickname));
    }

    // 회원 정보 수정(비밀번호)
    public void updatePwd(Scanner sc) {
        System.out.println("=====비밀번호 변경=====");
        if (loginId != null) {
            Member m = dao.selectById(loginId);
            System.out.print("새로운 비밀번호: ");
            String new_pwd = sc.next();
            System.out.print("비밀번호 확인: ");
            String check_pwd = sc.next();
            if (check_pwd.equals(new_pwd)) {
                dao.updatePwd(new_pwd, loginId);
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(닉네임)
    public void updateNickname(Scanner sc) {
        System.out.println("=====닉네임 변경=====");
        if (loginId != null) {
            Member m = dao.selectById(loginId);
            String nickname = "";
            Member m2 = null;
            do {
                System.out.print("변경할 닉네임: ");
                nickname = sc.next();
                m2 = dao.select(2, nickname);
            } while (m2 != null);

            dao.updateNickname(nickname, loginId);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(칭호)
    public void updateTitle(Scanner sc) {
        System.out.println("=====칭호 변경=====");
        if (loginId != null) {

        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(카테고리)
    public void updateCategory(Scanner sc) {
        if (loginId != null) {
            System.out.println("=====카테고리 변경=====");
            Member m = dao.selectById(loginId);
            System.out.print("1.학생 2.취준생 3.자격증준비");
            Long c_id = sc.nextLong();
            dao.updateCategory(loginId, c_id);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 로그인
    public void Login(Scanner sc) {
        if (loginId == null) {
            System.out.println("=====로그인=====");
            System.out.print("ID: ");
            String username = sc.next();
            System.out.print("PASSWORD: ");
            String password = sc.next();

            Member m = dao.select(1, username);
            if (password.equals(m.getPassword())) {
                loginId = m.getId();
                System.out.println(m.getUsername() + " 로그인 성공");
            } else {
                System.out.println("로그인 실패. 비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("로그인 상태입니다.");
        }
    }

    // 로그아웃
    public void Logout() {
        System.out.println("로그아웃 되었습니다.");
        loginId = null;
    }

    // 회원 탈퇴
    public void delMember() {
        System.out.println("=====회원 탈퇴=====");
        if (loginId != null) {
            dao.delete(loginId);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }
}