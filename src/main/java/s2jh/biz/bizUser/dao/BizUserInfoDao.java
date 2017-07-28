package s2jh.biz.bizUser.dao;


import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.bizUser.entity.BizUserInfo;

@Repository
public interface BizUserInfoDao extends BaseDao<BizUserInfo, Long> {

}