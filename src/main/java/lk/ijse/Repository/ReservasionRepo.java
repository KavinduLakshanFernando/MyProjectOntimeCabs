package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Reservation;
import lk.ijse.Model.TM.ReservationTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasionRepo {


    public static List<ReservationTM> getAll() throws SQLException {
        String sql = "SELECT reservation.Re_id, reservation.P_id, reservation_Details.V_id, service.S_type, reservation.nic, customer.Name AS customer_name, payment.Amount, payment.Payment_method, reservation_Details.Date AS reservation_date FROM reservation JOIN reservation_Details ON reservation.Re_id = reservation_Details.Re_id JOIN customer ON reservation.nic = customer.nic JOIN payment ON reservation.P_id = payment.P_id JOIN vehicle ON reservation_Details.V_id = vehicle.V_id JOIN service ON reservation.S_id = service.S_id";

        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ReservationTM> resList = new ArrayList<>();

        while (resultSet.next()) {
            String reId = resultSet.getString(1);
            String paymentId = resultSet.getString(2);
            String vehicleId = resultSet.getString(3);
            String serviceType = resultSet.getString(4);
            String nic = resultSet.getString(5);
            String customerName = resultSet.getString(6);
            double ammount = resultSet.getDouble(7);
            String payType = resultSet.getString(8);
            Date date = resultSet.getDate(9);

            ReservationTM reservationTm = new ReservationTM(reId, paymentId, vehicleId, serviceType, nic, customerName, ammount, payType, date);
            resList.add(reservationTm);
        }
        return resList;
    }



















    public static String getCurrentId() throws SQLException {
        String sql = "SELECT Re_id FROM reservation ORDER BY Re_id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String resId = resultSet.getString(1);
            return resId;
        }
        return null;
    }


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
