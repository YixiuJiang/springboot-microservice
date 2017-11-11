package spaceshuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceshuttle.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);

    User findUserByEmail(String email);

    User findById(Long id);
}