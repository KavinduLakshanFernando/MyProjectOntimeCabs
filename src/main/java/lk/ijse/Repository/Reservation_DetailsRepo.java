package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Reservation_Details;

import java.awt.image.DataBuffer;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reservation_DetailsRepo {
    public static boolean savedata(Reservation_Details reservationDetails) throws SQLException {

        String sql = "insert into reservation_details values (?,?,?)";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,reservationDetails.getRe_id());
        pstm.setObject(2,reservationDetails.getV_id());
        pstm.setObject(3,reservationDetails.getData());

        return  pstm.executeUpdate()>0;
    }
}
