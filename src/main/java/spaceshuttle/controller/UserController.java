package spaceshuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import spaceshuttle.model.APIResponse;
import spaceshuttle.model.User;
import spaceshuttle.repository.UserRepository;

@RestController
@RequestMapping(path = "/users") // This means URL's start with /user (after Application path)

public class UserController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @RequestMapping("/hello")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User getUserById(@RequestParam Long userId) {
        return userRepository.findOne(userId);
    }

    @DeleteMapping
    public @ResponseBody
    String deleteUser(@RequestParam Long userId) {
        userRepository.delete(userId);
        return "Deleted";
    }

    @PutMapping
    public @ResponseBody
    String updateUser(@RequestParam Long userId, @RequestParam String username, @RequestParam String password) {
        User currentUser = userRepository.findOne(userId);
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        userRepository.save(currentUser);
        return "Updated";
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE) // Map ONLY GET Requests
    public @ResponseBody
    APIResponse addNewUser(@RequestParam String username
            , @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)// Map ONLY GET Requests
    public @ResponseBody
    User addUser(@RequestBody User newUser) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        return userRepository.save(newUser);
    }
}