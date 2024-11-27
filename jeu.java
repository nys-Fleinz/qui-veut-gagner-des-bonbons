import extensions.CSVFile;
import extensions.File;


class bonbon extends Program{

    CSVFile pokedex = loadCSV("questions.csv");

    Joueur newJoueur(String nom) {
        Joueur joueur = new Joueur();
        joueur.nom = nom;
        joueur.points = 0;
        joueur.bonneReponse = 0;
        joueur.mauvaiseReponse = 0;
        return joueur;
    }

    Joueurs CreerJoueurs(int nombreJoueurs) {
        Joueurs tab = new Joueurs();
        tab.joueur = new Joueur[nombreJoueurs];
        for(int i=0; i<nombreJoueurs; i=i+1) {
            println("Insérez le nom du joueur numéro"+i+": ");
            tab.joueur[i] = newJoueur(readString());
        }
        return tab;
    }





    void algorithm() {
        
    }
}