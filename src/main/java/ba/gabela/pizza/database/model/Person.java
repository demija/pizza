package ba.gabela.pizza.database.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
public final class Person {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    @NotBlank(message = "This field is required")
    private String username;

    @NotBlank(message = "This field is required")
    private String password;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }
}
