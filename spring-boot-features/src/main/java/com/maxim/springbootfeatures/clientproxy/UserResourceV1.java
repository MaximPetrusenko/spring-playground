package com.maxim.springbootfeatures.clientproxy;

import com.maxim.springbootfeatures.model.User;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.MEDIA_TYPE_WILDCARD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Path("api/v1/users")
public interface UserResourceV1 {
    @GET
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(MEDIA_TYPE_WILDCARD)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(APPLICATION_JSON_VALUE)
    @Consumes(MEDIA_TYPE_WILDCARD)
    @Path("{userUid}")
    User fetchUser(@PathParam("userUid") UUID userUid);
    @POST
    @Consumes(APPLICATION_JSON_VALUE)
    @Produces(APPLICATION_JSON_VALUE)
    void insertNewUser(User user);

    @PUT
    @Consumes(APPLICATION_JSON_VALUE)
    @Produces(APPLICATION_JSON_VALUE)
    void updateUser(User user);

    @DELETE
    @Consumes(APPLICATION_JSON_VALUE)
    @Path("{userUid}")
    void deleteUser(@PathParam("userUid") UUID userUid);
}
