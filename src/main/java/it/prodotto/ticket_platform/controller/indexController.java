package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.repository.userRepository;


@Controller
@RequestMapping("/index")
public class indexController {

    @Autowired
    private userRepository userRepo;

    // ----- PAGINA INIZIALE -----
    @GetMapping
    public String main(Authentication authentication, Model model) {
    
        // model.addAttribute ("usernameLogged", authentication.getName());
        // model.addAttribute ("authorityLogged", authentication.getAuthorities());

        return "index";
    }
    

}
