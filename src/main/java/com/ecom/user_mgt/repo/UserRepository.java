package com.ecom.user_mgt.repo;

import com.ecom.user_mgt.model.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
