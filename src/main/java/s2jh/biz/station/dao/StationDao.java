package s2jh.biz.station.dao;


import lab.s2jh.core.dao.jpa.BaseDao;
import org.springframework.stereotype.Repository;
import s2jh.biz.station.entity.Station;

@Repository
public interface StationDao extends BaseDao<Station, Long> {

}