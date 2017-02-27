package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.UC;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 10:31.
 */
@Service("ucService")
public interface IUCService extends BaseRepository<UC, Integer>, JpaSpecificationExecutor<UC> {

    UC findByTokenAndCode(String token, String code);

    UC findByUidAndCid(Integer uid, Integer cid);
}
