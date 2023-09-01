package groupmember.dao;

import groupmember.vo.GroupMember;
import common.DbUtils;
import member.service.MemberService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDao {
    private DbUtils dbUtils;
    public GroupMemberDao(){
        dbUtils = DbUtils.getInstance();
    }

//* 그룹장의 기능 *//
    // 멤버 추가 (해당 그룹에 해당 멤버가 없어야함)
    public void insert(Long Member_id, String username, Long Group_id) { // 그룹원의idx, Member_id, Group_id, Cumulative_time

        String sql = """
                insert into GroupMember(Member_id, Cumulative_time, Group_id) 
                values(?,
                        (select sum(sh.Cumulative_time) from `Member` m
                        join `Subject` s on s.Member_id = m.Id
                        join StudyHour sh on s.Id = sh.Subject_id
                        where m.Username = ?),
                        ?)
                """;

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, Member_id);
            pstmt.setString(2, username); //
            pstmt.setLong(3, Group_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    // 그룹멤버 추방 (내 그룹에 있는)
    public void delete(long Member_id, Long Group_id) {

        String sql = "delete from GroupMember where Member_id =? and Group_id =?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, Member_id); //
            pstmt.setLong(2, Group_id);

            int cnt = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//* 공통기능 *//
    // 전체 검색 (내 그룹원들만) -> 누적시간까지 포함!
    public List<GroupMember> selectAll(long Group_id) {
        List<GroupMember> list = new ArrayList<>();

        String sql = """
                select gm.Id, gm.Member_id, gm.Group_id, Cumulative_time
                from GroupMember gm
                join `Group` g on g.id = gm.Group_id
                where gm.Group_id = ?
                """;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, Group_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new GroupMember(
                        rs.getLong(1), // Id
                        rs.getLong(2), // Member_id
                        rs.getLong(3), // Group_name
                        rs.getLong(4) // Cumulative_time
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    // 나와 같은 Group_id를 가진 그룹원 中 Member_id를 입력해서 멤버의 정보확인
    public GroupMember selectGroupMember (long Member_id, long Group_id){

        String sql = """
                select gm.Id, gm.Member_id, gm.Group_id, Cumulative_time
                from GroupMember gm
                join `Group` g on g.id = gm.Group_id
                where gm.Member_id =? and gm.Group_id =?
                """;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, Member_id);
            pstmt.setLong(2, Group_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new GroupMember(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getLong(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 내 그룹원인지 확인( Member_id -> Group_id)
    public boolean checkMyGroup(long Member_id, long Group_id) {

        String sql = """
                select * from GroupMember where Member_id =? and Group_id =? 
                """;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, Member_id);
            pstmt.setLong(2, Group_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt(3) == Group_id) return true;
                else return false;

            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}