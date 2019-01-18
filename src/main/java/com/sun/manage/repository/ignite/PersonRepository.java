package com.sun.manage.repository.ignite;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

/**
 * Description:PersonRepository
 * Author: sunzhao
 * Create on: 2019-01-16 13:54
 */
@RepositoryConfig(cacheName = "PersonCache")
public interface PersonRepository extends IgniteRepository<Person, Long> {

}
