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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    APIResponse addUser(@RequestBody User newUser) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setErrorCode("123");
        apiResponse.setResponseObject(userRepository.save(newUser));
        return apiResponse;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    APIResponse addUserByXML(@RequestBody User newUser) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setResponseObject(userRepository.save(newUser));
        return apiResponse;
    }

    @GetMapping(value = "/{id}")
    public APIResponse getUserById(@PathVariable("id") Long id) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        apiResponse.setResponseObject(userRepository.findOne(id));
        return apiResponse;

    }

    @DeleteMapping(value = "/{id}")
    public APIResponse deleteUserById(@PathVariable("id") Long id) {
        userRepository.delete(id);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @PutMapping(value = "/{id}")
    public APIResponse updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User oldUser = userRepository.findById(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        return apiResponse;
    }
}