package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Send;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:03.
 */
public interface ISendService extends BaseRepository<Send, Integer>, JpaSpecificationExecutor<Send> {
}
