$projectPath = "C:\DEV\Lechgar\DrissLechgarRepo\SeleniumScripts\downloads\TP-18 ( id 182 )\Etude-de-Cas-Analyse-Scalabilit-Performance-des-APIs-Modernes-travers-un-Cas-Reel-de-G-d-hotel-main"
$srcMainJava = "$projectPath\src\main\java"
$srcMainProto = "$projectPath\src\main\proto"
$oldPackagePath = "$srcMainJava\com\hotel\performance"
$newPackagePath = "$srcMainJava\com\youssef\grpc\hotel"

# 1. Create New Package Structure
New-Item -ItemType Directory -Force -Path "$newPackagePath\config" | Out-Null
New-Item -ItemType Directory -Force -Path "$newPackagePath\controller" | Out-Null
New-Item -ItemType Directory -Force -Path "$newPackagePath\model" | Out-Null
New-Item -ItemType Directory -Force -Path "$newPackagePath\repository" | Out-Null
New-Item -ItemType Directory -Force -Path "$newPackagePath\service\grpc" | Out-Null
New-Item -ItemType Directory -Force -Path "$newPackagePath\soap" | Out-Null

# 2. Update Proto File
$protoFile = "$srcMainProto\hotel.proto"
if (Test-Path $protoFile) {
    (Get-Content $protoFile) -replace 'option java_package = "com.hotel.performance.grpc";', 'option java_package = "com.youssef.grpc.hotel.proto";' `
                             -replace 'option java_outer_classname = "HotelProto";', 'option java_outer_classname = "HotelApi";' | Set-Content $protoFile
}

# 3. Move and Rename Core Files
# Main App
if (Test-Path "$oldPackagePath\PerformanceComparisonApplication.java") {
    Move-Item "$oldPackagePath\PerformanceComparisonApplication.java" "$newPackagePath\HotelServiceApplication.java" -Force
}

# Recursively move other contents
Get-ChildItem "$oldPackagePath\config" -Recurse | Copy-Item -Destination "$newPackagePath\config" -Recurse -Force
Get-ChildItem "$oldPackagePath\controller" -Recurse | Copy-Item -Destination "$newPackagePath\controller" -Recurse -Force
Get-ChildItem "$oldPackagePath\model" -Recurse | Copy-Item -Destination "$newPackagePath\model" -Recurse -Force
Get-ChildItem "$oldPackagePath\repository" -Recurse | Copy-Item -Destination "$newPackagePath\repository" -Recurse -Force

# Service: specific handling for gRPC vs others
if (Test-Path "$oldPackagePath\grpc") {
    Get-ChildItem "$oldPackagePath\grpc" -Recurse | Copy-Item -Destination "$newPackagePath\service\grpc" -Recurse -Force
}
if (Test-Path "$oldPackagePath\soap") {
    Get-ChildItem "$oldPackagePath\soap" -Recurse | Copy-Item -Destination "$newPackagePath\soap" -Recurse -Force
}
# Move other services
if (Test-Path "$oldPackagePath\service") {
    Get-ChildItem "$oldPackagePath\service" -Recurse | Copy-Item -Destination "$newPackagePath\service" -Recurse -Force
}

# 4. Remove Old Directories
Remove-Item "$srcMainJava\com\hotel" -Recurse -Force -ErrorAction SilentlyContinue

# 5. Content Replacement (Java)
$filesToUpdate = Get-ChildItem -Path "$newPackagePath" -Recurse -Filter "*.java"

foreach ($file in $filesToUpdate) {
    $content = Get-Content $file.FullName -Raw

    # Package Declarations
    $content = $content -replace "package com.hotel.performance;", "package com.youssef.grpc.hotel;"
    $content = $content -replace "package com.hotel.performance.config;", "package com.youssef.grpc.hotel.config;"
    $content = $content -replace "package com.hotel.performance.controller;", "package com.youssef.grpc.hotel.controller;"
    $content = $content -replace "package com.hotel.performance.model;", "package com.youssef.grpc.hotel.model;"
    $content = $content -replace "package com.hotel.performance.repository;", "package com.youssef.grpc.hotel.repository;"
    $content = $content -replace "package com.hotel.performance.service;", "package com.youssef.grpc.hotel.service;"
    $content = $content -replace "package com.hotel.performance.grpc;", "package com.youssef.grpc.hotel.service.grpc;"
    $content = $content -replace "package com.hotel.performance.soap;", "package com.youssef.grpc.hotel.soap;"

    # Imports
    $content = $content -replace "import com.hotel.performance.grpc.HotelProto;", "import com.youssef.grpc.hotel.proto.HotelApi;"
    $content = $content -replace "import com.hotel.performance.grpc.HotelServiceGrpc;", "import com.youssef.grpc.hotel.proto.HotelServiceGrpc;"
    $content = $content -replace "import com.hotel.performance.model.*;", "import com.youssef.grpc.hotel.model.*;"
    $content = $content -replace "import com.hotel.performance.repository.*;", "import com.youssef.grpc.hotel.repository.*;"
    $content = $content -replace "import com.hotel.performance.service.*;", "import com.youssef.grpc.hotel.service.*;"

    # Class Names
    $content = $content -replace "PerformanceComparisonApplication", "HotelServiceApplication"
    $content = $content -replace "HotelProto", "HotelApi" # Generated protobuf class name change

    Set-Content -Path $file.FullName -Value $content
}

# 6. Update POM
$pomPath = "$projectPath\pom.xml"
if (Test-Path $pomPath) {
    (Get-Content $pomPath) -replace "com.hotel", "com.youssef.grpc" `
                           -replace "performance-comparison", "hotel-service" `
                           -replace "<name>performance-comparison</name>", "<name>Hotel gRPC Service</name>" | Set-Content $pomPath
}

# 7. Create README
$readmeContent = "# Hotel gRPC Service (Performance Comparison)

## Overview
A multi-protocol service (REST, SOAP, GraphQL, gRPC) designed to compare API performance.
Refactored by **Youssef Bahaddou** with a focus on gRPC implementation.

## Protocols
- **gRPC**: Efficient Protobuf-based communication (See \`src/main/proto/hotel.proto\`).
- **REST**: Standard JSON APIs.
- **SOAP**: XML Web Services via Apache CXF.
- **GraphQL**: Flexible queries.

## Build and Run
1. Generate Protobuf stubs:
   \`\`\`bash
   mvn clean compile
   \`\`\`
2. Run Service:
   \`\`\`bash
   mvn spring-boot:run
   \`\`\`

## Author
Youssef Bahaddou
"
Set-Content -Path "$projectPath\README.md" -Value $readmeContent

Write-Host "TP-18 Refactoring Complete!"
