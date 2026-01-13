package fr.ilyas.rev.exercice7b;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DepartementDAO {

    private SessionFactory sessionFactory;

    public DepartementDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Departement departement) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.persist(departement); // Using modern persist instead of save
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Departement findById(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Departement.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public List<Departement> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Departement> query = session.createQuery("from Departement", Departement.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void delete(Departement departement) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.remove(departement); // Using remove instead of delete
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    // Question 5: ajoutez la méthode updateDepartement(...)
    public void updateDepartement(Departement departement) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.merge(departement); // Using merge for update
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }
}
