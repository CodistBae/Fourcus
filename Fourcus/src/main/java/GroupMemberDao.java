import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupMemberDao {
    // Connection
    public GroupMemberDao(){
    // dbconn
    }

// 멤버 추가
    public void insert(GroupMember gb){
        // Connection
        String sql = "insert into GroupMember values(null, ?,?,?)";
        try{

        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            try{

            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
//  그룹원 select (그룹원 id로 -> 해당 그룹원의 정보 확인)
    public GroupMember select (long Group_id){
        String sql = "select * from GroupMember where Group_id = ?";
        try{
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                return new GroupMember(
                        rs.getLong(1), // Id
                        rs.getString(2), // Member_id
                        rs.getString(3), // Group_name
                        rs.getDate.toLocalDateTime(4) // cummulative time
                );
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
// 전체 검색
    public ArrayList<GroupMember> selectAll(){
        ArrayList<GroupMember> list = new ArrayList<>();

        String sql = "select * from GroupMember";

        try{

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new GroupMember(
                        rs.getLong(1), // Id
                        rs.getString(2), // Member_id
                        rs.getString(3), // Group_name
                        rs.getDate(4)
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{
                conn.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }
// 그룹멤버 추방
    public void delete(long Group_id){

        String sql = "delete GroupMember where Group_id =?";

        try{


            int cnt = pstmt.executeUpdate();
            System.out.printf("%s님 추방 완료", Group_id);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally{
            try{
                conn.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
// 그룹의 누적시간 가져오기
    public Date


}
