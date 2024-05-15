package lk.ijse.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Payment {
     private String P_id;
     private String Amount;
     private String Date;
     private String Payment_method;


}
