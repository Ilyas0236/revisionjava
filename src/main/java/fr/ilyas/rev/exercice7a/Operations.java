package fr.ilyas.rev.exercice7a;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Operations {

    // Lock pour la question 1.b
    private static final Lock lock = new ReentrantLock();

    /**
     * Question 1.a : Modifier pour garantir un seul thread à la fois avec
     * Synchronized.
     * Pour rendre la méthode "thread-safe" globalement (static), on utilise
     * synchronized static.
     */
    public static synchronized void execQuerySynchronized(String query, Connection con) {
        execute(query, con);
    }

    /**
     * Question 1.b : En utilisant Lock.
     */
    public static void execQueryWithLock(String query, Connection con) {
        lock.lock();
        try {
            execute(query, con);
        } finally {
            lock.unlock();
        }
    }

    // Méthode commune pour éviter la duplication
    private static void execute(String query, Connection con) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            if (con == null)
                return; // Sécurité si connection est null (mock)
            statement = con.createStatement();
            // Attention: executeQuery retourne un ResultSet.
            // Le code de l'image utilise statement.executeQuery(query)
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Le code de l'image suppose une colonne "hometown".
                // On met un try/catch ou on suppose que ça existe.
                try {
                    String valCol = resultSet.getString("hometown");
                    System.out.println(valCol);
                } catch (SQLException e) {
                    System.out.println("Colonne hometown introuvable ou erreur rs: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Bonnes pratiques: fermeture ressources
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
            }
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
            }
        }
    }

    // La méthode originale demandée par l'énoncé sans modif de nom, mais modifiée
    // pour être synchronized:
    public static synchronized void execQuery(String query, Connection con) {
        execute(query, con);
    }
}
