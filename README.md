# TeacherLib

Le site a été déployé sur une VM de l'ISTIC, il est disponible à l'URL https://teacherlib.tk. Certains user-agent sont bloqués à cause du reverse proxy Nginx Bunkerized qui blacklist beaucoup de user-agent et d'ip dans sa configuration de base. Cette version a été déployé avec le fichier `docker-compose.yml` du dossier docker.

## Installer localement

Pour tester un déploiment en local, un autre fichier `docker-compose.local.yml` est présent dans le dossier, car le docker-compose précédent requiert une ip routable sur internet pour la demande de signature des certificats https par let's encrypt.

```bash 
cd docker
docker-compose -f docker-compose.local.yml up -d 
```

Browse http://localhost:80

## Tester

Quand on arrive sur la page d'acceuil du site on peut se logger soit en tant qu'étudiant, soit en tant que professeur. La base de données a été pré-remplie avec des données. Les étudiants et professeurs ont donc déjà des rendez-vous sur leur Dashboard et les professeurs ont déjà plusieurs semaines de disponibilités ajoutées. **Nous n'avons pas traiter tous les cas d'erreur nottement au niveau du front donc des erreurs peuvent survenir lors de la prise de renez-vous sur des disponibilités antérieures à la date du jour ou sur l'ajout de disponibilités antérieures à la date du jour côté professeur !** 

**Etudiants**: 

- Paul
    - mail: paul.borie@etudiant.univ-rennes1.fr
    - username: paul
    - password: pass

- Jean
    - mail: jean.derieux@etudiant.univ-rennes1.fr
    - username: jean
    - password: pass 

**Professeurs**:

- Olivier 
    - mail: nicolas.demongeot@etudiant.univ-rennes1.fr
    - username: olivier
    - password: pass
- Grazon
    - mail: nicolas.demongeot@etudiant.univ-rennes1.fr
    - username: anne
    - password: pass

Les mails des prof ont été remplacés par nos mails étudiants pour éviter le spam. Il y a possibilité d'enregister un nouvel utilisateur depuis la page d'acceuil de login avec une adresse mail de test pour tester la fonctionnalité d'envoi des mails. 


## Qu'est ce que c'est ?

TeacherLib est une application de prise de rendez-vous entre étudiants et professeurs. Les professeurs peuvent s'enregistrer sur l'application et se connecter. Ils arrivent alors sur leur dashboard où ils peuvent voir leurs prochains rendez-vous. Ils peuvent également rajouter leurs disponibilités depuis leur Dashboard en cliquant sur **add availabilities**. 
Les étudiants, peuvent s'enregistrer sur l'application et se connecter. Après une connection réussie, ils sont également redirigés vers leur Dashboard où ils peuvent consulter la liste de leur rendez-vous. Ils peuvent alors cliquer sur le bouton **Prendre un rendez-vous** pour être redirigé vers la liste des profs et leurs disponibilités affichés dans un calendrier. Lorsque l'étudiant clique sur une disponibilité d'un professeur, un formulaire apparait, l'étudiant peut y renseigner le nom du rendez-vous ainsi qu'une description et valider. L'étudant et le professeur recevront ainsi un email de confirmation pour la prise du rendez-vous avec un récapitulatif du rendez-vous. L'étudiant ainsi que le professeur concerné par le rendez-vous verront alors apparaître le rendez-vous sur leur Dashboard et la disponibilité correspondante à ce rendez-vous sera supprimé des disponibilités du professeur concerné. 

## Diagramme de classe UML 

![Drag Racing](teacherlib-uml.png)

Nous avons fait pour choix de ne pas faire hériter Student et Teacher d'une classe mère User car nous avons pensé qu'ils représentaient une logique métier forte et différente. Cela a pour effet d'avoir du code quasi similaire dupliqué à plusieurs endroits de l'application. 

## Structuration du Back-end


 - Le package **business**: On y trouve les 6 classes métiers correspondantes aux classes dans le diagramme UML annotés avec les annotations de JPA pour que Hibernate puisse générer les tables correspondantes dans la base de donnée

- Le package **dao**: on y trouve les différents repository qui fournissent les requêtes pour venir interroger la base de données MySql

 - Le package **dto**: on retrouve les classes DTO qui fournissent des objets intermédiaires pour le traitement des données. En effet, certains endpoint Rest récupèrent des requêtes clients des objets qui ne correspondent pas exactement aux objets métiers définis dans le package business. De même certains endpoint ne retournent pas des objets métiers mais des objets avec des attributs en moins. On utilise alors ces objets Dto intermédiaires. 

- Le package **rest**: on y retrouve les différents Controller Rest avec tous les endpoints http de l'api rest. Ils sont divisés ainsi :
/api/session : Ce endpoint gère toutes les requêtes d'authentification : register, login, logout etc...
/api/teacher : Ce endpoint gère toutes les requêtes faites par les professeurs : ajouter une Availability, voir ses rdv etc.... Elles doivent donc être authentifiées
/api/student : Ce endpoint gère toutes les requêtes faites par les étudiants : voir ses rdv, prendre un rdv...Elles doivent donc être authentifiées

- Le package **mail**: Ce package contient la classe service qui permet d'envoyer des mail et également une classe avec les différents tamplate des mails.

- Le package **service**: Ce package contient les classes utilitaires comme la classe DTOMapper qui permet de convertir les objets DTO en objet métier et inversement, une classe utilitaire pour la manipulation des dates ainsi qu'une classe qui permet de charger des données dans la base de données MySql au démarrage

- Le package **aspect**: qui contient une classe qui log toutes les requêtes rest faites sur les endpoint de l'application 

- Le package **swagger**: pour exposer avec une jolie UI les différents endpoints de l'API rest.  


## Authentification

Nous avons fait pour choix d'utiliser une bête authentification par Session. De plus nous avons choisi de persister les Sessions utilisateurs dans la base de donnée MySql. Le problème des sessions est la duplication de dode dans tous les endpoint HTTP pour vérifier le cookie de session et récupérer les infos de l'utilisateur. Peut-être moyen plus propre avec Spring Security mais pas eu le temps (Plus vidéo indisponible sur le site du cours de TAA).

## Optimisation

Nous n'avons pas pensé à la pagination pour certaines requêtes Sql, ce qui pourrait poser certains problèmes de performance pour des requêtes qui rendraient beacoup de résultat comme /api/teacher/teachers qui renvoient la liste de tous les professeurs et toutes leurs disponibilités.

Rajouter différentes fonctionnalités : lieux des rendez-vous parmis une liste prédifinies en base de donnée, demande de confirmation au professeur avant la validation d'un rendez-vous etc...

Aucune encryption des mots de passes en base de donnée non plus, c'est une mauvaise pratique.



 

 
