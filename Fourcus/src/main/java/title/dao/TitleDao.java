package title.dao;

import common.DbUtils;
import title.vo.Title;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TitleDao {
    private final DbUtils dbUtils;

    public TitleDao() {
        dbUtils = DbUtils.getInstance();
    }

    public void insert(Title title) {
        String sql = "insert into Title(Member_id, Title_name) values (?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, title.getMemberId());
            ps.setString(2, title.getTitleName());
            ps.executeUpdate();

            System.out.println("Title append success.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Title currentTitle(long memberId) {
        String sql = "select * from Title where member_id = ? and select = true";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int id2 = resultSet.getInt(1); // 컬럼값 꺼내는 메서드. get컬럼타입(컬럼순서)
                String name = resultSet.getString(2);
                int departmentId = resultSet.getInt(3);
            }

            System.out.println("CurrentTitle find!!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Title> findAllByMemberId(long memberId) {
        List<Title> titles = new ArrayList<>();
        String sql = "select * from Title where Member_id = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                titles.add(
                        Title.builder()
                                .id(resultSet.getLong(1))
                                .memberId(resultSet.getLong(2))
                                .titleName(resultSet.getString(3))
                                .build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return titles;
    }

    public void modify(long id) {
        String sql = "update Title set Select = true where id = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

            System.out.println("Title select success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTitle(long memberId) {
        String sql = "update Title set Select = false where Member_id = ? and `select` = true";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ps.executeUpdate();

            System.out.println("Title initialization success");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
