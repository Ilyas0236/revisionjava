package com.revision.exercice3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainExercice3 {
    public static void main(String[] args) {
        Set<Produit> produits = new HashSet<>();
        produits.add(new Produit("12", "TV", 1800, "Electronique", 2023));
        produits.add(new Produit("55", "PC", 2500, "Electronique", 2022));
        produits.add(new Produit("101", "Livre Java", 45, "Librairie", 2020));
        produits.add(new Produit("99", "Chaise", 150, "Meuble", 2021));
        produits.add(new Produit("7", "Smartphone", 1200, "Electronique", 2024));

        System.out.println("--- Avant Stream ---");
        produits.forEach(System.out::println);

        /*
         * Objectif:
         * 1. Reduction 15% pour prix < 2000 et Categorie Electronique.
         * 2. Modification ID:
         * - Si Categorie Librairie ou Jardin -> pas changement.
         * - Sinon -> "3 premieres lettres categorie"-"id separé par un tiré" (Exemple
         * "ELE-12").
         * Wait, "ELE-12" implies "3 letters" + "-" + "id".
         * The example logic is:
         * "pour un produit dont l'id est 12 ... son id devient ELE-12".
         * 3. Stocker dans une collection accessible par des indices (List).
         */

        List<Produit> resultat = produits.stream()
                .map(p -> {
                    // Traitement Prix
                    if (p.getCategorie().equals("Electronique") && p.getPrix() < 2000) {
                        p.setPrix(p.getPrix() * 0.85); // -15%
                    }

                    // Traitement ID
                    // "Dans la suite, on ne doit pas changer la forme des id ... Librairie et
                    // Jardin"
                    // "pour les autres ... changer la forme"
                    if (!p.getCategorie().equals("Librairie") && !p.getCategorie().equals("Jardin")) {
                        String prefix = "";
                        if (p.getCategorie().length() >= 3) {
                            prefix = p.getCategorie().substring(0, 3).toUpperCase();
                        } else {
                            prefix = p.getCategorie().toUpperCase();
                        }
                        p.setId(prefix + "-" + p.getId());
                    }

                    return p;
                })
                .collect(Collectors.toList());

        System.out.println("\n--- Apres Stream (Resultat List) ---");
        resultat.forEach(System.out::println);
    }
}
