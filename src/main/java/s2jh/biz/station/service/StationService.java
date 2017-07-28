package s2jh.biz.station.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.station.entity.Station;
import s2jh.biz.station.dao.StationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StationService extends BaseService<Station,Long>{
    
    @Autowired
    private StationDao stationDao;

    @Override
    protected BaseDao<Station, Long> getEntityDao() {
        return stationDao;
    }
}
