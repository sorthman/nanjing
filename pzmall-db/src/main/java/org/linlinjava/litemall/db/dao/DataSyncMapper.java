package org.linlinjava.litemall.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.linlinjava.litemall.db.vo.SyncErrorRecordVo;
import org.linlinjava.litemall.db.vo.SyncHealthVo;
import org.linlinjava.litemall.db.vo.SyncOutsideVo;
import org.linlinjava.litemall.db.vo.SyncReturnNanjingVo;
import org.linlinjava.litemall.db.vo.SyncTemperatureVo;

public interface DataSyncMapper {

	Date getLastSyncTime(String jobName);

	void updateSyncTime(Map<String, Object> map);

	List<SyncReturnNanjingVo> getReturnNanjingData(Map<String, Object> map);

	void saveErrorRecord(SyncErrorRecordVo syncErrorRecord);

	void saveJobRecord(Map<String, Object> map);

	List<SyncReturnNanjingVo> getAllUserData(Map<String, Object> map);

	List<SyncOutsideVo> getOutSiteData(Map<String, Object> map);

	List<SyncTemperatureVo> getTemperatureData(Map<String, Object> map);
	
	List<SyncHealthVo> getHealthData(Map<String, Object> map);

}
