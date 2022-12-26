package com.maxim.springbootfeatures.resources;

import com.maxim.springbootfeatures.model.User;
import com.maxim.springbootfeatures.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RestController
//@RequestMapping(
//       path = "/api/v1/users"
//)
public class UserResource {

    private final UserService userService;

    //@Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> fetchUsers(@QueryParam("gender") String gender) {

        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "{userUid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {
        Optional<User> userOptional = userService.getUser(userUid);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage("user " + userUid + " was not found."));

//        return userService.getUser(userUid).<ResponseEntity<?>>map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ErrorMessage("user " + userUid + " was not found.")));

    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{userUid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> deleteUser(@PathVariable("userUid") UUID userUid){
    int result = userService.removeUser(userUid);
    return getIntegerResponseEntity(result);
    }

    private static ResponseEntity<Integer> getIntegerResponseEntity(int result) {
        if (result == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
