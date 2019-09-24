package com.stackroute.repository;

import com.stackroute.domain.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

  public UserProfile findByEmailId(String userName);


}
