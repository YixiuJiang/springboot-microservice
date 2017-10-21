package spaceshuttle.repository;

import spaceshuttle.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);

    User findByUserId(Long id);
}