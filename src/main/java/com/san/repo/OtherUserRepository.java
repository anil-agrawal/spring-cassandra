package com.san.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.san.domain.User;

public interface OtherUserRepository extends CassandraRepository<User>{

}
