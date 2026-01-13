package com.revision.exercice3;

public class Produit {
    private String id;
    private String nom;
    private double prix;
    private String categorie;
    private int anneFabric;

    public Produit(String id, String nom, double prix, String categorie, int anneFabric) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.categorie = categorie;
        this.anneFabric = anneFabric;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getAnneFabric() {
        return anneFabric;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
