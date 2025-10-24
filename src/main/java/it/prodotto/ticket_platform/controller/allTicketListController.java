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
import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.noteRepository;
import it.prodotto.ticket_platform.repository.stateRepository;
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

    @Autowired
    private stateRepository statusRepo;


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
    
    @PostMapping("/refreshTec")
    public String refreshTec(){

        // per ogni tecnico faccio un sum-up dei ticket che ha e setto il suo stato di disponiobilità

        List<user> allUsers = userRepo.findAll();

        for(user singlUser : allUsers){

            List<ticket> toDoTicketsList = ticketRepo.findByUserAssignedAndActualStatus(singlUser, statusRepo.findById(2).get());
            Integer numberToDoTickets = toDoTicketsList.size();

            List<ticket> toWorkTicketsList = ticketRepo.findByUserAssignedAndActualStatus(singlUser, statusRepo.findById(1).get());
            Integer numberToWorkTickets = toWorkTicketsList.size();

            if (numberToDoTickets > 0 || numberToWorkTickets > 0) {
                singlUser.setAvailable(false);
            } else {
                singlUser.setAvailable(true);
            }

            userRepo.save(singlUser);

        }

        return "redirect:/ListaTickets";
    }
}
