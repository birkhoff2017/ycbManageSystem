package s2jh.biz.refund.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.refund.dao.RefundLogDao;
import s2jh.biz.refund.entity.RefundLog;

@Service
@Transactional
public class RefundLogService extends BaseService<RefundLog, Long> {

    @Autowired
    private RefundLogDao refundLogDao;

    @Override
    protected BaseDao<RefundLog, Long> getEntityDao() {
        return refundLogDao;
    }
}
