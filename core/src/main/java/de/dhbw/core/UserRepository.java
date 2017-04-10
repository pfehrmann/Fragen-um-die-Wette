package de.dhbw.core;

/**
 * Created by phili on 06.04.2017.
 */
public interface UserRepository {
    User getUserById(long id);
    User getUserByName(String name);

    void persistUser(User user);
}
