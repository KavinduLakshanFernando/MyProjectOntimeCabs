package lk.ijse.Model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VehicleTMDetails {
    private String VNumber;
    private String INumber;
    private String Color;
    private String Model;
    private String StartDate;
    private String EndDate;

}
