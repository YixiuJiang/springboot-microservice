package spaceshuttle;

import spaceshuttle.model.User;

public class UserMother {
    public static User getDefaultUser() {
        User user = new User();
        user.setEmail("test@mailinator.com");
        user.setFirstName("Charlie");
        user.setLastName("Brown");
        user.setPassword("password");
        user.setUsername("charlie");
        return user;
    }
}
