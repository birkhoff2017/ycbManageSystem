package s2jh.biz.frequency.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by duxinyuan on 17-9-1.
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_frequency")
@MetaData(value = "业务模块:租借限制")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Frequency extends BaseNativeEntity {

    private static final long serialVersionUID = 3765661109363634661L;

    @MetaData("最高同时使用块数")
    @Column(name = "battery_num", nullable = false)
    private Integer batteryNum = 0;

    @MetaData("每日最高订单数量")
    @Column(name = "order_num", nullable = false)
    private Integer orderNum = 0;

}
