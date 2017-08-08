package s2jh.biz.refund.dao;


import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.refund.entity.RefundLog;

@Repository
public interface RefundLogDao extends BaseDao<RefundLog, Long> {

}