package tamagotchi.dao;

import common.DbUtils;
import tamagotchi.vo.Tamagotchi;
import tamagotchi.vo.Type;

import java.sql.*;

public class TamagotchiDao {

    private final DbUtils dbUtils;

    public TamagotchiDao() {
        dbUtils = DbUtils.getInstance();
    }

    public void insert(Tamagotchi tamagotchi) {
        String sql = "insert into Tamagotchi (Member_id, Tamagotchi_name, Level, Type) values ?, ?, ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tamagotchi.getTamagotchiName());
            ps.setInt(2, tamagotchi.getLevel());
            ps.setString(3, tamagotchi.getType().getName());
            ps.executeUpdate();

            System.out.println("Tamagotchi append success.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modify(Tamagotchi tamagotchi) {
        String sql = "update Tamagotchi set Tamagotchi_name = ?, Level = ?, Type = ? where id = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tamagotchi.getTamagotchiName());
            ps.setInt(2, tamagotchi.getLevel());
            ps.setString(3, tamagotchi.getType().getName());
            ps.setLong(4, tamagotchi.getId());
            ps.executeUpdate();

            System.out.println("Tamagotchi modify success.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tamagotchi findByMemberId(long memberId) {
        String sql = "select * from Tamagotchi where Member_id = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String tamagotchiName = resultSet.getString(3);
                int level = resultSet.getInt(4);
                Type type = Type.valueOf(resultSet.getString(5));

                return Tamagotchi.builder()
                        .id(id)
                        .tamagotchiName(tamagotchiName)
                        .level(level)
                        .type(type)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteByMemberId(long memberId) {
        String sql = "delete from Tamagotchi where Member_id = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ps.executeUpdate();

            System.out.println("Tamagotchi delete success.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
