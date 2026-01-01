$baseUrl = "http://localhost:8080/api/rest"

Write-Host "Nettoyage de la base de donnees..." -ForegroundColor Yellow

try {
    $reservations = Invoke-RestMethod -Uri "$baseUrl/reservations" -Method Get
    
    Write-Host "Suppression de $($reservations.Count) reservations..." -ForegroundColor Cyan
    foreach ($reservation in $reservations) {
        Invoke-RestMethod -Uri "$baseUrl/reservations/$($reservation.id)" -Method Delete
    }
    
    Write-Host "Base de donnees nettoyee avec succes!" -ForegroundColor Green
} catch {
    Write-Host "Erreur lors du nettoyage: $_" -ForegroundColor Red
}
