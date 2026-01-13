package fr.ilyas.rev.exercice1;

import java.util.HashSet;
import java.util.Set;

public class Gerant extends Personnel {
    private String specialite;
    // Un gérant peut gérer plusieurs départements (One-To-Many)
    // On ne stocke pas forcement la liste ici si on veut juste 'Gerant' simple,
    // mais pour "gèrer plusieurs départements", on peut imaginer une liste.
    // Cependant, l'exercice demande de déduire.
    // "un gérant peut gérer plusieurs départements" -> List<Departement> dans
    // Gerant ?
    // "un département (..) géré que par un seul gérant" -> Gerant dans Departement.

    // Vu la question 1 partie 2, on manipule un Set<Departement> externe.
    // Donc la classe Gerant peut être simple, ou avoir une liste.
    // Je vais ajouter une liste pour refléter le "One-To-Many" explicitement dans
    // la classe si besoin,
    // mais souvent JPA/Hibernate le gère. En POO pure, on peut le mettre.

    private Set<Departement> departementsGeres = new HashSet<>();

    public Gerant(String idE, double salaire, String specialite) {
        super(idE, salaire);
        this.specialite = specialite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Set<Departement> getDepartementsGeres() {
        return departementsGeres;
    }

    public void addDepartementRef(Departement d) {
        this.departementsGeres.add(d);
    }

    public void removeDepartementRef(Departement d) {
        this.departementsGeres.remove(d);
    }

    @Override
    public String toString() {
        return "Gerant{" +
                "specialite='" + specialite + '\'' +
                ", idE='" + idE + '\'' +
                ", salaire=" + salaire +
                '}';
    }
}
