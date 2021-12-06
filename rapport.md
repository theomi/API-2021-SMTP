% Rapport labo 4 – SMTP
% Louis Hadrien; Mirabile Théo
% Cours d'API, HEIG-VD 2021

---

# Introduction et but

Le but de ce laboratoire est de mettre en pratique les connaissances acquises sur les 
entrées / sorties en Java ainsi que se familiariser avec le protocole SMTP et Docker. Il est demandé de réaliser
un client SMTP permettant d'envoyer des emails forgés (pranks) à une liste de victimes tout en utilisant un serveur
de mocking SMTP. 

# Descriptif de l'implémentation

## Gestion de la configuration (classe `ConfigurationManager`)

Comme cité précédemmant, cet outil de prank doit pouvoir fonctionner sur n'importe quel serveur
SMTP. Pour ce faire, nous avons décidé de créer 1 fichier de configuration contenant 
l'adresse du serveur SMTP, son port ainsi que les identifiants de connexion si nécessaire. 
Ce fichier contient également le nombre de groupes auxquels les plaisantieries seront envoyées. Une seule plaisanterie sera envoyée par groupe.

Deux autres fichiers de configuration existent: 
- `messages.utf8` contenant la liste des diverses "plaisanteries" que l'on souhaite
envoyer.
- `victims.utf8` contenant la liste des diverses victimes que l'on cible

Il est important de noter que les expéditeurs et destinataires (les membres de chaque groupe) sont automatiquement choisis
une fois le programme lancé. Il n'est pas possible de les choisir manuellement. Il n'est également pas possible de choisir 
le message qui est envoyé à chaque groupe. Tout est généré aléatoirement.

Comme décrit ci-dessus, la solution proposée permet une configuration personnalisée permettant 
de s'adapter à tout serveur SMTP tout en spécifiant ses propres victimes.


## Client SMTP (classe `SmtpClient`)

...

## Personnes et groupes

...

## Modélisation des e-mails

...

## Génération des plaisanteries (classe `PrankGenerator`)

Nous avons décidé de créer une classe `PrankGenerator` s'occupant, comme son nom l'indique, 
de la génération des pranks. Cette classe possède une méthode publique `generateMails`permettant
de générer une liste contenant tous les mails à envoyer. Cette méthode est ensuite appelée par le programme
principal une fois la connexion au client SMTP établie. 

Pour générer les mails correctement, la classe `PrankGenerator` possède 3 autres méthodes privées.
La première méthode `generateVictimsList` permet de parser le fichier des victimes afin de vérifier que les emails soient correctement formés.
Cette méthode retourne donc une liste de toutes les victimes.

La deuxième méthode `generateMessage` va, elle aussi, parser le fichier des messages afin de vérifier qu'ils soient correctement formés. 
Cette méthode retourne la liste des messages.

La troisième méthode `generatePranks` s'occupe de créer un prank par groupe (spécifié dans le fichier de config) puis associe à chaque prank
la liste des destinaires (les membres du groupes) ainsi que l'expéditeur. Cette méthode spécifie aussi le message du prank qui sera utilisé

La méthode `generateMails`, fait donc appel aux trois fonctions précédentes afin de générer la liste des mails à envoyer.

# Serveur de _mocking_

# Mode d'emploi

## Comment paramétrer l'application

Pour paramétrer correctement l'application, il existe 3 fichiers.

- `config.properties` - Permet de spécifier l'adresse et le port du serveur SMTP ainsi que
les identifiants de connexion. C'est également dans ce fichier que se trouve le nombre de groupes
souhaité
```
smtpServerAddress=
smtpServerPort=
numberOfGroups=
smtpAuth=[true / false]
smtpUsername=[optionnel]
smtpPassword=[optionnel]
```
- `messages.utf8` - Permet de spécifier les différents messages à envoyer. Chaque message
doit être formaté comme suit :

```txt
Subject : [TITRE DU MAIL]
[CONTENU DU MAIL]
--
Subject : [TITRE DU MAIL]
[CONTENU DU MAIL]
--
```
- `victims.utf8` - Permet de lister les différentes victimes (expéditeur et destinataires des pranks)
Chaque victime doit être formatée comme suit :
```
[PRENOM] [NOM_1] [NOM_2] ...
[EMAIL]
--
[PRENOM] [NOM_1] [NOM_2] ...
[EMAIL]
--
```

## Comment lancer l'application

...

# Diagramme de classes

![Diagramme UML des classes du projet](./uml_v1.svg)

# Conclusion
