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
@Table(name = "customer")
public class Customer {
    @Id
    @Type(type = ("uuid-char"))
    private UUID id;
    private String name;
    private String mobileNo;
    private String email;
    private String nik;
    private String accountNo;
    private String gender;
    private boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;


    @PrePersist
    public void setModelBeforeInsert() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}
