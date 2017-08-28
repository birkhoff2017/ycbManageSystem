package s2jh.biz.feeStrategy.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.feeStrategy.entity.FeeStrategy;
import s2jh.biz.feeStrategy.dao.FeeStrategyDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeeStrategyService extends BaseService<FeeStrategy,Long>{
    
    @Autowired
    private FeeStrategyDao feeStrategyDao;

    @Override
    protected BaseDao<FeeStrategy, Long> getEntityDao() {
        return feeStrategyDao;
    }
}
