package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Url;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 10:20.
 */
public interface IUrlService extends BaseRepository<Url, Integer>, JpaSpecificationExecutor<Url> {

    @Query("SELECT u.status FROM Url u WHERE u.token=?1")
    String queryStatus(String token);

    Url findByToken(String token);

    @Query("SELECT u.amount FROM Url u WHERE u.token=?1")
    Integer surplus(String token);

    @Query("UPDATE Url u SET u.amount=u.amount-?2 WHERE u.token=?1")
    @Modifying
    @Transactional
    void minusAmount(String token, Integer amount);
}
