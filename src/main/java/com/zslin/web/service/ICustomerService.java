package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 14:17.
 */
public interface ICustomerService extends BaseRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c.appKey FROM Customer c WHERE c.token=?1")
    String findAppKey(String token);

    Customer findByToken(String token);

    @Query("SELECT c.status FROM Customer c WHERE c.token=?1")
    String findStatus(String token);
}
