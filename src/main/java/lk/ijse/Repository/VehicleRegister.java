package lk.ijse.Repository;

import lk.ijse.Database.DBConnection;
import lk.ijse.Model.Insurence;
import lk.ijse.Model.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;

public class VehicleRegister {
    public static boolean regiVehicle(Insurence insurence, Vehicle vehicle) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = InsurenceRepo.saveData(insurence);
            if (isSaved) {
                boolean issaved2 = VehicleRepo.save(vehicle);
                if (issaved2) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
            }catch (Exception e){
            connection.rollback();
            return false;
             } finally {
            connection.setAutoCommit(true);
        }

    }
}
