package fr.ilyas.rev.exercice7b;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departement")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDep")
    private Long idDep;

    @Column(name = "nomDep", nullable = false)
    private String nomDep;

    @Column(name = "categorie")
    private String categorie;

    // OneToMany avec classe Livre (à créer)
    @OneToMany(mappedBy = "departement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Livre> livres;

    public Departement() {
    }

    public Departement(String nomDep, String categorie) {
        this.nomDep = nomDep;
        this.categorie = categorie;
    }

    public Long getIdDep() {
        return idDep;
    }

    public void setIdDep(Long idDep) {
        this.idDep = idDep;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}
