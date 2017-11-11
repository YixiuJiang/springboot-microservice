package spaceshuttle.service;

import spaceshuttle.model.User;

public interface UserService {
    void saveUser(User user);

    User findUserByEmail(String email);
}