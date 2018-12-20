package com.tradingPlatform;

import com.tradingPlatform.DataObjects.CoinInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CMCRepository extends MongoRepository<CoinInfo, String> {

    CoinInfo findBySymbol(String symbol);

}
