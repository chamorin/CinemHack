# CinemHack

De nos jours la cybersécurité se trouve souvent au cœur de l’actualité et des films hollywoodiens. Souvent mal représenté, le hacking dans les films et les jeux-vidéo ne représente pas du tout les différentes techniques utilisées par les pirates informatiques par exemple : La Matrice, Watch_Dogs, Deus Ex, CSI, Ghost in the Shell. C’est pourquoi nous avons pensé faire un programme pour tester les vulnérabilités des réseaux domestiques qui respectent cette image hollywoodienne. Ceci permettra d’informer les gens sur la cybersécurité et montrer que tout le monde est concerné. Notre programme offrira à l’utilisateur un environnement graphique « user-friendly » sans chiffre ni code complexe. Permettant ainsi à n’importe qui de tester facilement les vulnérabilités d’un réseau domestique au lieu d’entrer plusieurs commandes compliquées et de recueillir de nombreuses informations. Cependant, pour insister sur l’aspect de caricature des films, l’utilisateur devra accomplir des mini-jeux avant chaque test. Tout ceci sur un Raspberry pi avec écran tactile pour optimiser la portabilité du gadget. Les tests possibles sur le réseau domestique seront les suivants:

-	Scanner les réseaux environnants et ses utilisateurs et choisir sa cible
-	Cloner un réseau
-	Pirater un mot de passe
-	Enregistrer des entrées au clavier des utilisateurs du réseau
-	Retirer l’accès à une ou plusieurs personnes sur le réseau

Tel qu’il est décrit plus haut, afin de rendre l’expérience hollywoodienne encore plus immersive, chaque test de vulnérabilité sera exécuté seulement si l’utilisateur réussi à accomplir le mini-jeu en lien avec le test choisi. Les mini-jeux seront les suivants:

-	Former un mot avec les lettres mélangées
-	Un jeu où l'utilisateur doit entrer des mots le plus rapidement possible
-	Un jeu de mémoire
-	Un labyrinthe dont on ne doit pas toucher les murs
-	Taper du doigt les boutons lorsqu’ils s’allument

Tous ces jeux devront être accomplis dans les temps donnés sinon le test ne sera pas exécuté.

Description en détails des mini-jeux
Jeu de lumière :
Neuf lumières sont affichées en grille, elles s’allument aléatoirement, le but est simple, cliquer sur la lumière avant qu'elle ne s’éteigne. Le joueur devra obtenir un score donné avant que la limite de temps soit écoulée sans perdre trop de points de vie.

Mots mélangés :
Les lettres mélangées d'un mot sont affichées à l'écran. Le joueur doit trouver à quel mot les lettres appartiennent. Le joueur doit résoudre environ trois mots mélangés pour réussir. Une limite de temps est attribuée pour trouver les mots.

Chaînes de caractères : 
Un mot est présenté au joueur et il doit l'écrire correctement le plus rapidement possible. Une barre de temps indique combien de temps il lui reste pour écrire le nombre de mots donné.  Plus le temps avance, plus les mots deviennent longs et compliqués. À chaque fois que le joueur écrit correctement un mot, des éléments graphiques sont affichés à l'écran pour donner l'illusion que le joueur est un pirate informatique professionnel.

Jeu de mémoire :
Six images sont cachées. Le joueur doit cliquer sur une image pour la révéler. L'image se recache après quelques secondes. Quand le joueur révèle deux images identiques, elles restent affichées. Le joueur gagne une fois que toutes les images ont été révélées.

Labyrinthe :
Le jeu du labyrinthe est simple, le joueur déplace son doigt sur l'écran tactile en tentant de ne pas toucher les murs. Des effets sonores et graphiques seront ajoutés pour accroître le sentiment d’immersion. Par exemple, une alarme retentit si le joueur touche à un mur.
