package s2jh.biz.shop.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by zhuhui on 17-7-24.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_shop")
@MetaData(value = "商铺")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shop extends BaseNativeEntity {

    private static final long serialVersionUID = 5441361451823295870L;

    @MetaData("店铺名")
    @Column(name = "name")
    private String name;

    @MetaData("省份")
    @Column(name = "province")
    private String province;

    @MetaData("城市")
    @Column(name = "city")
    private String city;

    @MetaData("区域")
    @Column(name = "area")
    private String area;

    @MetaData("地址")
    @Column(name = "locate")
    private String locate;

    @MetaData("人均消费")
    @Column(name = "cost")
    private Integer cost;

    @MetaData("手机")
    @Column(name = "phone")
    private String phone;

    @MetaData("营业开始时间")
    @Column(name = "stime")
    private String stime;

    @MetaData("营业结束时间")
    @Column(name = "etime")
    private String etime;

    @MetaData("logo")
    @Column(name = "logo")
    private String logo;

    @MetaData("轮播图")
    @Column(name = "carousel")
    private String carousel;

    @MetaData("业态")
    @Column(name = "type")
    private Integer type;

    @MetaData("状态")
    @Column(name = "status")
    private Integer status;


}
