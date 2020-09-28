package tm.paro.clientmovieapp.model.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Address implements Serializable {
    private int zipCode;
    private String street;
    private String city;
    private String country;
}
