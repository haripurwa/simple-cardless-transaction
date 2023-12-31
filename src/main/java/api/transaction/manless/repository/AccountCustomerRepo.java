package api.transaction.manless.repository;

import api.transaction.manless.model.AccountCustomer;
import api.transaction.manless.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountCustomerRepo extends JpaRepository<AccountCustomer, UUID> {
    Optional<AccountCustomer> findByAccountNo(@Param("accountNo") String accountNo);

}
