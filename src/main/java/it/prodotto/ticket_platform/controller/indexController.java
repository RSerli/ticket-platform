package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.repository.userRepository;


@Controller
@RequestMapping("/index")
public class indexController {

    @Autowired
    private userRepository userRepo;

    // ----- PAGINA INIZIALE con tutti i ticket associati all'utente loggato-----
    @GetMapping("/{id}")
    public String main() {
        
        return "index";
    }
    

}
