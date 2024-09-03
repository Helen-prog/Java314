package org.ee.jakarta;

import java.util.Optional;

public class UserService {

    private final UserDao userDao = new UserDao();

    public Optional<UserDto> getUser(Long id){
        return userDao.findById(id).map(user -> new UserDto(user.getName()));
    }
}
