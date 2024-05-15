package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;

import java.awt.image.DataBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepo {
    public static List<String> getNamelist() throws SQLException {
        String sql = "select S_type from service  ";

        List<String> nameList = new ArrayList<>();

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()){
            String type = resultSet.getString(1);
            nameList.add(type);
    }
return nameList;
}


    public static String getId(String servicename) throws SQLException {

        String sql = "select S_id from service where S_type = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
         pstm.setObject(1,servicename);

         ResultSet resultSet = pstm.executeQuery();

         if (resultSet.next()){


             String id = resultSet.getString(1);

             return id;
         }

         return null;
    }
}