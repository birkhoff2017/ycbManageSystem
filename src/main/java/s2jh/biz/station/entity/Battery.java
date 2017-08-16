package s2jh.biz.station.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import s2jh.biz.order.entity.TradeOrderLog;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuhui on 17-8-11.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_battery")
@MetaData(value = "业务模块:充电宝电池")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Battery extends BaseNativeEntity {

    private static final long serialVersionUID = 2639956316730521089L;

    @MetaData("电池唯一")
    @Column(name = "rfid")
    private String rfid;

    @MetaData("最近所在设备")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stationid")
    private Station station;

    @MetaData("最近所属订单")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid")
    private TradeOrderLog orderLog;

    // 0：正常;3,4：锁住
    @MetaData("电池状态")
    @Column(name = "status")
    private Integer status;

    @MetaData("最近所在设备槽位")
    @Column(name = "slot")
    private Integer slot;

    @MetaData("电池类型")
    @Column(name = "batt_type")
    private String battType;

    @MetaData("颜色")
    @Column(name = "colorid")
    private Integer colorId;

    // 二合一线：2 5 8 11(B) 14(E)
    // type-c线：3 6 9 12(C) 15(F)
    @MetaData("充电线类型")
    @Column(name = "cable")
    private Integer cable;

    @MetaData("电量")
    @Column(name = "power")
    private Integer power;

    @MetaData("电压")
    @Column(name = "voltage")
    private Integer voltage;

    @MetaData("电流")
    @Column(name = "currentval")
    private Integer currentval;

    @MetaData("温度")
    @Column(name = "temperature")
    private Integer temperature;

    @MetaData("是否损坏")
    @Column(name = "isdamage")
    private Boolean isdamage;

    @MetaData("是否带充电头")
    @Column(name = "adapter")
    private Boolean adapter;

    @MetaData("按键是否按下")
    @Column(name = "battery_key")
    private Boolean batteryKey;

    @MetaData("防拆保护是否打开")
    @Column(name = "broke")
    private Boolean broke;

    @MetaData("是否在充电")
    @Column(name = "chargesta")
    private Boolean chargesta;

    @MetaData("电池设备信息")
    @Column(name = "dev_info")
    private String devInfo;

    @MetaData("同步时间")
    @Column(name = "sync_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;

    @MetaData("最后归还时间")
    @Column(name = "last_back_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastBackTime;

}
