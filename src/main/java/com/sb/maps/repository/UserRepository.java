package com.sb.maps.repository;

import com.sb.maps.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmailIgnoreCase(String email);

    User findOneByUsernameIgnoreCase(String login);
}
