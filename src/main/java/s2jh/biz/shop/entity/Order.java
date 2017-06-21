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
 * Created by zhuhui on 17-6-20.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_order")
@MetaData(value = "订单")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order extends BaseNativeEntity {
    private static final long serialVersionUID = 2610186787706708954L;

    @MetaData(value = "订单编号")
    @Column(name = "order_seq", length = 64, nullable = true)
    private String orderSeq;

    @MetaData(value = "订单状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = true)
    private OrderStatusEnum status;

    private enum OrderStatusEnum {
        @MetaData("进行中")
        U,
        @MetaData("已完成")
        C,
        @MetaData("已报失")
        L;
    }
}
