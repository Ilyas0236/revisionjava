package com.revision.exercice7a;

import java.sql.Connection;

public class TaskExeQuery extends Thread {
    public Connection connection;
    public String query;

    public TaskExeQuery(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    @Override
    public void run() {
        // Appel de la méthode statique sécurisée
        Operations.execQuery(query, connection);
    }
}
