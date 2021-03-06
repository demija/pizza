package ba.gabela.pizza.service.item.impl;

import ba.gabela.pizza.database.model.PizzaItem;
import ba.gabela.pizza.database.repository.PizzaItemRepository;
import ba.gabela.pizza.exception.CustomException;
import ba.gabela.pizza.generated.model.ItemResponse;
import ba.gabela.pizza.generated.model.PatchItem;
import ba.gabela.pizza.service.item.ItemService;
import ba.gabela.pizza.util.MyModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.beans.FeatureDescriptor;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public final class ItemServiceImpl implements ItemService {
    @Autowired
    private PizzaItemRepository pizzaItemRepository;

    @Autowired
    private MyModelMapper modelMapper;

    @Override
    public List<ItemResponse> searchByName(String name) {
        return pizzaItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(pizzaItem -> modelMapper.map(pizzaItem, ItemResponse.class))
                .sorted(Comparator.comparing(ItemResponse::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void patch(PatchItem body) {
        PizzaItem pizzaItem = pizzaItemRepository.findById(body.getSlug()).orElse(null);

        if (pizzaItem == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Not found", "Menu item not found");
        }

        PizzaItem map = modelMapper.map(body, PizzaItem.class);

        BeanUtils.copyProperties(map, pizzaItem, getNullPropertyNames(map));

        pizzaItemRepository.save(pizzaItem);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);

        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
