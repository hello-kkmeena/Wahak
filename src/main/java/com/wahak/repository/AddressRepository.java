package com.wahak.repository;

import com.wahak.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
