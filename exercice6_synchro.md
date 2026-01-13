# Exercice 6 : Multi-threading Synchronization

**1. Expliquez l’importance de la synchronisation dans un environnement multithreading.**
Dans un système concurrent, plusieurs threads partagent le même espace mémoire. Si deux threads tentent de modifier une donnée partagée en même temps (écriture concurrente), les opérations peuvent s'entrelacer de manière incorrecte, menant à des **données corrompues** ou incohérentes. La synchronisation est le mécanisme qui permet d'ordonnancer cet accès pour garantir l'atomicité des opérations et la cohérence de la mémoire (Memory Consistency).

**2. Expliquez le problème que la synchronisation des threads peut résoudre. C.-à-d., dans quels cas on doit utiliser la synchronisation.**
Elle résout les **Conditions de Course (Race Conditions)**.
On doit l'utiliser impérativement quand :
1.  Une ressource est partagée par plusieurs threads.
2.  Au moins un thread effectue une **écriture** (modification) sur cette ressource.
3.  L'opération n'est pas atomique par nature (ex: incrémentation, vérification puis action "check-then-act").

**3. Quel est l’utilité de Lock et comment l’utiliser ?**
L'interface `Lock` (ex: `ReentrantLock`) est une alternative moderne et plus sophistiquée au bloc `synchronized`.
**Avantages** :
*   **Flexibilité** : Le verrouillage (`lock()`) et déverrouillage (`unlock()`) peuvent se faire dans des méthodes différentes.
*   **Non-bloquant** : Possibilité d'utiliser `tryLock()` pour ne pas attendre indéfiniment si le verrou est pris.
*   **Filtres d'attente multiples** : Utilisable avec des objets `Condition` pour des scénarios complexes (Producteur/Consommateur).
**Utilisation** :
```java
Lock lock = new ReentrantLock();
lock.lock(); // Acquisition manuelle
try {
    // Section critique
} finally {
    lock.unlock(); // Libération OBLIGATOIRE dans le finally
}
```

**4. Quel est l’utilité de « synchronized » ?**
`synchronized` est le mot-clé natif Java. Il simplifie la synchronisation en gérant l'acquisition et la libération du verrou (le moniteur de l'objet) automatiquement.
*   Sur une méthode : `public synchronized void method() {}` (verrouille `this`).
*   Sur un bloc : `synchronized(object) { ... }`.
Il assure l'**exclusion mutuelle** (un seul thread à la fois) et la **visibilité** des modifications mémoire aux autres threads.

**5. Comment s’assurer que votre code cause un problème de manque de synchronisation ?**
On peut le prouver par un test de stress :
Créez une variable `compteur = 0`. Lancez 50 threads qui font chacun 1000 fois `compteur++`. À la fin (après `join` des threads), affichez la valeur.
Si le code n'est pas synchronisé, la valeur finale sera aléatoire et inférieure à 50000 (car des opérations de lecture/écriture se chevauchent et s'écrasent).
C'est la preuve d'une "Race Condition".

**6. Quels sont les points clé à prendre en considération pour mettre en place une solution synchronisant l’exécution concurrente.**
*   **Granularité** : Ne synchronisez que le stricte nécessaire (la section critique). Trop synchroniser l'application la rendra séquentielle et lente (**goulot d'étranglement**).
*   **Choix du Moniteur** : Verrouillez sur le bon objet. Attention aux verrous sur des Strings ou des objets réutilisés.
*   **Deadlocks** : Si vous utilisez plusieurs verrous, acquérez-les toujours dans le même ordre partout.
*   **Automicité vs Visibilité** : Parfois `volatile` suffit (si juste lecture/écriture simple), mais pour des opérations composées, la synchronisation complète est requise.

**7. Qu’est-ce que la section critique d’un code ?**
C'est le segment de code précis où l'accès à une ressource partagée a lieu (lecture ou modification). C'est la "zone de danger" qui doit être protégée pour qu'un seul thread ne puisse s'y trouver à un instant T.

**8. Qu’est-ce que « start() » et « join() » ?**
*   **`start()`** : Ordonne à la JVM de créer une nouvelle pile d'exécution (natif OS) et d'y exécuter la méthode `run()`. C'est ce qui rend l'exécution *parallèle* ou *concurrente*.
*   **`join()`** : C'est une barrière de synchronisation. L'instruction `t1.join()` bloque le thread appelant (le met en état WAITING) jusqu'à ce que le thread `t1` ait terminé son exécution (soit mort). Cela permet d'attendre un résultat avant de continuer.
