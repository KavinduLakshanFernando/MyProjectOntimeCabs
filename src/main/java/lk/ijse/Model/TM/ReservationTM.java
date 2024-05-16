package lk.ijse.Model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class ReservationTM {
    private String Re_id;
    private String P_id;
    private String vehicleId;
    private String serviceType;
    private String cusNic;
    private String name;
    private double amount;
    private String paymentType;
    private Date date;
}
