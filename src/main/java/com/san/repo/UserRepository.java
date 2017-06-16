package com.san.repo;


import org.springframework.data.repository.CrudRepository;

import com.san.domain.User;

public interface UserRepository extends CrudRepository<User, String> {

}
