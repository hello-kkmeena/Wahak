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

    @Query(value="SELECT orm2.* FROM order_rider_mapping orm2 WHERE orm2.rider_id=?1 order by orm2.created_at desc",
            nativeQuery = true)
    List<OrderRiderMapping> findChalakOrder(Integer chalakId, Pageable pageable);

    @Query(value = "SELECT orm.* FROM order_rider_mapping orm WHERE orm.order_id=?1 AND orm.rider_id=?2",nativeQuery = true)
    Optional<OrderRiderMapping> findByOrderIdAndRiderId(Integer orderId, String chalakId);
}
