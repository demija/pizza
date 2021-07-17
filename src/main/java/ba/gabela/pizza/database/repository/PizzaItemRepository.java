package ba.gabela.pizza.database.repository;

import ba.gabela.pizza.database.model.PizzaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PizzaItemRepository extends JpaRepository<PizzaItem, String> {
    PizzaItem findBySlugIgnoreCase(String slug);
    List<PizzaItem> findByNameContainingIgnoreCase(String name);
}
