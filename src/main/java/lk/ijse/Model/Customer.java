package lk.ijse.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class Customer {
    private String nic;
    private String Name;
    private String Address;
    private String Tel;
}
