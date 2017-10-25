package s2jh.biz.feeStrategy.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.feeStrategy.dao.FeeStrategyDao;
import s2jh.biz.feeStrategy.entity.FeeStrategy;

import java.math.BigDecimal;

@Service
@Transactional
public class FeeStrategyService extends BaseService<FeeStrategy, Long> {

    @Autowired
    private FeeStrategyDao feeStrategyDao;

    @Override
    protected BaseDao<FeeStrategy, Long> getEntityDao() {
        return feeStrategyDao;
    }

    /**
     * 根据收费策略计算已花费租金
     *
     * @param feeStrategy
     * @param duration
     * @return
     */
    public BigDecimal calUseFee(FeeStrategy feeStrategy, Long duration) {
        //将计算出来的费用初始化为0
        BigDecimal useFee = BigDecimal.ZERO;
        //计算免费时长
        Long fixedTime = feeStrategy.getFixedTime() * feeStrategy.getFixedUnit();
        //计算意外借出时长
        Long freeTime = feeStrategy.getFreeTime() * feeStrategy.getFreeUnit();
        //计算最长收费时间
        Long maxFeeTime = feeStrategy.getMaxFeeTime() * feeStrategy.getMaxFeeUnit();
        if (duration < freeTime) {
            // 如果租借时长小于意外借出时间，则不计费用
            return useFee;
        } else if (duration > maxFeeTime) {
            // 租借时长大于最高收费时长，按最高收费
            //一天的秒数
            Long daySeconds = Long.valueOf(24 * 60 * 60);
            // 计算借出总天数
            Integer days = Math.toIntExact(duration / daySeconds);
            //如果借用时长没有超过一天，按最高费用返回
            if (days.equals(0)) {
                useFee = feeStrategy.getMaxFee();
            } else {
                //计算最后不足一天的费用，一次递归调用本函数，因此传递回去的应该是最后不足一天的时间
                Long todaySeconds = duration - daySeconds * days;
                BigDecimal todayUsedFee = this.calUseFee(feeStrategy, todaySeconds);
                //最终费用是使用天数*最高费用+最后不足一天的使用费用
                useFee = feeStrategy.getMaxFee().multiply(BigDecimal.valueOf(days)).add(todayUsedFee);
            }
        } else {
            // 计算收费时长
            Long expirTime = duration - fixedTime;
            //如果收费时长小于0，那么费用为固定收费，0元
            if (expirTime < 0) {
                useFee = feeStrategy.getFixed();
            } else {
                useFee = feeStrategy.getFixed().
                        add(feeStrategy.getFee().multiply(BigDecimal.valueOf(1L + (expirTime / feeStrategy.getFeeUnit()))));
                BigDecimal maxFee = feeStrategy.getMaxFee();
                if (useFee.compareTo(maxFee) > 0) {
                    return maxFee;
                }
            }
        }
        return useFee;
    }
}
