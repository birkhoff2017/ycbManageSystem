package s2jh.biz.station.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.station.entity.Battery;
import s2jh.biz.station.dao.BatteryDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BatteryService extends BaseService<Battery,Long>{
    
    @Autowired
    private BatteryDao batteryDao;

    @Override
    protected BaseDao<Battery, Long> getEntityDao() {
        return batteryDao;
    }
}
