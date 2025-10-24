package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.model.Note;
import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.noteRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;
import it.prodotto.ticket_platform.repository.userRepository;
import jakarta.validation.Valid;


@Controller
@RequestMapping("{ide}/AggiungiNota")
public class noteController {

    @Autowired
    private userRepository userRepo;

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private noteRepository noteRepo;

    

    @GetMapping
    public String main (@PathVariable("ide") Integer ide, Model model){

        Note singolaNota = new Note();
        singolaNota.setTargetTicket(ticketRepo.findById(ide).get());
        model.addAttribute("newNote", singolaNota);

        return "/note/create";
    }


    @PostMapping
    public String create(@Valid @ModelAttribute("newNote") Note userInput, BindingResult bindingResult, @PathVariable("ide") Integer ide){

        if(bindingResult.hasErrors()){
            return "note/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        // punto dell'utente loggato
        user detailUser = userRepo.findByEmail(currentPrincipalName).get();

        userInput.setAuthor(detailUser.getName());

        userInput.setTargetTicket(ticketRepo.findById(ide).get());

        noteRepo.save(userInput);
        return "redirect:/viewTicket/" + userInput.getTargetTicket().getId();
    }

    
}
