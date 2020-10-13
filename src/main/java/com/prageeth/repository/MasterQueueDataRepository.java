package com.prageeth.repository;

import com.prageeth.entity.MasterQueueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterQueueDataRepository extends JpaRepository<MasterQueueData,Integer> {

    MasterQueueData findByShopId(int shopId);

}
