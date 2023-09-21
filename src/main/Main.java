package main;

import components.Client;
import components.Account;
import components.CurrentAccount;
import components.SavingsAccount;
import components.Flow;
import components.Debit;
import components.Credit;
import components.Transfert;


import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.io.File;

import javax.xml.parsers.*;



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
        
        // Créez un tableau de flux
        List<Flow> flows = generateFlows();
        
        // Mettez à jour les soldes des comptes en fonction des flux
        updateAccountBalances(flows, accountTable);
        
        // Utiliser une méthode pour afficher le contenu de la Hashtable
        displayAccountTable(accountTable);
        
        
        // Charger les données à partir des fichiers JSON
        Flow[] flows2 = loadFlowsFromJSON(Paths.get("flows.json"));
        
        //Afficher les flux chargés à partir du fichier JSON
        System.out.println("Flows from JSON:");
        for (Flow flow2 : flows2) {
            System.out.println(flow2.toString());
        }
        
        
        
        String xmlFilePath = "accounts.xml";

        // Load accounts from the XML file
        Account[] accounts2 = loadAccountsFromXML(xmlFilePath);

        // Create a Hashtable of accounts
        Hashtable<Integer, Account> accountTable2 = createAccountTable(accounts2);

        // Display the accounts in ascending order of balance
        displayAccountTable(accountTable2);
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
    
	// Méthode pour générer un tableau de flux
    private static List<Flow> generateFlows() {
        List<Flow> flows = new ArrayList<>();
        flows.add(new Debit("Débit de 50€ depuis le compte n°1", 1, 50, 1, true, getFutureDate(2)));
        flows.add(new Credit("Crédit de 100.50€ sur tous les comptes courants", 2, 100.50, -1, true, getFutureDate(2)));
        flows.add(new Credit("Crédit de 1500€ sur tous les comptes d'épargne", 3, 1500, -1, true, getFutureDate(2)));
        flows.add(new Transfert("Transfert de 50€ du compte n°1 au compte n°2", 4, 50, 1, 2, true, getFutureDate(2)));
        return flows;
    }
 
    // Méthode pour obtenir la date future en ajoutant un certain nombre de jours à la date actuelle
    private static String getFutureDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysToAdd);
        return futureDate.toString();
    }
    
    // Méthode pour mettre à jour les soldes des comptes en fonction des flux
    private static void updateAccountBalances(List<Flow> flows, Map<Integer, Account> accountMap) {
        for (Flow flow : flows) {
        	// Traiter les flux de crédit qui concernent tous les comptes courants ou d'épargne
            if (flow instanceof Credit && flow.getTargetAccountNumber() == -1) {
                // Obtener le type de compte cible depuis le commentaire du flux
                String comment = flow.getComment().toLowerCase();
                
                // Obtener la liste de tous les comptes
                Collection<Account> accounts = accountMap.values();

                // Mettre à jour le solde de chaque compte en fonction du type de compte
                for (Account account : accounts) {
                    if ((account instanceof CurrentAccount && comment.contains("courant")) ||
                        (account instanceof SavingsAccount && comment.contains("épargne"))) {
                        account.setBalance(flow);
                    }
                }
            } else {
                Account account = accountMap.get(flow.getTargetAccountNumber());
                if (account != null) {
                    // Mettre à jour le solde du compte en fonction du flux
                    account.setBalance(flow);
                }
            }
        }

        // Vérifier s'il y a des comptes avec un solde négatif
        boolean hasNegativeBalance = accountMap.values().stream()
                .anyMatch(account -> account.getBalance() < 0);

        // Afficher un message si des comptes ont un solde négatif
        if (hasNegativeBalance) {
            System.out.println("Attention : Certains comptes ont un solde négatif !");
        }

        
    }
    
    // Méthode pour charger les flux à partir d'un fichier JSON
    private static Flow[] loadFlowsFromJSON(Path filePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(filePath));
            JSONArray jsonArray = new JSONArray(jsonContent);
            Flow[] flows = new Flow[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // extraire les valeurs nécessaires du JSONObject
                String comment = jsonObject.getString("comment");
                int identifier = jsonObject.getInt("identifier");
                double amount = jsonObject.getDouble("amount");
                int targetAccountNumber = jsonObject.getInt("targetAccountNumber");
                boolean effect = jsonObject.getBoolean("effect");
                String date = jsonObject.getString("date");

                // Determiner le type de flux en fonction de comment ou d'autres critères
                Flow flow;

                // si comment contient "Transfert", utiliser la classe Transfert
                if (comment.contains("Transfert")) {
                    int sourceAccountNumber = jsonObject.getInt("sourceAccountNumber");
                    flow = new Transfert(comment, identifier, amount, sourceAccountNumber, targetAccountNumber, effect, date);
                }
                // Si comment contient "Débit", utiliser la classe Debit
                else if (comment.contains("Débit")) {
                    flow = new Debit(comment, identifier, amount, targetAccountNumber, effect, date);
                }
                // Sinon, par défaut, utiliser la classe Credit
                else {
                    flow = new Credit(comment, identifier, amount, targetAccountNumber, effect, date);
                }

                flows[i] = flow;
            }

            return flows;
        } catch (IOException e) {
            e.printStackTrace();
            return new Flow[0]; // Retourner un tableau vide en cas d'erreur
        }
    }
    
 // Method to load accounts from an XML file and return an array
    private static Account[] loadAccountsFromXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList accountList = doc.getElementsByTagName("account");

            List<Account> accountArrayList = new ArrayList<>();

            for (int temp = 0; temp < accountList.getLength(); temp++) {
                Element accountNode = (Element) accountList.item(temp);
                String label = accountNode.getElementsByTagName("label").item(0).getTextContent();
                double balance = Double.parseDouble(accountNode.getElementsByTagName("balance").item(0).getTextContent());
                int accountNumber = Integer.parseInt(accountNode.getElementsByTagName("accountNumber").item(0).getTextContent());
                String clientName = accountNode.getElementsByTagName("name").item(0).getTextContent();
                String clientFirstName = accountNode.getElementsByTagName("firstName").item(0).getTextContent();
                int clientNumber = Integer.parseInt(accountNode.getElementsByTagName("clientNumber").item(0).getTextContent());

                // Create a Client object
                Client client = new Client(clientName, clientFirstName);
                client.setNumeroClient(clientNumber);

                // Determine the account type based on the label
                Account account;
                if (label.contains("Compte courant")) {
                    account = new CurrentAccount(label, client);
                } else {
                    account = new SavingsAccount(label, client);
                }

                account.setAccountNumber(accountNumber);
                account.setBalance(balance);

                accountArrayList.add(account);
            }

            // Convert the list of accounts to an array
            return accountArrayList.toArray(new Account[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
       
    
    

}
