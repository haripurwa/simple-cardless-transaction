package api.transaction.manless.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CardlessTransaction {
    @Id
    @Type(type = ("uuid-char"))
    private UUID id;
    private String accountNo;
    private String debetAmount;
    private String location;
    private String description;
    private LocalDateTime createdAt;
    @PrePersist
    public void setModelBeforeInsert() {
        this.createdAt = LocalDateTime.now();
    }

}
