package com.prageeth.repository;

import com.prageeth.entity.CustomerQueueDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerQueueDetailRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerQueueDetailRepository customerQueueDetailRepository;

    CustomerQueueDetail customerQueueDetail;

    @BeforeEach
    void setUp() {
        customerQueueDetail = new CustomerQueueDetail();
        customerQueueDetail.setShopId(100);
        customerQueueDetail.setPosition(1);
        customerQueueDetail.setQueueId(2);
        customerQueueDetail.setWaitingMinutes(1.0);
        entityManager.persist(customerQueueDetail);
        entityManager.flush();
    }

    @Test
    void findByShopIdTest() {
        List<CustomerQueueDetail> found = customerQueueDetailRepository.findByShopId(customerQueueDetail.getShopId());
        assertThat(found.get(0).getShopId()).isEqualTo(customerQueueDetail.getShopId());
    }

    @Test
    void findByShopIdAndQueueIdTest() {
        List<CustomerQueueDetail> found = customerQueueDetailRepository.findByShopIdAndQueueId(customerQueueDetail.getShopId(), customerQueueDetail.getQueueId());
        assertThat(found.get(0).getShopId()).isEqualTo(customerQueueDetail.getShopId());
        assertThat(found.get(0).getQueueId()).isEqualTo(customerQueueDetail.getQueueId());
    }
}