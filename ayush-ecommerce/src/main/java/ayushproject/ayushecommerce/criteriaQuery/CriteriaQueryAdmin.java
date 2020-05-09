package ayushproject.ayushecommerce.criteriaQuery;

import ayushproject.ayushecommerce.entities.Category;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

//@Repository
@Service
class CriteriaQueryAdmin {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    Optional<User> findById(String name){
//
//    }


    public List<User> getAllUserCriteria() {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User> userRoot=criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Category> getAllcategories() {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery=criteriaBuilder.createQuery(Category.class);
        Root<Category> userRoot=criteriaQuery.from(Category.class);
        criteriaQuery.select(userRoot);
        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }


    public List<Integer[]> getProductCount() {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer[]> criteriaQuery=criteriaBuilder.createQuery(Integer[].class);
        Root<Product> userRoot=criteriaQuery.from(Product.class);
        criteriaQuery.multiselect(userRoot.get("category"),criteriaBuilder.count(userRoot)).groupBy(userRoot.get("category"));

//        criteriaQuery.select(criteriaBuilder.count(userRoot));
        TypedQuery<Integer[]> query = entityManager.createQuery(criteriaQuery);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    public List<Product> getAllProducts() {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery=criteriaBuilder.createQuery(Product.class);
        Root<Product> userRoot=criteriaQuery.from(Product.class);
        criteriaQuery.select(userRoot);
        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
