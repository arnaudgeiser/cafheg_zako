# CAFHEG ZAKO

Projet de pratique de développement. PDD

#### 1. Compréhension du projet source TDD et Refactoring
Cette étape a été cruciale et très intéressante. Il a 
fallu prendre en main le projet intégralement et comprendre ce que chaque
classe faisait. J'ai commencé par le projet d'Industrialisation du logiciel, 
ce qui m'as permis de commencer les étapes de PDD avec déjà une bonne compréhension.

En effet j'avais déjà testé l'API REST, manipuler le pom.xml et étudier les différentes
librairies disponibles dans ce projet.

###### 1.2 REST API
Afin de prendre en main l'API, j'ai utilisé Postman, qui permet de créer et manipuler des
requêtes POST et GET très simplement. J'en ai créé quelque unes afin de mieux comprendre
le fonctionnement de l'application.

![alt text](https://github.com/[username]/[reponame]/blob/[branch]/images/TEST_API_0.png?raw=true"Test API 0")

En ayant déjà configuré le déploiement de l'application sur TOMCAT (Surtout utilisé avec la branche Master), 
j'ai pu tester tout au long du projet directement les méthodes développées via l'API, sans lancer l'application car
les déploiements étaient également automatisés.

![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/TEST_API_1.png "Test API 1")

###### 1.2 TDD
Afin de pratiqué le TDD, j'ai effectué une 
batterie de tests sur les méthodes: 
* getParentDroitAllocation
* updateallocataire

###### 1.2.1 getParentDroitAllocation
Comme demandé, j'ai commencé par faire des tests unitaires afin de contrôler le fonctionnement de cette méthode.
Je me suis vite aperçu qu'elle ne réagissait pas de la manière souhaitée. En mauvais reflex de programmeur débutant, je
n'ai pas complétement et intégralement suivi les principes du TDD car j'ai créé du code, petit à petit, à mesure que mes tests
étaient prêt. J'ai mieux respecté le principe par la suite.

![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/TDD_0.png "Test Drive Development 1")

###### 1.2.1 updateallocataire
La création de ce service a été parfaite, pour moi, pour appliquer le TDD dans les règles de l'art. J'avais déjà créé la méthode
"removeAllocataire", ce qui m'as permis d'être reès à l'aise avec le projet, ainsi que l'API Rest. J'ai donc créé intégralement ma batterie
de tests, avant de coder quoique ce soit.

![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/TDD_1.png "Test Drive Development 2")
Le coverage a été assuré.
![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/TDD_2.png "Test Drive Development 3")

Je trouve que le TDD est difficile à appliquer car il faut penser a tous les comportements d'un service, au début, étape à laquelle normalement,
je n'ai jamais tout en tête. Ce principe demande également beaucoup de rigueur. Ca a été un exercice intéressant et introspectif sur ma façon de coder.

###### 1.2 Refactoring
J'ai choisi de changer la structure de donnée de "getParentDroitAllocation" de Map<String, Object> à 
Un objet <Famille> qui contient deux <Parent> et un <Enfant>. Il a fallu de ce fait, refaire entièrement la méthode. Ainsi que
tous les tests. En effet, il vaut mieux être certain de nos choix en termes de structure de données avant de commencer à développer.


#### 2. Nouveaux services REST
J'ai choisi de directement implémenter ces méthodes dans l'API Rest.

###### 2.1 removeAllocataire 
En utilisant la méthode déjà existante findVersementParentEnfantParMois, qui retourne une liste de VersementParentParMois, et en y appliquant un un filtre sur un stream,
on peut trouver si un versement a déjà été effectué pour un allocataire. Si aucun n'est trouvé, on peut alors effacer l'allocataire.
![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/remove_allocataire_0.png "Remove Allocataire 1")
![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/remove_allocataire_1.png "Remove Allocataire 2")

###### 2.2 updateAllocataire 
La mise à jour se fait uniquement sur le nom et/ou le prénom, et n'est effectué uniquement si un changement est effectif.
La méthode findById d'AllocataireMapper, permet de s'assurer que le nom et/ou le prénom a bien été modifié.
J'ai appliqué intégralement les principes du TDD pour l'elaboration de cette méthode.

Le premier appel modifie bien l'allocataire numéro 14.
![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/update_allocataire_0.png "Remove Allocataire 1")
Impossible de le modifié une deuxième fois car le nom et le prénom sont identique.
![alt text](https://github.com/zachariegossi/cafheg_zako/blob/master/images/update_allocataire_1.png "Remove Allocataire 2")

#### 3. Standard de code et format
Chapitre rapidement effectué, mais intéressant car je n'ai jamais changé de format de code lors de mes
études et de mon travail. Il est bon de savoir qu'on peut partager à l'ensemble des participants d'une projet,
des standards communs. Le formatage se fait a la sauvegarde de chaque document, ainsi qu'à la saisie de Ctrl+Alt+S.

#### 4. Loggers

J'ai ajouté des logs de type "Info" dans les services précedement créés.
###### 4.1 Debug dans la console (Ainsi que tout les autres niveaux supérieurs.)
Définition du "root-level" à "debug", avec un appender "Console" permet de loguer toutes les traces dans la console
```xml
  <!-- Console -->
  <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%msg%n</pattern>
    </encoder>
  </appender>
  <root level="debug">
    <appender-ref ref="CONSOLE"/>
  </root>
```

###### 4.2 Info dans un fichier journalier (Ainsi que le niveau Error).
J'ai utilisé un autre appender "FILE_INFO" de type "RollingFileAppender". Il a fallu utiliser une 
variable timestamp pour générer le nom du fichier différemment chaque jour. J'ai choisi de loguer ce niveau
pour le répertoire "ch.hearc.cafheg", car les services que j'ai créés, se trouvent dans les classes déjà proposées 
dans le projet initiale.
```xml
  <timestamp key="timestamp" datePattern="dd.MM.yyyy"/>
  <appender name="FILE_INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>${INFO_LOG_PATH}info_${timestamp}.log</file>
  </appender>
```

###### 4.3 Error dans un autre fichier.
Afin de ne tracer que les logs de niveau "Error", j'ai appliqué un filtre. Ce système de log est actif
pour tout les descendant de "ch".
```xml
    <filter class="ch.qos.logback.classic.filter.LevelFilter">
      <level>ERROR</level>
      <onMatch>ACCEPT</onMatch>
      <onMismatch>DENY</onMismatch>
    </filter>
```
