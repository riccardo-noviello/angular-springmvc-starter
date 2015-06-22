package com.riccardonoviello.myapp.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riccardonoviello.myapp.web.model.Person;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author novier
 */
@RequestMapping("/persons")
@Controller
public class PersonService {

    private final static Logger logger = Logger.getLogger(PersonService.class.getName());
    
   private static ArrayList<Person> listOfPersons = (ArrayList<Person>) populateList();

    @Autowired
    @Qualifier("jsonMapper")
    protected ObjectMapper jsonMapper;

    /**
     * Plain JSON result
     *
     * @return
     */
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getPersons() {
        StringWriter json = new StringWriter();
        try {
            jsonMapper.writeValue(json, listOfPersons);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "error converting clicks to json.", ex);
        }
        return json.toString();
    }

    /**
     * Plain JSON result
     *
     * @return
     */
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getOnePerson(@PathVariable("id") int personId) {
        StringWriter json = new StringWriter();
        
        try {
            jsonMapper.writeValue(json, findOne(personId, listOfPersons));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "error converting clicks to json.", ex);
        }
        return json.toString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public  @ResponseBody String addPerson(@RequestBody Person person) {
        listOfPersons.add(person);
        return "ok";
    }
    
    /**
     * Mocks a List of Persons
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
       for (Person item : listOfPersons){
           if(item.getId()==personId) return item;
       }
       return null;
    }

}
