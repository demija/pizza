package ba.gabela.pizza.util;

import org.modelmapper.ModelMapper;

public class MyModelMapper extends ModelMapper {
    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if(source == null){
            return null;
        }

        return super.map(source, destinationType);
    }
}
