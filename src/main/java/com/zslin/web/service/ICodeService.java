package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Code;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 10:30.
 */
public interface ICodeService extends BaseRepository<Code, Integer>, JpaSpecificationExecutor<Code> {

    Code findByC(String c);
}
