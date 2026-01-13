# Exercice 5 : ThreadPoolExecutor / ExecutorService

**1. Quels sont les critères à prendre en considération avant d’adopter une solution basée sur une architecture ThreadPoolExecutor/ExecutorService ?**
*   **Nature des tâches** : Sont-elles "CPU-intensive" (calculs lourds) ou "I/O-intensive" (attente réseau, disque) ?
*   **Nombre de tâches** : Pour un grand nombre de tâches courtes, un pool évite le coût de création répétée des threads.
*   **Ressources disponibles** : Le nombre de cœurs CPU et la mémoire disponible. Trop de threads provoquent un "context switching" excessif qui dégrade les performances.
*   **Stabilité système** : Limiter le nombre de threads concurrents évite de saturer le serveur.

**2. Comment déclarer un ThreadPoolExecutor/ExecutorService permettant d’exécuter 7 threads en parallèle.**
```java
// Solution recommandée (Factory method)
ExecutorService executor = Executors.newFixedThreadPool(7);

// Ou déclaration explicite (plus configurable)
ThreadPoolExecutor pool = new ThreadPoolExecutor(
    7, // corePoolSize
    7, // maximumPoolSize
    0L, TimeUnit.MILLISECONDS, // keepAliveTime
    new LinkedBlockingQueue<Runnable>() // WorkQueue
);
```

**3. Que représente une classe qui hérite de la classe Thread dans une architecture ThreadPoolExecutor/ExecutorService ?**
Dans une architecture moderne basée sur `ExecutorService`, hériter de `Thread` est déconseillé. Cependant, techniquement, puisque `Thread` implémente `Runnable`, une instance de cette classe est considérée comme une **tâche unitaire** (Task). L'Executor n'utilisera pas le mécanisme de thread interne de cette classe (ne lancera pas `start()`), mais exécutera simplement sa méthode `run()` à l'intérieur de l'un des threads du pool (les "Worker Threads").

**4. Quel est l’utilité de la méthode run() ?**
La méthode `run()` encapsule le **code métier** de la tâche. C'est la séquence d'instructions qui sera exécutée de manière asynchrone. Elle ne doit pas être appelée directement par le développeur (sinon elle s'exécute dans le thread courant), mais par le mécanisme de threading (via `start()` ou via un `Executor`).

**5. Expliquez l’utilité de la méthode submit() dans le cadre des ThreadPoolExecutor/ExecutorService ?**
`submit()` sert à envoyer une tâche (`Runnable` ou `Callable`) au pool pour qu'elle soit exécutée.
**Différence clé avec `execute()`** : Elle retourne un objet **`Future<?>`**. Ce `Future` est crucial car il permet de :
*   Vérifier si la tâche est terminée (`isDone()`).
*   Attendre la fin de la tâche (`get()`, bloquant).
*   Récupérer la valeur de retour (dans le cas d'un `Callable`).
*   Gérer les exceptions survenues pendant l'exécution.

**6. Qu’est-ce qu’une file d’attente (Work Queue) ?**
C'est une structure tampon (Tampon FIFO généralement, ex: `BlockingQueue`) qui stocke les tâches soumises lorsque tous les threads du pool sont actuellement occupés. Les threads libérés viendront piocher les tâches suivantes dans cette file.
*   Si la file est bornée (Bounded) et pleine, les nouvelles tâches peuvent être rejetées (Policy de rejection).
*   Si elle est non bornée, on risque une `OutOfMemoryError` si les tâches arrivent trop vite.

**7. Quels sont les problèmes d’exécution qui peuvent être causés par l’utilisation d’une architecture ThreadPoolExecutor/ExecutorService ?**
*   **Deadlocks** : Si les tâches du pool s'attendent mutuellement.
*   **Thread Starvation** : Si des tâches longues monopolisent tous les threads, bloquant les tâches courtes en attente.
*   **Memory Leak / OOM** : Si on utilise une file d'attente illimitée (`LinkedBlockingQueue` sans capacité) avec une production de tâches supérieure à la consommation.
*   **Silent Failures** : Si une exception est levée dans un thread du pool et non attrapée, le thread peut mourir ou l'exception être perdue si on n'inspecte pas le `Future`.

**8. Expliquez l’utilisation de : Collection< Future <?> >.**
Une `Collection<Future<?>>` (souvent une `List`) est utilisée pour conserver les "poignées" (handles) vers un ensemble de tâches lancées en parallèle.
**Scénario typique** :
1.  On soumet 10 calculs en parallèle (`submit`).
2.  On stocke chaque `Future` retourné dans la liste.
3.  Ensuite, on itère sur cette liste pour appeler `future.get()`. Cela permet d'attendre que **toutes** les tâches soient terminées et de collecter tous leurs résultats l'un après l'autre.
