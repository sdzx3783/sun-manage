package com.sun.manage.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sun.manage.entity.mongo.NonDocInterfacesBean;

public interface NonDocInterfacesBeanRepository  extends MongoRepository<NonDocInterfacesBean, String>{

}
