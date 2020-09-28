package tm.paro.clientmovieapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tm.paro.clientmovieapp.service.ClientService;

@Controller
@Slf4j
@RequestMapping("/")
public class LoginController {
    private final ClientService clientService;
    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "login")
    public String login(Model model){
        log.info("Client App - login");
        return "login";
    }

/*
    @PostMapping("auth")
    public ResponseEntity authentication(){
        log.info("Client App - auth");
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        System.out.println("post login");
        System.out.println(username+" "+password);
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("password", password);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String str=process(authentication);
        return ResponseEntity.ok(response);
    }

*/

/*    @GetMapping(value = "process")
    public String process(Authentication authentication){
        log.info("Client App - process");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        String password = ((UserDetails)principal).getPassword();
        System.out.println(username+" "+password);

        AccessTokenResponse tokenResponse;
        tokenResponse=clientService.httpapache();
        //tokenResponse=clientService.httpSpring();
        return "redirect:/directors";
    }*/





}
