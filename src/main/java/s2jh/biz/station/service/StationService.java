package s2jh.biz.station.service;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.pagination.GroupPropertyFilter;
import lab.s2jh.core.pagination.PropertyFilter;
import lab.s2jh.core.service.BaseService;
import lab.s2jh.module.sys.entity.DataDict;
import lab.s2jh.module.sys.service.DataDictService;
import lab.s2jh.support.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s2jh.biz.station.dao.StationDao;
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
    private DataDictService dataDictService;


    @Override
    protected BaseDao<Station, Long> getEntityDao() {
        return stationDao;
    }

    /**
     * 批量修改设备的同步IP和port
     *
     * @param ids
     */
    public void alterIpAndPort(Long[] ids) {
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        groupPropertyFilter.forceAnd(new PropertyFilter(PropertyFilter.MatchType.IN, "id", ids));
        List<Station> stationList = this.findByFilters(groupPropertyFilter);

        if (null != stationList && stationList.size() > 0) {
            for (Station station : stationList) {
                //获取设备的同步策略
                Integer syncSetting = station.getSyncSetting();
                String syncIpAndPort = "";
                //如果同步策略为空，则去查询默认同步策略
                List<DataDict> dataDictList = dataDictService.findChildrenByPrimaryKey("Special_Sync_Setting");
                DataDict dataDict = null;
                //当查询出的策略不为空的时候
                if (null != dataDictList && dataDictList.size() > 0) {
                    //遍历策略列表
                    for (DataDict selectedDataDict : dataDictList) {
                        if (null == syncSetting || "".equals(syncSetting)) {
                            if ("default".equals(selectedDataDict.getPrimaryKey())) {
                                dataDict = selectedDataDict;
                                break;
                            }
                        } else {
                            if (syncSetting.equals(selectedDataDict.getPrimaryKey())) {
                                dataDict = selectedDataDict;
                                break;
                            }
                        }
                    }
                }

                //如果没有查询出同步策略
                if (null == dataDict) {
                    syncIpAndPort = "ip:39.108.14.135;port:54589";
                } else {
                    syncIpAndPort = dataDict.getSecondaryValue();
                }

                //syncIpAndPort格式为：ip:127.0.0.1;port:8000
                String[] split = syncIpAndPort.split(";");
                String ip = split[0].split(":")[1];
                String port = split[1].split(":")[1];

                Long sid = station.getSid();
                String mac = station.getMac();
                Integer softVer = station.getSoftVer();
                Integer heartbeatRate = station.getHeartbeatRate();
                String domain = "https://x.yunchongba.com";
                String cmd = "ACT:sync_setting_cmd;STATIONID:" + sid
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
