package api.transaction.manless.repository;
import api.transaction.manless.model.CardlessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardlessTransRepo extends JpaRepository<CardlessTransaction, UUID> {
}
