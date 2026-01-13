# Exercice 7B : Hibernate

**1. Expliquez l'intérêt de chacune des Annotations de la classe Departement.**
*   **`@Entity`** : Indispensable. Elle définit la classe comme un objet métier persistant géré par l'ORM. Elle sera mappée à une ligne e table.
*   **`@Table`** : Permet de personnaliser le mapping (nom de la table, schéma, index) si on ne veut pas utiliser les valeurs par défaut (nom de la classe).
*   **`@Id`** : Définit la clé primaire (Primary Key) de l'objet.
*   **`@GeneratedValue`** : Délègue la création de la ou unique au SGBD (ici `IDENTITY` correspond à une colonne `AUTO_INCREMENT` en MySQL/PostgreSQL).
*   **`@Column`** : Permet de définir les propriétés physiques de la colonne (nom, contrainte `NOT NULL`, longueur).
*   **`@OneToMany`** : Définit la relation structurelle. Un Département possède plusieurs Livres.
    *   `mappedBy="departement"` : Indique que la relation est bidirectionnelle et que la clé étrangère est portée par la classe `Livre` (attribut `departement`).
    *   `fetch=CheckType.LAZY` : Optimisation. Les livres ne seront chargés depuis la BDD que si on appelle `getLivres()`, évitant de charger toute la base en mémoire.
    *   `cascade=CascadeType.ALL` : Propage les actions. Sauvegarder un département sauvegarde ses livres.

**2. Selon la classe Département, donnez la déclaration complète de la classe Livre.**
```java
@Entity
public class Livre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idL;
    
    // ... autres champs (titre, auteur)

    @ManyToOne // Relation inverse obligatoire
    @JoinColumn(name = "idDep") // Nom de la colonne clé étrangère dans la table Livre
    private Departement departement;
}
```

**3. Quel est le rôle de : cascade = CascadeType.ALL ?**
Ce paramètre active la propagation transitive des opérations.
Si on applique une opération (`persist`, `merge`, `remove`) sur l'entité Parent (`Departement`), Hibernate l'appliquera automatiquement sur toutes les entités Enfants (`Livre`) contenues dans la liste.
*Exemple* : `session.save(departement)` enregistrera aussi tous les objets `Livre` de la liste `departement.getLivres()`.

**4. Expliquez le rôle de : Transaction, beginTransaction, createQuery, commit, SessionFactory.**
*   **SessionFactory** : C'est l'usine de configuration (lourde, thread-safe). Elle correspond à la base de données et permet de créer les `Session`.
*   **Session** : C'est le contexte de persistance (léger, non thread-safe). Elle représente une conversation avec la base de données.
*   **Transaction** : Délimite un ensemble d'opérations atomiques (ACID).
*   **beginTransaction / commit** : Démarre et valide la transaction. Sans commit, aucune modification n'est enregistrée définitivement.
*   **createQuery** : Permet d'écrire des requêtes orientées objet (HQL/JPQL). Ex: `from Departement` au lieu de `SELECT * FROM T_DEP`.

**5. Ajoutez la méthode updateDepartement(...)**
Utilisez `session.merge()` (standard JPA moderne) ou `session.update()` (spécifique Hibernate ancien).
```java
public void updateDepartement(Departement d) {
    Session s = factory.openSession();
    Transaction tx = s.beginTransaction();
    s.merge(d); // Met à jour l'entité
    tx.commit();
    s.close();
}
```

**6. Expliquez l'utilité des concepts et outils suivants en développement java : Jakarta, persistance des données, ORM.**
*   **Persistance** : Capacité à sauvegarder l'état d'un objet (données mémoire) sur un support durable (Disque/BDD) pour le retrouver plus tard.
*   **ORM (Object Relational Mapping)** : Outil qui automatise la "traduction" entre les Objets Java (graphe d'objets, héritage) et les Tables relationnelles (lignes, colonnes, clés étrangères). Il évite au développeur d'écrire du SQL répétitif (`INSERT INTO...`). Hibernate est le plus connu.
*   **Jakarta EE (ex-Java EE)** : C'est la spécification standard des APIs d'entreprise. Dans ce contexte, **Jakarta Persistence (JPA)** est l'interface standard que les ORM (comme Hibernate) doivent implémenter. Cela permet de standardiser le code (annotations `@Entity`, etc.) quel que soit l'implémentation sous-jacente.
