# Exercice 5 : ThreadPoolExecutor / ExecutorService

**1. Quels sont les critères à prendre en considération avant d’adopter une solution basée sur une architecture ThreadPoolExecutor/ExecutorService ?**
*   Le nombre de tâches à exécuter (grand nombre de tâches courtes ?).
*   Les ressources système disponibles (CPU, mémoire). Créer trop de threads peut saturer le système.
*   La nécessité de gérer le cycle de vie des tâches (attente, annulation).
*   L'indépendance des tâches entre elles.

**2. Comment déclarer un ThreadPoolExecutor/ExecutorService permettant d’exécuter 7 threads en parallèle.**
```java
ExecutorService executor = Executors.newFixedThreadPool(7);
// Ou explicitement avec ThreadPoolExecutor
ThreadPoolExecutor pool = new ThreadPoolExecutor(
    7, 7, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
);
```

**3. Que représente une classe qui hérite de la classe Thread dans une architecture ThreadPoolExecutor/ExecutorService ?**
*   Dans une architecture ExecutorService, on préfère implémenter l'interface `Runnable` ou `Callable` plutôt que d'hériter de `Thread`. Cependant, si une classe hérite de `Thread`, elle est aussi un `Runnable`, donc elle représente une **tâche** (Task) qui peut être soumise à l'executor. Mais l'executor utilisera ses propres threads (workers) pour exécuter la méthode `run()` de cette tâche, ignorant le mécanisme de thread de la classe héritée.

**4. Quel est l’utilité de la méthode run() ?**
*   La méthode `run()` contient le code (la logique métier) qui doit être exécuté par le thread. C'est le point d'entrée de l'exécution de la tâche.

**5. Expliquez l’utilité de la méthode submit() dans le cadre des ThreadPoolExecutor/ExecutorService ?**
*   `submit()` permet de soumettre une tâche (`Runnable` ou `Callable`) au pool pour exécution. Contrairement à `execute()`, elle retourne un objet `Future` qui permet de suivre l'état de la tâche, d'attendre sa fin, ou de récupérer son résultat (si `Callable`).

**6. Qu’est-ce qu’une file d’attente ?**
*   C'est une structure de données (généralement `BlockingQueue`) utilisée par le ThreadPool pour stocker les tâches soumises mais qui ne peuvent pas encore être exécutées car tous les threads du pool sont occupés.

**7. Quels sont les problèmes d’exécution qui peuvent causés par l’utilisation d’une architecture ThreadPoolExecutor/ExecutorService ?**
*   **Deadlock (Interblocage)** : Si les tâches du pool dépendent les unes des autres.
*   **Famine (Starvation)** : Si des tâches longues saturent le pool, bloquant les autres.
*   **Saturation mémoire (OOM)** : Si la file d'attente n'est pas bornée (unbounded) et que les tâches arrivent plus vite qu'elles ne sont traitées.
*   **Fuite de threads** : Si les threads ne sont pas correctement arrêtés (shutdown).

**8. Expliquez l’utilisation de : Collection< Future <?> >.**
*   C'est une collection (par exemple `List<Future<String>>`) utilisée pour stocker les objets `Future` retournés par `submit()` ou `invokeAll()`. Elle permet de conserver une référence vers toutes les tâches lancées en parallèle, afin de pouvoir ultérieurement itérer dessus pour attendre leur complétion (`future.get()`) et récupérer leurs résultats agrégés.
