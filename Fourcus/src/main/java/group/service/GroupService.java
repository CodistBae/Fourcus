package group.service;

import group.vo.Group;
import group.dao.GroupDao;
import member.service.MemberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class GroupService {
    private GroupDao dao;
    public static Long loginId = MemberService.loginId;
    public static Long groupId;

    public GroupService(){
        dao = new GroupDao();
        groupId = null;
    }

    // 그룹 생성
    public void createGroup(BufferedReader br) throws IOException {
        if(loginId != null) {
            System.out.println("=====그룹 생성=====");
            System.out.print("그룹명: ");
            String group_name = br.readLine();
            dao.insert(new Group(loginId, group_name));
            System.out.println(group_name + " 그룹이 생성되었습니다.");
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹 선택
    public void selectGroup(BufferedReader br) throws IOException {
        System.out.print("관리할 그룹번호를 입력하세요: ");
        MyGroupList();
        groupId = Long.parseLong(br.readLine());
    }


    // 그룹 검색
    public void searchGroup(BufferedReader br) throws IOException{
        if(loginId != null){
            System.out.println("=====그룹 검색=====");
            System.out.print("검색할 그룹명: ");
            String group_name = br.readLine();

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
    public void updateGroupName(BufferedReader br) throws IOException{
        if(loginId != null){
            System.out.println("=====그룹명 수정=====");
            System.out.print("새로운 그룹명: ");
            String group_name = br.readLine();

            dao.update(group_name, groupId, loginId);
        }
    }

    // 그룹 삭제
    public void deleteGroup(BufferedReader br) throws IOException{
        if(loginId != null){
            System.out.println("=====그룹 삭제=====");
            MyGroupList();

            System.out.println("삭제할 그룹번호: ");
            Long group_id = Long.parseLong(br.readLine());

            dao.delete(group_id, loginId);
        }
    }

    // 공지사항 등록/수정
    public void Notice(BufferedReader br) throws IOException{
        if(loginId != null){
            System.out.println("=====공지사항=====");
            System.out.println("===내용 입력===");
            String content = br.readLine();

            dao.notice(content, groupId, loginId);
        }
    }

}
