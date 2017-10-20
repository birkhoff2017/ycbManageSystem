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
     * 获取次要数据，即secondaryValue
     *
     * @param key
     * @return
     */
    public String getSecondaryValue(String key) {
        String sql = "SELECT secondaryValue FROM sys_datadict WHERE parent_id = '58' AND primaryKey  = '" + key + "'";
        Object singleResult = entityManager.createNativeQuery(sql).getSingleResult();
        return (String) singleResult;
    }
}
