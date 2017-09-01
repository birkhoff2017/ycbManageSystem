package s2jh.biz.formid.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.service.BaseService;
import s2jh.biz.formid.entity.Message;
import s2jh.biz.formid.dao.MessageDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService extends BaseService<Message,Long>{
    
    @Autowired
    private MessageDao messageDao;

    @Override
    protected BaseDao<Message, Long> getEntityDao() {
        return messageDao;
    }
}
