package it.prodotto.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.prodotto.ticket_platform.model.Note;
import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.repository.noteRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;
import it.prodotto.ticket_platform.repository.userRepository;


@Controller
@RequestMapping("/ListaTickets")
public class allTicketListController {

    @Autowired
    private noteRepository noteRepo;

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private userRepository userRepo;


    @GetMapping
    public String mainView(@RequestParam(name="keywordTicketName", required=false) String keywordTicketName, Model model) {
        
        List<ticket> listResultSerching;

        if (keywordTicketName == null || keywordTicketName.isBlank()) {
            listResultSerching = ticketRepo.findAll();
        } else {
            listResultSerching = ticketRepo.findByNomeContainingIgnoringCase(keywordTicketName);
        }

        model.addAttribute("allTickets", listResultSerching);


        model.addAttribute("allUser", userRepo.findAll());

        return "allTickets/view";
    }

    @PostMapping("/cancellaTicket/{id}")
    public String cancellaTicket(@PathVariable("id") Integer id){

        ticket singoloTicketDaEliminare = ticketRepo.findById(id).get();

        // cancello tutti le note associate al ticket
        for(Note noteDaEliminare : singoloTicketDaEliminare.getNoteList()){
            noteRepo.delete(noteDaEliminare);
        }

        // rimuovo collegamento ticket all'user assegnato
        

        ticketRepo.delete(singoloTicketDaEliminare);

        return "redirect:/ListaTickets";
    }
    
}
