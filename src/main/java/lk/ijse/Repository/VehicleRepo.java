package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Insurence;
import lk.ijse.Model.TM.VehicleTMDetails;
import lk.ijse.Model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepo {
    public static boolean save(Vehicle vehicle) throws SQLException {
        String sql = "insert into vehicle values (?,?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, vehicle.getId());
        pstm.setObject(2, vehicle.getModel());
        pstm.setObject(3, vehicle.getColur());
        pstm.setObject(4, vehicle.getIId());
        pstm.setObject(5,vehicle.getStatus() );

        return pstm.executeUpdate() > 0;
    }

    public static Vehicle searchData(String searchid) throws SQLException {
        String sql = "select * from vehicle where V_id = ? AND status = 'active'";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, searchid);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String v_id = resultSet.getString(1);
            String model = resultSet.getString(2);
            String colur = resultSet.getString(3);
            String iid = resultSet.getString(4);
            String status = resultSet.getString(5);

            Vehicle vehicle = new Vehicle(v_id, model, colur, iid,status);
            return vehicle;
        }
        return null;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "delete from Vehicle WHERE V_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);


        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql ="select v_id from vehicle where status = 'active'";
PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> codeList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            codeList.add(id);
        }
        return codeList;
    }

    public static List<VehicleTMDetails> getAll() throws SQLException {
        String sql = "SELECT * FROM vehicle INNER JOIN insurance ON vehicle.I_id = insurance.I_id WHERE Status ='Active';";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<VehicleTMDetails> VehicleList = new ArrayList<>();


        while (resultSet.next()){
            String Vid = resultSet.getString(1);
            String Iid = resultSet.getString(4);
            String Color = resultSet.getString(3);
            String Model = resultSet.getString(2);
            String SDate = resultSet.getString(7);
            String EDate = resultSet.getString(8);

            VehicleTMDetails details = new VehicleTMDetails(Vid, Iid, Color, Model, SDate, EDate);
        VehicleList.add(details);
        }
        return VehicleList;
    }
}

