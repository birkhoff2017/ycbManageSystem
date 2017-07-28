package s2jh.biz.bizUser.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.bizUser.entity.BizUserInfo;
import s2jh.biz.bizUser.dao.BizUserInfoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizUserInfoService extends BaseService<BizUserInfo,Long>{
    
    @Autowired
    private BizUserInfoDao bizUserInfoDao;

    @Override
    protected BaseDao<BizUserInfo, Long> getEntityDao() {
        return bizUserInfoDao;
    }
}
