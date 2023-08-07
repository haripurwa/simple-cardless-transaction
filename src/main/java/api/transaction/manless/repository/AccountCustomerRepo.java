package api.transaction.manless.repository;

import api.transaction.manless.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountCustomerRepo extends JpaRepository<AccountCustomerRepo, Integer> {
    Optional<Customer> findByAccountNo(@Param("accountNo") String accountNo);

}
