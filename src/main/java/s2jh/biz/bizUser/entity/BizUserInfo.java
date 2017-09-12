package s2jh.biz.bizUser.entity;

import lab.s2jh.core.annotation.MetaData;
import lab.s2jh.core.entity.BaseNativeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuhui on 17-7-24.
 */
@Getter
@Setter
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "ycb_mcs_userinfo")
@MetaData(value = "业务模块:客户详情")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizUserInfo extends BaseNativeEntity {
    private static final long serialVersionUID = -2942060905173620483L;

    @MetaData("用户openid")
    @Column(name = "openid", unique = true)
    private String openid;

    @MetaData("昵称")
    @Column(name = "nickname")
    private String nickname;

    @MetaData("性别")
    @Column(name = "sex")
    private Integer sex;

    @MetaData("城市")
    @Column(name = "city")
    private String city;

    @MetaData("省份")
    @Column(name = "province")
    private String province;

    @MetaData("国家")
    @Column(name = "country")
    private String country;

    @MetaData("头像")
    @Column(name = "headimgurl")
    private String headimgurl;

    @MetaData("语言")
    @Column(name = "language")
    private String language;

    @MetaData("关注时间")
    @Column(name = "subscribe_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscribeTime;

    @MetaData("unionid")
    @Column(name = "unionid")
    private String unionid;

    @MetaData("remark")
    @Column(name = "remark")
    private String remark;

    @MetaData("组")
    @Column(name = "groupid")
    private String groupid;

}
