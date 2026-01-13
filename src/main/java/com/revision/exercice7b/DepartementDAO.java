package com.revision.exercice7b;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DepartementDAO {

    private SessionFactory sessionFactory;

    public DepartementDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Departement departement) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(departement); // Deprecated in hibernate 6 but valid in older/exercises. In 6: persist.
        // Or session.persist(departement);
        tx.commit();
        session.close();
    }

    public Departement findById(Long id) {
        Session session = sessionFactory.openSession();
        Departement dep = session.get(Departement.class, id);
        session.close();
        return dep;
    }

    public List<Departement> findAll() {
        Session session = sessionFactory.openSession();
        // HQL
        List<Departement> list = session.createQuery("from Departement", Departement.class).list();
        session.close();
        return list;
    }

    public void delete(Departement departement) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(departement); // In 6: remove
        tx.commit();
        session.close();
    }

    // Question 5: ajoutez la méthode updateDepartement(...) en utilisant les
    // méthodes Hibernate standards.
    public void updateDepartement(Departement departement) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        // update ou merge
        session.update(departement);
        // ou session.merge(departement);
        tx.commit();
        session.close();
    }
}
