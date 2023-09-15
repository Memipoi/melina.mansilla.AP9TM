package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
/**	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return (args )-> {

			Loan hipotecario = new Loan("Hipotecario", 500000.00,List.of(12,24,36,48,60));
			Loan personal = new Loan("Personal", 100000.00,List.of(6,12,24));
			Loan automotriz = new Loan("Automotriz", 300000.00,List.of(6,12,24,36));

			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);



			Client client = new Client("41875618","Melba","Morel","melbamorel@gmail.com", passwordEncoder.encode("abc"));
			Client client2 = new Client("41875615","Melina Antonella","Mansilla","melinpucca@live.com", passwordEncoder.encode("cba"));
			Client client3 = new Client("00000000","admin","admin","admin@admin.com", passwordEncoder.encode("admin"));



			clientRepository.save(client);
			clientRepository.save(client2);
			clientRepository.save(client3);


			ClientLoan loanhipotecario= new ClientLoan(400000.00,60,client,hipotecario);
			ClientLoan loanPersonal= new ClientLoan(50000.00,12,client,personal);
			ClientLoan loanPersonal2= new ClientLoan(100000.00,24,client2,personal);
			ClientLoan loanAtomotriz= new ClientLoan(200000.00,36,client2,automotriz);
			clientLoanRepository.save(loanhipotecario);
			clientLoanRepository.save(loanPersonal);
			clientLoanRepository.save(loanPersonal2);
			clientLoanRepository.save(loanAtomotriz);

			Card card = new Card(client.getFirstName() + client.getLastName(), CardType.DEBIT,CardColor.GOLD,"3325-6745-7876-4445",990,LocalDate.now(),LocalDate.now().plusYears(5),client);
			Card card2 = new Card(client.getFirstName() + client.getLastName(), CardType.CREDIT,CardColor.TITANIUM,"3326-6748-7879-4447",980,LocalDate.now(),LocalDate.now().plusYears(5),client);
			Card card3 = new Card(client2.getFirstName() + client2.getLastName(), CardType.CREDIT,CardColor.SILVER,"3366-6798-7179-4847",700,LocalDate.now(),LocalDate.now().plusYears(5),client2);
			cardRepository.save(card);
			cardRepository.save(card2);
			cardRepository.save(card3);



			Account account = new Account("VIN001",LocalDate.now(),5000.00,client);
			Account account2 = new Account("VIN002",LocalDate.now().minusDays(1),7500.00,client);
			Account account3 = new Account("VIN003",LocalDate.now(),9000.00,client2);
			client.addAccount(account);
			client.addAccount(account2);
			client2.addAccount(account3);
			ClientDTO clientDTO = new ClientDTO(client);

			accountRepository.save(account);
			accountRepository.save(account2);
			accountRepository.save(account3);


			Transactions transaction = new Transactions(TransactionType.CREDIT, LocalDate.now(),300.00,"FacturaLuz", account);
			Transactions transaction2 = new Transactions(TransactionType.DEBIT, LocalDate.now(), 400.00,"FacturaGas", account2);
			account.addTransactions(transaction);
			account2.addTransactions(transaction2);

			AccountDTO accountDTO = new AccountDTO(account);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction2);

			accountRepository.save(account);
			accountRepository.save(account2);

			client.addAccount(account);
			client.addAccount(account2);

			clientRepository.save(client);








		};
	}**/
}
