package ba.gabela.pizza.service.menu.impl;

import ba.gabela.pizza.database.model.PizzaItem;
import ba.gabela.pizza.database.repository.PizzaItemRepository;
import ba.gabela.pizza.generated.model.Item;
import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.service.menu.MenuService;
import ba.gabela.pizza.util.MyModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private PizzaItemRepository pizzaItemRepository;

    @Autowired
    private MyModelMapper modelMapper;

    @Override
    public void create(List<Item> body) {
        if (pizzaItemRepository.count() > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Menu already exists");
        }

        pizzaItemRepository.saveAll(body.stream()
                .map(item -> modelMapper.map(item, PizzaItem.class))
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteItem(String slug) {
        PizzaItem pizzaItem = pizzaItemRepository.findBySlugIgnoreCase(slug);

        if (pizzaItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found");
        }

        pizzaItemRepository.delete(pizzaItem);
    }

    @Override
    public List<ItemResponse> get() {
        return pizzaItemRepository.findAll().stream()
                .map(pizzaItem -> modelMapper.map(pizzaItem, ItemResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(List<Item> body) {
        pizzaItemRepository.deleteAll();
        pizzaItemRepository.saveAll(body.stream()
                .map(item -> modelMapper.map(item, PizzaItem.class))
                .collect(Collectors.toList()));
    }
}
