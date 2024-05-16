package lk.ijse.Model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode

public class MaintenanceTM {
    private String V_id;
    private String M_id;
    private String desc;
    private Date date;
    private String cost;
}
