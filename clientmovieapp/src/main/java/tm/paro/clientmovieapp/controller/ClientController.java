package tm.paro.clientmovieapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tm.paro.clientmovieapp.model.AccessTokenResponse;
import tm.paro.clientmovieapp.model.Resource.Director;
import tm.paro.clientmovieapp.service.ClientService;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class ClientController {

    private final ClientService clientService;
    @Value("${authserver.accessTokenUri}")
    private String tokenEndpoint;
    AccessTokenResponse tokenResponse;
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
        /* configure user-service, password-encoder etc ... */
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);


    @GetMapping(value = "process")
    public String process(Authentication authentication){
        LOGGER.info("Client App - process");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        String password = ((UserDetails)principal).getPassword();
        System.out.println(username+" "+password);

        tokenResponse=clientService.httpapache();
        //clientService.httpSpring();
        return "redirect:/directors";
    }

    @GetMapping("/")
    public String main() {

        return "main";
    }

    @GetMapping("/directors")
    public String getAllDirectors(Model model) {
        List<Director> directorList=clientService.getAllDirectors();
        model.addAttribute("directors", directorList);
        return "directors";
    }

    @GetMapping("/director/{id}")
    public String getDirectorById(@PathVariable(value = "id") int id, Model model) {
        Director director=clientService.getDirectorById(id);
        model.addAttribute("director", director);
        return "director";
    }

    @PostMapping(value = "/", consumes = "application/json")    //Will only handle requests whose Content-type matches application/json
    @ResponseStatus(code = HttpStatus.CREATED)
    public String add(@RequestBody Director director){
        clientService.add(director);
        return "redirect:/directors";
    }

    @PutMapping(value = "/{id}")
    public String putById(@RequestBody Director director){
        clientService.put(director);
        return "redirect:/directors";
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)   // 204
    public String deleteById(@PathVariable("id") Long directorId) {
        clientService.deleteById(directorId);
        return "redirect:/directors";
    }


}
