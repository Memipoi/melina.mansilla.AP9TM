package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        List<Client>allClients = clientRepository.findAll();
        return allClients.stream().map(currentClient -> new ClientDTO(currentClient)).collect(Collectors.toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return new ClientDTO(clientOptional.get());
    }

}
