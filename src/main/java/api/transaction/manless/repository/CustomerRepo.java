package api.transaction.manless.repository;

import api.transaction.manless.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByMobileNo(@Param("mobileNo") String mobileNo);
    List<Customer> findByNik(@Param("nik") String nik);
    Optional<Customer> findByNikAndAccountNo(@Param("nik") String nik,@Param("accountNo") String accountNo);
}
