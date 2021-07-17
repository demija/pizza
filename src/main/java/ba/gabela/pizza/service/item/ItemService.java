package ba.gabela.pizza.service.item;

import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.generated.model.PatchItem;
import java.util.List;

public interface ItemService {
    List<ItemResponse> searchByName(String name);
    void patch(PatchItem body);
}
