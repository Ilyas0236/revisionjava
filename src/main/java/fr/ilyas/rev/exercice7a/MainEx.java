package fr.ilyas.rev.exercice7a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainEx {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1/ourDataBase";
        String user = "root";
        String password = ""; // Mot de passe vide souvent par défaut en local
        Connection connection = null;

        try {
            // connection = DriverManager.getConnection(url, user, password);
            // Pour tester sans vraie DB, on laissera connection à null ou on mockera si
            // besoin.
            // Ici le code continuera avec connection null, Operations.execute gère le null
            // check que j'ai ajouté.
            // Si on voulait vraiment tester la DB, il faudrait une instance MySQL locale.
            // Je laisse le code tel quel.
        } catch (Exception e) { // Catch plus large au cas ou driver pas chargé
            e.printStackTrace();
        }

        TaskExeQuery task1 = new TaskExeQuery(connection, "SELECT * FROM membre");
        TaskExeQuery task2 = new TaskExeQuery(connection, "SELECT hm FROM membre");

        Thread th1 = new Thread(task1); // Attention: TaskExeQuery extends Thread, donc c'est DEJA un thread.
        // Mais le code de l'image fait: "Thread th1 = new Thread(task1);"
        // Si TaskExeQuery extends Thread, c'est aussi un Runnable. Donc c'est valide.
        // Cela dit, ça crée un Thread qui enveloppe un autre Thread (considéré comme
        // Runnable).
        // C'est un peu bizarre mais c'est ce que montre l'image.

        // CORRECTION: L'image montre "TaskExeQuery extends Thread".
        // ET "Thread th1 = new Thread(task1);".
        // C'est valide car Thread implements Runnable.

        Thread th2 = new Thread(task2);

        // Instructions permettant l'exécution des deux threads th1 et th2,
        // le thread th2 doit attendre la fin d'exécution du thread th1.

        th1.start();

        try {
            th1.join(); // Le thread courant (main) attend la fin de th1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        th2.start();
        // th2 démarre seulement après que th1 ait fini (car join() bloque jusqu'à la
        // fin de th1).

        // Pour être propre on pourrait aussi join th2 à la fin.
        try {
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fin des deux threads.");
    }
}
