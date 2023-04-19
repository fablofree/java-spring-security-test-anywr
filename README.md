# Anywr Gestion Authentification et Authorisation

Système d'authentification et d'authorisation



### Démarrage de java-spring-security-test en local

java-spring-security-test est une application Spring Boot qui est compilé à l'aide de maven

```shell
git clone https://github.com/fablofree/java-spring-security-test.git
cd java-spring-security-test
./mvnw package
java -jar target/*.jar
```


##### Prérequis avant de démarrer l'application

Installation de [Jdk17 ou plus](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

Installation de [Mysql8](https://dev.mysql.com/downloads/mysql)

Création d'une base de donnée:

- BDD: **java-spring-security-test**

- username: **root**

- password: 

##### Si vous voulez customizer les informations ci-dessus

```shell
java -jar target/*.jar -Dspring.datasource.url=jdbc:mysql://<adresse du serveur[:port]>/<nom bdd>?serverTimezone=EST5EDT -Dspring.datasource.username=<nom du compte> -Dspring.datasource.password=<votre password>
```

##### Autre option du démarrage de l'application

```shell
./mvnw spring-boot:run
```

##### Environnement de développement

[STS](https://spring.io/tools)

[Eclipse](https://www.eclipse.org/downloads/)

[IntelliJ IDEA](https://www.jetbrains.com/idea/download)

Si vous voulez avoir accès aux endpoints :

1. démarrage de l'application


##### Aperçu de la page connexion API

http://localhost:8080/api/v1/users/authenticate
Il existe deux users par défaut: 
- **admin** et 
- **user** avec pour mot de passe 
- **password**
Dans le body remplissez entre username et password puis vous aurez en réponse votre token généré


Utilisez ce token pour acceder aux autres API


###### Mise à jour des modifications

```shell
git status // permet de se rassurer que toutes les modifications sont disponibles
git add . // permet de prendre en compte les modifications
git commit -m "ASSOC-xxx | <commentaire>"
```
###### Ressources utilisées


https://start.spring.io
https://spring.io/
https://docs.liquibase.com/tools-integrations/springboot/springboot.html
https://www.bezkoder.com/spring-boot-jwt-authentication/
https://www.baeldung.com/jackson-ignore-null-fields
