package s2jh.biz.bizUser.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.bizUser.dao.BizUserDao;
import s2jh.biz.bizUser.entity.BizUser;

@Service
@Transactional
public class BizUserService extends BaseService<BizUser, Long> {

    @Autowired
    private BizUserDao bizUserDao;

    @Override
    protected BaseDao<BizUser, Long> getEntityDao() {
        return bizUserDao;
    }
}
