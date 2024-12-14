# Projet : Jeu de Quiz avec Événements Aléatoires **QUI VEUT GAGNER DES BONBONS**

## Description
Ce projet est un jeu de quiz multijoueur avec des événements aléatoires qui affectent les scores, les vies et les interactions entre les joueurs. Le jeu est conçu pour être à la fois compétitif et imprévisible, grâce à une variété d'effets qui ajoutent du suspense à chaque tour.

Les joueurs répondent à des questions, gagnent ou perdent des points/vies, et subissent des événements aléatoires qui peuvent changer le cours de la partie.
Ce projet n'est pas encore terminé.

## Fonctionnalités principales
- **Système de quiz** : Les joueurs répondent à des questions pour gagner des points.
- **Système d'événements aléatoires** : 9 événements différents qui modifient le gameplay :
  1. **Double Points** : Les points de la question précédente sont doublés.
  2. **Question Bonus** : 10 points supplémentaires si la réponse est correcte.
  3. **Récupère une Vie** : Le joueur gagne une vie supplémentaire.
  4. **Échange de Points** : Les points du joueur sont échangés avec un adversaire aléatoire.
  5. **Bloque Ton Adversaire** : Un joueur adverse perd son tour.
  6. **Immunité** : Protection contre la perte de vie pendant un tour.
  7. **Mort Instantanée** : Perte immédiate de toutes les vies.
  8. **Gain Surprise** : Ajout aléatoire de 1 à 3 points.
  9. **Question Fatale** : Une mauvaise réponse coûte deux vies.
- **Affichage des scores** : Une fonction dédiée pour afficher un tableau récapitulatif des scores des joueurs.


## Structure du projet
- `Joueur` : Classe représentant un joueur, contenant les attributs (nom, points, vies, état bloqué).
- `Joueurs` : Classe regroupant la liste des joueurs et permettant des interactions entre eux.
- `appliquerEvent` : Fonction principale pour appliquer les événements aléatoires.
- `printTableauScores` : Fonction pour afficher les scores dans un tableau formaté.
- `tour` : Fonction qui détermine le déroulement d'un tour
- `partieTerminee` : Fonction qui détermiune la fin de partie.


## Utilisation
1. Compilez le jeu avec `compile.sh`.
2. Lancez le jeu avec `run.sh`.
3. Entrez le nombre de joueurs et leurs noms.
4. Chaque joueur répond aux questions en tour par tour.
5. À chaque tour, un événement aléatoire peut se produire, modifiant les scores ou les vies des joueurs.
6. Le jeu continue jusqu'à ce qu'il ne reste qu'un seul joueur en vie ou qu'une condition de fin soit atteinte.

## Exemple de sortie
```

Ethan à ton tour !

[❓] Quel animal miaule ? (🍬 12 bonbons)
REPONSE 1 -> Chien
REPONSE 2 -> Chat
REPONSE 3 -> Cheval

[🍬] Numéro de la réponse: **2**
[✅] Bonne réponse :) Ethan


======= Tableau des Scores ========
| JOUEURS           | PTS |  VIES   |
| Ethan             | 12  | ❤️❤️❤️ |
| Romain            | 0   | ❤️❤️   |
====================================

Appuyez pour continuer...
```


## Améliorations à faire
- Ajouter questions Scratch
- Ajouter des niveaux de difficulté pour les questions.
- Enregistrer les scores et statistiques des joueurs dans un fichier ou une base de données.

## Auteur
- **Ethan** et **Romain**