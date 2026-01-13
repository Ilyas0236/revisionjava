# Exercice 7A : Threads & JDBC

**1. Modifiez la déclaration de la méthode execQuery...**
Pour garantir l'exclusion mutuelle (Thread-Safety), deux approches :
*   **a. Synchronized** : Ajouter le mot clé `synchronized` à la méthode statique. Cela utilise le verrou de la classe `Operations.class`.
    ```java
    public static synchronized void execQuery(...) { ... }
    ```
*   **b. Lock** : Créer une instance statique de `ReentrantLock` et l'utiliser pour encadrer le corps de la méthode.
    ```java
    private static final Lock lock = new ReentrantLock();
    public static void execQuery(...) {
        lock.lock();
        try { ... } finally { lock.unlock(); }
    }
    ```

**2. Instructions pour th1 et th2 (th2 attend th1)**
Pour séquencer l'exécution de threads indépendants, on utilise `join()`.
```java
Thread th1 = new Thread(task1);
Thread th2 = new Thread(task2);

th1.start(); // Démarre le thread 1
try {
    th1.join(); // Le thread principal se met en pause jusqu'à la fin de th1
} catch (InterruptedException e) {
    e.printStackTrace();
}
th2.start(); // Ne démarre qu'une fois th1 terminé
```

**3. Expliquez l'utilité de :**

*   **a. DriverManager** :
    C'est la classe utilitaire historique de JDBC (`java.sql`).
    *   Elle sert de registre pour les pilotes JDBC (Drivers).
    *   Lors de l'appel à `DriverManager.getConnection(url, user, pass)`, elle parcourt les pilotes enregistrés pour trouver celui capable de comprendre l'URL fournie.
    *   *Note moderne* : Depuis JDBC 4.0, le chargement manuel du driver (`Class.forName`) n'est plus nécessaire grâce au mécanisme de SPI (Service Provider Interface) utilisé par `DriverManager`.

*   **b. jdbc:mysql://...** :
    C'est la chaîne de connexion (Connection URL) formatée standard pour JDBC. Elle décompose les informations nécessaires pour atteindre la base :
    *   **jdbc** : Le protocole (API Java).
    *   **mysql** : Le sous-protocole (Type de Base de données, indique quel Driver utiliser).
    *   **//127.0.0.1/ourDataBase** : L'adresse réseau (Host/Port) et le nom de la ressource (Base de données).
