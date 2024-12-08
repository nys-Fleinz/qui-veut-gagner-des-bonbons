import extensions.CSVFile;
import extensions.File;


class bonbon extends Program {
    final String nomDuJeu = "Qui veut gagner des bonbons";
    CSVFile questions = loadCSV("questions.csv");


    void initialiserTableauReponses(boolean[] questionsPosees) {
        for(int i=1; i<length(questionsPosees); i=i+1) {
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
        String[] ligne = new String[columnCount(questions, numeroQuestion)];
        for(int i=0; i<length(ligne); i=i+1) {
            ligne[i] = getCell(questions, numeroQuestion, i);
        }
        return ligne;
    }

    boolean poserQuestion(Joueur joueur, int numeroQuestion) {
        String[] question = getQuestion(numeroQuestion);
        String reponses="";
        String header="";
        for(int i=0; i<stringToInt(question[1]); i=i+1) {
            reponses=reponses+question[i+2]+"\t";
            header=header+ANSI_BLUE+"REPONSE "+ANSI_PURPLE+(i+1)+"\t";
        }
        println(ANSI_GREEN+joueur.nom+ANSI_PURPLE+" à ton tour !");
        println(ANSI_GREEN+question[0]+ANSI_RESET+"\n");
        println(header);
        println(reponses);

        boolean resultatReponse=repondreQuestion(joueur, question);

        if(!resultatReponse) {

        } else {
            resultatReponse=true;

        }
        return false;
    }

    boolean repondreQuestion(Joueur joueur, String[] question) {
        int numeroBonneReponse=stringToInt(question[stringToInt(question[1])+2]); //récupérer le numéro de la bonne réponse en fonction du nombre de réponse
        println("BONNE REPONSE: "+(numeroBonneReponse)+" ("+question[numeroBonneReponse+1]+")");
        print(ANSI_BLUE+"["+"🍬"+ANSI_BLUE+"] "+ANSI_GREEN+"Numéro de la réponse: "+ANSI_PURPLE);
        int reponse = readInt();
        boolean resultat;
        if(reponse==numeroBonneReponse) {
            println("Bonne réponse "+joueur.nom);
            resultat=true;
        } else {
            resultat=false;
        }
        return resultat;
    }

    int donnerQuestion(boolean[] questionsPosees) {
        int numeroQuestion=(int) (random()*rowCount(questions));
        int compteur=0;
        while((numeroQuestion==0 || questionsPosees[numeroQuestion]) && compteur<length(questionsPosees)) {
            println("pas trouvé");
            compteur=compteur+1;
            numeroQuestion=(int) (random()*rowCount(questions));
        }
        if(!(compteur<length(questionsPosees))) {
            initialiserTableauReponses(questionsPosees);
            numeroQuestion=(int) (random()*rowCount(questions));
        }
        questionsPosees[numeroQuestion]=true;
        return numeroQuestion;
    }

    void tour(Joueurs joueurs, boolean[] questionsPosees) {
        for(int i=0; i<length(joueurs.joueur); i=i+1) {
            poserQuestion(joueurs.joueur[i], donnerQuestion(questionsPosees));
            for(int j=0; j<length(questionsPosees); j++) {
                println(questionsPosees[j]);
            }
        }
    }





    void algorithm() {
        // INITIALISER JOUEURS
        println(ANSI_PURPLE+"Bienvenu dans le jeu "+ANSI_GREEN+nomDuJeu+ANSI_PURPLE+"."+ANSI_RESET);
        Joueurs joueurs = CreerJoueurs();

        // INITIALISER DATA
        boolean[] questionsPosees = new boolean[rowCount(questions)];
        initialiserTableauReponses(questionsPosees);

        tour(joueurs, questionsPosees);
    }
}