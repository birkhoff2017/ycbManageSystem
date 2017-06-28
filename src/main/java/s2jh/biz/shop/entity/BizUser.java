package s2jh.biz.shop.entity;

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
 * Created by zhuhui on 17-6-27.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "mcs_user")
@MetaData(value = "客户")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizUser extends BaseNativeEntity {
    private static final long serialVersionUID = 4231028228537930521L;

    @MetaData("用户openid")
    @Column(name = "openid", nullable = false, unique = true)
    private String openid;

    @MetaData("用户所属平台")
    @Column(name = "platform", nullable = false)
    private Integer platform = 0;

    @MetaData("账户余额")
    @Column(name = "usablemoney", precision = 8, scale = 2, nullable = false)
    private BigDecimal usablemoney = BigDecimal.ZERO;

    @MetaData("押金")
    @Column(name = "deposit", precision = 8, scale = 2, nullable = false)
    private BigDecimal deposit = BigDecimal.ZERO;

    @MetaData("待退款数目")
    @Column(name = "refund", precision = 8, scale = 2, nullable = false)
    private BigDecimal refund = BigDecimal.ZERO;

    // 1:取消 0:未取消
    @MetaData("是否取消关注")
    @Column(name = "unsubscribe", nullable = false)
    private boolean unsubscribe;
}
