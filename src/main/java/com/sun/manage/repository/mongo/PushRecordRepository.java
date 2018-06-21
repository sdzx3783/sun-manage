package com.sun.manage.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sun.manage.entity.mongo.PushRecord;


public interface PushRecordRepository extends MongoRepository<PushRecord, String> {
	public List<PushRecord> findByPushResult(boolean pushResult);
}
