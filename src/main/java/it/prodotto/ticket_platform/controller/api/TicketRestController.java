package it.prodotto.ticket_platform.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prodotto.ticket_platform.model.state;
import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.repository.stateRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;

@RestController
@CrossOrigin
@RequestMapping ("/api/ticket")
public class TicketRestController {

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private stateRepository statusRepo;

    // visualizzare l’elenco dei ticket

    @GetMapping
    public ResponseEntity<List<ticket>> listaTicket() {
        
        List<ticket> listResultSerching = ticketRepo.findAll();

        return ResponseEntity.ok(listResultSerching);
    }

    // filtrare l’elenco dei ticket per stato

    @GetMapping("/{Status}")
    public ResponseEntity<List<ticket>> listaTicketStatus(@PathVariable("Status") String selectedStatus) {
        
        List<ticket> listResultSerching = null;

        state selectedState = statusRepo.findByName(selectedStatus);
        
        
        if(selectedState != null){
            listResultSerching = selectedState.getSelectedTickets();
            return ResponseEntity.ok(listResultSerching);
        }else{
            return new ResponseEntity (listResultSerching, HttpStatus.BAD_REQUEST);
        }
    }
}