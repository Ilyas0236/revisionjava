# Exercice 7B : Hibernate

**1. Expliquez l'intérêt de chacune des Annotations de la classe Departement.**
*   `@Entity`: Indique à Hibernate que cette classe est un bean persistant (une entité) qui doit être mappé à une table de base de données.
*   `@Table(name = "departement")`: Spécifie le nom exact de la table dans la base de données vers laquelle l'entité pointe. Utile si le nom de la classe diffère du nom de la table ou pour gérer les schémas.
*   `@Id`: Marque le champ comme étant la clé primaire de l'objet.
*   `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Indique comment la valeur de la clé primaire est générée. `IDENTITY` signifie que c'est la base de données qui gère l'auto-incrémentation (auto_increment MySQL).
*   `@Column(name = "...", nullable = false)`: Mappe l'attribut de la classe à une colonne spécifique. `nullable = false` ajoute une contrainte NOT NULL.
*   `@OneToMany(mappedBy = "...", ...)`: Définit une relation Un-à-Plusieurs.
    *   `mappedBy`: Indique que c'est l'autre côté de la relation (classe `Livre`, attribut `departement`) qui porte la clé étrangère ("owner side").
    *   `fetch = FetchType.LAZY`: Les données liées (livres) ne sont chargées que lorsqu'on y accède explicitement (performance).
    *   `cascade = CascadeType.ALL`: Les opérations (save, update, delete) faites sur le Département sont propagées aux Livres.

**2. Selon la classe Département, donnez la déclaration complète de la classe Livre.**
Voir le fichier `Livre.java`. Elle doit contenir `@Entity`, `@Id`, et surtout `@ManyToOne @JoinColumn(name="idDep") private Departement departement;` pour correspondre au `mappedBy="departement"`.

**3. Quel est le rôle de : cascade = CascadeType.ALL ?**
Cela signifie que toutes les opérations de persistance (PERSIST, MERGE, REMOVE, REFRESH, DETACH) appliquées à l'entité mère (`Departement`) seront automatiquement propagées aux entités filles (`Livre`). Par exemple, si on supprime un département, Hibernate supprimera aussi tous les livres associés, ou si on sauvegarde un département avec de nouveaux livres ajoutés à sa liste, ils seront sauvegardés aussi.

**4. Expliquez le rôle de : Transaction, beginTransaction, createQuery, commit, SessionFactory.**
*   **SessionFactory**: Objet lourd (thread-safe) créé une seule fois au démarrage, sert d'usine pour créer des `Session`. Il contient la configuration de connexion et les mappings.
*   **Session**: Interface principale pour interagir avec la base (sauvegarder, récupérer des objets). Elle n'est pas thread-safe et doit être ouverte/fermée pour chaque unité de travail (requête).
*   **Transaction**: Unité de travail atomique. Garantit que soit toutes les opérations réussissent, soit aucune ne s'applique.
*   **beginTransaction**: Démarre une nouvelle transaction sur la session courante.
*   **commit**: Valide la transaction, rendant les modifications permanentes en base de données.
*   **createQuery**: Permet de créer une requête HQL (Hibernate Query Language) ou JPQL pour interroger les objets plutôt que les tables SQL.

**5. Ajoutez la méthode updateDepartement(...)**
Voir `DepartementDAO.java`. Utilise `session.update(dep)` ou `session.merge(dep)`.

**6. Expliquez l'utilité des concepts et outils suivants en développement java : Jakarta, persistance des données, ORM.**
*   **Persistance des données**: Fait de conserver les données de l'application de manière durable (sur disque via une BDD) pour qu'elles survivent à l'arrêt du programme.
*   **ORM (Object-Relational Mapping)**: Technique/Outil qui permet de faire le pont entre le monde Objet (Java) et le monde Relationnel (SQL). Il permet au développeur de manipuler des objets Java simples (`Departement`, `Livre`) et laisse l'outil (Hibernate) générer le SQL nécessaire.
*   **Jakarta (Jakarta EE)**: Est l'évolution de Java EE (Enterprise Edition). C'est un ensemble de spécifications (APIs) pour le développement d'applications d'entreprise en Java. `Jakarta Persistence` (anciennement JPA) est la spécification standard pour l'ORM en Java, dont Hibernate est une implémentation.
