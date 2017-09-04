package s2jh.biz.frequency.dao;


import lab.s2jh.core.dao.jpa.BaseDao;

import org.springframework.stereotype.Repository;

import s2jh.biz.frequency.entity.Frequency;

@Repository
public interface FrequencyDao extends BaseDao<Frequency, Long> {

}