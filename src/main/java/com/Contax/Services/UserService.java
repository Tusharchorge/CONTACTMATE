package com.Contax.Services;

import com.Contax.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     User saveUser(User user);

     Optional<User> getUserById(String id);

     Optional<User> updateUser(User user);

     void deleteUser(String id);

     boolean isUserExist(String userId);

     boolean isUserExistByEmail(String email);

     List<User> getAllUsers();

     User getUserByEmail(String email);



}