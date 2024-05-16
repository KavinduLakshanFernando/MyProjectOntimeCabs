package lk.ijse.Model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VehicleTMDetails {
    private String VNumber;
    private String INumber;
    private String Color;
    private String Model;
    private Date StartDate;
    private Date EndDate;
}
