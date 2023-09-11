package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;

import com.mindhub.homebanking.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(currentLoan -> new LoanDTO(currentLoan)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createNewLoan(Authentication authentication,
                                                @RequestBody LoanApplicationDTO loanApplicationDTO){
        List<LoanDTO> loans = getLoans();
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        Client client = clientRepository.findByEmail(authentication.getName());

        if (loanApplicationDTO.getAmount() == 0.0 || loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("403 prohibido - El monto o las cuotas se encuentran vacios", HttpStatus.FORBIDDEN);
        }else if(loan == null){
            return new ResponseEntity<>("403 prohibido - El prestamo no existe" , HttpStatus.FORBIDDEN);
        }else if(loan.getMaxAmount()<loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("403 prohibido - El monto solicitado es mayor al prestamo", HttpStatus.FORBIDDEN);
        } else if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("403 prohibido - Las cuotas solicitadas no se encuentran en el prestamo", HttpStatus.FORBIDDEN);
        } else if(account == null){
            return new ResponseEntity<>("403 prohibido - La cuenta destino del prestamo no existe", HttpStatus.FORBIDDEN);
        }else if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("403 prohibido - La cuenta destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(Math.round((loanApplicationDTO.getAmount()*1.20)*100.0) / 100.0, loanApplicationDTO.getPayments(),client, loan);
        clientLoanRepository.save(clientLoan);
        Transactions transaction = new Transactions(TransactionType.CREDIT, LocalDate.now(),loanApplicationDTO.getAmount(),loan.getName() + " loan approved ",accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber()));
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(account);


        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
}
