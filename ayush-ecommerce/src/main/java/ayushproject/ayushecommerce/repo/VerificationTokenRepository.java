package ayushproject.ayushecommerce.repo;

import ayushproject.ayushecommerce.security.VerificationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Integer> {

    VerificationToken findByVerificationToken(String verificationToken );
}
