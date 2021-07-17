package ba.gabela.pizza.controller;

import ba.gabela.pizza.generated.api.ItemsApi;
import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.generated.model.PatchItem;
import ba.gabela.pizza.service.item.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Api(tags = {"item"})
public final class ItemController implements ItemsApi {
    @Autowired
    private ItemService itemService;

    @Override
    public ResponseEntity<List<ItemResponse>> searchItem(String name) {
        return ResponseEntity.ok(itemService.searchByName(name));
    }

    @Override
    public ResponseEntity<Void> updateItem(String authorization, PatchItem body) {
        itemService.patch(body);
        return ResponseEntity.noContent().build();
    }
}
