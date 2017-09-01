package s2jh.biz.formid.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by duxinyuan on 17-8-28.
 */

@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_message")
@MetaData(value = "业务模块:推送消息")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message extends BaseNativeEntity {

    private static final long serialVersionUID = -6311575125667643141L;

    @MetaData("用户openid")
    @Column(name = "openid")
    private String openid;

    //form_id或prepay_id
    @MetaData("form_id")
    @Column(name = "form_prepay_id")
    private String form_prepay_id;

    @MetaData("类型") // 1为form_id, 2为prepay_id
    @Column(name = "type")
    private Integer type;

    @MetaData("订单编号")
    @Column(name = "orderid")
    private String orderid;

    //次数  form_id只能使用一次，prepay_id可以使用三次
    @MetaData("剩余次数")
    @Column(name = "number")
    private Integer number;

}
