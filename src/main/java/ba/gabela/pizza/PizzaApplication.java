package ba.gabela.pizza;

import ba.gabela.pizza.util.MyModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PizzaApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MyModelMapper modelMapper() {
		MyModelMapper modelMapper = new MyModelMapper();
		modelMapper.getConfiguration()
				.setSkipNullEnabled(true);

		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

}
