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


    //Crée le tableau de joueurs à l'aide du nombre de l'entrée utilisateur 
    Joueurs CreerJoueurs() {
        println("Combien de joueurs êtes-vous?"); //demander le nombre de joueurs
        int nombreJoueurs = readInt();
        Joueurs tab = new Joueurs();
        tab.joueur = new Joueur[nombreJoueurs];
        for(int i=0; i<nombreJoueurs; i=i+1) {
            println("Insérez le nom du joueur numéro "+ANSI_BLUE+(i+1)+ANSI_RESET+": "); //demander le nom de chaque joueur numéro i
            tab.joueur[i] = newJoueur(readString());
            tab.nbJoueurs=tab.nbJoueurs+1;
        }
        return tab;
    }

    //Retourner un tableau de String d'une ligne au hasard du fichier events.csv
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

    //récupérer un tableau de String d'une ligne aléatoire du fichier questions.csv
    String[] getQuestion(int numeroQuestion) {
        String[] ligne = new String[columnCount(questions, numeroQuestion)];
        for(int i=0; i<length(ligne); i=i+1) {
            ligne[i] = getCell(questions, numeroQuestion, i);
        }
        return ligne;
    }

    boolean poserQuestion(Joueur joueur, int numeroQuestion, Joueurs joueurs) {
        String[] event = getEvent();
        String[] question = getQuestion(numeroQuestion);
        int prix = (int) (random()*11)+10;

        println(ANSI_GREEN+joueur.nom+ANSI_PURPLE+" à ton tour !\n");
        if(!equals(event[0], "no_event")) {
            println("[🎲] "+ANSI_YELLOW+event[0]+" "+ANSI_BLUE+event[1]);
        }

        println(ANSI_GREEN+ "[❓] " +question[0]+ANSI_RESET+ ANSI_CYAN+" (🍬 "+prix+" bonbons)"+ANSI_RESET);
        
        afficherQuestion(question);
        return repondreQuestion(joueur, question, event, prix, joueurs);
    }

    void afficherQuestion(String[] question){
        String header = "";
        String reponses ="";
        int position = 0;

        for(int i=0; i<stringToInt(question[1]); i=i+1) {
            print(ANSI_BLUE+"REPONSE "+ANSI_PURPLE+(i+1)+" -> ");
            print(question[i+2]);
            println();
        }
    }

    //Traîter les entrées utilisateurs et quelques affichages
    boolean repondreQuestion(Joueur joueur, String[] question, String[] event, int prix, Joueurs joueurs) {
        int numeroBonneReponse=stringToInt(question[stringToInt(question[1])+2]); //récupérer le numéro de la bonne réponse en fonction du nombre de réponse
        print(ANSI_BLUE+"\n[🍬] "+ANSI_GREEN+"Numéro de la réponse: "+ANSI_PURPLE);
        int reponse = readInt();
        boolean resultat;
        if(reponse==numeroBonneReponse) {
            println(ANSI_GREEN+"[✅] Bonne réponse :) "+joueur.nom);
            joueur.points+=prix;
            joueur.bonnesReponses+=1;
            resultat=true;
        } else {
            println(ANSI_RED+"[❌] Mauvaise réponse :(");
            resultat=false;
            joueur.mauvaisesReponses+=1;
            joueur.vies=joueur.vies-1;
        }
        appliquerEvent(joueur, event, resultat, prix, joueurs);
        if(joueurElimine(joueur)) {
            println("[☠️ ] Vous-êtes éliminé.");
        }
        printStats(joueur);
        println("Appuyez pour continuer...");
        readString();
        return resultat;
    }

    //Afficher les stats joueur
    void printStats(Joueur joueur) {
        println(ANSI_BLUE   + "============================");
        println(ANSI_PURPLE + "📊 Statistiques de " + joueur.nom + " 📊" + ANSI_RESET);
        println(ANSI_BLUE   + "============================" + ANSI_RESET);
        println(ANSI_GREEN  + "[🍬] Points : " + ANSI_YELLOW + joueur.points + ANSI_RESET);
        println(ANSI_GREEN  + "[✅] Bonnes réponses : " + ANSI_YELLOW + joueur.bonnesReponses + ANSI_RESET);
        println(ANSI_GREEN  + "[❌] Mauvaises réponses : " + ANSI_YELLOW + joueur.mauvaisesReponses + ANSI_RESET);
        println(ANSI_GREEN  + "[❤️] Vies restantes : " + viesToString(joueur.vies) + ANSI_RESET);
        println(ANSI_BLUE   + "============================" + ANSI_RESET);
    }

    void printTableauScores(Joueurs joueurs) {
        clearScreen();
        println(ANSI_BLUE + "\n=========================== Tableau des Scores ============================" + ANSI_RESET);
        //AFFICHAGE HEADER
        println(ANSI_YELLOW+"|"+ANSI_PURPLE+" JOUEURS          "+ANSI_YELLOW+" | "+ANSI_RED+"PTS "+ANSI_YELLOW+"| "+ANSI_GREEN+" VIES  "+ANSI_YELLOW+"| BONNES REPONSES | MAUVAISES REPONSES |");
        // Parcours des joueurs pour afficher leurs stats
        for (int i = 0; i < joueurs.nbJoueurs; i++) {
            Joueur joueur = joueurs.joueur[i];
            String nom = joueur.nom;
            int points = joueur.points;
            int vies = joueur.vies;
            int bonnesReponses = joueur.bonnesReponses;
            int mauvaisesReponses = joueur.mauvaisesReponses;
            // Affichage des stats pour chaque joueur
            println(ANSI_YELLOW+"| "+ANSI_PURPLE+nom + genererCaracteres(18-length(nom), ' ')+
            ANSI_YELLOW+"| "+ANSI_RED+points+genererCaracteres(4-length(""+points), ' ')+ANSI_YELLOW
            +"| "+ viesToString(vies)+genererCaracteres((3-vies)*2, ' ') + ANSI_YELLOW+" |"+
            genererCaracteres(17/2, ' ')+bonnesReponses+ genererCaracteres((17/2)-length(""+bonnesReponses)+1, ' ') +"|"+
            genererCaracteres(20/2, ' ') + mauvaisesReponses + genererCaracteres((20/2)-length(""+mauvaisesReponses), ' ')+"|");
        }

        println(ANSI_BLUE + "===========================================================================" + ANSI_RESET);
    }

    boolean joueurElimine(Joueur joueur) {
        boolean vf=false;
        if(joueur.vies<=0) {
            vf=true;
        }
        return vf;
    }

    String genererCaracteres(int nombre, char car) {
        String generation="";
        for(int i=0; i<nombre; i=i+1) {
            generation=generation+car;
        }
        return generation;
    }


    //Retourner un string avec le nombre de vies por l'affichage
    String viesToString(int nombreDeVies) {
        String affichage="";
        for(int i=0; i<nombreDeVies; i=i+1) {
            affichage=affichage+"❤️ ";
        }
        return affichage;
    }

    // EVENTS
    void appliquerEvent(Joueur joueur, String[] event, boolean resultat, int prix, Joueurs joueurs) {
        if (!equals(event[0], "no_event")) {
            // Double Points
            if (equals(event[0], "Double Points")) {
                if (resultat) {
                    joueur.points = joueur.points + prix;
                    println(ANSI_YELLOW + "[💥] Double Points ! " + ANSI_RESET + "Les points de la question précédente sont doublés.");
                }
            }

            // Question Bonus
            else if (equals(event[0], "Question Bonus")) {
                if (resultat) {
                    joueur.points = joueur.points + 10;
                    println(ANSI_GREEN + "[✨] Question Bonus ! " + ANSI_RESET + "Tu gagnes 10 points supplémentaires.");
                }
            }

            // Récupère une Vie
            else if (equals(event[0], "Récupère une Vie")) {
                if(!(joueur.vies >= 3)) {
                    joueur.vies = joueur.vies + 1;
                    println(ANSI_RED + "[❤️] Récupère une Vie ! " + ANSI_RESET + "Félicitations, tu récupères une vie !");
                }
            }

            // Échange de Points
            else if (equals(event[0], "Échange de Points")) {
                if (!(length(joueurs.joueur) == 1)) {
                    int numeroJoueurEchanger = (int) (random() * joueurs.nbJoueurs);
                    int temp = joueurs.joueur[numeroJoueurEchanger].points;
                    joueurs.joueur[numeroJoueurEchanger].points = joueur.points;
                    joueur.points = temp;
                    clearScreen();
                    println(ANSI_BLUE + "[🔄] Échange de Points ! " + ANSI_RESET + "Tes points ont été échangés avec " + joueurs.joueur[numeroJoueurEchanger].nom + ".");
                    printStats(joueur);
                    printStats(joueurs.joueur[numeroJoueurEchanger]);
                    print("Appuyez sur entrée pour continuer...");
                    readString();
                }
            }

            // Bloque Ton Adversaire
            else if (equals(event[0], "Bloque Ton Adversaire")) {
                if (!(length(joueurs.joueur) == 1)) {
                    println("Choisis un adversaire à bloquer:");
                    String listeJoueurs = "";
                    for (int i = 0; i < length(joueurs.joueur); i = i + 1) {
                        if (!equals(joueurs.joueur[i].nom, joueur.nom)) {
                            listeJoueurs = listeJoueurs + " [" + (i + 1) + "] " + joueurs.joueur[i].nom + " ";
                        }
                    }
                    println(listeJoueurs);
                    print("Numéro du joueur à bloquer: ");
                    int numeroJoueurBloque = readInt() - 1;
                    joueurs.joueur[numeroJoueurBloque].bloque = true;
                    println(ANSI_BLUE + "[🚫] Bloque Ton Adversaire ! " + ANSI_RED + joueurs.joueur[numeroJoueurBloque].nom + ANSI_BLUE + " est bloqué pour un tour." + ANSI_RESET);
                }
            }

            // Immunité
            else if (equals(event[0], "Immunité")) {
                if (!resultat) {
                    joueur.vies = joueur.vies + 1;
                    println(ANSI_CYAN + "[🛡️] Immunité ! " + ANSI_RESET + "Tu ne perds pas de vie ce tour.");
                }
            }

            // Mort instantanée
            else if (equals(event[0], "Mort instantanée")) {
                if (!resultat) {
                    joueur.vies = 0;
                    println(ANSI_RED + "[☠️ ] Mort instantanée ! " + ANSI_RESET + "Tu es éliminé !");
                }
            }

            // Gain Surprise
            else if (equals(event[0], "Gain Surprise")) {
                int pointsGagnes = (int) (random() * 3) + 1; // Gain aléatoire entre 1 et 3
                joueur.points = joueur.points + pointsGagnes;
                println(ANSI_GREEN + "[🎁] Gain Surprise ! " + ANSI_RESET + "Tu gagnes " + pointsGagnes + " points.");
            }

            // Question Fatale
            else if (equals(event[0], "Question Fatale")) {
                if (!resultat) {
                    joueur.vies = joueur.vies - 2;
                    println(ANSI_RED + "[☠️] Question Fatale ! " + ANSI_RESET + "Une seule erreur et tu perds 2 vies !");
                }
            }
        }
    }


    int donnerQuestion(boolean[] questionsPosees) {
        int numeroQuestion=(int) (random()*rowCount(questions));
        int compteur=0;
        while((numeroQuestion==0 || questionsPosees[numeroQuestion]) && compteur<length(questionsPosees)) {
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

    boolean partieTerminee(Joueurs joueurs) {
        boolean termine=false;
        int compteur=0;
        int elimines=0;
        while(compteur<length(joueurs.joueur) && !termine) {
            if(joueurElimine(joueurs.joueur[compteur])) {
                elimines=elimines+1;
            }
            if(joueurs.joueur[compteur].bonnesReponses>=10) { // FINI SI UN JOUEUR A DIX REPONSES
                termine=true;
            }

            compteur=compteur+1;
        }

        if(compteur==elimines) { // FINI SI TOUS LES JOUEURS SONT MORTS
            termine=true;
        }
        return termine;
    }

    void tour(Joueurs joueurs, boolean[] questionsPosees) {
        for(int i=0; i<length(joueurs.joueur); i=i+1) {
            if(!joueurElimine(joueurs.joueur[i])) {
                clearScreen();
                poserQuestion(joueurs.joueur[i], donnerQuestion(questionsPosees), joueurs);
            }
        }
        printTableauScores(joueurs);
        print("\nAppuyez pour continuer...");
        readString();
    }





    void algorithm() {
        clearScreen();
        println(ANSI_BLUE + "[🎮]" + ANSI_GREEN  + "Bienvenue dans '" + nomDuJeu + "'\n" + ANSI_RESET);
        println(ANSI_BLUE + "[📜]" + ANSI_YELLOW + "Règle 1: Chaque joueur commence avec 3 vies." + ANSI_RESET);
        println(ANSI_BLUE + "[🍬]" + ANSI_YELLOW + "Règle 2: Une bonne réponse donne des points, une mauvaise fait perdre une vie." + ANSI_RESET);
        println(ANSI_BLUE + "[✨]" + ANSI_YELLOW + "Règle 3: Atteignez 10 bonnes réponses pour gagner !" + ANSI_RESET);
        println(ANSI_BLUE + "[🎲]" + ANSI_YELLOW + "Règle 4: Certains tours incluent des bonus aléatoires !" + ANSI_RESET);
        println(ANSI_BLUE + "[💔]" + ANSI_RED    + "Règle 5: Si vous perdez vos 3 vies, vous êtes éliminé." + ANSI_RESET);
        println(ANSI_BLUE + "[🏆]" + ANSI_PURPLE + "Bonne chance et amusez-vous bien !\n\n" + ANSI_RESET);

        // INITIALISER JOUEURS
        Joueurs joueurs = CreerJoueurs();
        
        // INITIALISER DATA
        boolean[] questionsPosees = new boolean[rowCount(questions)];
        initialiserTableauReponses(questionsPosees);

        while(!partieTerminee(joueurs)) {
            tour(joueurs, questionsPosees);
        }

        printTableauScores(joueurs);
        println(ANSI_BLUE+"[>]"+ANSI_PURPLE+" Partie terminée bravo aux joueurs!");
    }
}