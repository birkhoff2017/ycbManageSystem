package s2jh.biz.formid.dao;


import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.formid.entity.Message;

@Repository
public interface MessageDao extends BaseDao<Message, Long> {

}