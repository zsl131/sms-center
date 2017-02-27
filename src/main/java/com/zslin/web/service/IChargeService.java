package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Charge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:02.
 */
public interface IChargeService extends BaseRepository<Charge, Integer>, JpaSpecificationExecutor<Charge> {
}
