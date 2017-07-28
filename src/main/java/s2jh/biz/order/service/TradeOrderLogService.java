package s2jh.biz.order.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.order.dao.TradeOrderLogDao;
import s2jh.biz.order.entity.TradeOrderLog;

@Service
@Transactional
public class TradeOrderLogService extends BaseService<TradeOrderLog,Long> {
    
    @Autowired
    private TradeOrderLogDao tradeOrderLogDao;

    @Override
    protected BaseDao<TradeOrderLog, Long> getEntityDao() {
        return tradeOrderLogDao;
    }
}
