# RecipesHelper
Une application d'assitance pour la réalisation de recette.
Utilise l'internet des objets et la technologie NFC.

### Features
* Lecture de tag NFC contenant des recettes
* Assistant afin de trouver des items dans la cuisine (ingrédients,outils,etc.) via un API Rest du DOMUS
* Fonction de lecture «text-to-speech» pour les directives
* Téléchargement asynchrone des images via Picasso http://square.github.io/picasso/
* Téléchargement asynchrone des informations sur la base de données (AsyncTask)

### Technologies
* NFC
* DOMUS Rest API (toolbox pour la cuisine intelligente)
* TTS
* AsyncTask
* DAO Pattern
* Picasso (librairie pour l'utilisation d'image)
* Postgres SQL pour le téléchargement des informations supplémentaires d'une recette
* Javatuples (librairie pour les différents types de tuples possibles)

### TODO LIST
- [X] Ajouter le support des ingrédients pour «l'assistant»
- [ ] Corriger les nombreux bug visuels
- [X] Description Direction_TextView, réinitialiser le défilement sur Next, Previous
- [X] Ajouter une forme de progression pour le téléchargement des images via picasso 
- [ ] Tester au Labo
- [ ] Réusiner le code 
- [ ] Ajouter des commentaires
- [ ] Ajouter de la documentation JavaDoc



