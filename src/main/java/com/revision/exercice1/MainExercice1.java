package com.revision.exercice1;

import java.util.List;
import java.util.Map;

public class MainExercice1 {
    public static void main(String[] args) {
        GestionEntreprise ge = new GestionEntreprise();

        // Création objets
        Gerant g1 = new Gerant("G1", 9000, "IT");
        Gerant g2 = new Gerant("G2", 12000, "Finance");
        Gerant g3 = new Gerant("G3", 9500, "IT");

        Assistant a1 = new Assistant("A1", 3000, 2020);
        Assistant a2 = new Assistant("A2", 3000, 2021);
        Assistant a3 = new Assistant("A3", 4000, 2022);

        ge.lesPrs.add(g1);
        ge.lesPrs.add(g2);
        ge.lesPrs.add(g3);
        ge.lesPrs.add(a1);
        ge.lesPrs.add(a2);
        ge.lesPrs.add(a3);

        Departement d1 = new Departement("Dev");
        Departement d2 = new Departement("Compta");
        Departement d3 = new Departement("Dev"); // Doublon nom

        System.out.println("--- Test Ajout Departement ---");
        ge.ajouterDepartement(d1, g1);
        ge.ajouterDepartement(d2, g2);
        ge.ajouterDepartement(d3, g3); // Devrait échouer

        System.out.println("\n--- Test Relations ---");
        System.out.println("Gerant G1 deps: " + g1.getDepartementsGeres());
        System.out.println("Dept Dev gerant: " + d1.getGerant());

        System.out.println("\n--- Test Map Assistants (Salaire -> Count) ---");
        Map<Double, Integer> mapAssistants = ge.assistantsParSalaire();
        System.out.println(mapAssistants);

        System.out.println("\n--- Test Map Gerants < 10000 (Specialite -> List) ---");
        Map<String, List<Gerant>> mapGerants = ge.gerantsParSpecialite();
        mapGerants.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\n--- Test Suppression ---");
        GestionEntreprise.supprimerDepartement(ge.lesDeps, d1);
        System.out.println("Gerant G1 deps apres suppression: " + g1.getDepartementsGeres());
    }
}
