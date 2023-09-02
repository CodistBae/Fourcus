package group.service;

import group.vo.Group;
import group.dao.GroupDao;
import groupmember.dao.GroupMemberDao;
import member.dao.MemberDao;
import member.service.MemberService;
import member.vo.Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private GroupDao dao;
    private GroupMemberDao gmdao;
    private MemberDao mdao;
    public static Long groupId;

    public GroupService() {
        dao = GroupDao.getInstance();
        gmdao = GroupMemberDao.getInstance();
        mdao = MemberDao.getInstance();
        groupId = null;
    }

    // 그룹 생성
    public void createGroup(BufferedReader br) throws IOException {
        if (MemberService.loginId != null) {
            System.out.println("=====그룹 생성=====");
            System.out.print("그룹명: ");
            String group_name = br.readLine();
            dao.insert(new Group(MemberService.loginId, group_name));

            Member m = mdao.selectById(MemberService.loginId);
            gmdao.insert(MemberService.loginId, m.getUsername(), );
//            System.out.println(group_name + " 그룹이 생성되었습니다.");
        } else {
            System.out.println("로그인 후 사용해주세요.");
        }
    }

    // 그룹 선택(내가 그룹장인 그룹을 선택)
    public boolean selectGroup(BufferedReader br) throws IOException {
        boolean flag = false;
        ArrayList<Group> list = MyGroupList();
        if(list.size()!=0) {
            System.out.print("관리할 그룹번호를 입력하세요: ");
            groupId = Long.parseLong(br.readLine());
            flag = true;
            return flag;
        } else {
            System.out.println("내가 그룹장인 그룹이 없습니다.");
            return flag;
        }
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
    public ArrayList<Group> MyGroupList() {
        if (MemberService.loginId != null) {
            ArrayList<Group> list = dao.selectAll(MemberService.loginId);

            for (Group g : list)
                System.out.println(g);
            return list;
        } else {
            System.out.println("로그인 후 사용해주세요.");
            return null;
        }
    }
    // boolean
    public boolean selectMyBelongingGroup(BufferedReader br) throws IOException{
        boolean flag = false;
        List<Group> list = MyBelongingGroupList();
        if(list.size()!=0) {
            System.out.print("선택할 그룹번호를 입력하세요: ");
            groupId = Long.parseLong(br.readLine());
            flag = true;
            return flag;
        } else {
            return flag;
        }
    }


    // 내가 속한 그룹 리스트
    public List<Group> MyBelongingGroupList(){
        if (dao.selectMyGroup(MemberService.loginId)!= null){
            List<Group> list = dao.selectMyGroup(MemberService.loginId);

            for (Group g : list){
                System.out.println(g);
            }
            return list;

        } else{
            System.out.println("소속된 그룹이 없습니다");
            return null;
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
