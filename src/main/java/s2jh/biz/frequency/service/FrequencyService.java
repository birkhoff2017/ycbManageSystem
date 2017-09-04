package s2jh.biz.frequency.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.frequency.entity.Frequency;
import s2jh.biz.frequency.dao.FrequencyDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FrequencyService extends BaseService<Frequency,Long>{
    
    @Autowired
    private FrequencyDao frequencyDao;

    @Override
    protected BaseDao<Frequency, Long> getEntityDao() {
        return frequencyDao;
    }
}
