package s2jh.biz.bizUser.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.bizUser.entity.BizUserAuth;
import s2jh.biz.bizUser.dao.BizUserAuthDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizUserAuthService extends BaseService<BizUserAuth,Long>{
    
    @Autowired
    private BizUserAuthDao bizUserAuthDao;

    @Override
    protected BaseDao<BizUserAuth, Long> getEntityDao() {
        return bizUserAuthDao;
    }
}
