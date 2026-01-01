# Guide d'Utilisation des Scripts JMeter

## Installation de JMeter

1. Télécharger Apache JMeter : https://jmeter.apache.org/download_jmeter.cgi
2. Extraire l'archive
3. Lancer JMeter : `bin/jmeter.bat` (Windows) ou `bin/jmeter.sh` (Linux/Mac)

## Préparation

### 1. Créer des Données de Test

Avant de lancer les tests, vous devez créer au moins 1 client et 1 chambre :

```bash
curl -X POST http://localhost:8080/api/rest/clients ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Dupont\",\"prenom\":\"Jean\",\"email\":\"jean@mail.com\",\"telephone\":\"0601020304\"}"

curl -X POST http://localhost:8080/api/rest/chambres ^
  -H "Content-Type: application/json" ^
  -d "{\"type\":\"Double\",\"prix\":150.0,\"disponible\":true}"
```

Note : Notez les IDs retournés (généralement 1 pour le premier client et 1 pour la première chambre).

## Utilisation des Scripts

### REST API

1. Ouvrir JMeter
2. File → Open → `REST-Test-Plan.jmx`
3. Modifier les variables si nécessaire :
   - `THREADS` : Nombre d'utilisateurs simultanés (10, 100, 500, 1000)
   - `BASE_URL` : Adresse du serveur (par défaut : localhost)
   - `PORT` : Port du serveur (par défaut : 8080)
4. Cliquer sur le bouton vert "Start" (▶)
5. Consulter les résultats dans "Summary Report"

### GraphQL API

1. Ouvrir JMeter
2. File → Open → `GraphQL-Test-Plan.jmx`
3. Modifier la variable `THREADS` (10, 100, 500, 1000)
4. Lancer le test (bouton vert ▶)
5. Consulter "Summary Report"

### SOAP API

Pour SOAP, vous devez utiliser **SoapUI** car JMeter ne supporte pas nativement les services SOAP complexes :

1. Télécharger SoapUI : https://www.soapui.org/downloads/soapui/
2. Créer un nouveau projet SOAP
3. WSDL : `http://localhost:8080/soap-api/reservations?wsdl`
4. Configurer un Load Test avec 10/100/500/1000 threads

### gRPC API

Pour gRPC, utilisez **ghz** (outil de benchmarking gRPC) :

```bash
# Installation
go install github.com/bojand/ghz/cmd/ghz@latest

# Test basique
ghz --insecure --proto src/main/proto/hotel.proto --call HotelServiceGrpc.GetReservation -d '{"id":1}' -n 1000 -c 100 localhost:9090
```

## Métriques à Noter

Pour chaque test, notez dans vos tableaux :

### Latence (ms)
- **Average** : Temps de réponse moyen
- **Median** : Temps de réponse médian
- **90% Line** : 90e percentile
- **95% Line** : 95e percentile
- **99% Line** : 99e percentile
- **Min** : Temps minimum
- **Max** : Temps maximum

### Débit (req/s)
- **Throughput** : Nombre de requêtes par seconde

### Taux d'Erreur (%)
- **Error %** : Pourcentage d'erreurs

## Exporter les Résultats

1. Dans JMeter, clic droit sur "Summary Report"
2. Save Table Data
3. Choisir un nom de fichier (ex: `rest-100-threads.csv`)
4. Ouvrir dans Excel pour analyse

## Scénarios de Test Recommandés

Pour chaque protocole, testez avec :

| Threads | Loops | Ramp-up | Durée Totale |
|---------|-------|---------|--------------|
| 10      | 10    | 5s      | ~10s         |
| 100     | 10    | 10s     | ~20s         |
| 500     | 10    | 20s     | ~40s         |
| 1000    | 10    | 30s     | ~60s         |

## Monitoring des Ressources

Pendant les tests, surveillez :

**Windows** :
- Ouvrir Task Manager (Ctrl+Shift+Esc)
- Onglet "Performance"
- Noter CPU% et Mémoire (MB)

**Linux** :
```bash
top
# ou
htop
```

## Conseils

1. **Échauffement** : Lancez un petit test (10 threads) avant le vrai test pour "échauffer" l'application
2. **Entre chaque test** : Attendez 30 secondes entre deux tests différents
3. **Répétez** : Lancez chaque test 3 fois et prenez la moyenne
4. **Base de données** : Videz la base entre les tests pour des résultats cohérents :
   ```sql
   DELETE FROM reservation;
   DELETE FROM client;
   DELETE FROM chambre;
   ```
