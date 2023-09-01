package group.service;

import group.vo.Group;
import group.dao.GroupDao;
import member.service.MemberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class GroupService {
    private GroupDao dao;
    public static Long groupId;

    public GroupService() {
        dao = new GroupDao();
        groupId = null;
    }

    // 그룹 생성
    public void createGroup(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {
            System.out.println("=====그룹 생성=====");
            System.out.print("그룹명: ");
            String group_name = br.readLine();
            dao.insert(new Group(MemberService.loginId, group_name));
//            System.out.println(group_name + " 그룹이 생성되었습니다.");
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹 선택
    public void selectGroup(BufferedReader br) throws IOException {
        MyGroupList();
        System.out.print("관리할 그룹번호를 입력하세요: ");
        groupId = Long.parseLong(br.readLine());
    }


    // 그룹 검색
    public void searchGroup(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {
            System.out.println("=====그룹 검색=====");
            System.out.print("검색할 그룹명: ");
            String group_name = br.readLine();

            dao.select(group_name);
        } else
            System.out.println("로그인 후 사용해주세요.");
    }

    // 내가 그룹장인 그룹 리스트
    public void MyGroupList() {
        if (MemberService.loginId != null) {
            ArrayList<Group> list = dao.selectAll(MemberService.loginId);
            for (Group g : list)
                System.out.println(g);
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹이름 수정
    public void updateGroupName(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {
            System.out.println("=====그룹명 수정=====");
            System.out.print("새로운 그룹명: ");
            String group_name = br.readLine();

            dao.update(group_name, groupId, MemberService.loginId);
        }
    }

    // 그룹 삭제
    public void deleteGroup(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {

            dao.delete(groupId, MemberService.loginId);
        }
    }

    // 공지사항 등록/수정
    public void Notice(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {
            System.out.println("=====공지사항=====");
            System.out.println("===내용 입력(종료는 /stop)===");
            StringBuilder sb = new StringBuilder();
            while (true) {
                String str = br.readLine();
                if (str.startsWith("/stop")) break;
                sb.append(str + "\n");
            }
            String content = String.valueOf(sb);
            dao.notice(content, groupId, MemberService.loginId);
        }
    }

}
