package com.revision.exercice1;

public class Assistant extends Personnel {
    private int anneeDebu;

    public Assistant(String idE, double salaire, int anneeDebu) {
        super(idE, salaire);
        this.anneeDebu = anneeDebu;
    }

    public int getAnneeDebu() {
        return anneeDebu;
    }

    public void setAnneeDebu(int anneeDebu) {
        this.anneeDebu = anneeDebu;
    }

    @Override
    public String toString() {
        return "Assistant{" +
                "anneeDebu=" + anneeDebu +
                ", idE='" + idE + '\'' +
                ", salaire=" + salaire +
                '}';
    }
}
