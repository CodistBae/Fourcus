package member.service;

import group.dao.GroupDao;
import group.service.GroupService;
import groupmember.dao.GroupMemberDao;
import groupmember.vo.GroupMember;
import member.dao.MemberDao;
import member.vo.Member;
import tamagotchi.dao.TamagotchiDao;
import tamagotchi.vo.Tamagotchi;
import tamagotchi.vo.Type;
import title.service.TitleService;

import java.io.BufferedReader;
import java.io.IOException;

public class MemberService {
    private final TamagotchiDao tamagotchiDao;
    private MemberDao mdao;
    private GroupDao gdao;
    private GroupMemberDao gmdao;
    public static Long loginId;
    public TitleService titleService;

    public MemberService() {
        mdao = MemberDao.getInstance();
        gdao = GroupDao.getInstance();
        gmdao = GroupMemberDao.getInstance();
        loginId = null;
        this.tamagotchiDao = TamagotchiDao.getInstance();
        this.titleService = TitleService.getInstance();
    }

    // 회원가입
    public void join(BufferedReader br) throws IOException {
        if (loginId == null) {
            System.out.println("=====회원 가입=====");
            String username = "";
            String nickname = "";
            Member m = null;
            do {
                System.out.print("ID: ");
                username = br.readLine();
                m = mdao.select(1, username);
            } while (m != null);
            System.out.print("PASSWORD: ");
            String password = br.readLine();
            m = null;
            do {
                System.out.print("닉네임: ");
                nickname = br.readLine();
                m = mdao.select(2, nickname);
            } while (m != null);
            System.out.print("email: ");
            String email = br.readLine();
            System.out.println("1.학생 2.취준생 3.자격증준비");
            System.out.print("카테고리: ");
            Long category_id = Long.valueOf(br.readLine());

            mdao.insert(new Member(username, password, nickname, email, category_id));
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
    public void searchMember(BufferedReader br) throws IOException {
        System.out.println("=====회원 검색=====");
        System.out.print("검색할 회원 닉네임: ");
        String nickname = br.readLine();
        System.out.println(mdao.select(2, nickname));
    }

    // 회원 정보 수정(비밀번호)
    public void updatePwd(BufferedReader br) throws IOException {
        System.out.println("=====비밀번호 변경=====");
        if (loginId != null) {
            Member m = mdao.selectById(loginId);
            System.out.print("새로운 비밀번호: ");
            String new_pwd = br.readLine();
            System.out.print("비밀번호 확인: ");
            String check_pwd = br.readLine();
            if (check_pwd.equals(new_pwd)) {
                mdao.updatePwd(new_pwd, loginId);
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(닉네임)
    public void updateNickname(BufferedReader br) throws IOException {
        System.out.println("=====닉네임 변경=====");
        if (loginId != null) {
            Member m = mdao.selectById(loginId);
            String nickname = "";
            Member m2 = null;
            do {
                System.out.print("변경할 닉네임: ");
                nickname = br.readLine();
                m2 = mdao.select(2, nickname);
            } while (m2 != null);

            mdao.updateNickname(nickname, loginId);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(칭호)
    public void updateTitle(BufferedReader br) {
        System.out.println("=====칭호 변경=====");
        if (loginId != null) {

        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 회원 정보 수정(카테고리)
    public void updateCategory(BufferedReader br) throws IOException {
        if (loginId != null) {
            System.out.println("=====카테고리 변경=====");
            Member m = mdao.selectById(loginId);
            System.out.print("1.학생 2.취준생 3.자격증준비");
            Long c_id = Long.valueOf(br.readLine());
            mdao.updateCategory(loginId, c_id);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 로그인
    public boolean Login(BufferedReader br) throws IOException {
        if (loginId == null) {
            System.out.println("=====로그인=====");
            System.out.print("ID: ");
            String username = br.readLine();
            System.out.print("PASSWORD: ");
            String password = br.readLine();

            Member m = mdao.select(1, username);
            if(m==null){
                System.out.println(username+"이 존재하지 않습니다.");
                return false;
            }
            if (password.equals(m.getPassword())) {
                loginId = m.getId();
                System.out.println(m.getUsername() + " 로그인 성공");
                return true;
            } else {
                System.out.println("로그인 실패. 비밀번호가 일치하지 않습니다.");
                return false;
            }
        } else {
            System.out.println("로그인 상태입니다.");
            return true;
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
            if(gdao.selectAll(loginId).size() != 0){
                System.out.println("내가 그룹장인 그룹 존재. 탈퇴 불가능");
            } else {
//                gmdao.delete(loginId, GroupService.groupId);
                gmdao.delete(loginId);
                mdao.delete(loginId);
                loginId = null;
            }
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    public void printMember(){
        System.out.println("=====내 정보 확인=====");
        String title = titleService.getTitle();// 타이틀 가공해주세요.!!
        Member member = mdao.selectById(loginId);
        System.out.println(title + " " + member.getNickname());
    }
}