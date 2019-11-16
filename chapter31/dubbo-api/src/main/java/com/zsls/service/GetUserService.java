package com.zsls.service;

import com.zsls.entity.User;

import java.util.List;

public interface GetUserService {

    List<User> getUserList(String name);

}