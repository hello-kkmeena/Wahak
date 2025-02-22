package com.wahak.repository;

import com.wahak.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
}
