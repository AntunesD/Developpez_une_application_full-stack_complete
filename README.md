# Projet MDD - Monde Du Dév

## Description

Le projet **MDD** (Monde Du Dév) est un réseau social développé dans le cadre d'un projet étudiant sur **OpenClassrooms**. Le but de cette application est d’aider les développeurs en recherche de travail, en facilitant la mise en relation et en encourageant la collaboration entre pairs ayant des intérêts communs. 

L'objectif est de créer un espace où les développeurs peuvent se connecter, échanger des idées, et potentiellement collaborer sur des projets. À terme, **MDD** pourrait devenir une plateforme pour les entreprises à la recherche de profils spécialisés dans le développement.

## Technologies utilisées

- **Backend** : Spring Boot
- **Base de données** : MySQL
- **Frontend** : Angular
- **Style** : Tailwind CSS
- **Authentification** : JSON Web Token (JWT)
- **Gestion de données asynchrones** : RxJS

## Installation

### Prérequis
Avant de commencer, assurez-vous d'avoir installé les outils suivants :
- Java 23 ou supérieur
- Maven
- Node.js et npm
- Angular CLI

### 1. Cloner le repository
Commencez par cloner ce repository sur votre machine locale :

```bash
git clone https://github.com/AntunesD/Developpez_une_application_full-stack_complete
```

### 2. Configuration de la base de données
Vous devez configurer une base de données MySQL. Si nécessaire, vous pouvez utiliser le fichier SQL inclus pour créer la structure de la base de données.

Le fichier est situé à `back/src/main/resources/monde_de_dev.sql`.

1. Créez une base de données MySQL vide.
2. Importez le fichier SQL :


### 3. Installer les dépendances

Avant de lancer les applications, installez les dépendances pour le frontend et le backend :

#### Backend
Dans le dossier `back`, exécutez la commande suivante pour nettoyer le projet et télécharger les dépendances Maven :

```bash
cd back
mvn clean
mvn spring-boot:run
```

#### Frontend
Dans le dossier `front`, exécutez la commande suivante pour installer les dépendances Node.js et démarrer le serveur Angular :

```bash
cd front
npm install
ng serve
```

Le frontend sera accessible sur `http://localhost:4200`, et le backend sur `http://localhost:8080`.

## Utilisation

Une fois l'application en cours d'exécution, vous pouvez :

- Créer un compte en tant que développeur.
- Vous connecter avec vos identifiants.
- Voir les articles publiés par les autres utilisateurs.
- Commenter les articles.
- Suivre des thèmes qui vous intéressent.
- Modifier votre profil pour mettre à jour vos informations.


