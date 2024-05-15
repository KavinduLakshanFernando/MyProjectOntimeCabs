package lk.ijse.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Maintenance {
    private String M_id;
    private String Description;
    private String cost;
    private String date;
}
