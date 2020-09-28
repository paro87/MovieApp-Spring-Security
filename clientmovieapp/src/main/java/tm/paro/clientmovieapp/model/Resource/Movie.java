package tm.paro.clientmovieapp.model.Resource;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Movie {
    private int id;
    private String movieName;
    private int releaseDate;
    private Type movieType;
    private List<Genre> genreList=new ArrayList<>();
    private Set<Actor> actorList=new HashSet<>();
    private Director director;
}
