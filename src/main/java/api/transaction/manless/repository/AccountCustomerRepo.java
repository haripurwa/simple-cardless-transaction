package api.transaction.manless.repository;

import api.transaction.manless.model.AccountCustomer;
import api.transaction.manless.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCustomerRepo extends JpaRepository<AccountCustomer, Integer> {
    Optional<Customer> findByAccountNo(@Param("accountNo") String accountNo);

}
