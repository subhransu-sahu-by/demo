package com.example.demo.cachestore;

import com.example.demo.dto.OrderDetails;
import com.example.demo.dto.OrderKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepositoryCostom extends MongoRepository<OrderKey, OrderDetails> {
}
