import extensions.CSVFile;
import extensions.File;


class bonbon extends Program {
    final String nomDuJeu = "Qui veut gagner des bonbons";
    CSVFile questions = loadCSV("questions.csv");
    CSVFile eventsCSV = loadCSV("events.csv");


    void initialiserTableauReponses(boolean[] questionsPosees) {
        for(int i=1; i<length(questionsPosees); i=i+1) {
            questionsPosees[i]=false;
        }
    }


    Joueur newJoueur(String nom) {
        Joueur joueur = new Joueur();
        joueur.nom = nom;
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

    String[] getEvent() {
        String[] ligne = new String[columnCount(eventsCSV)];
        int eventRandom = (int) (random()*(rowCount(eventsCSV)-1)+1);
        if(stringToDouble(getCell(eventsCSV, eventRandom, 2))>=random()) {
            for(int i=0; i<length(ligne); i=i+1) {
                ligne[i] = getCell(eventsCSV, eventRandom, i);
            }
        } else {
            ligne[0]="no_event";
        }
        return ligne;
    }

    String[] getQuestion(int numeroQuestion) {
        String[] ligne = new String[columnCount(questions, numeroQuestion)];
        for(int i=0; i<length(ligne); i=i+1) {
            ligne[i] = getCell(questions, numeroQuestion, i);
        }
        return ligne;
    }

    boolean poserQuestion(Joueur joueur, int numeroQuestion) {
        String[] event = getEvent();
        String[] question = getQuestion(numeroQuestion);
        String reponses="";
        String header="";
        int prix = (int) (random()*21);
        for(int i=0; i<stringToInt(question[1]); i=i+1) {
            reponses=reponses+question[i+2]+"\t";
            header=header+ANSI_BLUE+"REPONSE "+ANSI_PURPLE+(i+1)+"\t";
        }
        println(ANSI_GREEN+joueur.nom+ANSI_PURPLE+" à ton tour !");
        if(!equals(event[0], "no_event")) {
            println("[🎲] "+ANSI_YELLOW+event[0]+" "+ANSI_BLUE+event[1]);
        }
        println(ANSI_GREEN+question[0]+ANSI_RESET+"\n (🍬 "+prix+" bonbons)");
        println(header);
        println(reponses);
        return repondreQuestion(joueur, question, event, prix);
    }

    boolean repondreQuestion(Joueur joueur, String[] question, String[] event, int prix) {
        int numeroBonneReponse=stringToInt(question[stringToInt(question[1])+2]); //récupérer le numéro de la bonne réponse en fonction du nombre de réponse
        println("BONNE REPONSE: "+(numeroBonneReponse)+" ("+question[numeroBonneReponse+1]+")");
        print(ANSI_BLUE+"[🍬] "+ANSI_GREEN+"Numéro de la réponse: "+ANSI_PURPLE);
        int reponse = readInt();
        boolean resultat;
        if(reponse==numeroBonneReponse) {
            println("Bonne réponse "+joueur.nom+"\n\n\n\n");
            resultat=true;
        } else {
            println("Mauvaise réponse :("+"\n\n\n\n");('')
            resultat=false;
        }
        appliquerEvent(joueur, event, resultat, prix);
        return resultat;
    }

    void appliquerEvent(Joueur joueur, String[] event, boolean resultat, int prix) {
        if(!equals(event[0], "no_event")) {

            //     // Double Points
            // if (equals(event[0], "Double Points")) {
            //     if (resultat) {
            //         joueur.setPoints(joueur.getPoints() + prix * 2); // Double les points
            //         System.out.println("💥 Double Points ! Tes points sont doublés.");
            //     }
            // } 
            
            // // Perte de Vie
            // else if (equals(event[0], "Perte de Vie")) {
            //     joueur.perdreVie();
            //     System.out.println("💔 Perte de Vie ! Tu as perdu une vie.");
            // } 
            
            // // Question Bonus
            // else if (equals(event[0], "Question Bonus")) {
            //     if (resultat) {
            //         joueur.setPoints(joueur.getPoints() + 2); // Ajoute 2 points
            //         System.out.println("✨ Question Bonus ! Tu gagnes 2 points.");
            //     }
            // } 
            
            // // Récupère une Vie
            // else if (equals(event[0], "Récupère une Vie")) {
            //     if (joueur.getVies() < joueur.getMaxVies()) {
            //         joueur.gagnerVie();
            //         System.out.println("❤️ Récupère une Vie ! Tu récupères une vie.");
            //     } else {
            //         System.out.println("🔴 Tu as déjà toutes tes vies !");
            //     }
            // } 
            
            // // Échange de Points
            // else if (equals(event[0], "Échange de Points")) {
            //     Joueur autreJoueur = choisirJoueurAleatoire(); // Méthode à implémenter
            //     int temp = joueur.getPoints();
            //     joueur.setPoints(autreJoueur.getPoints());
            //     autreJoueur.setPoints(temp);
            //     System.out.println("🔄 Échange de Points ! Tes points ont été échangés avec " + autreJoueur.getNom() + ".");
            // } 
            
            // // Bloque Ton Adversaire
            // else if (equals(event[0], "Bloque Ton Adversaire")) {
            //     Joueur adversaire = choisirJoueurAleatoire(); // Méthode à implémenter
            //     adversaire.setBloque(true); // Suppose que le joueur a un statut "bloqué"
            //     System.out.println("🚫 Bloque Ton Adversaire ! " + adversaire.getNom() + " est bloqué pour un tour.");
            // } 
            
            // // Immunité
            // else if (equals(event[0], "Immunité")) {
            //     if(!resultat) {
            //         joueur.vies=joueur.vies+1;
            //         println("🛡️ Immunité ! Tu n'as pas perdu de vie ce tour.");
            //     }
            // } 
            
            // // Mort instantanée
            // else if (equals(event[0], "Perte Totale")) {
            //     joueur.vies=0;
            //     println("☠️ Perte Totale ! Tous tes points sont perdus.");
            // } 
            
            // // Gain Surprise
            // else if (equals(event[0], "Gain Surprise")) {
            //     int pointsGagnes = (int) (random()*20) + 1;
            //     joueur.points=joueur.points+pointsGagnes;
            //     println("🎁 Gain Surprise ! Tu gagnes " + pointsGagnes + " points.");
            // } 
            
            // // Question Fatale
            // else if (equals(event[0], "Question Fatale")) {
            //     if (!resultat) {
            //         joueur.vies=joueur.vies-2;
            //         println("☠️ Question Fatale ! Mauvaise réponse : tu perds 2 vies.");
            //     }
            // }
        }
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
        }
    }





    void algorithm() {
        // INITIALISER JOUEURS
        clearScreen();
        println(ANSI_BLUE + "[" + "🎮" + ANSI_BLUE + "] " + ANSI_GREEN + "Bienvenue dans '"+nomDuJeu+"'\n" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "📜" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 1: Chaque joueur commence avec 3 vies." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🍬" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 2: Une bonne réponse donne des points, une mauvaise fait perdre une vie." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "✨" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 3: Atteignez 10 bonnes réponses pour gagner !" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🎲" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 4: Certains tours incluent des bonus aléatoires !" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "💔" + ANSI_BLUE + "] " + ANSI_RED + "Règle 5: Si vous perdez vos 3 vies, vous êtes éliminé." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🏆" + ANSI_BLUE + "] " + ANSI_PURPLE + "Bonne chance et amusez-vous bien !\n\n" + ANSI_RESET);
        Joueurs joueurs = CreerJoueurs();

        // INITIALISER DATA
        boolean[] questionsPosees = new boolean[rowCount(questions)];
        initialiserTableauReponses(questionsPosees);

        tour(joueurs, questionsPosees);
    }
}