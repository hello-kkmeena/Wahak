package com.wahak.repository;

import com.wahak.entity.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author krishna.meena
 */
public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {

    @Query("SELECT s FROM StoreItem s WHERE s.storeId = ?1 AND s.isAvailable = true " +
            "AND s.isActive=true order by s.sequence")
    List<StoreItem> findAvailableItemofStore(Integer addressStoreId, Pageable pageable);

    @Query("SELECT s FROM StoreItem s WHERE s.storeId = ?1 AND s.isActive = true order by s.sequence")
    List<StoreItem> findAvailableItemofStoreforSanchalak(Integer addressStoreId);

    @Query("SELECT s FROM StoreItem s WHERE s.item.name=?1 AND s.isActive = true order by s.sequence")
    Page<StoreItem> findByNameContainsIgnoreCase(String name, Pageable pageable);

    @Query("SELECT s FROM StoreItem s WHERE LOWER(s.item.name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "AND s.storeId = ?2 AND s.isActive = true AND s.isAvailable = true order by s.item.name")
    Page<StoreItem> findByNameContainsIgnoreCase1(String name, Integer addressStoreId, Pageable pageable);

    @Query("SELECT s FROM StoreItem s WHERE s.storeId = ?1 AND s.id in (?2) AND s.isActive = true" +
            " AND s.isAvailable=true order by s.sequence")
    List<StoreItem> findByStoreIdAndItemIdIn(Integer storeId, Set<Integer> itemids);
}
