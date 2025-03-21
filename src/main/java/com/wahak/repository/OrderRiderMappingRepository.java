package com.wahak.repository;

import com.wahak.entity.OrderRiderMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author krishna.meena
 */
public interface OrderRiderMappingRepository extends JpaRepository<OrderRiderMapping,Integer> {

    @Query("SELECT orm FROM OrderRiderMapping orm WHERE orm.riderId=?1 order by orm.createdAt desc")
    List<OrderRiderMapping> findChalakOrder(String chalakId, Pageable pageable);

    @Query("SELECT orm FROM OrderRiderMapping orm WHERE orm.orderId=?1 AND orm.riderId=?2")
    Optional<OrderRiderMapping> findByOrderIdAndRiderId(Integer orderId, String chalakId);
}
