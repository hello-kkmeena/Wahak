package com.wahak.repository;

import com.wahak.entity.ItemCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface ItemCommonRepository extends JpaRepository<ItemCommon, Integer> {

    boolean existsByIdAndIsActive(Integer id,boolean isActive);

    @Query("SELECT ic FROM ItemCommon ic " +
            "WHERE ic.isActive = true " +
            "AND (:name IS NULL OR " +
            "   LOWER(ic.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "   LOWER(ic.description) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "   LOWER(ic.category) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "ORDER BY ic.name DESC")
    Page<ItemCommon> findByActiveAndNameContainsIgnoreCase(
            @Param("name") String name,
            Pageable pageable
    );


    @Query("SELECT e FROM ItemCommon e " +
            "WHERE e.isActive = true " +
            "AND e.id IN :itemIds " +
            "ORDER BY e.name")
    List<ItemCommon> findByIdInOrderbyName(List<Integer> itemIds);
}
