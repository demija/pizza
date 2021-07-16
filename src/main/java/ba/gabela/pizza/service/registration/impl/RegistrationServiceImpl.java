package ba.gabela.pizza.service.registration.impl;

import ba.gabela.pizza.database.model.Person;
import ba.gabela.pizza.database.repository.PersonRepository;
import ba.gabela.pizza.generated.model.User;
import ba.gabela.pizza.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public void register(User body) {
        if (personRepository.findByUsernameIgnoreCase(body.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        Person person = new Person();
        person.setUsername(body.getUsername());
        person.setPassword(bcryptEncoder.encode(body.getPassword()));
        personRepository.save(person);
    }
}
