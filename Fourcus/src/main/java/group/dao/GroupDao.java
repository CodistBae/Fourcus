package group.dao;

import common.DbUtils;
import group.vo.Group;
import member.vo.Member;

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
        Connection conn = dbUtils.getConnection();
        String sql = "insert into `Group` (Member_id, Group_name) values(?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, g.getMember_id());
            pstmt.setString(2, g.getGroup_name());

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
        String sql = "delete `Group` where Group_id =? and Member_id=?";

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

    // 그룹 리스트
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
        return null;
    }

    // 공지 생성, 수정
    public void notice(String notice, Long Group_id, Long Member_id){
        Connection conn = dbUtils.getConnection();
        String sql = "update `Group` set Notice=? where Group_id=? and Member_id=?";

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
