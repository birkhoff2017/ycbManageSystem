package s2jh.biz.refund.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import s2jh.biz.bizUser.entity.BizUser;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by 杜欣源:提现记录 on 17-8-3.
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_refund_log")
@MetaData(value = "业务模块:提现记录")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RefundLog extends BaseNativeEntity {

    private static final long serialVersionUID = 9117705864086377890L;

//    @MetaData("提现id")
//    @Column(name = "refundid", length = 32, nullable = false, unique = true)
//    private String refundid;

    @MetaData("用户id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private BizUser bizUser;

    //新添加的字段 使退款与订单对应上   一条退款对应一个订单
    @MetaData("订单编号")
    @Column(name = "orderid")
    private String orderid;

    @MetaData("提现金额")
    @Column(name = "refund")
    private BigDecimal refund;

    @MetaData("已退款金额")
    @Column(name = "refunded")
    private BigDecimal refunded;

    @MetaData("状态")  //1为退款申请、 2为退款完成
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @MetaData("发起时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time")
    private Date requestTime;

    @MetaData("提现时间")
    @Column(name = "refund_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date refundTime;

    @MetaData("退款描述")
    @Column(name = "detail")
    private String detail;

}
