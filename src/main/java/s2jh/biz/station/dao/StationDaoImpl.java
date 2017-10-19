package s2jh.biz.station.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Huo on 2017/10/19.
 */
@Repository
public class StationDaoImpl {
    private final Logger logger = LoggerFactory.getLogger(StationDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 从字典表中查询对应的value
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        String sql = "SELECT primaryValue FROM sys_datadict WHERE primaryKey  = '" + key + "'";
        Object singleResult = entityManager.createNativeQuery(sql).getSingleResult();

        return (String) singleResult;

    }
}
