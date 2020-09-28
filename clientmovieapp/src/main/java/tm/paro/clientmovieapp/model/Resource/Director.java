package tm.paro.clientmovieapp.model.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director implements Serializable {
    private int directorId;
    //@Size(max = 255, message = "Name is required, maximum 255 characters.")
    private String name;
    //@Size(max = 255, message = "Surname is required, maximum 255 characters.")
    private String surname;
    //@Min(value = 2002, message = "Birth date should not be less than 2002.")
    private int birthYear;
    private Address homeAddress;
    private Set<Movie> movieList=new HashSet<>();


}
