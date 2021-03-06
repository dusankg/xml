package com.example.registerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import com.example.registerservice.dto.UserDTO;
import com.example.registerservice.enumeration.RoleEnum;
import com.example.registerservice.model.RegistrationRequest;
import com.example.registerservice.model.Validation;
import com.example.registerservice.services.RegisterService;
import com.example.registerservice.services.RegistrationRequestService;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	RegisterService registerService;
	
	@Autowired
	RegistrationRequestService requestService;
	
	// adding new registration request
    @GetMapping("/{username}")
    public ResponseEntity<Boolean> checkIfUsernameTaken(@PathVariable String username){
        RegistrationRequest rr= requestService.getRequest(username);
        if (rr == null){
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<String> addRegistrationRequest(@RequestBody UserDTO user) throws MailException, InterruptedException{
    	requestService.createRequest(user);
    	return new ResponseEntity<>("", HttpStatus.OK);
    }
    
    // adding user to database if validation data is OK
    @PutMapping
    public ResponseEntity<String> validateUser(@RequestBody Validation sentValidation){
    	UserDTO user = requestService.validateUser(sentValidation);
    	
    	if(user == null) {
    		return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    	}
    	
        boolean success = registerService.register(user);
        if(success){
            return new ResponseEntity<>("", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @PostMapping("/company")
    public ResponseEntity<String> addCompany(@RequestBody UserDTO user){
    	if(user == null) {
    		return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    	}
    	user.setActive(true);
    	user.setRole(RoleEnum.COMPANY);
    	boolean success = registerService.addCompany(user);
    	
        if(success){
            return new ResponseEntity<>("", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    
    
}
