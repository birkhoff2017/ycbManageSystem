package s2jh.biz.shop.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lab.s2jh.module.auth.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import s2jh.biz.feeStrategy.entity.FeeStrategy;
import s2jh.biz.station.entity.Station;

import javax.persistence.*;

/**
 * Created by zhuhui on 17-7-25.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_shop_station")
@MetaData(value = "商铺站点")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShopStation extends BaseNativeEntity {
    private static final long serialVersionUID = -8969639160325119936L;

    @MetaData("关联商铺")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopid")
    private Shop shop;

    @MetaData("关联设备")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @MetaData("设备码")
    @Column(name = "lbsid")
    private Long lbsid;

    @MetaData("商铺站点名")
    @Column(name = "title")
    private String title;

    // 原表desc
    @MetaData("设备具体摆放位置")
    @Column(name = "position")
    private String position;

    @MetaData("地址")
    @Column(name = "address")
    private String address;

    @MetaData("经度")
    @Column(name = "longitude")
    private String longitude;

    @MetaData("纬度")
    @Column(name = "latitude")
    private String latitude;

    @MetaData("费用设置")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_settings")
    private FeeStrategy feeSettings;

    @MetaData("销售")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User admin;

    @MetaData("设备状态")
    @Column(name = "status")
    private Integer status;

}
