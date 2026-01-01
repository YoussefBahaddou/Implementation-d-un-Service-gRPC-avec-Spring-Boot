$baseUrl = "http://localhost:8080/api/rest"

Write-Host "Creation de donnees de test..." -ForegroundColor Green

Write-Host "`n1. Creation de clients..." -ForegroundColor Yellow
$client1 = @{
    nom = "Dupont"
    prenom = "Jean"
    email = "jean.dupont@example.com"
    telephone = "0601020304"
} | ConvertTo-Json

$client2 = @{
    nom = "Martin"
    prenom = "Marie"
    email = "marie.martin@example.com"
    telephone = "0605060708"
} | ConvertTo-Json

$client3 = @{
    nom = "Bernard"
    prenom = "Pierre"
    email = "pierre.bernard@example.com"
    telephone = "0609101112"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/clients" -Method Post -Body $client1 -ContentType "application/json"
Invoke-RestMethod -Uri "$baseUrl/clients" -Method Post -Body $client2 -ContentType "application/json"
Invoke-RestMethod -Uri "$baseUrl/clients" -Method Post -Body $client3 -ContentType "application/json"

Write-Host "3 clients crees avec succes" -ForegroundColor Green

Write-Host "`n2. Creation de chambres..." -ForegroundColor Yellow
$chambre1 = @{
    type = "Simple"
    prix = 80.0
    disponible = $true
} | ConvertTo-Json

$chambre2 = @{
    type = "Double"
    prix = 150.0
    disponible = $true
} | ConvertTo-Json

$chambre3 = @{
    type = "Suite"
    prix = 300.0
    disponible = $true
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/chambres" -Method Post -Body $chambre1 -ContentType "application/json"
Invoke-RestMethod -Uri "$baseUrl/chambres" -Method Post -Body $chambre2 -ContentType "application/json"
Invoke-RestMethod -Uri "$baseUrl/chambres" -Method Post -Body $chambre3 -ContentType "application/json"

Write-Host "3 chambres creees avec succes" -ForegroundColor Green

Write-Host "`n3. Creation de quelques reservations de test..." -ForegroundColor Yellow
$reservation1 = @{
    dateDebut = "2025-01-15"
    dateFin = "2025-01-20"
    preferences = "Vue sur mer"
} | ConvertTo-Json

$reservation2 = @{
    dateDebut = "2025-02-10"
    dateFin = "2025-02-15"
    preferences = "Etage eleve"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/reservations?clientId=1&chambreId=1" -Method Post -Body $reservation1 -ContentType "application/json"
Invoke-RestMethod -Uri "$baseUrl/reservations?clientId=2&chambreId=2" -Method Post -Body $reservation2 -ContentType "application/json"

Write-Host "2 reservations creees avec succes" -ForegroundColor Green

Write-Host "`nDonnees de test creees avec succes!" -ForegroundColor Cyan
Write-Host "- 3 clients (IDs: 1, 2, 3)" -ForegroundColor White
Write-Host "- 3 chambres (IDs: 1, 2, 3)" -ForegroundColor White
Write-Host "- 2 reservations initiales" -ForegroundColor White
