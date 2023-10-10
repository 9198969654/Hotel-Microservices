package com.lcwd.user.service.controllers;

//import ch.qos.logback.classic.Logger;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import org.slf4j.Logger;



import java.util.List;

@RestController
//@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);



    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
      User user1 = userService.saveUser(user);
      return  ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    //single User get
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
      User user=  userService.getUser(userId);
      return ResponseEntity.ok(user);
    }

    //creating fall back method for circuitbreaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        // Create a User object with dummy data
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created as Dummy because some services are down")
                .userId("141234")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //Get All User
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
       List<User> allUser =  userService.getAllUser();
       return ResponseEntity.ok(allUser);
    }
}
