package com.zslin.web.service;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.web.model.Module;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 17:02.
 */
public interface IModuleService extends BaseRepository<Module, Integer>, JpaSpecificationExecutor<Module> {

    @Query("UPDATE Module m SET m.flag=?3 WHERE m.id=?1 AND m.token=?2")
    @Modifying
    @Transactional
    void updateFlag(Integer id, String token, String flag);

    @Query("SELECT iid FROM Module WHERE id=?1 AND token=?2 AND status='1'")
    Integer findId(Integer id, String token);

    Module findByPwd(String pwd);
}
