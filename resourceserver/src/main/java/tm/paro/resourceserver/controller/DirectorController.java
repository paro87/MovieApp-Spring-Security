package tm.paro.resourceserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("movieapp/")
public class DirectorController {


    @PreAuthorize("hasAuthority('GET')")
    @GetMapping(value = "director")
    public String getAll(@RequestHeader("authorization") String authorization) {
        System.out.println("In Get mapping");
        return "DIRECTORS";
    }

    @GetMapping(value = "director/{id}")
    @PreAuthorize("hasAuthority('GET')")
    public String getById(@PathVariable("id") Integer directorId) {
        System.out.println("Get by id");
        return "MOVIE";
    }

    //@PreAuthorize("hasAuthority('POST')")
    @PostMapping(value = "director", consumes = "application/json")
    //Will only handle requests whose Content-type matches application/json
    public void add() {
        System.out.println("POST");
    }

    //@PreAuthorize("hasAuthority('PUT')")
    @PutMapping(value = "director/{id}")
    public void putById() {
        System.out.println("PUT");
    }

    //@PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping(value = "director/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)   // 204
    public void deleteById() {
        System.out.println("DELETE");
    }




}
