package lk.ijse.Model;

import lk.ijse.Model.TM.VehicleTMDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Vehicle extends VehicleTMDetails {
    private String id ;
    private String model;
    private String colur;
    private String iId;
    private String status;
}
