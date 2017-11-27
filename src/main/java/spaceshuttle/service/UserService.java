package spaceshuttle.service;

import org.springframework.data.jpa.domain.Specification;
import spaceshuttle.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    List<User> findByExample(User example);

    List<User> findBySpecification(Specification<User> specification);

    User findUserByEmail(String email);

    Optional<User> findUserByEmailOptional(String email);

}