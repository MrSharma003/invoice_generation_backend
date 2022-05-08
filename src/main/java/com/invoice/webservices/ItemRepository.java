package com.invoice.webservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository <Invoice, Long> {
    List<Invoice> findByUsername(String username);
}
