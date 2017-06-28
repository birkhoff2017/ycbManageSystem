package s2jh.biz.shop.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.entity.BizUser;
import s2jh.biz.shop.dao.BizUserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizUserService extends BaseService<BizUser,Long>{
    
    @Autowired
    private BizUserDao bizUserDao;

    @Override
    protected BaseDao<BizUser, Long> getEntityDao() {
        return bizUserDao;
    }
}
