package components;

//1.1.1 Creation of the client class
public class Client {
    private String nom;
    private String prenom;
    private int numeroClient;
    private static int compteurClients = 1; // Compteur statique initialisé à 1
    
    public Client(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        // Incrémentation automatique du numéro de client
        this.numeroClient = generateNumeroClient();
    }
    
    // Accesseurs et mutateurs pour chaque attribut
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public int getNumeroClient() {
        return numeroClient;
    }
    
    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }
    
    // Méthode pour formater l'affichage complet du client
    @Override
    public String toString() {
        return "Client [Nom=" + nom + ", Prénom=" + prenom + ", Numéro de client=" + numeroClient + "]";
    }
    
    // Méthode pour générer automatiquement le numéro de client
    private int generateNumeroClient() {
        // Utilisation du compteur statique et incrémentation pour obtenir un numéro unique
        int numero = compteurClients;
        compteurClients++;
        return numero;
    }
}
