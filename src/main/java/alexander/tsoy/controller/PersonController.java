package alexander.tsoy.controller;

import alexander.tsoy.exception.ResourceNotFoundException;
import alexander.tsoy.repository.PersonRepository;
import alexander.tsoy.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value="/people", method= RequestMethod.POST)
    public ResponseEntity<?> createPoll(@Valid @RequestBody Person person) {
        person = personRepository.save(person);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/people/{personId}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long personId) {
        verifyPerson(personId);
        personRepository.delete(personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/people/search/findByEmail", method= RequestMethod.GET)
    public ResponseEntity<?> computeResult(@RequestParam String email) {
        List<Person> person = personRepository.findByEmail(email);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    protected void verifyPerson(Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findOne(personId);
        if(person == null) {
            throw new ResourceNotFoundException("Poll with id " + personId + " not found");
        }
    }
}
