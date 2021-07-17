package ba.gabela.pizza.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public final class PizzaItem {
    @Id
    @Column(unique = true)
    @NotBlank(message = "This field is required")
    private String slug;

    @NotBlank(message = "This field is required")
    private String name;

    @NotNull(message = "This field is required")
    private Integer size;

    @NotNull(message = "This field is required")
    @DecimalMin(value = "0.01", message = "Minimal price is 0.01")
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
