package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Driver;
import lk.ijse.Model.TM.DriverTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepo {
    public static boolean save(Driver driver) throws SQLException {
        String sql = "insert into driver values(?,?,?,?,?,?)";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, driver.getId());
        pstm.setObject(2, driver.getName());
        pstm.setObject(3, driver.getAddress());
        pstm.setObject(4, driver.getContact());
        pstm.setObject(5, driver.getVnumber());
        pstm.setObject(6, driver.getStatus());

        return pstm.executeUpdate() > 0;

    }

    public static Driver searchData(String id) throws SQLException {
        String sql = "select * from driver where D_id = ? and status = 'active'";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String idd = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String vid = resultSet.getString(5);
            String status = resultSet.getString(6);

            Driver driver = new Driver(idd, name, address, tel, vid, status);
            return driver;
        }
        return null;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "update driver set status = 'deactivated' where D_id = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Driver driver) throws SQLException {
        String sql = "UPDATE driver SET Name=?, Address=?, Tel=?, V_id=? WHERE D_id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, driver.getName());
        pstm.setString(2, driver.getAddress());
        pstm.setString(3, driver.getContact());
        pstm.setString(4, driver.getVnumber());
        pstm.setString(5, driver.getId());


        return pstm.executeUpdate() > 0;
    }

    public static List<Driver> getAll() throws SQLException {
        String sql = "SELECT * FROM driver where status = 'active' ";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Driver> DriverList = new ArrayList<>();

        while (resultSet.next()){
           String id= resultSet.getString(1);
           String name=resultSet.getString(2);
           String address=resultSet.getString(3);
           String tel=resultSet.getString(4);
           String vnumber=resultSet.getString(5);
           String status= resultSet.getString(6);

            Driver driver = new Driver(id, name, address, tel, vnumber,status);
            DriverList.add(driver);
        }
        return DriverList;
    }
}




