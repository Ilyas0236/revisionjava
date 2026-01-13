package com.revision.exercice1;

import java.util.*;
import java.util.stream.Collectors;

public class GestionEntreprise {
    public List<Personnel> lesPrs = new ArrayList<>();
    public Set<Departement> lesDeps = new HashSet<>();

    /**
     * 1. Ecrivez une méthode qui prend les paramètres nécessaires puis ajoute un
     * département au set lesDeps.
     * NB : chaque département doit voir un nom unique. Et, pour ajouter un
     * département il faut l’affecter à un gérant.
     */
    public void ajouterDepartement(Departement d, Gerant g) {
        // Vérification unicité nom
        for (Departement dep : lesDeps) {
            if (dep.getNomD().equals(d.getNomD())) {
                System.out.println("Erreur: Le département " + d.getNomD() + " existe déjà.");
                return;
            }
        }

        // Affectation
        d.setGerant(g);
        g.addDepartementRef(d);

        // Ajout
        lesDeps.add(d);
        System.out.println("Département " + d.getNomD() + " ajouté et affecté au gérant " + g.getIdE());
    }

    /**
     * 2. Ecrivez une méthode statique qui permet de supprimer un département du
     * set.
     * NB : n’oublier pas de traiter la relation entre département et gérant lors de
     * la suppression.
     */
    public static void supprimerDepartement(Set<Departement> deps, Departement d) {
        if (deps.contains(d)) {
            Gerant g = d.getGerant();
            if (g != null) {
                g.removeDepartementRef(d);
            }
            deps.remove(d);
            d.setGerant(null);
            System.out.println("Département " + d.getNomD() + " supprimé.");
        } else {
            System.out.println("Département introuvable.");
        }
    }

    /**
     * 3. On désire regrouper le nombre d’assistants par salaire... Map<Double,
     * Integer> ... (salaire : nombre d’assistants).
     * Note: L'énoncé dit "par année de début" au début de la phrase, mais l'exemple
     * de map est (salaire : nombre).
     * Je vais suivre la description de la Map: (salaire : nombre).
     */
    public Map<Double, Integer> assistantsParSalaire() {
        Map<Double, Integer> map = new HashMap<>();
        for (Personnel p : lesPrs) {
            if (p instanceof Assistant) {
                map.put(p.getSalaire(), map.getOrDefault(p.getSalaire(), 0) + 1);
            }
        }
        return map;
    }

    /**
     * 4. Regrouper les Gerants stockés dans la liste ‘lesPrs’ ayant un salaire
     * inférieur à 10000 par spécialité.
     * Map<String, List<Gerant>> (spécialité : liste gérants).
     */
    public Map<String, List<Gerant>> gerantsParSpecialite() {
        // En Java classique sans stream pour l'exercice, ou avec stream. Je vais faire
        // classique pour être sûr, puis stream si besoin.
        Map<String, List<Gerant>> map = new HashMap<>();

        for (Personnel p : lesPrs) {
            if (p instanceof Gerant) {
                Gerant g = (Gerant) p;
                if (g.getSalaire() < 10000) {
                    map.putIfAbsent(g.getSpecialite(), new ArrayList<>());
                    map.get(g.getSpecialite()).add(g);
                }
            }
        }
        return map;
    }
}
