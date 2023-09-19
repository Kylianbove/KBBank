package main;

import components.Client;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Déclarer un tableau de clients et le charger à l'aide d'une méthode
        Client[] clients = generateClientArray(3);
        
        // Utiliser une méthode pour afficher le contenu du tableau
        displayClients(clients);
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
}
