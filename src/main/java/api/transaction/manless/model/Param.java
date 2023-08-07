package api.transaction.manless.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "account_customer")
public class Param {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String transaction;
    private  String limit;
    private LocalDateTime createdAt;

    @PrePersist
    public void setModelBeforeInsert() {
        this.createdAt = LocalDateTime.now();
    }
}
