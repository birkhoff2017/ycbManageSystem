package s2jh.biz.bizUser.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by zhuhui on 17-10-10.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_user_auth")
@MetaData(value = "业务模块:客户授权信息表")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizUserAuth extends BaseNativeEntity {

    @MetaData("用户表")
    @Column(name = "user_id", nullable = false)
    private Long userid;

    @MetaData("账户类型")
    @Column(name = "identity_type", nullable = false)
    private String identityType;

    @MetaData("账户值")
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @MetaData("凭据")
    @Column(name = "credential", nullable = false)
    private String credential;

}
