package com.invoice.webservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository <UserDetails, Long> {
//    Boolean existsByEmail(String email);
//    Boolean existsByPassword(String password);
    UserDetails findUserDetailsByUsernameAndPassword(String email, String password);
}
