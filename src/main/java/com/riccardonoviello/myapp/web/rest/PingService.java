
package com.riccardonoviello.myapp.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author novier
 */
@RequestMapping("/ping")
@Controller
public class PingService {

	
    private final static Logger logger = Logger.getLogger(PingService.class.getName());

    @Autowired
    @Qualifier("jsonMapper")
    protected ObjectMapper jsonMapper;
    
    /**
     * Plain JSON result
     * @return
     */
    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String ping() {
    	logger.info("Ping called");
        return "{'ping':'pong'}";
        
    }    
    
    /**
     * JSON mapper result
     * @return
     */
    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getGclids() {
        StringWriter json = new StringWriter();

        try {
            jsonMapper.writeValue(json, "hi");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "error converting clicks to json.", ex);
        }
        
        return json.toString();
    }
    
}
