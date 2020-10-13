package com.prageeth.repository;

import com.prageeth.entity.CustomerQueueDetail;
import com.prageeth.entity.MasterQueueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerQueueDetailRepository extends JpaRepository<CustomerQueueDetail,Integer> {

    List<CustomerQueueDetail> findByShopId(int shopId);

    List<CustomerQueueDetail> findByShopIdAndQueueId(int shopId, int queueId);
}
