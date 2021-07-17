package ba.gabela.pizza.controller;

import ba.gabela.pizza.generated.api.RegisterApi;
import ba.gabela.pizza.generated.model.User;
import ba.gabela.pizza.service.registration.RegistrationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"register"})
public final class RegisterController implements RegisterApi {
    @Autowired
    private RegistrationService registrationService;

    @Override
    public ResponseEntity<Void> register(User body) {
        registrationService.register(body);
        return ResponseEntity.noContent().build();
    }
}
