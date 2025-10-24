package it.prodotto.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.prodotto.ticket_platform.model.state;
import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.repository.stateRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;

@Controller
@RequestMapping ("/changeStatus/{ide}")
public class statusController {

    // Riceve l'ID del ticket dal path e il nuovo status (newStatusId) dal form

    @Autowired
    private stateRepository statusRepo;

    @Autowired
    private ticketRepository ticketRepo;

    @PostMapping
    public String main (@PathVariable("ide") Integer idTicket,@RequestParam(name="newStatusId") Integer newStatusId, Model model){

        ticket changeStatusTicket = ticketRepo.findById(idTicket).get();

        state newStatus = statusRepo.findById(newStatusId).get();

        changeStatusTicket.setActualStatus(newStatus);

        ticketRepo.save(changeStatusTicket);

        return "redirect:/index";
    }
}
