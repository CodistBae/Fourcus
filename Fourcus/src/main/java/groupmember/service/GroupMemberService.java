package groupmember.service;

import groupmember.dao.GroupMemberDao;
import member.dao.MemberDao;
import member.vo.Member;

import java.util.Scanner;

public class GroupMemberService {
    private GroupMemberDao dao;
    private MemberDao mdao;
    public static long Group_id = 0L;

    public GroupMemberService() {
        dao = new GroupMemberDao();
        mdao = MemberDao.getInstance();
    }
    // 기본적으로 Group_id를 갖고있는 상태
    /* 그룹장 기능 */

    // 1. 그룹멤버 추가(입 :username)
    public void addGroupMember(Scanner sc) {
        System.out.println("==== 그룹 멤버 추가 ====");
        while (true) {
            System.out.print("이름으로검색(1)을 입력하시오");
            int num = sc.nextInt();
            System.out.print("추가할 username: ");
            String username = sc.next();
            Member m = mdao.select(num, username);
            if (m == null) {
                System.out.println("존재하지 않는 회원");
            } else {
                if (dao.checkMyGroup(m.getId(), Group_id)) {
                    System.out.printf("이미 %s에 소속된 멤버입니다", Group_id);
                } else {
                    dao.insert(username, Group_id);
                    System.out.printf("%s님 추가완료", username);
                }
                break;
            }
        }
    }

    // 2. 그룹멤버 추방 (멤버 id를 받아서)
    public void delGroupMember(Scanner sc) {
        System.out.println("==== 그룹 멤버 추방 ====");
        System.out.println("");
        // 나의 그룹원이 아니다

        // 회원가입이 안된 멤버이다


        // 추방 완료
    }

    /* 공통 */
    // 내 그룹원 확인(전체)
    public void printMyGroupMember() {

    }

    // 내 그룹원 확인(id입력해서) -> 프로필 확인
    public void printMyGroupMemberProfile(){

    }
}

