package practiceQuery;

import ayushproject.ayushecommerce.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
class CriteriaQueryAdmin implements UserDao{

    EntityManager entityManager;

//    @Override
//    Optional<User> findById(String name){
//
//    }

    @Override
    public User findById(Integer id) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);

        Root<User> userRoot=criteriaQuery.from(User.class);
        Predicate idPredicate = criteriaBuilder.equal(userRoot.get("id"), id);
        criteriaQuery.where(idPredicate);

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        System.out.println(query.getSingleResult().getId());

        return query.getSingleResult();
//        return Optional.empty();
    }
}
