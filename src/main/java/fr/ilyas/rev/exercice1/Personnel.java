package fr.ilyas.rev.exercice1;

import java.util.Objects;

public abstract class Personnel {
    protected String idE;
    protected double salaire;

    public Personnel(String idE, double salaire) {
        this.idE = idE;
        this.salaire = salaire;
    }

    public String getIdE() {
        return idE;
    }

    public void setIdE(String idE) {
        this.idE = idE;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Personnel personnel = (Personnel) o;
        return Objects.equals(idE, personnel.idE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idE);
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "idE='" + idE + '\'' +
                ", salaire=" + salaire +
                '}';
    }
}
