package lk.ijse.Model;

import lk.ijse.Database.DBConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Reservation {
    private String Re_id;
    private String P_id;
    private String S_id;
    private String Cu_id;

    public static boolean savedata(Reservation reservation) throws SQLException {

        String sql = "insert into reservation values (?,?,?,?) ";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,reservation.getRe_id());
        pstm.setObject(2,reservation.getP_id());
        pstm.setObject(3,reservation.getS_id());
        pstm.setObject(4,reservation.getCu_id());

        return pstm.executeUpdate()>0;
    }
}
