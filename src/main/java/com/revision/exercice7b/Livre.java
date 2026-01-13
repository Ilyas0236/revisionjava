package com.revision.exercice7b;

import jakarta.persistence.*;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idL")
    private Long idL;

    @Column(name = "titre")
    private String titre;

    @Column(name = "auteur")
    private String auteur;

    // Relation ManyToOne inverse de OneToMany dans Departement
    // "mappedBy = departement" dans Departement implique un attribut "departement"
    // ici.
    // Et le schéma montre #IdDep dans Livre, c'est la foreign key.
    @ManyToOne
    @JoinColumn(name = "idDep", nullable = false)
    private Departement departement;

    public Livre() {
    }

    public Livre(String titre, String auteur, Departement departement) {
        this.titre = titre;
        this.auteur = auteur;
        this.departement = departement;
    }

    public Long getIdL() {
        return idL;
    }

    public void setIdL(Long idL) {
        this.idL = idL;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
