package subject.dao;

import common.DbUtils;
import subject.vo.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Subjectdao {
    private DbUtils dbUtils;
    public Subjectdao() {
        dbUtils = DbUtils.getInstance();
    }

    // 과목 추가
    public void insert(Long memberId, String subjectName) {
        Connection connection = dbUtils.getConnection();
        String sql = "insert into Subject(Member_id,Subject_name) values (?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, memberId);
            ps.setString(2, subjectName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 과목 수정
    public void update(String subjectName, Long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "update Subject set Subject_name = ? where Id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, subjectName);
            ps.setLong(2, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 과목 삭제
    public void delete(Long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "delete Subject where Id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 과목 번호로 검색 => 과목 객체 반환
    public Subject select(Long subjectId) {
        Subject subject = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select * from Subject where Id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                subject = new Subject(rs.getLong(1), rs.getLong(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return subject;
    }



    // 과목 전체 검색
    public ArrayList<Subject> selectAll(Long memberId) {
        ArrayList<Subject> list = new ArrayList<>();
        Connection connection = dbUtils.getConnection();
        String sql = "select * from Subject where Member_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, memberId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Subject(rs.getLong(1),rs.getLong(2),rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return list;
    }



}
