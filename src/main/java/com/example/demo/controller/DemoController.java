package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.email.EmailUtil;
import com.example.demo.exception.ResourceNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RestController("SpringBoOtDemoController")
@Api(tags = {"SpringBootDemoAPI"})
@RequestMapping("springboot/demo/v1")
public class DemoController {
	 private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
	 private static final Cache userCache = CacheManager.getInstance().getCache("ehCacheDemo");
	
	@RequestMapping(value="name" , method=RequestMethod.GET)
	@ApiOperation(value = "API to Get username ", response = String.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 424, message = "Failed Dependency"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server-side error.")
})
	public String getname() {
		return "Deepak";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ApiOperation(value = "API to save user ", response = String.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 424, message = "Failed Dependency",response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error - Server-side error." , response = String.class)
})
	
	public  ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userdto) {
        //Save user Object
		//return ResponseEntity.status(HttpStatus.OK).body(userdto);
		//return ResponseEntity.status(HttpStatus.OK).body(userdto);
		userCache.put(new Element(userdto.getId(),userdto));
		return ResponseEntity.status(HttpStatus.OK).body(userdto);
		//throw new NullPointerException();
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "API to Get user by id ", response = String.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 424, message = "Failed Dependency"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server-side error.")
})
	public ResponseEntity<Object> getUser(@PathVariable("id") final long id) {
		//service method to get object byId object
		Element cacheElement = userCache.get(id);
		Long userId = null;
		if(cacheElement != null) {
			System.out.println("user exist in cache");
			UserDTO useDTO = (UserDTO)cacheElement.getObjectValue();
			//UserDTO useDTO = new UserDTO("Deepak",userId,new AddressDTO("Sector-78" , "Noida"));
			return ResponseEntity.status(HttpStatus.OK).body(useDTO);
		}
		logger.info("User does not exist in the System for id :{} ", id);
		throw new ResourceNotFoundException("User does not exist in the System");
	}
	
	
	@RequestMapping(value = "/sendEmail/{emailId}", method = RequestMethod.GET)
	@ApiOperation(value = "API to send email", response = String.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 424, message = "Failed Dependency"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server-side error.")
})
	public ResponseEntity<Object> sendEmail (@PathVariable("emailId") final String emailId) throws Exception {
		logger.info("Sendinf Email to :{} ", emailId);
		EmailUtil.sendEmail(emailId);
		return ResponseEntity.status(HttpStatus.OK).body("Email Sent Sucessfully to "+emailId);
		
	}

}
