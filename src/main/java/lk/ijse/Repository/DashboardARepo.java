package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardARepo {


    public static int getVehicleCount(Dashboard dashboard) throws SQLException {
        String sql = "select count(*) as vehicleCount from vehicle";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("vehicleCount");
        }
        return 0;
    }

    public static int getCustomerCount(Dashboard dashboard) throws SQLException {
        String sql = "select count(*) as CustomerCount from customer";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("Customercount");
        }
        return 0;
    }



    public static int getDriversCount(Dashboard dashboard) throws SQLException {
        String sql = "select count(*) as DriversCount from driver";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("DriversCount");
        }
        return 0;
    }


    public static int getResCount(Dashboard dashboard) throws SQLException {
        String sql = "select count(*) as ResCount from reservation";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("ResCount");
        }
        return 0;
    }


}
