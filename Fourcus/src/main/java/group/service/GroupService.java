package group.service;

import group.vo.Group;
import group.dao.GroupDao;
import member.service.MemberService;

import java.util.ArrayList;
import java.util.Scanner;

public class GroupService {
    private GroupDao dao;
    public static Long loginId = MemberService.loginId;

    public GroupService(){
        dao = new GroupDao();
    }

    // 그룹 생성
    public void createGroup(Scanner sc){
        if(loginId != null) {
            System.out.println("=====그룹 생성=====");
            System.out.print("그룹명: ");
            String group_name = sc.next();
            dao.insert(new Group(loginId, group_name));
            System.out.println(group_name + " 그룹이 생성되었습니다.");
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹 검색
    public void searchGroup(Scanner sc){
        if(loginId != null){
            System.out.println("=====그룹 검색=====");
            System.out.print("검색할 그룹명: ");
            String group_name = sc.next();

            dao.select(group_name);
        } else
            System.out.println("로그인 후 사용해주세요.");
    }

    // 내가 그룹장인 그룹 리스트
    public void MyGroupList(){
        if(loginId != null){
            ArrayList<Group> list = dao.selectAll(loginId);
            for(Group g: list)
                System.out.println(g);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹이름 수정
    public void updateGroupName(Scanner sc){
        if(loginId != null){
            System.out.println("=====그룹명 수정=====");
            MyGroupList();

            System.out.print("변경할 그룹번호: ");
            Long group_id = sc.nextLong();
            System.out.print("새로운 그룹명: ");
            String group_name = sc.next();

            dao.update(group_name, group_id, loginId);
        }
    }

    // 그룹 삭제
    public void deleteGroup(Scanner sc){
        if(loginId != null){
            System.out.println("=====그룹 삭제=====");
            MyGroupList();

            System.out.println("삭제할 그룹번호: ");
            Long group_id = sc.nextLong();

            dao.delete(group_id, loginId);
        }
    }

    // 공지사항 등록/수정
    public void Notice(Scanner sc){
        if(loginId != null){
            System.out.println("=====공지사항=====");
            MyGroupList();

            System.out.print("공지사항 등록할 그룹 번호: ");
            Long group_id = sc.nextLong();
            System.out.println("===내용 입력===");
            String content = sc.next();

            dao.notice(content, group_id, loginId);
        }
    }

}
