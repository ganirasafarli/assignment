package com.copaco.assignment.domian.repo;

import com.copaco.assignment.domian.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
