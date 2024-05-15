package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRepo {
    public static boolean savedata(Payment payment) throws SQLException {

        String sql = "insert into Payment values (?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,payment.getP_id());
        pstm.setDouble(2, Double.parseDouble(payment.getAmount()));
        pstm.setObject(3,payment.getDate());
        pstm.setObject(4,payment.getPayment_method());

        return pstm.executeUpdate()>0;
    }

    public static Payment search(String pId) throws SQLException {
        String sql = "select * from payment where P_id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
pstm.setObject(1,pId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String pid = resultSet.getString(1);
            String amount = resultSet.getString(2);
            String date = resultSet.getString(3);
            String pmethod = resultSet.getString(4);

            Payment payment = new Payment(pid, amount, date, pmethod);
            return payment;
        }
        return null;
    }

    public static boolean delete(String pid) throws SQLException {
        String sql = " delete from payment where P_id = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,pid);


        return pstm.executeUpdate() > 0;
    }
}
