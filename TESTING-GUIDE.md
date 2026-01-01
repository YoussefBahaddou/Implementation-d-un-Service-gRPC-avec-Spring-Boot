# Guide Complet de Test de Performance

## Étape 1 : Préparer les Données

Avant de commencer les tests, créez des données initiales :

```powershell
cd c:\Users\asus\Desktop\hotel-api-performance
.\scripts\create-test-data.ps1
```

Ceci créera :
- 3 clients (IDs: 1, 2, 3)
- 3 chambres (IDs: 1, 2, 3)
- 2 réservations initiales

## Étape 2 : Lancer JMeter

### Installation
1. Télécharger JMeter : https://jmeter.apache.org/download_jmeter.cgi
2. Extraire dans `C:\jmeter`
3. Lancer : `C:\jmeter\bin\jmeter.bat`

### Ouvrir les Plans de Test
- REST : `jmeter\REST-Test-Plan.jmx`
- GraphQL : `jmeter\GraphQL-Test-Plan.jmx`

## Étape 3 : Exécuter les Tests

Pour chaque protocole, testez avec 4 niveaux de charge :

| Test | Threads | Configuration JMeter |
|------|---------|---------------------|
| 1    | 10      | Modifier `THREADS` = 10 |
| 2    | 100     | Modifier `THREADS` = 100 |
| 3    | 500     | Modifier `THREADS` = 500 |
| 4    | 1000    | Modifier `THREADS` = 1000 |

### Procédure par Test

1. Ouvrir le plan de test dans JMeter
2. Cliquer sur "Test Plan" dans l'arborescence
3. Modifier la variable `THREADS` (User Defined Variables)
4. Sauvegarder (Ctrl+S)
5. Lancer le test (bouton vert ▶)
6. Attendre la fin du test
7. Noter les résultats du "Summary Report"
8. Exporter : Clic droit sur "Summary Report" → Save Table Data
9. Attendre 30 secondes avant le test suivant

## Étape 4 : Collecter les Métriques

### Tableau 1 : Performances - Latence (ms)

Pour chaque test, notez dans Excel :

| Requêtes | REST (ms) | SOAP (ms) | GraphQL (ms) | gRPC (ms) |
|----------|-----------|-----------|--------------|-----------|
| 10       |           |           |              |           |
| 100      |           |           |              |           |
| 500      |           |           |              |           |
| 1000     |           |           |              |           |

Valeur à prendre : **Average** dans Summary Report

### Tableau 2 : Performances - Débit (req/s)

| Requêtes | REST | SOAP | GraphQL | gRPC |
|----------|------|------|---------|------|
| 10       |      |      |         |      |
| 100      |      |      |         |      |
| 500      |      |      |         |      |
| 1000     |      |      |         |      |

Valeur à prendre : **Throughput** dans Summary Report

### Tableau 3 : Consommation des Ressources

Pendant chaque test :
1. Ouvrir Task Manager (Ctrl+Shift+Esc)
2. Onglet "Performance"
3. Noter le CPU% maximum atteint
4. Noter la Mémoire (MB) utilisée

| Requêtes | CPU REST (%) | CPU SOAP (%) | CPU GraphQL (%) | CPU gRPC (%) |
|----------|--------------|--------------|-----------------|--------------|
| 10       |              |              |                 |              |
| 100      |              |              |                 |              |
| 500      |              |              |                 |              |
| 1000     |              |              |                 |              |

### Tableau 4 : Simplicité d'Implémentation

Comptez manuellement :

| Critère | REST | SOAP | GraphQL | gRPC |
|---------|------|------|---------|------|
| Temps d'implémentation | ~2h | ~3h | ~2h | ~3h |
| Nombre de lignes de code | ? | ? | ? | ? |

Pour compter les lignes dans IntelliJ :
1. Sélectionner le package du protocole
2. Analyser → Inspect Code
3. Consulter "Code Statistics"

### Tableau 5 : Sécurité

| Critère | REST | SOAP | GraphQL | gRPC |
|---------|------|------|---------|------|
| Support TLS/SSL | Oui | Oui | Oui | Oui |
| Authentification | OAuth2, JWT | WS-Security | JWT | OAuth2, mTLS |
| Chiffrement des données | Oui | Oui | Oui | Oui |

## Étape 5 : Tests SOAP (SoapUI)

1. Télécharger SoapUI : https://www.soapui.org/downloads/soapui/
2. Créer un nouveau projet SOAP
3. WSDL : `http://localhost:8080/soap-api/reservations?wsdl`
4. Créer un LoadTest avec les mêmes configurations (10, 100, 500, 1000 threads)
5. Noter les résultats

## Étape 6 : Tests gRPC (ghz)

### Installation de ghz

```powershell
# Via Chocolatey
choco install ghz

# Ou télécharger depuis https://github.com/bojand/ghz/releases
```

### Lancer les Tests

```powershell
cd c:\Users\asus\Desktop\hotel-api-performance

# Test avec 10 utilisateurs
ghz --insecure --proto src/main/proto/hotel.proto --call HotelServiceGrpc.GetReservation -d "{\"id\":1}" -n 100 -c 10 localhost:9090

# Test avec 100 utilisateurs
ghz --insecure --proto src/main/proto/hotel.proto --call HotelServiceGrpc.GetReservation -d "{\"id\":1}" -n 1000 -c 100 localhost:9090

# Test avec 500 utilisateurs
ghz --insecure --proto src/main/proto/hotel.proto --call HotelServiceGrpc.GetReservation -d "{\"id\":1}" -n 5000 -c 500 localhost:9090

# Test avec 1000 utilisateurs
ghz --insecure --proto src/main/proto/hotel.proto --call HotelServiceGrpc.GetReservation -d "{\"id\":1}" -n 10000 -c 1000 localhost:9090
```

Noter les résultats :
- **Average** : Latence moyenne
- **Requests/sec** : Débit

## Étape 7 : Nettoyer entre les Tests

Entre chaque série de tests (changement de protocole) :

```powershell
.\scripts\clear-database.ps1
.\scripts\create-test-data.ps1
```

## Résumé de la Procédure

1. ✅ Démarrer l'application Spring Boot
2. ✅ Créer les données de test
3. ✅ Tester REST (10, 100, 500, 1000 threads)
4. ✅ Nettoyer + Recréer données
5. ✅ Tester GraphQL (10, 100, 500, 1000 threads)
6. ✅ Nettoyer + Recréer données
7. ✅ Tester SOAP avec SoapUI (10, 100, 500, 1000 threads)
8. ✅ Nettoyer + Recréer données
9. ✅ Tester gRPC avec ghz (10, 100, 500, 1000 threads)
10. ✅ Compiler les résultats dans Excel
11. ✅ Créer des graphiques comparatifs
