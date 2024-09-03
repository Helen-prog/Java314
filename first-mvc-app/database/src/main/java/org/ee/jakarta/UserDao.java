package org.ee.jakarta;

import javax.swing.text.html.Option;
import java.util.Optional;

public class UserDao {
    public Optional<User> findById(Long id){
        User user = new User();
        user.setName("Виктор");
        return Optional.of(user);
    }
}
