
## 1. Tools:
| Tool           | Description                                                                                                                                                                          |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **WireMock**   | A flexible HTTP mock server for simulating APIs (REST/JSON) for unit & integration testing. Built for stubbing and verifying HTTP(S) requests and responses.                         |
| **MockServer** | A powerful tool for mocking **and verifying** HTTP and HTTPS interactions, particularly useful for dynamic expectations, request validation, and microservice communication testing. |
| **Testcontainers + Real API Sandbox** | Use Testcontainers to spin up a real service (like Stripe or Mongo) in Docker |


## 2. Sperate Unit Tests from Integration Tests

**2.1. Plugin maven-surefire-plugin**
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.1.2</version>
</plugin>
```

- **Rôle** : Exécuter les tests unitaires.
- **Phase d’exécution** : par défaut dans la phase Maven test.
- **Fonctionnement** :
    - Le plugin détecte et exécute les tests unitaires, généralement ceux nommés *Test.java.
    - Il permet de lancer ces tests rapidement et de vérifier que chaque composant fonctionne isolément.
- **Version** : 3.1.2 est une version stable et récente.

**2.2 Plugin maven-failsafe-plugin**
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-failsafe-plugin</artifactId>
  <version>3.1.2</version>
  <executions>
    <execution>
      <goals>
        <goal>integration-test</goal>
        <goal>verify</goal>
      </goals>
      <configuration>
        <includes>
          <include>**/*IT.java</include>
        </includes>
      </configuration>
    </execution>
  </executions>
</plugin>
```

- **Rôle** : Exécuter les tests d’intégration.

- **Phases d’exécution** :

- **integration-test** : phase où les tests d’intégration sont lancés.

- **verify** : phase qui valide le succès des tests d’intégration et peut déclencher des actions supplémentaires (ex : fail build si tests échouent).

- **Différence avec Surefire** :

    - Surefire est pour tests unitaires, souvent rapides, isolés.

    - Failsafe est conçu pour les tests d’intégration, qui impliquent des ressources externes (base de données, API, services, etc.) et qui doivent être lancés après la compilation et le packaging.

- **Configuration des tests** :

    - <includes> définit les fichiers de tests ciblés.

    - Ici, seuls les fichiers dont le nom se termine par IT.java (exemple : UserServiceIT.java) seront exécutés par Failsafe.

- **Avantage** :

    - Permet de séparer clairement tests unitaires et tests d’intégration, ce qui facilite la gestion des phases de build et des environnements.


## 🧪 3. Run Integration Tests via Command Line
**A. Run Only Unit Tests:**
```bash
mvn test
```

**B. Run Only Integration Tests:**
```bash
mvn failsafe:integration-test failsafe:verify
```

**C. Run All (Unit + Integration):**
```bash
mvn verify
```