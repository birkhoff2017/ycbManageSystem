package s2jh.biz.station.dao;


import lab.s2jh.core.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;
import s2jh.biz.station.entity.Battery;

@Repository
public interface BatteryDao extends BaseDao<Battery, Long> {

}