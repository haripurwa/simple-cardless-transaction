package api.transaction.manless.repository;

import api.transaction.manless.model.DataParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;
@Repository
public interface DataParamRepo extends JpaRepository<DataParam, UUID> {
    Optional<DataParam> findByTransaction(@Param("transaction") String transaction);
}
