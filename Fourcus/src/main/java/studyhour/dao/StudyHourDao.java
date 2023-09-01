package studyhour.dao;

import common.DbUtils;
import studyhour.vo.StudyHour;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StudyHourDao {
    private DbUtils dbUtils;

    public StudyHourDao() {
       dbUtils = DbUtils.getInstance();
    }



    // 공부시간 startTime, startDate 넣기
    public void start(Time startTime, String startDate, Long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "insert into StudyHour(Start_time, Start_date , Subject_id, Cumulative_time, Max_focus_time) " +
                "values(?,?,?,0,0)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTime(1, startTime);
            ps.setString(2, startDate);
            ps.setLong(3, subjectId);
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

    // 종료시간 업데이트
    public void stop(Time stopTime, String startDate, Long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Stop_time = ? where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTime(1, stopTime);
            ps.setLong(2, subjectId);
            ps.setString(3,startDate);
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

    // 공부시간 reStarttime 업데이트
    public void reStart(Time reStartTime, String startDate, Long subjectId) {
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Restart_time = ? where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTime(1, reStartTime);
            ps.setLong(2, subjectId);
            ps.setString(3, startDate);
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
    public Time selectStartTime(String startDate, Long subjectId){
        Time startTime = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select Start_time from StudyHour where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.setString(2, startDate);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
               startTime = rs.getTime(1);
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
    public Time selectStopTime(String startDate, Long subjectId){
        Time stopTime = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select Stop_time from StudyHour where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.setString(2, startDate);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                stopTime = rs.getTime(1);
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

    // restart 시간 가져오기
    public Time selectRestartTime(String startDate,Long subjectId){
        Time restartTime = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select Restart_time from StudyHour where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.setString(2, startDate);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                restartTime = rs.getTime(1);
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

        return restartTime;
    }


    // 과목 id로 검색
    public StudyHour select(Long subjectId){
        StudyHour sh = null;
        Connection connection = dbUtils.getConnection();
        String sql = "select * from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                sh = new StudyHour(rs.getLong(1), rs.getLong(2),
                        rs.getString(3) ,rs.getTime(4), rs.getTime(5),
                        rs.getTime(6), rs.getLong(7),rs.getLong(8));
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
    public long selectCumulativeTime(String startDate, Long subjectId){
        long time = 0;
        Connection connection = dbUtils.getConnection();
        String sql = "select Cumulative_time from StudyHour where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.setString(2, startDate);
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
    public void updateCumulativeTime(long newCumulativeTIme, String startDate, Long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Cumulative_time = ?  where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, newCumulativeTIme);
            ps.setLong(2, subjectId);
            ps.setString(3, startDate);
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
    public long selectMax(String startDate, long subjectId){
        long max = 0;
        Connection connection = dbUtils.getConnection();
        String sql = "select Max_focus_time from StudyHour where Subject_id = ? and Start_date = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ps.setString(2, startDate);
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
    public void updateMax(long newMax, String startDate, Long subjectId){
        Connection connection = dbUtils.getConnection();
        String sql = "update StudyHour set Max_focus_time = ?  where Subject_id = ? and Start_date = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, newMax);
            ps.setLong(2, subjectId);
            ps.setString(3, startDate);
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

    // select startDate
    public String selectStartDate(Long subjectId){
        String startDate = null ;
        Connection connection = dbUtils.getConnection();
        String sql = "select Start_date from StudyHour where subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                startDate = rs.getString(1);
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
        return startDate;
    }

    // 시분초 변환 메서드
    public void changeSec(long secconds) {
        int hour = (int)secconds/(60*60);
        int min = (int)secconds/60;
        int sec = (int)secconds%60;

        System.out.print(hour+"시간 " + min +"분 " + sec +"초" );

    }

    // select ct 날짜 선택 x
    public ArrayList<Long> selectSubjectCumulativeTime(Long subjectId){
        ArrayList<Long> list = new ArrayList<>();
        Connection connection = dbUtils.getConnection();
        String sql = "select Cumulative_time from StudyHour where Subject_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
               list.add(rs.getLong(1));
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
        return list;
    }

    // 하루 전체 중 최대 집중 시간 가져오기
    public ArrayList<Long> selectTodayMax(String startDate, Long memberId){
        ArrayList<Long> list = new ArrayList<>();
        Connection connection = dbUtils.getConnection();
        String sql = "select Cumulative_time from StudyHour where Start_date = ?" +
                " and Subject_id in(select Id from Subject where Member_id = ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setLong(2, memberId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getLong(1));
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

    // max 값으로 객체 찾기
    public ArrayList<StudyHour> selectAll(){
        ArrayList<StudyHour> studyHours = new ArrayList<>();
        Connection connection = dbUtils.getConnection();
        String sql = "select * from StudyHour";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                studyHours.add(new StudyHour(rs.getLong(1), rs.getLong(2), rs.getString(3),
                        rs.getTime(4), rs.getTime(5), rs.getTime(6),
                        rs.getLong(7), rs.getLong(8)));
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
        return studyHours;
    }

}

