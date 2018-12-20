package com.tradingPlatform;

import com.tradingPlatform.DataObjects.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    UserInfo findByUserName(String userName);

}
