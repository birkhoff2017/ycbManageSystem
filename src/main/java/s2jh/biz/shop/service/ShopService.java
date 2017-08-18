package s2jh.biz.shop.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.shop.dao.ShopDao;
import s2jh.biz.shop.entity.Shop;

@Service
@Transactional
public class ShopService extends BaseService<Shop, Long> {

    @Autowired
    private ShopDao shopDao;

    @Override
    protected BaseDao<Shop, Long> getEntityDao() {
        return shopDao;
    }
}
