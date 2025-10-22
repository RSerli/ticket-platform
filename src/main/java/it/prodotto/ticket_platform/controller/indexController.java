package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.userRepository;


@Controller
@RequestMapping("/index")
public class indexController {

    @Autowired
    private userRepository userRepo;

    @GetMapping()
    public String main(Authentication auth, Model model) {
    
        // punto dell'utente loggato
        user loggedUser = userRepo.findByEmail(auth.getName()).get();
        
        model.addAttribute ("loggedUser",loggedUser);


        return "index";
    }
    

}
