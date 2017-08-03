package s2jh.biz.station.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by zhuhui on 17-7-25.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_station")
@MetaData(value = "设备表")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station extends BaseNativeEntity {
    private static final long serialVersionUID = -7293995948887951079L;

    @MetaData("Mac地址")
    @Column(name = "mac")
    private String mac;

    @MetaData("可借数")
    @Column(name = "usable")
    private Integer usable;

    @MetaData("可还数")
    @Column(name = "empty")
    private Integer empty;

    @MetaData("槽位状态")
    @Column(name = "slotstatus")
    private String slotstatus;

    // "2:安卓苹果二合一线 3:TypeC线"
    @MetaData("线类型")
    @Column(name = "cable")
    private Integer cable;

    @MetaData("设备名")
    @Column(name = "title")
    private String title;

    // "0,1,2,31,32,10,11,12,13,14,15,20"
    @MetaData("设备版本")
    @Column(name = "device_ver")
    private Integer deviceVer;

    @MetaData("心跳率")
    @Column(name = "heartbeat_rate")
    private Integer heartbeatRate;

    @MetaData("开机时长")
    @Column(name = "power_on_time")
    private Long powerOnTime;

    @MetaData("同步时间")
    @Column(name = "sync_time")
    private Date syncTime;

}