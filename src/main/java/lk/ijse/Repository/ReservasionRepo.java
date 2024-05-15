package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservasionRepo {
    public static Reservation search( String Rid) throws SQLException {
        String sql = "select * from reservation";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            String Reid = resultSet.getString(1);
            String pid = resultSet.getString(2);
            String Sid = resultSet.getString(3);
            String cuid = resultSet.getString(4);

            Reservation reservation = new Reservation(Reid, pid, Sid, cuid);
            return reservation;
        }
            return null;
    }

    public static boolean delecte(String rid) throws SQLException {
        String sql = " delete from reservation where Re_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,rid);


        return pstm.executeUpdate() > 0;
    }
}
