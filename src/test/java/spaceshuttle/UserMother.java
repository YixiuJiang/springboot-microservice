package spaceshuttle;

import spaceshuttle.model.User;

public class UserMother {
    public static User getDefaultUser() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        return user;
    }
}
