package s2jh.biz.formid.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import s2jh.biz.formid.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Huo on 2017/10/18.
 */
@Repository
public class MessageDaoImpl {

    private final Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    //获取form_id
    public Message getUsableMessage(String openid) {
        String sql = "SELECT id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, optlock, form_prepay_id,number, openid, orderid, type FROM ycb_mcs_message WHERE openid='" + openid + "' AND form_prepay_id<>'the formId is a mock one' AND createdDate > DATE_SUB(CURDATE(), INTERVAL 1 WEEK) order by createdDate limit 1";

        List<Message> messageList = entityManager.createNativeQuery(sql, Message.class).getResultList();

        if (null != messageList && messageList.size() > 0) {
            Message message = messageList.get(0);
            return message;
        } else {
            logger.info("该条退款消息推送失败！没有可用的form_id了");
            return null;
        }
    }

    //清除本条已经使用过的数据
    public void deleteMessageById(Long id) {
        String sql = "DELETE FROM ycb_mcs_message WHERE id=" + id;
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    //清除该用户过期数据
    public void deleteMessageByOpenid(String openid) {
        String sql = "DELETE FROM ycb_mcs_message WHERE openid='" + openid + "' AND createdDate <= DATE_SUB(CURDATE(), INTERVAL 1 WEEK)";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    //更新prepay_id的使用次数
    public void updateMessageNumberById(Long messageId, Integer number) {
        int i = number - 1;
        String sql = "UPDATE ycb_mcs_message SET lastModifiedBy='SYS:message',lastModifiedDate=NOW(),number=" + i + " WHERE id = " + messageId;
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
