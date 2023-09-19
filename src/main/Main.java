package main;

import components.Client;
import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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
}
