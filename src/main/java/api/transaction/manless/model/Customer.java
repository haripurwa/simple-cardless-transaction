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
@Table(name = "customer")
public class Customer {
    @Id
    private UUID id;
    private String name;
    private String mobileNo;
    private String email;
    private String nik;
    private String gender;
    private String password;
    private boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;


    @PrePersist
    public void setModelBeforeInsert() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

}
