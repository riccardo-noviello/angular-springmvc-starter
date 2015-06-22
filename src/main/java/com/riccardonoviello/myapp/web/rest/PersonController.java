package com.riccardonoviello.myapp.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riccardonoviello.myapp.web.model.Person;

/**
 *
 * @author novier
 */
@RequestMapping("/persons")
@Controller
public class PersonController {

	private final static Logger logger = Logger
			.getLogger(PersonController.class.getName());

	private static ArrayList<Person> listOfPersons = (ArrayList<Person>) populateList();

                        @RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String hello() {
                            return "hello";
	}
        
	/**
	 * Plain JSON result
	 *
	 * @return
	 */
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<Person>> getPersons() {
		return new ResponseEntity<>(listOfPersons, HttpStatus.OK);
	}

	/**
	 * Plain JSON result
	 *
	 * @return
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Person> getOnePerson(@PathVariable("id") int personId) {
		return new ResponseEntity<>(findOne(personId, listOfPersons),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public String addPerson(@RequestBody Person person) {
		listOfPersons.add(person);
		return "ok";
	}

	/**
	 * Mocks a List of Persons
	 * 
	 * @return
	 */
	private static List<Person> populateList() {
		List<Person> list = new ArrayList<Person>();
		Person person = new Person();
		person.setAge(28);
		person.setId(46387558L);
		person.setName("Riccardo");
		person.setSurname("Noviello");
		list.add(person);

		person = new Person();
		person.setId(2934432L);
		person.setAge(34);
		person.setName("John");
		person.setSurname("Doe");
		list.add(person);

		person = new Person();
		person.setId(9065976L);
		person.setAge(55);
		person.setName("Mark");
		person.setSurname("Jones");
		list.add(person);

		return list;
	}

	private Person findOne(int personId, ArrayList<Person> listOfPersons) {
		for (Person item : listOfPersons) {
			if (item.getId() == personId)
				return item;
		}
		return null;
	}

}
