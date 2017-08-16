package s2jh.biz.order.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;
import s2jh.biz.bizUser.entity.BizUser;
import s2jh.biz.shop.entity.Shop;
import s2jh.biz.shop.entity.ShopStation;
import s2jh.biz.station.entity.Station;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by zhuhui on 17-6-27.
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_tradelog")
@MetaData(value = "业务模块:订单表")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TradeOrderLog extends BaseNativeEntity {

    private static final long serialVersionUID = 5585622256958279901L;

    @MetaData("订单id")
    @Column(name = "orderid", length = 32, nullable = false, unique = true)
    private String orderid;

    @MetaData("订单金额")
    @Column(name = "price", precision = 8, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @MetaData("用户")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", nullable = false)
    private BizUser bizUser;

    @MetaData("平台")
    @Column(name = "platform", nullable = false)
    private Integer platform = 0;

    @MetaData("状态")
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @MetaData("借出时间")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "borrow_time")
    private Date borrowTime;

    @MetaData("归还时间")
    @Column(name = "return_time")
    private Date returnTime;

    @MetaData("借出商铺id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_shop_id")
    private Shop borrowShop;

    @MetaData("归还商铺id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_shop_id")
    private Shop returnShop;

    @MetaData("借出商铺名")
    @Column(name = "borrow_station_name")
    private String borrowShopName;

    @MetaData("归还商铺名")
    @Column(name = "return_station_name")
    private String returnShopName;

    @MetaData("借出设备id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_station_id")
    private Station borrowStation;

    @MetaData("归还设备id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_station_id")
    private Station returnStation;

    @MetaData("借出商铺站点id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_shop_station_id")
    private ShopStation borrowShopStation;

    @MetaData("归还商铺站点id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_shop_station_id")
    private ShopStation returnShopStation;

    @MetaData("借出电池id")
    @Column(name = "borrow_battery_rfid")
    private String borrowBattery;

    @MetaData("借出时所在城市")
    @Column(name = "borrow_city")
    private String borrowCity;

    @MetaData("归还时所在城市")
    @Column(name = "return_city")
    private String returnCity;

    @MetaData("已退款金额")
    @Column(name = "refunded")
    private BigDecimal refunded;

    @MetaData("租金")
    @Column(name = "usefee")
    private BigDecimal usefee;

    @MetaData("已付款")
    @Column(name = "paid")
    private BigDecimal paid;

}
