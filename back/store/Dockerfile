# Etapa de build con Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom primero para aprovechar layer cache, luego el código
COPY pom.xml mvnw* ./
COPY .mvn .mvn
# Copiamos el código fuente
COPY src ./src

# Ejecutamos el package (skip tests para iterar rápido)
RUN mvn -B -DskipTests package

# Etapa runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia cualquier JAR generado (más robusto que nombre fijo)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]