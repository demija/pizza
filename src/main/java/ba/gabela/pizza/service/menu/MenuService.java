package ba.gabela.pizza.service.menu;

import ba.gabela.pizza.generated.model.Item;
import ba.gabela.pizza.generated.model.ItemResponse;
import java.util.List;

public interface MenuService {
    void create(List<Item> body);
    void deleteItem(String slug);
    List<ItemResponse> get();
    void update(List<Item> body);
}
