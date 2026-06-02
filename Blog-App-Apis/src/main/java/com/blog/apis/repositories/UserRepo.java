package com.blog.apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.apis.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
