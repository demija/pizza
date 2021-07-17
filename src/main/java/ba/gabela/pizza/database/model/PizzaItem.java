package ba.gabela.pizza.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class PizzaItem {
    @Id
    private String slug;

    @NonNull
    private String name;

    @NonNull
    private Integer size;

    @NonNull
    @DecimalMin(value = "0.01")
    private Float price;

    private LocalDateTime date;

    @PrePersist
    void onCreate() {
        this.setDate(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setDate(LocalDateTime.now());
    }
}
