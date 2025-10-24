package it.prodotto.ticket_platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.stateRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;
import it.prodotto.ticket_platform.repository.userRepository;



@Controller
@RequestMapping("/")
public class indexController {

    @Autowired
    private userRepository userRepo;

    @Autowired
    private stateRepository statusRepo;

    @Autowired
    private ticketRepository ticketRepo;

    @GetMapping("/")
    public String index() {
        return "redirect:/main";
    }
    

    @GetMapping("/main")
    public String main(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        // punto dell'utente loggato
        user detailUser = userRepo.findByEmail(currentPrincipalName).get();

        model.addAttribute ("loggedUser",detailUser);

        model.addAttribute("allStatus", statusRepo.findAll());

        // faccio un escursus di tutti i ticket assegnati all'utente e li conto per stato

        List<ticket> finishedTicketsList = ticketRepo.findByUserAssignedAndActualStatus(detailUser, statusRepo.findById(3).get());
        Integer numberFinishedTickets = finishedTicketsList.size();
        model.addAttribute ("numberFinishedTickets",numberFinishedTickets);

        List<ticket> toDoTicketsList = ticketRepo.findByUserAssignedAndActualStatus(detailUser, statusRepo.findById(2).get());
        Integer numberToDoTickets = toDoTicketsList.size();
        model.addAttribute ("numberToDoTickets",numberToDoTickets);

        List<ticket> toWorkTicketsList = ticketRepo.findByUserAssignedAndActualStatus(detailUser, statusRepo.findById(1).get());
        Integer numberToWorkTickets = toWorkTicketsList.size();
        model.addAttribute ("numberToWorkTickets",numberToWorkTickets);
        
        // controllo per settare la disponibilità di un utente

        if (numberToDoTickets > 0 || numberToWorkTickets > 0) {
            detailUser.setAvailable(false);
        } else {
            detailUser.setAvailable(true);
        }


        userRepo.save(detailUser);

        return "index";
    }
    
    // --- FUNZIONI DI UTIILTY ---



}
