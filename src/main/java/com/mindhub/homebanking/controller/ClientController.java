package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.CardRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(currentClient -> new ClientDTO(currentClient)).collect(Collectors.toList());
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createNewAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() > 3) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }
        Account account = new Account("VIN-" + Math.round(Math.random()*9545343), LocalDate.now(),0.00,client);
        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createNewCard(
            Authentication authentication, @RequestParam CardType cardType,
            @RequestParam CardColor cardColor) {
        Client client = clientRepository.findByEmail(authentication.getName());
        System.out.println(client.getCards().stream().filter(card -> card.getType() == cardType));
        System.out.println(client.getCards().stream().filter(card -> card.getType() == cardType).count());

        if (client.getCards().stream().filter(card -> card.getType() == cardType).count() == 3) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }
        Card card = new Card(client.getFirstName() + client.getLastName(), cardType,cardColor,Math.round(1000 + Math.random()*9000) + "-" + Math.round(1000 + Math.random()*9000) + "-" + Math.round(1000 + Math.random()*9000) + "-" + Math.round(1000 + Math.random()*9000), Math.toIntExact(Math.round(100 + Math.random()*900)),LocalDate.now(),LocalDate.now().plusYears(5),client);
        cardRepository.save(card);
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return new ClientDTO(clientOptional.get());
    }


    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client("", firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        Account account = new Account("VIN-" + Math.round(Math.random()*99999999), LocalDate.now(),0.00,client);
        accountRepository.save(account);
        client.addAccount(account);
        clientRepository.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
