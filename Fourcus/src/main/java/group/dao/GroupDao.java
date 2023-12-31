package group.dao;

import common.DbUtils;
import group.vo.Group;
import groupmember.vo.GroupMember;
import member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private DbUtils dbUtils;

    private static GroupDao gdao = new GroupDao();

    private GroupDao(){
        dbUtils = DbUtils.getInstance();
    }

    public static GroupDao getInstance(){ return gdao;}

    // 그룹 생성
    public void insert(Group g){
        Connection conn = dbUtils.getConnection();
        String sql = "insert into `Group` (Member_id, Group_name) values(?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, g.getMember_id());
            pstmt.setString(2, g.getGroup_name());

            pstmt.executeUpdate();
            System.out.println("그룹생성완료");

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    // 그룹명 수정(그룹장만)
    public void update(String group_name, Long Group_id, Long Member_id){
        Connection conn = dbUtils.getConnection();
        String sql = "update `Group` set Group_name=? where Id=? and Member_id=?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, group_name);
            pstmt.setLong(2, Group_id);
            pstmt.setLong(3, Member_id);

            pstmt.executeUpdate();
            System.out.println("그룹명 수정 완료");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    // 그룹 삭제 (그룹장만 가능)
    public void delete(Long Group_id, Long Member_id){
        Connection conn = dbUtils.getConnection();
        String sql = "delete from `Group` where Id =? and Member_id=?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, Group_id);
            pstmt.setLong(2, Member_id);
            pstmt.executeUpdate();
            System.out.println("그룹 삭제 완료");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    // 그룹 선택
    public Group select(String group_name){
        Connection conn = dbUtils.getConnection();
        String sql = "select * from `Group` where Group_name=?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, group_name);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
                return new Group(rs.getLong(1), rs.getLong(2),
                                rs.getString(3), rs.getString(4));
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    // (내가 그룹장인)그룹 리스트
    public ArrayList<Group> selectAll(Long Member_id){
        ArrayList<Group> list = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        String sql = "select * from `Group` where Member_id=?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, Member_id);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(new Group(rs.getLong(1), rs.getLong(2),
                                    rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
    // 내가 속한 그룹 (내가 그룹원인)
    public List<Group> selectMyGroup(Long Member_id){
        List<Group> list = new ArrayList<>();

        String sql = """
                   select * from `Group` g
                   join GroupMember gm on gm.Group_id = g.Id
                   where gm.Member_id =?
                   """;

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, Member_id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Group(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getString(4)
                ));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    // 공지 생성, 수정
    public void notice(String notice, Long Group_id, Long Member_id){
        Connection conn = dbUtils.getConnection();
        String sql = "update `Group` set Notice=? where Id=? and Member_id=?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, notice);
            pstmt.setLong(2, Group_id);
            pstmt.setLong(3, Member_id);

            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
