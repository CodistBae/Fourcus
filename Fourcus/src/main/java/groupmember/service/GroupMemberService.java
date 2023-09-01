package groupmember.service;

import groupmember.dao.GroupMemberDao;
import groupmember.vo.GroupMember;
import member.dao.MemberDao;
import member.vo.Member;

import java.util.ArrayList;
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

    // 2. 그룹멤버 추방 (username을 받아서)
    public void delGroupMember(Scanner sc) {
        System.out.println("==== 그룹 멤버 추방 ====");

        while (true) {
            System.out.print("이름으로검색(1)을 입력하시오");
            int num = sc.nextInt();
            System.out.print("추방할 username: ");
            String username = sc.next();
            Member m = mdao.select(num, username);
            if (m == null) {
                System.out.println("존재하지 않는 회원");
            } else {
                if (dao.checkMyGroup(m.getId(), Group_id)) {
                    dao.delete(m.getId(), Group_id);
                    System.out.printf("%s님 추가완료", username);
                } else {
                    System.out.println("소속된 멤버가 아님");
                }
                break;
            }
        }
    }

    /* 공통 */
    // 내 그룹원 확인(전체)
    public void printMyGroupMember() {
        System.out.println("==== 내 그룹원 전체 확인 ====");
        ArrayList<GroupMember> list = dao.selectAll(Group_id);
        printAll(list);
    }

    // 내 그룹원 확인(username 입력해서) -> 프로필 확인
    public void printMyGroupMemberProfile(Scanner sc){
        System.out.println("==== 내 그룹원 프로필 보기 ====");
        System.out.print("이름으로검색(1)을 입력하시오");
        int num = sc.nextInt();
        System.out.print("확인할 username: ");
        String username = sc.next();
        Member m = mdao.select(num, username);
        // 존재하지 않는 멤버
        if (m == null){
            System.out.println("존재하지 않는 회원");
        } else{
            GroupMember gm = dao.selectGroupMember(m.getId(), Group_id);
            if(dao.checkMyGroup(m.getId(), Group_id) &&
                    gm != null){
                System.out.println("gm");
            }
            else{
                System.out.println("소속멤버가 아님");
            }
        }

    }
    public void printAll(ArrayList<GroupMember> list){
        for (GroupMember gb: list){
            System.out.println(gb);
        }
    }
}

