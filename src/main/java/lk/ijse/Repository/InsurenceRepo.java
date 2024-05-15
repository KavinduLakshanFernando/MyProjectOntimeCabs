package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Insurence;
import lk.ijse.Model.Vehicle;

import java.sql.*;

public class InsurenceRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(CAST(I_id AS UNSIGNED)) AS max_id FROM insurance ";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String aid = resultSet.getString(1);
            return aid;
        }
        return null;
    }

    public static boolean saveData(Insurence insurence) throws SQLException {
        String sql = "insert into insurance values (?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,insurence.getI_id());
        pstm.setObject(2,insurence.getStdate());
        pstm.setObject(3,insurence.getEnddate());

       return pstm.executeUpdate() >0;
    }

    public static Insurence seachdata(String iId) throws SQLException {
        String sql = "select * from insurance where I_id = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,iId);

        ResultSet resultSet= pstm.executeQuery();

        if (resultSet.next()){
            String iid = resultSet.getString(1);
            Date stdate = resultSet.getDate(2);
            Date enddate = resultSet.getDate(3);
            Insurence insurence =new Insurence(iid,stdate,enddate);
            return insurence;
        }
        return null;
    }

    public static boolean delete(String iid) throws SQLException {
        String sql = "delete from insurance where I_id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,iid);

        return  pstm.executeUpdate() > 0;
    }
}
