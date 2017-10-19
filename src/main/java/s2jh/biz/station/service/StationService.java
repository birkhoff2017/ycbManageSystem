package s2jh.biz.station.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.pagination.GroupPropertyFilter;
import lab.s2jh.core.pagination.PropertyFilter;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.support.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.station.dao.StationDao;
import s2jh.biz.station.dao.StationDaoImpl;
import s2jh.biz.station.entity.Station;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class StationService extends BaseService<Station, Long> {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private SocketService socketService;

    @Autowired
    private StationDaoImpl stationDaoImpl;


    @Override
    protected BaseDao<Station, Long> getEntityDao() {
        return stationDao;
    }

    /**
     * 批量修改设备的同步IP和port
     * @param ids
     */
    public void alterIpAndPort(Long[] ids) {
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        groupPropertyFilter.forceAnd(new PropertyFilter(PropertyFilter.MatchType.IN, "id", ids));
        List<Station> stationList = this.findByFilters(groupPropertyFilter);

        if (null != stationList && stationList.size() > 0) {
            for (Station station : stationList) {
                //String cmd = "ACT:popup;EVENT_CODE:55;STATIONID:" + battery.getStation().getSid() + ";MAC:" + mac + ";SLOT:" + slot + "\r\n";
                Long sid = station.getSid();
                String mac = station.getMac();
                Integer softVer = station.getSoftVer();
                Integer heartbeatRate = station.getHeartbeatRate();
                String domain = stationDaoImpl.getValue("Domain");
                String ip = stationDaoImpl.getValue("Ip");
                String port = stationDaoImpl.getValue("Port");
                String cmd = "ACT:sync_setting_cmd;EVENT_CODE:77;STATIONID:" + sid
                        + ";MAC:" + mac + ";DOMAIN:" + domain + ";IP:" + ip
                        + ";PORT:" + port + ";CHECKUPDATEDELAY:1;SOFT_VER:" +
                        softVer + ";FILE_NAME:null;HEATBEAT:" + heartbeatRate + "\r\n";
                try {
                    socketService.SendCmd(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
