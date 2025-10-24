package it.prodotto.ticket_platform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prodotto.ticket_platform.model.Note;
import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.noteRepository;
import it.prodotto.ticket_platform.repository.stateRepository;
import it.prodotto.ticket_platform.repository.ticketRepository;
import it.prodotto.ticket_platform.repository.userRepository;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ModificaTicket/{id}")
public class ticketModifiyController {

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private userRepository userRepo;

    @Autowired
    private stateRepository statusRepo;

    @Autowired
    private noteRepository noteRepo;

    // caricamento dei tecnici disponibili
    private void loadAvailableTechnicians(Model model) {
        List<user> allUsers = userRepo.findAll();
        List<user> freeTechnician = new ArrayList<>();

        for (user singleUser : allUsers) {
            if (singleUser.isAvailable()) {
                freeTechnician.add(singleUser);
            }
        }

        model.addAttribute ("freeTech", freeTechnician);
    }

   @GetMapping
    public String main(@PathVariable("id") Integer id,Model model) {

        loadAvailableTechnicians(model);

        model.addAttribute ("allStatus", statusRepo.findAll());
        model.addAttribute ("selectedTicketToModify", ticketRepo.findById(id).get());

        return "/ticket/modify";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("selectedTicketToModify") ticket userInput, BindingResult bindingResult, RedirectAttributes redirectAttributes,@PathVariable("id") Integer id ,Model model) {
        

        if(bindingResult.hasErrors()){
            loadAvailableTechnicians(model);
            model.addAttribute ("allStatus", statusRepo.findAll());
            model.addAttribute ("selectedTicketToModify", ticketRepo.findById(id).get());
            return "ticket/modify";
        }

        ticketRepo.save(userInput);
        redirectAttributes.addFlashAttribute("successAlertModMessage", "Ticket Modificato correttamente!");
        return "redirect:/viewTicket/" + userInput.getId();
    }

    @PostMapping("/cancellaNota/{ide}")
    public String cancellaNota(@PathVariable("ide") Integer id){

        Note singolaNotaDaEliminare = noteRepo.findById(id).get();

        noteRepo.delete(singolaNotaDaEliminare);

        return "ticket/modify";
    }
}
