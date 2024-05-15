package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Maintenance_Details;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Maintenance_DetailsRepo {
    public static boolean save(Maintenance_Details maintenanceDetails) throws SQLException {
        String sql = "insert into maintenance_Details values (?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,maintenanceDetails.getV_id());
        pstm.setObject(2,maintenanceDetails.getM_id());

        return pstm.executeUpdate()>0;
    }

    public static String getVid(String mid) throws SQLException {
        String sql = "select V_id from maintenance_Details where M_id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,mid);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String vid = resultSet.getString(1);
            return vid;
        }
        return null;
    }
}
