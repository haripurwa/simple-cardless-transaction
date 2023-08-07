package api.transaction.manless.repository;

import api.transaction.manless.dto.ListTransactionResponse;
import api.transaction.manless.model.CardlessTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardlessTransRepo extends JpaRepository<CardlessTransaction, UUID> {

    @Query(value = "select c.account_no ,c.email ,ct.debet_amount ,ct.location, ct.created_at \n" +
            "from customer c \n" +
            "join cardless_transaction ct \n" +
            "on c.account_no =ct.account_no \n " +
            "where account_no=:accountNo ORDER BY c.created_at DESC",
            countQuery = "select count(c.id) " +
                    "from customer c \n" +
                    "join cardless_transaction ct \n" +
                    "on c.account_no =ct.account_no \n" +
                    "where account_no=:accountNo "
            , nativeQuery = true)
    Page<ListTransactionResponse> findListTransaction(String accountNo,Pageable pageable);
}
