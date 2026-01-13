# Exercice 7A : Threads & JDBC

**1. Modifiez la déclaration de la méthode execQuery...**
Voir le code dans `Operations.java`.
a. **Synchronized**: `public static synchronized void execQuery(...)`
b. **Lock**: Utilisation de `ReentrantLock` autour du bloc critique.

**2. Instructions pour th1 et th2 (th2 attend th1)**
Voir `MainEx.java`:
```java
th1.start();
try {
    th1.join(); // Le thread courant attend que th1 termine
} catch (InterruptedException e) { ... }
th2.start(); // Lance th2 après la fin de th1
```

**3. Expliquez l'utilité de :**

*   **a. DriverManager** :
    C'est une classe de base du package `java.sql` qui gère une liste de pilotes (drivers) de base de données. Son rôle principal est d'établir une connexion à la base de données via la méthode `getConnection(url, user, password)`. Elle sélectionne le pilote approprié parmi ceux chargés en fonction de l'URL JDBC fournie.

*   **b. jdbc:mysql://** :
    C'est le début de l'URL JDBC (Uniform Resource Locator) qui identifie le **protocole** et le **sous-protocole** à utiliser pour la connexion.
    *   `jdbc` : indique qu'on utilise l'API Java Database Connectivity.
    *   `mysql` : indique qu'on souhaite se connecter spécifiquement à une base de données MySQL (le pilote MySQL Connector/J reconnaîtra ce préfixe).
    La suite de l'URL précise généralement l'hôte, le port et le nom de la base de données (ex: `//localhost:3306/maBase`).
