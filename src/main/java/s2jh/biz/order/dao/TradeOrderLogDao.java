package s2jh.biz.order.dao;


import lab.s2jh.core.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;
import s2jh.biz.order.entity.TradeOrderLog;

@Repository
public interface TradeOrderLogDao extends BaseDao<TradeOrderLog, Long> {

}