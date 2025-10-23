package it.prodotto.ticket_platform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.repository.ticketRepository;


@Controller
@RequestMapping ("/viewTicket/{id}")
public class viewTicketController {

    @Autowired
    private ticketRepository ticketRepo;

    @GetMapping
    public String MainView(@PathVariable("id") Integer id,Model model) {
        // controllo presenza della pizza nel database ricercandola attaverso l'id
        Optional<ticket> optionalTicket = ticketRepo.findById(id);
        if(optionalTicket.isPresent()){
            model.addAttribute("selectedTicket", optionalTicket.get());
            model.addAttribute("isSelectedTicket", true);
        }else{
            model.addAttribute("isSelectedTicket", false);

        }
        return "ticket/view";
    }
    
}
