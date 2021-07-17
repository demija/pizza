package ba.gabela.pizza.controller;

import ba.gabela.pizza.generated.api.ItemsApi;
import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.generated.model.PatchItem;
import ba.gabela.pizza.service.item.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Api(tags = {"item"})
@SuppressWarnings({"AnnotationUseStyle", "DesignForExtension"})
public class ItemController implements ItemsApi {
    @Autowired
    private ItemService itemService;

    @Override
    @Cacheable("items")
    public ResponseEntity<List<ItemResponse>> searchItem(String name) {
        return ResponseEntity.ok(itemService.searchByName(name));
    }

    @Override
    @CacheEvict(value = "items", allEntries = true)
    public ResponseEntity<Void> updateItem(String authorization, PatchItem body) {
        itemService.patch(body);
        return ResponseEntity.noContent().build();
    }
}
