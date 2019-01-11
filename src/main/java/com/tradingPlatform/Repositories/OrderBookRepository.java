package com.tradingPlatform.Repositories;

import com.tradingPlatform.DataObjects.OrderBook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderBookRepository extends MongoRepository<OrderBook, String> {

}
