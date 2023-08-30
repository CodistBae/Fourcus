package GroupMember.service;

import java.util.Scanner;
import GroupMember.dao.GroupMemberDao;

public class GroupMemberService {
    private GroupMemberDao dao;
    public GroupMemberService(){
        dao = new GroupMemberDao();
    }

    // 1. 그룹멤버 추가
    public void addGroupMember (Scanner sc){
        System.out.println("==== 그룹 멤버 추가 ====");
        System.out.print("추가할 멤버 이름: ");
        String Member_id = sc.next();

    }

    // 3. 그룹멤버 추방 (멤버 id를 받아서)
    public void delGroupMember(Scanner sc){
        System.out.println("==== 그룹 멤버 추방 ====");
        System.out.println("");
    }

}
