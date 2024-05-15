package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Maintenance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceRepo {


    public static boolean save(Maintenance maintenance) throws SQLException {
        String sql = "insert into Maintenance values (?,?,?,?)";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,maintenance.getM_id());
        pstm.setObject(2,maintenance.getDescription());
        pstm.setObject(3,maintenance.getCost());
        pstm.setObject(4,maintenance.getDate());

        return pstm.executeUpdate()>0;
    }


    public static boolean delete(String mid) throws SQLException {
        String sql = "delete from Maintenance where M_id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,mid);

        return pstm.executeUpdate()> 0;
    }

    public static Maintenance searchData(String mid) throws SQLException {
        String sql = "select * from maintenance where M_id = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,mid);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String id = resultSet.getString(1);
            String desc = resultSet.getString(2);
            String cost = resultSet.getString(3);
            String date = resultSet.getString(4);
            Maintenance maintenance = new Maintenance(id, desc, cost, date);
            return  maintenance;
        }
        return null;
    }
}
