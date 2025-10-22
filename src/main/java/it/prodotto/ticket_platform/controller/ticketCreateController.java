package it.prodotto.ticket_platform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.ticketRepository;
import it.prodotto.ticket_platform.repository.userRepository;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/CreaTicket")
public class ticketCreateController {

    @Autowired
    private ticketRepository ticketRepo;

    @Autowired
    private userRepository userRepo;

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
    public String main(Model model) {

        loadAvailableTechnicians(model);

        model.addAttribute ("newTicket", new ticket());

        return "/ticket/create";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("newTicket") ticket userInput, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        
        // controllo se non viene inserito un nuovo elemento con lo stesso nome
        Optional<ticket> optionalTicket = ticketRepo.findByNome(userInput.getNome());

        if (optionalTicket.isPresent())
        {
            bindingResult.addError(new ObjectError("errorDuplictedName", "Ticket già presente con lo stesso nome!"));
            model.addAttribute("warningAlertMessageTicket", "Ticket già presente!");
        }

        // controllo se si è selezionato un tecnico
        if(userInput.getUserAssigned() == null){
            bindingResult.addError(new ObjectError("errorNoTechincianSelected", "Seleziona un tecnico!"));
            model.addAttribute("warningAlertMessageTechnician", "Seleziona un tecnico!");
        }

        if(bindingResult.hasErrors()){
            loadAvailableTechnicians(model);
            return "ticket/create";
        }

        ticketRepo.save(userInput);
        redirectAttributes.addFlashAttribute("successAlertMessage", "Nuovo ticket inserito correttamente!");
        return "redirect:/index";
    }
}
