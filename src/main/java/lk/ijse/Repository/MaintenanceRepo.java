package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Maintenance;
import lk.ijse.Model.TM.MaintenanceTM;
import lk.ijse.Model.TM.ReservationTM;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT M_id FROM maintenance ORDER BY M_id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String mId = resultSet.getString(1);
            return mId;
        }
        return null;
    }


    public static boolean save(Maintenance maintenance) throws SQLException {
        String sql = "insert into Maintenance values (?,?,?,?,?)";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,maintenance.getM_id());
        pstm.setObject(2,maintenance.getV_id());
        pstm.setObject(3,maintenance.getDescription());
        pstm.setObject(4,maintenance.getCost());
        pstm.setObject(5,maintenance.getDate());

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
            String vid = resultSet.getString(2);
            String desc = resultSet.getString(3);
            String cost = resultSet.getString(4);
            String date = resultSet.getString(5);
            Maintenance maintenance = new Maintenance(id,vid, desc, cost, date);
            return  maintenance;
        }
        return null;
    }

    public static List<MaintenanceTM> getAll() throws SQLException {
        String sql = "select * from maintenance";
        PreparedStatement pstm = DBConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MaintenanceTM> resList = new ArrayList<>();

        while (resultSet.next()) {
            String vid = resultSet.getString(1);
            String mid = resultSet.getString(2);
            String desc = resultSet.getString(3);
            String cost = resultSet.getString(4);
            Date date = resultSet.getDate(5);

//
//            private String V_id;
//            private String M_id;
//            private String desc;
//            private Date date;
//            private String cost;


            MaintenanceTM maintenancetm = new MaintenanceTM(vid, mid, desc, date, cost);
            resList.add(maintenancetm);
        }
        return resList;
    }

}
