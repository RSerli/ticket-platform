package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.stateRepository;
import it.prodotto.ticket_platform.repository.userRepository;


@Controller
@RequestMapping("/")
public class indexController {

    @Autowired
    private userRepository userRepo;

    @Autowired
    private stateRepository statusRepo;

    @GetMapping("/index")
    public String main(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        // punto dell'utente loggato
        user detailUser = userRepo.findByEmail(currentPrincipalName).get();

        model.addAttribute ("loggedUser",detailUser);

        model.addAttribute("allStatus", statusRepo.findAll());


        return "index";
    }
    

}
