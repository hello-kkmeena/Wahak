package com.wahak.repository;

import com.wahak.entity.ItemCommon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface ItemCommonRepository extends JpaRepository<ItemCommon, Integer> {

    boolean existsByIdAndIsActive(Integer id,boolean isActive);
}
