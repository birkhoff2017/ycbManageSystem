package s2jh.biz.bizUser.dao;


import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.bizUser.entity.BizUserAuth;

@Repository
public interface BizUserAuthDao extends BaseDao<BizUserAuth, Long> {

}