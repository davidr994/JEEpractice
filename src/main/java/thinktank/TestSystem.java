package thinktank;

import javax.persistence.*;
import java.util.List;



public class TestSystem {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("thinktank");

    public static void main(String[] args) {

        addCustomer(1, "David","Röhrig");
        addCustomer(2, "Ruri","Ito-Röhrig");
        addCustomer(3, "Max","powers");
        addCustomer(4, "Ivan","Sergienko");
        addCustomer(5, "Flora","Pedals");

        getCustomer(2);

        changeFName(5,"Fiona");

        deleteCustomer(4);


        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addCustomer(int id, String fname, String lname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Customer customer = new Customer();
            customer.setId(id);
            customer.setfName(fname);
            customer.setlName(lname);
            em.persist(customer);
            et.commit();
        } catch (Exception e) {
            if(et != null) {
                et.rollback();
            } e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void getCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.id = :custID";

        TypedQuery<Customer> tq = em.createQuery(query,Customer.class);
        tq.setParameter("custID", id);
        Customer customer = null;
        try {
            customer = tq.getSingleResult();
                    System.out.println(customer.getfName() + " " + customer.getlName());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void getCustomers(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String strQuery = "SELECT c FROM Customer c WHERE c.id IS NOT NULL";
        TypedQuery<Customer> tq = em.createQuery(strQuery,Customer.class);
        List<Customer> customers;
        try {
            customers = tq.getResultList();
            customers.forEach(customer->System.out.println(customer.getfName() + " " + customer.getlName()));
        } catch (NoResultException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        }

    public static void changeFName(int id, String fname) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;

        try {
            et = em.getTransaction();
            et.begin();
            customer = em.find(Customer.class, id);
            customer.setfName(fname);
            em.persist(customer);
            et.commit();
        } catch (Exception e) {
            if(et != null) {
                et.rollback();
            } e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void deleteCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;

        try {
            et = em.getTransaction();
            et.begin();
            customer = em.find(Customer.class, id);
            em.remove(customer);
            em.persist(customer);
            et.commit();
        } catch (Exception e) {
            if(et != null) {
                et.rollback();
            } e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

}

