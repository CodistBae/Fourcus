package studyhour.dao;

import common.DbUtils;
import studyhour.vo.StudyHour;

import java.sql.*;
import java.time.LocalDateTime;

public class StudyHourDao {
    private DbUtils dbUtils;

    public StudyHourDao() {
       dbUtils = DbUtils.getInstance();
    }



    // 공부시간 startTime 넣기
    public void start(LocalDateTime startTime, long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "insert into StudyHour(Start_time) values(?) where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(startTime));
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

    // 공부시간 stopTime not null 이어서 0으로 해두고 update 하는 형식
    public void stop(LocalDateTime stopTime, long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Stop_time = ? where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(stopTime));
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

    // 공부시간 reStarttime 추가
    public void reStart(LocalDateTime reStartTime, long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Restart_time = ? where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(reStartTime));
            ps.setLong(2, subjectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // 시작 시각 가져오기
    public LocalDateTime selectStartTime(long subjectId){
        LocalDateTime startTime = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select Start_time from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
               startTime = rs.getTimestamp(1).toLocalDateTime();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return startTime;

    }

    // 종료 시각 가져오기
    public LocalDateTime selectStopTime(long subjectId){
        LocalDateTime stopTime = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select Stop_time from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                stopTime = rs.getTimestamp(1).toLocalDateTime();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return stopTime;

    }


    // 과목 id로 검색
    public StudyHour select(long subjectId){
        StudyHour sh = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select * from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                sh = new StudyHour(rs.getLong(1), rs.getLong(2),
                        rs.getTimestamp(3).toLocalDateTime(), rs.getTimestamp(4).toLocalDateTime(),
                        rs.getTimestamp(5).toLocalDateTime(), rs.getLong(6),rs.getLong(7));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return sh;
    }

    // select ct
    public long selectCumulativeTime(long subjectId){
        long time = 0;
        Connection connection = dbUtils.getConnection();
        String sql = "select Cumulative_time from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                time = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return time;
    }


    // update ct( 누적시간 )
    public void updateCumulativeTime(long newCumulativeTIme, long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Cumulative_time = ?  where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, newCumulativeTIme);
            ps.setLong(2, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // select max
    public long selectMax(long subjectId){
        long max = 0;
        Connection connection = dbUtils.getConnection();
        String sql = "select Max_focus_time from StudyHour where Subject_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                max = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return max;

    }


    // update max
    public void updateMax(long newMax, long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Max_focus_time = ?  where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, newMax);
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

}

