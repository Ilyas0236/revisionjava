# Exercice 6 : Multi-threading Synchronization

**1. Expliquez l’importance de la synchronisation dans un environnement multithreading.**
Dans un environnement multithreading, plusieurs threads peuvent tenter d'accéder et de modifier les mêmes ressources partagées (variables, fichiers, objets) simultanément. Sans synchronisation, cela conduit à des **incohérences de données** et des comportements imprévisibles (Race Conditions). La synchronisation assure que l'accès aux ressources partagées est coordonné, garantissant l'intégrité des données et la cohérence de l'état de l'application.

**2. Expliquez le problème que la synchronisation des threads peut résoudre. C.-à-d., dans quels cas on doit utiliser la synchronisation.**
La synchronisation résout le problème de l'**accès concurrentiel non contrôlé** (Race Condition).
On doit l'utiliser lorsque :
*   Plusieurs threads accèdent à une ressource partagée (lecture/écriture).
*   Au moins un des threads modifie la ressource.
*   L'opération sur la ressource n'est pas atomique (ex: `count++` qui est lecture-modification-écriture).
Exemple classique : Deux threads incrémentent un compteur global. Sans synchronisation, des incréments peuvent être perdus.

**3. Quel est l’utilité de Lock et comment l’utiliser ?**
`Lock` (interface `java.util.concurrent.locks.Lock`) offre un mécanisme de verrouillage plus flexible et puissant que le mot-clé `synchronized`.
**Utilité :**
*   Permet de verrouiller et déverrouiller dans des méthodes différentes (ce que `synchronized` ne permet pas).
*   Offre des méthodes comme `tryLock()` pour tenter d'acquérir le verrou sans bloquer indéfiniment.
*   Permet d'avoir plusieurs conditions d'attente (`Condition`).
**Utilisation :**
```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    // Section critique (code à protéger)
} finally {
    lock.unlock(); // Toujours libérer dans finally
}
```

**4. Quel est l’utilité de « synchronized » ?**
`synchronized` est le mécanisme primitif de Java pour la synchronisation.
**Utilité :**
*   Il garantit qu'un seul thread à la fois peut exécuter un bloc de code ou une méthode donnée pour une instance donnée (ou une classe).
*   Il assure la visibilité des changements de mémoire entre les threads (happens-before relationship).
Il est simple à utiliser pour des cas standard.

**5. Comment s’assurer que votre code cause un problème de manque de synchronisation ?**
Pour démontrer un problème de manque de synchronisation :
1.  Créer une ressource partagée mutable (ex: une variable `int compteur = 0`).
2.  Lancer plusieurs threads (ex: 10 threads) qui effectuent une opération non atomique sur cette ressource en boucle (ex: `compteur++` 1000 fois).
3.  Attendre la fin de tous les threads (`join`).
4.  Vérifier la valeur finale. Si la synchronisation manque, la valeur finale sera très probablement inférieure à `nombre_threads * interruptions_par_thread` (ex: < 10000), prouvant que des mises à jour ont été écrasées.

**6. Quels sont les points clé à prendre en considération pour mettre en place une solution de synchronisant l’exécution concurrente.**
*   **Identifier les ressources partagées** : Quelles variables sont accédées par plusieurs threads ?
*   **Définir la section critique** : Quelle est la plus petite partie du code qui doit être atomique ? (Ne pas trop synchroniser pour éviter de tuer les performances).
*   **Choisir le bon verrou** : Verrouiller sur le bon objet (instance vs classe vs objet dédié).
*   **Éviter les Deadlocks** : Attention à l'ordre d'acquisition des verrous si plusieurs sont nécessaires.
*   **Atomicité et Visibilité** : S'assurer que les changements sont visibles par les autres threads (le mot clé `volatile` peut suffire pour la visibilité seule, mais pas pour l'atomicité).

**7. Qu’est-ce que la section critique d’un code ?**
La section critique est la portion de code qui accède à une ressource partagée (lecture ou écriture) et qui ne doit être exécutée que par un seul thread à la fois pour éviter les conflits. C'est le bloc de code qui doit être protégé par un mécanisme de synchronisation.

**8. Qu’est-ce que « start() » et « join() » ?**
*   **`start()`** : Méthode de la classe `Thread` qui démarre l'exécution du thread. Elle demande à la JVM de lancer un nouveau thread d'exécution et d'y appeler la méthode `run()`. Appeler `run()` directement n'exécute pas le code dans un nouveau thread, mais dans le thread courant.
*   **`join()`** : Méthode qui permet à un thread (ex: le thread principal) d'attendre la fin de l'exécution d'un autre thread. Si le thread A appelle `B.join()`, le thread A se met en pause jusqu'à ce que le thread B ait terminé son travail.
