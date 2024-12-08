import extensions.CSVFile;
import extensions.File;


class bonbon extends Program {
    final String nomDuJeu = "Qui veut gagner des bonbons";
    CSVFile questions = loadCSV("questions.csv");


    void initialiserTableauReponses(boolean[] questionsPosees) {
        for(int i=0; i<length(questionsPosees); i=i+1) {
            questionsPosees[i]=false;
        }
    }


    Joueur newJoueur(String nom) {
        Joueur joueur = new Joueur();
        joueur.nom = nom;
        joueur.points = 0;
        joueur.bonnesReponses = 0;
        joueur.mauvaisesReponses = 0;
        return joueur;
    }

    Joueurs CreerJoueurs() {
        println("Combien de joueurs êtes-vous?");
        int nombreJoueurs = readInt();
        Joueurs tab = new Joueurs();
        tab.joueur = new Joueur[nombreJoueurs];
        for(int i=0; i<nombreJoueurs; i=i+1) {
            println("Insérez le nom du joueur numéro "+ANSI_BLUE+(i+1)+ANSI_RESET+": ");
            tab.joueur[i] = newJoueur(readString());
            tab.nbJoueurs=tab.nbJoueurs+1;
        }
        return tab;
    }

    int event(double chanceDEvent) {
        return 0;
    }

    String[] getQuestion(int numeroQuestion) {
        String[] ligne = new String[stringToInt(getCell(questions, numeroQuestion, 1)) + (int) 2];
        for(int i=0; i<length(ligne); i=i+1) {
            ligne[i] = getCell(questions, numeroQuestion, i);
        }
        return ligne;
    }

    boolean poserQuestion(int numeroQuestion) {
        // IMPORTER LE JOUEUR QUI REPOND A LA QUESTION
        boolean resultatReponse=false;
        String[] question = getQuestion(numeroQuestion);
        println(ANSI_GREEN+question[0]+ANSI_RESET);
        String reponses="";
        String header="";
        for(int i=0; i<stringToInt(question[1]); i=i+1) {
            reponses=reponses+question[i+2]+"\t";
            header=header+"REPONSE "+i+"\t";
        }
        println(header);
        println(reponses);

        if(!bonneReponse) {

        } else {
            resultatReponse=true;

        }
        return ;
    }

    void tour() {

    }





    void algorithm() {
        // INITIALISER JOUEURS
        println(ANSI_PURPLE+"Bienvenu dans le jeu "+ANSI_GREEN+nomDuJeu+ANSI_PURPLE+"."+ANSI_RESET);
        Joueurs joueurs = CreerJoueurs();

        // INITIALISER DATA
        boolean[] questionsPosees = new boolean[rowCount(questions)];
        initialiserTableauReponses(questionsPosees);


        poserQuestion(1);
    }
}