package de.dhbw.web;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import de.dhbw.core.DependecyKnowItAll;
import de.dhbw.core.User;

/**
 * Created by phili on 13.04.2017.
 */
public class UserResource extends GenericResource {
    @Get
    public JacksonUser getUser() {
        User user = getUserFromParameters();
        if(user == null) {
            throw new RuntimeException("User was null.");
        }
        return JacksonUser.createFromUser(user);
    }

    @Post
    public JacksonUser createUser() {
        String idObject = (String) getRequest().getAttributes().get("id");
        if(null == idObject) {
            throw new RuntimeException("Parameter id or name is not supplied or null.");
        }
        User user = new User(idObject);
        DependecyKnowItAll.userRepository.persistUser(user);
        user = DependecyKnowItAll.userRepository.getUserByName(user.getName());
        return JacksonUser.createFromUser(user);
    }

    private User getUserFromParameters() {
        Object idObject = getRequest().getAttributes().get("id");
        if(null == idObject) {
            throw new RuntimeException("Parameter id or name is not supplied or null.");
        }
        try {
            long id = Long.parseLong(idObject.toString());
            return DependecyKnowItAll.userRepository.getUserById(id);
        } catch (Exception e) {
            System.out.println("No user found by id");
        }
        return DependecyKnowItAll.userRepository.getUserByName((String) idObject);
    }
}
