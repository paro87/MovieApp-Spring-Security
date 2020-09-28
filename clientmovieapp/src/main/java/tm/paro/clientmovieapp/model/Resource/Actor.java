package tm.paro.clientmovieapp.model.Resource;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data

public class Actor {
    private int actorId;
    private String primaryName;
    private Set<Movie> movieSet = new HashSet<Movie>();

}
