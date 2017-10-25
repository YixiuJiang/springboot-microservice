package spaceshuttle.repository;

import org.springframework.data.repository.CrudRepository;
import spaceshuttle.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);

    User findById(Long id);
}