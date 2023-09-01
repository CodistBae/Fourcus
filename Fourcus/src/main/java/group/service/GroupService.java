package group.service;

import group.vo.Group;
import group.dao.GroupDao;
import java.util.ArrayList;

public class GroupService {
    private GroupDao dao;
    public GroupService(){
        dao = new GroupDao();
    }
    // 내가 소속한 그룹보기( 여러개 일 수 있으므로) -> 그 중에 그룹 선택 ->  선택한 그룹에 대한 정보 보기
    public void printMyGroup(){
        System.out.println("==== 내 소속 그룹 확인 ====");
//        ArrayList<Group> list = dao.printMyGroup();
    }

    // 내 그룹 공지사항 보기
//    public void print

    //


}
