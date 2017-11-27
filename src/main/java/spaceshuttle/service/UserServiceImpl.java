package spaceshuttle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spaceshuttle.model.Role;
import spaceshuttle.model.User;
import spaceshuttle.repository.RoleRepository;
import spaceshuttle.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service("userService")
//@Profile("default")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public List<User> findByExample(User example) {
        Sort.Order usernameDescOrder = new Sort.Order(Sort.Direction.DESC, "username");
        Sort.Order nameAscOrder = new Sort.Order(Sort.Direction.ASC, "name");
        Sort sort = new Sort(usernameDescOrder, nameAscOrder);
        return userRepository.findAll(Example.of(example), sort);
    }

    @Override
    public List<User> findBySpecification(Specification<User> specification) {
        return userRepository.findAll(specification);
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return Optional.ofNullable(findUserByEmail(email));
    }
}