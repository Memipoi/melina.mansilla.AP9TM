package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createNewTransaction(
            Authentication authentication,
            @RequestParam  double amount,
            @RequestParam  String description,
            @RequestParam  String fromAccountNumber,
            @RequestParam  String toAccountNumber) {

        if (description.isEmpty() || amount == 0.00) {
            return new ResponseEntity<>("403 prohibido - El monto o la descripción se encuentran vacios", HttpStatus.FORBIDDEN);
        } else if (fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("403 prohibido - El/Los numero/os de cuenta/as se encuentra/an vacio/os", HttpStatus.FORBIDDEN);
        }else if (accountRepository.findByNumber(fromAccountNumber) == null){
            return new ResponseEntity<>("403 prohibido - La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        } else if (clientRepository.findByEmail(authentication.getName()).getAccounts().contains(fromAccountNumber)){
            return new ResponseEntity<>("403 prohibido - La cuenta de origen no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        } else if (accountRepository.findByNumber(fromAccountNumber).getBalance() < amount){
            return new ResponseEntity<>("403 prohibido - El cliente no posee fondos", HttpStatus.FORBIDDEN);
        } else if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("403 prohibido - Las cuentas de Origen y Destino son iguales", HttpStatus.FORBIDDEN);
        }
        Transactions transactionOrigen = new Transactions(TransactionType.DEBIT, LocalDate.now(),-amount,description+ " - " +fromAccountNumber, accountRepository.findByNumber(fromAccountNumber));
        Transactions transactionDestino = new Transactions(TransactionType.CREDIT, LocalDate.now(),amount,description+ " - " +toAccountNumber, accountRepository.findByNumber(toAccountNumber));
        transactionRepository.save(transactionOrigen);
        transactionRepository.save(transactionDestino);
        Account accountOrigen = accountRepository.findByNumber(fromAccountNumber);
        accountOrigen.setBalance(accountOrigen.getBalance() - amount);
        accountRepository.save(accountOrigen);
        Account accountDestino = accountRepository.findByNumber(toAccountNumber);
        accountDestino.setBalance(accountDestino.getBalance() + amount);
        accountRepository.save(accountDestino);
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
}
