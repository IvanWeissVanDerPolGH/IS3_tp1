package py.com.progweb.prueba.dao.Customer;

import py.com.progweb.prueba.model.Customer;
import py.com.progweb.prueba.exception.DatabaseException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;



import java.util.List;

@Stateless
public class CustomerDAOImpl implements CustomerDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        try {
            em.persist(customer);
        } catch (PersistenceException e) {
            throw new DatabaseException("Failed to create customer", e);
        }
    }
    

    @Override
    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    @Override
    public void delete(Integer id) {
        Customer customer = em.find(Customer.class, id);
        em.remove(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return em.find(Customer.class, id);
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Or handle it according to your business logic
        }
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
