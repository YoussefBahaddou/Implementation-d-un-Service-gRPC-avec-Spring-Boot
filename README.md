# Hotel gRPC Service (Performance Comparison)

## Overview
A multi-protocol service (REST, SOAP, GraphQL, gRPC) designed to compare API performance.
Refactored by **Youssef Bahaddou** with a focus on gRPC implementation.

## Protocols
- **gRPC**: Efficient Protobuf-based communication (See \src/main/proto/hotel.proto\).
- **REST**: Standard JSON APIs.
- **SOAP**: XML Web Services via Apache CXF.
- **GraphQL**: Flexible queries.

## Build and Run
1. Generate Protobuf stubs:
   \\\ash
   mvn clean compile
   \\\
2. Run Service:
   \\\ash
   mvn spring-boot:run
   \\\

## Author
Youssef Bahaddou

