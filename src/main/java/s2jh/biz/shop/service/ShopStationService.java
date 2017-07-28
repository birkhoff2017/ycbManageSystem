package s2jh.biz.shop.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.shop.entity.ShopStation;
import s2jh.biz.shop.dao.ShopStationDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopStationService extends BaseService<ShopStation,Long>{
    
    @Autowired
    private ShopStationDao shopStationDao;

    @Override
    protected BaseDao<ShopStation, Long> getEntityDao() {
        return shopStationDao;
    }
}
