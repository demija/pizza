package ba.gabela.pizza.controller;

import ba.gabela.pizza.generated.api.MenuApi;
import ba.gabela.pizza.generated.model.Item;
import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.service.menu.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Api(tags = {"menu"})
@SuppressWarnings({"AnnotationUseStyle", "DesignForExtension"})
public class MenuController implements MenuApi {
    @Autowired
    private MenuService menuService;

    @Override
    @CacheEvict(value = "items", allEntries = true)
    public ResponseEntity<Void> createMenu(String authorization, List<Item> body) {
        menuService.create(body);
        return ResponseEntity.ok().build();
    }

    @Override
    @CacheEvict(value = "items", allEntries = true)
    public ResponseEntity<Void> deleteItem(String authorization, String slug) {
        menuService.deleteItem(slug);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Cacheable("items")
    public ResponseEntity<List<ItemResponse>> getMenu() {
        return ResponseEntity.ok(menuService.get());
    }

    @Override
    @CacheEvict(value = "items", allEntries = true)
    public ResponseEntity<Void> updateMenu(String authorization, List<Item> body) {
        menuService.update(body);
        return ResponseEntity.noContent().build();
    }
}
