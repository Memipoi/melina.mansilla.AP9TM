package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.ClientRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args )-> {
			Client client = new Client("41875618","Melba","Morel","melbamorel@gmail.com");
			Client client2 = new Client("41875615","Melina Antonella","Mansilla","melinpucca@live.com");


			clientRepository.save(client);
			clientRepository.save(client2);

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
	}

}
