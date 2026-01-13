package fr.ilyas.rev.exercice1;

import java.util.Objects;

public class Departement {
    private String nomD;
    private Gerant gerant; // Un département ne peut être géré que par un seul gérant

    public Departement(String nomD) {
        this.nomD = nomD;
    }

    public String getNomD() {
        return nomD;
    }

    public void setNomD(String nomD) {
        this.nomD = nomD;
    }

    public Gerant getGerant() {
        return gerant;
    }

    public void setGerant(Gerant gerant) {
        this.gerant = gerant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Departement that = (Departement) o;
        return Objects.equals(nomD, that.nomD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomD);
    }

    @Override
    public String toString() {
        return "Departement{" +
                "nomD='" + nomD + '\'' +
                ", gerant=" + (gerant != null ? gerant.getIdE() : "null") +
                '}';
    }
}
