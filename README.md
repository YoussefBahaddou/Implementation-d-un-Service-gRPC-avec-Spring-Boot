# Étude de Cas : Analyse Scalabilité/Performance des APIs Modernes

<img width="1920" height="1020" alt="rest api 1000 threads" src="https://github.com/user-attachments/assets/4ba2b5dd-6bc3-43ae-8436-87bf64577142" />
<img width="1920" height="1020" alt="rest api 500 threads" src="https://github.com/user-attachments/assets/70dc4619-b08b-43e6-81b0-f2e2eb1a3afd" />
<img width="1920" height="1020" alt="rest api 100 threads" src="https://github.com/user-attachments/assets/5ff977f8-ab1b-49ea-8fbc-9e25b4348db1" />
<img width="1920" height="1020" alt="rest api 10 threads" src="https://github.com/user-attachments/assets/1078d644-b7d0-408a-8bb8-486b16646e9c" />
<img width="1920" height="1020" alt="graphqL api 1000 threads" src="https://github.com/user-attachments/assets/e228b910-9d3f-4ae6-bdde-1ce30bcf734a" />
<img width="1920" height="1020" alt="graphqL api 500 threads" src="https://github.com/user-attachments/assets/6d68d3a7-f88a-4362-a026-d2893ebf18b8" />
<img width="1920" height="1020" alt="graphqL api 100 threads" src="https://github.com/user-attachments/assets/77f8972f-350a-458a-8dcd-be400e23a792" />
<img width="1920" height="1020" alt="graphqL api 10 threads" src="https://github.com/user-attachments/assets/6a395066-5ecf-45d2-a478-5c76683cb340" />

Ce projet implémente une plateforme de réservation d'hôtels exposant les **mêmes** fonctionnalités métier via 4 protocoles différents pour comparer leurs performances :
- **REST**
- **SOAP**
- **GraphQL**
- **gRPC**

## Prérequis
- Java 17+
- Maven
- Docker (pour la base de données)

## Installation et Démarrage

1. **Démarrer la Base de Données**
   ```bash
   docker-compose up -d
   ```
   Ceci démarrera une instance MySQL sur le port 3307.

2. **Compiler le Projet**
   ```bash
   mvn clean install
   ```
   Cette étape va :
   - Générer les sources gRPC à partir de `src/main/proto/hotel.proto`.
   - Compiler tout le code Java.

3. **Lancer l'Application**
   ```bash
   mvn spring-boot:run
   ```
   L'application démarrera sur le port **8080** (Web) et **9090** (gRPC).

## Accès aux APIs

### REST
- URL de base : `http://localhost:8080/api/rest`
- Exemples :
    - `GET /reservations`
    - `POST /reservations?clientId=1&chambreId=1`

### SOAP
- WSDL : `http://localhost:8080/services/reservations?wsdl`
- Endpoint : `http://localhost:8080/services/reservations`

### GraphQL
- Interface GraphiQL : `http://localhost:8080/graphiql`
- Endpoint : `http://localhost:8080/graphql`
- Exemple de Query :
  ```graphql
  query {
    getAllReservations {
      id
      client { nom }
    }
  }
  ```

### gRPC
- Serveur : `localhost:9090`
- Outil recommandé : BloomRPC ou Postman (avec support gRPC).

## Tests de Performance
Utilisez les outils suivants pour tester les endpoints :
- **JMeter** : Scénarios complexes HTTP/SOAP.
- **k6** : Tests de charge scriptés en JS.
- **Gatling** : Tests haute performance.
