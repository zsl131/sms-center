package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.CallRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 16:03.
 */
public interface ICallRecordService extends BaseRepository<CallRecord, Integer>, JpaSpecificationExecutor<CallRecord> {
}
