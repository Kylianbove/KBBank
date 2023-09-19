package main;

import components.Client;
import components.Account;
import components.CurrentAccount;
import components.SavingsAccount;
import components.Flow;
import components.Debit;
import components.Credit;
import components.Transfert;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Hashtable;


public class Main {
    public static void main(String[] args) {
        // Déclarer un tableau de clients et le charger à l'aide d'une méthode
        Client[] clients = generateClientArray(3);
        
        // Utiliser une méthode pour afficher le contenu du tableau
        displayClients(clients);
        
        // Déclarer un tableau de comptes et le charger à l'aide d'une méthode
        Account[] accounts = generateAccountArray(6, generateClientArray(3));

        // Utiliser une méthode pour afficher le contenu du tableau
        displayAccounts(accounts);
        
        // Créer la Hashtable et la remplir avec les comptes
        Hashtable<Integer, Account> accountTable = createAccountTable(accounts);
        
        // Utiliser une méthode pour afficher le contenu de la Hashtable
        displayAccountTable(accountTable);
        
        // Créez un tableau de flux
        List<Flow> flows = new ArrayList<>();

        // Chargez le tableau avec les flux spécifiés
        flows.add(new Debit("Débit de 50€ depuis le compte n°1", 1, 50, 1, true, getFutureDate(2)));
        flows.add(new Credit("Crédit de 100.50€ sur tous les comptes courants", 2, 100.50, -1, true, getFutureDate(2)));
        flows.add(new Credit("Crédit de 1500€ sur tous les comptes d'épargne", 3, 1500, -1, true, getFutureDate(2)));
        flows.add(new Transfert("Transfert de 50€ du compte n°1 au compte n°2", 4, 50, 1, 2, true, getFutureDate(2)));


    }
    
    // Méthode pour générer un tableau de clients
    private static Client[] generateClientArray(int numClients) {
        Client[] clients = new Client[numClients];
        for (int i = 0; i < numClients; i++) {
            clients[i] = new Client("nom" + (i + 1), "prénom" + (i + 1));
        }
        return clients;
    }
    
    // Méthode pour afficher le contenu du tableau de clients
    private static void displayClients(Client[] clients) {
        Arrays.stream(clients)
              .map(Client::toString)
              .forEach(System.out::println);
    }
    
    // Méthode pour générer un tableau de comptes
    private static Account[] generateAccountArray(int numAccounts, Client[] clients) {
        Account[] accounts = new Account[numAccounts];
        for (int i = 0; i < numAccounts; i++) {
            if (i % 2 == 0) {
                accounts[i] = new CurrentAccount("Compte courant " + (i + 1), clients[i / 2]);
            } else {
                accounts[i] = new SavingsAccount("Compte épargne " + (i + 1), clients[i / 2]);
            }
        }
        return accounts;
    }

    // Méthode pour afficher le contenu du tableau de comptes
    private static void displayAccounts(Account[] accounts) {
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }
    
    // Méthode pour créer et remplir la Hashtable avec les comptes
    private static Hashtable<Integer, Account> createAccountTable(Account[] accounts) {
        Hashtable<Integer, Account> accountTable = new Hashtable<>();
        for (Account account : accounts) {
            accountTable.put(account.getAccountNumber(), account);
        }
        return accountTable;
    }

    // Méthode pour afficher le contenu de la Hashtable triée par solde
    private static void displayAccountTable(Hashtable<Integer, Account> accountTable) {
        accountTable.entrySet()
                    .stream()
                    .sorted(Comparator.comparingDouble(entry -> entry.getValue().getBalance()))
                    .forEach(entry -> {
                        System.out.println("Account Number: " + entry.getKey());
                        System.out.println(entry.getValue().toString());
                        System.out.println();
                    });
    }
    
    
 
    // Méthode pour obtenir la date future en ajoutant un certain nombre de jours à la date actuelle
    private static String getFutureDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysToAdd);
        return futureDate.toString();
    }
    

}
