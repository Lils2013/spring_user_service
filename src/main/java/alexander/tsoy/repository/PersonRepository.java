package alexander.tsoy.repository;

import alexander.tsoy.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByEmail(String email);

}