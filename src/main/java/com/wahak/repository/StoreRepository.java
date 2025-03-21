package com.wahak.repository;

import com.wahak.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface StoreRepository extends JpaRepository<Store,Integer> {
    boolean existsStoreByIdAndIsActive(Integer storeId, boolean isActive);

   @Query("select id from Store where cityId=:cityId and isActive=true")
    List<Integer> findIdsByCityId(@Param("cityId") Integer cityId);

    Store findFirstByCityId(Integer cityId);
}
