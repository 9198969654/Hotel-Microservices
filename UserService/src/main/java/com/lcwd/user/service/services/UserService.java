package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    //user operation

    //create
    User saveUser(User user);

    //get All User
    List<User> getAllUser();

    //get single User of given userId
    User getUser(String userId);

    //TODO: delete
    //TODO: update



}
