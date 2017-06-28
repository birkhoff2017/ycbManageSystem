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
@Table(name = "mcs_tradelog")
@MetaData(value = "订单历史表")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TradeOrderLog extends BaseNativeEntity {

    private static final long serialVersionUID = 5585622256958279901L;

    @MetaData("订单id")
    @Column(name = "orderid", length = 32, nullable = false, unique = true)
    private String orderid;

    @MetaData("订单金额")
    @Column(name = "price", precision = 8, scale = 2, nullable = false, unique = true)
    private BigDecimal price = BigDecimal.ZERO;


}
