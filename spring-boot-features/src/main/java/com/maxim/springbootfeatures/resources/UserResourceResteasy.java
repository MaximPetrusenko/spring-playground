package com.maxim.springbootfeatures.resources;

import com.maxim.springbootfeatures.model.User;
import com.maxim.springbootfeatures.service.UserService;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Component
@Path("api/v1/users")
public class UserResourceResteasy {

    private final UserService userService;

    @Autowired
    public UserResourceResteasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    public User fetchUser(@PathParam("userUid") UUID userUid) {
        Optional<User> userOptional = userService.getUser(userUid);
        return userService
                .getUser(userUid)
                .orElseThrow(() -> new NotFoundException("user " + userUid + "not found"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void insertNewUser(@Valid User user) {
        userService.insertUser(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(User user){
        userService.updateUser(user);

    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    public void deleteUser(@PathParam("userUid") UUID userUid){
        userService.removeUser(userUid);
    }

    private static Response getIntegerResponseEntity(int result) {
        if (result == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
