package s2jh.biz.feeStrategy.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by duxinyuan on 17-6-27.
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_fee_strategy")
@MetaData(value = "业务模块:收费策略")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeeStrategy extends BaseNativeEntity {

    private static final long serialVersionUID = -8444912433694270513L;

    @MetaData("策略名称")
    @Column(name = "name", nullable = false)
    private String name;

    @MetaData("意外借出免费时长")
    @Column(name = "free_time")
    private Long freeTime;

    @MetaData("意外借出免费时长单位")
    @Column(name = "free_unit")
    private Long freeUnit;

    @MetaData("固定收费时长")
    @Column(name = "fixed_time")
    private Long fixedTime;

    @MetaData("固定收费时长单位")
    @Column(name = "fixed_unit")
    private Long fixedUnit;

    @MetaData("固定费用")
    @Column(name = "fixed")
    private BigDecimal fixed;

    @MetaData("超出计费")
    @Column(name = "fee")
    private BigDecimal fee;

    @MetaData("超出计费时长单位")
    @Column(name = "fee_unit")
    private Long feeUnit;

    @MetaData("最高收费时长")
    @Column(name = "max_fee_time")
    private Long maxFeeTime;

    @MetaData("最高收费时长单位")
    @Column(name = "max_fee_unit")
    private Long maxFeeUnit;

    @MetaData("最高收费金额")
    @Column(name = "max_fee")
    private BigDecimal maxFee;

    @MetaData("租金扣费上限")
    @Column(name = "deposit_ceiling")
    private Boolean depositCeiling;

}
