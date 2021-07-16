package ba.gabela.pizza.database.repository;

import ba.gabela.pizza.database.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Person findByUsernameIgnoreCase(@Param("username") String username);
}
