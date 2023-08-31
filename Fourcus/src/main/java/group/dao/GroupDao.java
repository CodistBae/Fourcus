package group.dao;

import common.DbUtils;
import group.vo.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDao {
    private DbUtils dbUtils;

    public GroupDao(){
        dbUtils = DbUtils.getInstance();
    }

    // 그룹 생성
    public void insert(Group g){

        String sql = "insert into `Group` values( , ?,?)"; // 마지막 물음표는 쿼리문으로

        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setLong(1, g.getMember_id()); //

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // 그룹 삭제 (그룹장만 가능)
    public void delete(long Group_id){

        String sql = "delete `Group` where Group_id =?";

        try (Connection connection =dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)){


            int cnt = pstmt.executeUpdate();
//            System.out.printf("%s 그룹 삭제 완료", Group_name); // 그룹 객체 가져와서 이름 받는 방식으로?
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 내가 속한 그룹(들) 보기
    public ArrayList<Group> printMyGroup(long Group_id){
        ArrayList<Group> list = new ArrayList<>();

        String sql = "select * from Group where Group_id =?";

        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new Group(
                        rs.getLong(1), // Id
                        rs.getLong(2), // Member_id
                        rs.getString(3), // Group_name
                        rs.getString(4) // notice
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return list;


//        String

    }
    // 그룹 선택


    // 공지 생성 (그룹장만 가능)

    // 공지 확인

    //

}
