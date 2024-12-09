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

    boolean poserQuestion(Joueur joueur, int numeroQuestion, Joueurs joueurs) {
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
        println(ANSI_GREEN+question[0]+" "+ANSI_RESET+ ANSI_CYAN+ANSI_UNDERLINE+"(🍬 "+prix+" bonbons)"+ANSI_RESET);
        println(header);
        println(reponses);
        return repondreQuestion(joueur, question, event, prix, joueurs);
    }

    boolean repondreQuestion(Joueur joueur, String[] question, String[] event, int prix, Joueurs joueurs) {
        int numeroBonneReponse=stringToInt(question[stringToInt(question[1])+2]); //récupérer le numéro de la bonne réponse en fonction du nombre de réponse
        print(ANSI_BLUE+"\n[🍬] "+ANSI_GREEN+"Numéro de la réponse: "+ANSI_PURPLE);
        int reponse = readInt();
        boolean resultat;
        if(reponse==numeroBonneReponse) {
            println(ANSI_GREEN+"[✅] Bonne réponse :)"+joueur.nom);
            joueur.points+=prix;
            joueur.bonnesReponses+=1;
            resultat=true;
        } else {
            println(ANSI_RED+"[❌] Mauvaise réponse :(");
            resultat=false;
            joueur.mauvaisesReponses+=-1;
        }
        appliquerEvent(joueur, event, resultat, prix, joueurs);
        delay(2000);
        printStats(joueur);
        return resultat;
    }

    //Afficher les stats joueur
    void printStats(Joueur joueur) {
        println(ANSI_BLUE + "============================");
        println("Statistiques de " + ANSI_PURPLE + joueur.nom + ANSI_RESET);
        println(ANSI_BLUE + "============================" + ANSI_RESET);
        println(ANSI_GREEN + "Points : " + ANSI_YELLOW + joueur.points + ANSI_RESET);
        println(ANSI_GREEN + "Bonnes réponses : " + ANSI_YELLOW + joueur.bonnesReponses + ANSI_RESET);
        println(ANSI_GREEN + "Mauvaises réponses : " + ANSI_YELLOW + joueur.mauvaisesReponses + ANSI_RESET);
        println(ANSI_GREEN + "Vies restantes : " + (joueur.vies>1 ? ANSI_YELLOW : ANSI_RED) + joueur.vies + ANSI_RESET);
        println(ANSI_BLUE + "============================" + ANSI_RESET);
    }


    // EVENTS
    void appliquerEvent(Joueur joueur, String[] event, boolean resultat, int prix, Joueurs joueurs) {
        if(!equals(event[0], "no_event")) {

            // Double Points
            if (equals(event[0], "Double Points")) {
                if (resultat) {
                    joueur.points=joueur.points+prix;
                    println("[💥] Double Points ! Les points de la questions précédentes sont doublés.");
                }
            }
            
            // Question Bonus
            else if (equals(event[0], "Question Bonus")) {
                if (resultat) {
                    joueur.points= joueur.points+10;
                    println("[✨] Question Bonus ! Tu gagnes 10 points.");
                }
            } 
            
            // Récupère une Vie
            else if (equals(event[0], "Récupère une Vie")) {
                joueur.vies=joueur.vies+1;
                println("[❤️] Tu récupères une vie!");
            } 
            
            // Échange de Points
            else if (equals(event[0], "Échange de Points")) {
                if(!(length(joueurs.joueur)==1)) {
                    int numeroJoueurEchanger = (int) (random()*joueurs.nbJoueurs);
                    int temp=joueurs.joueur[numeroJoueurEchanger].points;
                    joueurs.joueur[numeroJoueurEchanger].points=joueur.points;
                    joueur.points=temp;
                    clearScreen();
                    println("[🔄] Échange de Points ! Tes points ont été échangés avec " + joueurs.joueur[numeroJoueurEchanger].nom + ".");
                    printStats(joueur);
                    printStats(joueurs.joueur[numeroJoueurEchanger]);
                    readString();
                }
            } 
            
            // Bloque Ton Adversaire
            else if (equals(event[0], "Bloque Ton Adversaire")) {
                if(!(length(joueurs.joueur)==1)) {
                    println("Choisis un adversaire à bloquer:");
                    String listeJoueurs="";
                    for(int i=0; i<length(joueurs.joueur); i=i+1) {
                        if(!equals(joueurs.joueur[i].nom, joueur.nom)) {
                            listeJoueurs=listeJoueurs+" ["+(i+1)+"] "+joueurs.joueur[i].nom+" ";
                        }
                    }
                    println(listeJoueurs);
                    print("Numéro du joueur à bloquer: ");
                    int numeroJoueurBloque = readInt()-1;
                    joueurs.joueur[numeroJoueurBloque].bloque=true;
                    println(ANSI_BLUE+"[🚫] Bloque Ton Adversaire ! " + ANSI_RED + joueurs.joueur[numeroJoueurBloque].nom + ANSI_BLUE+" est bloqué pour un tour.");
                }
            } 
            
            // Immunité
            else if (equals(event[0], "Immunité")) {
                if(!resultat) {
                    joueur.vies=joueur.vies+1;
                    println("[🛡️] Immunité ! Tu n'as pas perdu de vie ce tour.");
                }
            }
            
            // Mort instantanée
            else if (equals(event[0], "Perte Totale")) {
                if(!resultat) {
                    joueur.vies=0;
                    println("[☠️] Perte Totale ! Tous tes points sont perdus.");
                }
            } 
            
            // Gain Surprise
            else if (equals(event[0], "Gain Surprise")) {
                int pointsGagnes = (int) (random()*20) + 1;
                joueur.points=joueur.points+pointsGagnes;
                println("[🎁] Gain Surprise ! Tu gagnes " + pointsGagnes + " points.");
            } 
            
            // Question Fatale
            else if (equals(event[0], "Question Fatale")) {
                if (!resultat) {
                    joueur.vies=joueur.vies-2;
                    println("[☠️] Question Fatale ! Mauvaise réponse : tu perds 2 vies.");
                }
            }
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
            clearScreen();
            poserQuestion(joueurs.joueur[i], donnerQuestion(questionsPosees), joueurs);
        }
    }





    void algorithm() {
        clearScreen();
        println(ANSI_BLUE + "[" + "🎮" + ANSI_BLUE + "] " + ANSI_GREEN + "Bienvenue dans '"+nomDuJeu+"'\n" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "📜" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 1: Chaque joueur commence avec 3 vies." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🍬" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 2: Une bonne réponse donne des points, une mauvaise fait perdre une vie." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "✨" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 3: Atteignez 10 bonnes réponses pour gagner !" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🎲" + ANSI_BLUE + "] " + ANSI_YELLOW + "Règle 4: Certains tours incluent des bonus aléatoires !" + ANSI_RESET);
        println(ANSI_BLUE + "[" + "💔" + ANSI_BLUE + "] " + ANSI_RED + "Règle 5: Si vous perdez vos 3 vies, vous êtes éliminé." + ANSI_RESET);
        println(ANSI_BLUE + "[" + "🏆" + ANSI_BLUE + "] " + ANSI_PURPLE + "Bonne chance et amusez-vous bien !\n\n" + ANSI_RESET);

        // INITIALISER JOUEURS
        Joueurs joueurs = CreerJoueurs();


        // INITIALISER DATA
        boolean[] questionsPosees = new boolean[rowCount(questions)];
        initialiserTableauReponses(questionsPosees);

        tour(joueurs, questionsPosees);
    }
}