package com.prageeth.repository;

import com.prageeth.entity.MasterQueueData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
class MasterQueueDataRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MasterQueueDataRepository masterQueueDataRepository;

    MasterQueueData masterQueueData;

    @BeforeEach
    void setUp() {
        masterQueueData = new MasterQueueData();
        masterQueueData.setShopId(100);
        masterQueueData.setNumOfQueues(3);
        entityManager.persist(masterQueueData);
        entityManager.flush();
    }

    @Test
    void findByShopIdTest() {
        MasterQueueData found = masterQueueDataRepository.findByShopId(masterQueueData.getShopId());
        assertThat(found.getShopId()).isEqualTo(masterQueueData.getShopId());
    }
}