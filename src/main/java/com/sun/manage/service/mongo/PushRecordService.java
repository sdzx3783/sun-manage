package com.sun.manage.service.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sun.manage.entity.mongo.NonDocInterfacesBean;
import com.sun.manage.entity.mongo.PushRecord;
import com.sun.manage.repository.mongo.PushRecordRepository;

@Service
public class PushRecordService {
	@Autowired
	private PushRecordRepository pushRecordRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<PushRecord> testQuery() {
		PushRecord pushRecord=new PushRecord();
//		pushRecord.setId("5b19e031f274a51980f91381");
//		pushRecord.setPushResult(false);
//		Example paramExample=Example.of(pushRecord);
		
		List<PushRecord> findAll = pushRecordRepository.findAll();
		return findAll;
	}
	public PushRecord testOneQuery() {
		PushRecord findOne = pushRecordRepository.findOne("5b2f2f67cd76da26d0ddea64");
		return findOne;
	}
	public Page<PushRecord> testPageQuery(Integer page,Integer rows) {
		Sort sort = new Sort(Sort.Direction.DESC, "time");
        Page<PushRecord>pushRecordPage= pushRecordRepository.findAll(new PageRequest(1-1,10,sort));
		return pushRecordPage;
	}
	
	public List<PushRecord> testfindByPushResult(boolean pushResult) {
		List<PushRecord> findByPushResult = pushRecordRepository.findByPushResult(pushResult);
		return findByPushResult;
	}
	
	public PushRecord testAddPushRecord(PushRecord pushRecord) {
		PushRecord insert = pushRecordRepository.insert(pushRecord);
		return insert;
	}
	
	public PushRecord testSavePushRecord(PushRecord pushRecord) {
		PushRecord save = pushRecordRepository.save(pushRecord);
		return save;
	}
	public void testAddNonDocInterfacesBeanByMongoTemplate(NonDocInterfacesBean nonDocInterfacesBean) {
		mongoTemplate.insert(nonDocInterfacesBean);
	}
	
	public PushRecord testMonoTemplateFindOne() {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is("5b2f2f67cd76da26d0ddea64"));
		PushRecord findOne = mongoTemplate.findOne(query, PushRecord.class);
		return findOne;
	}
	
	public NonDocInterfacesBean testMonoTemplateFindOne1() {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is("5b2f3675cd76da2880de96ab"));
		NonDocInterfacesBean findOne = mongoTemplate.findOne(query, NonDocInterfacesBean.class);
		return findOne;
	}
}
