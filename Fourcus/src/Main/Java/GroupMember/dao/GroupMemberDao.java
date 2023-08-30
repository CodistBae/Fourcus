package GroupMember.dao;


import GroupMember.common.DbUtils;
import GroupMember.vo.GroupMember;

import java.sql.*;
import java.util.ArrayList;

public class GroupMemberDao {
    private DbUtils dbUtils;
    public GroupMemberDao(){
        dbUtils = DbUtils.getInstance();
    }

    // 멤버 추가
    public void insert(GroupMember gb){

        String sql = "insert into GroupMember values(null, ?,?,?)";
        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1, gb.getMember_id()); //

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    //  그룹원 select (그룹원 id로 -> 해당 그룹원의 정보 확인)
    public GroupMember select (long Group_id){
        String sql = "select * from GroupMember where Group_id = ?";
        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){

                return new GroupMember(
                        rs.getLong(1), // Id
                        rs.getString(2), // Member_id
                        rs.getString(3), // Group_name
                        rs.getTime(4) // 누적 시간
                );
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
    // 전체 검색
    public ArrayList<GroupMember> selectAll(){
        ArrayList<GroupMember> list = new ArrayList<>();

        String sql = "select * from GroupMember";

        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new GroupMember(
                        rs.getLong(1), // Id
                        rs.getString(2), // Member_id
                        rs.getString(3), // Group_name
                        rs.getTime(4) // Cumulative_time
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    // 그룹멤버 추방
    public void delete(long Group_id){

        String sql = "delete GroupMember where Group_id =?";

        try(Connection connection =dbUtils.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){


            int cnt = pstmt.executeUpdate();
            System.out.printf("%s님 추방 완료", Group_id);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
// 그룹의 누적시간 가져오기 ?



}