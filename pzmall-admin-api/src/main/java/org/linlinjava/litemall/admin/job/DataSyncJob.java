package org.linlinjava.litemall.admin.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.HttpUtil;
import org.linlinjava.litemall.db.dao.DataSyncMapper;
import org.linlinjava.litemall.db.vo.SyncErrorRecordVo;
import org.linlinjava.litemall.db.vo.SyncHealthVo;
import org.linlinjava.litemall.db.vo.SyncOutsideVo;
import org.linlinjava.litemall.db.vo.SyncReturnNanjingVo;
import org.linlinjava.litemall.db.vo.SyncTemperatureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 疫情数据同步到公安侧
 */
@Component
public class DataSyncJob {

	private final Log logger = LogFactory.getLog(DataSyncJob.class);

	@Autowired
	private DataSyncMapper dataSyncMapper;

	/**
	 * 第一次同步全量数据，之后的每10分钟同步增量数据到公安侧
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void syncDataToPolice1() {
		logger.info("系统开启任务，将【归宁人员】疫情数据增量同步到公安侧");

		Map<String, Object> jobRecordMap = new HashMap<>();
		// 任务名称
		jobRecordMap.put("jobName", "归宁人员");

		// 获取上次同步的时间点
		Date startTime = dataSyncMapper.getLastSyncTime("returnNanjing");
		Date endTime = new Date();

		// 同步时间
		jobRecordMap.put("syncTime", endTime);

		List<SyncReturnNanjingVo> dateList = null;

		// startTime为,则为第一次，需要同步历史全量数据；之后每10分钟同步增量数据
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);

		dateList = dataSyncMapper.getReturnNanjingData(timeMap);

		if (dateList.size() > 0) {
			// 同步数据条数
			jobRecordMap.put("syncDataNum", dateList.size());

			int errorNum = 0;

			// 进行数据同步到公安API
			for (SyncReturnNanjingVo syncData : dateList) {
				Map<String, String> params = new HashMap<>();
				params.put("name", syncData.getName());
				params.put("idCard", syncData.getIdCard());
				params.put("phone", syncData.getPhone());
				params.put("community", syncData.getCommunity());
				params.put("address", syncData.getAddress());
				params.put("arrivalDate", syncData.getArrivalDate());
				params.put("trackingRemark", syncData.getTrackingRemark());
				params.put("arrivalEpidemicArea", syncData.getArrivalEpidemicArea());
				params.put("arrivalEpidemicAreaDate", syncData.getArrivalEpidemicAreaDate());
				params.put("resideResult", syncData.getResideResult());
				params.put("streetLeader", syncData.getStreetLeader());
				params.put("streetLeaderPhone", syncData.getStreetLeaderPhone());
				params.put("streetCadre", syncData.getStreetCadre());
				params.put("streetCadrePhone", syncData.getStreetCadrePhone());
				params.put("communityWorker", syncData.getCommunityWorker());
				params.put("communityWorkerPhone", syncData.getCommunityWorkerPhone());
				params.put("communityPolice", syncData.getCommunityPolice());
				params.put("communityPolicePhone", syncData.getCommunityPolicePhone());
				params.put("medicalPersonnel", syncData.getMedicalPersonnel());
				params.put("medicalPersonnelPhone", syncData.getMedicalPersonnelPhone());

				String result = "";
				try {
					result = HttpUtil.sendPost("http://221.131.141.246:8088/mobile/mine/all/regression", params);
					JSONObject jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("code") == 20000) {
						// 同步成功
						continue;
					} else {
						errorNum++;
						// 同步失败，记录本条记录请求到数据库
						SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
						syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/regression");
						syncErrorRecord.setParamsJson(JSON.toJSONString(params));
						syncErrorRecord.setRespStr(result);
						syncErrorRecord.setErrorReason("返回结果非20000");

						dataSyncMapper.saveErrorRecord(syncErrorRecord);
					}
				} catch (Exception e) {
					errorNum++;
					// 接口请求异常，则记录该次请求到数据库
					SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
					syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/regression");
					syncErrorRecord.setParamsJson(JSON.toJSONString(params));
					syncErrorRecord.setErrorReason("同步接口调用异常");

					dataSyncMapper.saveErrorRecord(syncErrorRecord);
				}
			}
			// 同步数据条数
			jobRecordMap.put("errorNum", errorNum);
			// 记录当前任务执行记录
			dataSyncMapper.saveJobRecord(jobRecordMap);
		}

		// 同步后，修改当前job的同步时间
		Map<String, Object> map = new HashMap<>();
		map.put("jobName", "returnNanjing");
		map.put("lastSyncTime", endTime);
		dataSyncMapper.updateSyncTime(map);

	}

	/**
	 * 第一次同步全量数据，之后的每10分钟同步增量数据到公安侧
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void syncDataToPolice2() {
		logger.info("系统开启任务，将【全部人员】疫情数据增量同步到公安侧");

		Map<String, Object> jobRecordMap = new HashMap<>();
		// 任务名称
		jobRecordMap.put("jobName", "全部人员");

		// 获取上次同步的时间点
		Date startTime = dataSyncMapper.getLastSyncTime("allUser");
		Date endTime = new Date();

		// 同步时间
		jobRecordMap.put("syncTime", endTime);

		List<SyncReturnNanjingVo> dateList = null;

		// startTime为,则为第一次，需要同步历史全量数据；之后每10分钟同步增量数据
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);

		dateList = dataSyncMapper.getReturnNanjingData(timeMap);

		if (dateList.size() > 0) {
			// 同步数据条数
			jobRecordMap.put("syncDataNum", dateList.size());

			int errorNum = 0;

			// 进行数据同步到公安API
			for (SyncReturnNanjingVo syncData : dateList) {
				Map<String, String> params = new HashMap<>();
				params.put("name", syncData.getName());
				params.put("idCard", syncData.getIdCard());
				params.put("phone", syncData.getPhone());
				params.put("community", syncData.getCommunity());
				params.put("address", syncData.getAddress());
				params.put("arrivalDate", syncData.getArrivalDate());
				params.put("trackingRemark", syncData.getTrackingRemark());

				if ("常住".equals(syncData.getResideResult())) {
					params.put("type", "是");
				} else {
					params.put("type", "否");
				}

				params.put("arrivalEpidemicArea", syncData.getArrivalEpidemicArea());
				params.put("arrivalEpidemicAreaDate", syncData.getArrivalEpidemicAreaDate());
				params.put("resideResult", syncData.getResideResult());
				params.put("streetLeader", syncData.getStreetLeader());
				params.put("streetLeaderPhone", syncData.getStreetLeaderPhone());
				params.put("streetCadre", syncData.getStreetCadre());
				params.put("streetCadrePhone", syncData.getStreetCadrePhone());
				params.put("communityWorker", syncData.getCommunityWorker());
				params.put("communityWorkerPhone", syncData.getCommunityWorkerPhone());
				params.put("communityPolice", syncData.getCommunityPolice());
				params.put("communityPolicePhone", syncData.getCommunityPolicePhone());
				params.put("medicalPersonnel", syncData.getMedicalPersonnel());
				params.put("medicalPersonnelPhone", syncData.getMedicalPersonnelPhone());

				String result = "";
				try {
					result = HttpUtil.sendPost("http://221.131.141.246:8088/mobile/mine/all/report", params);
					JSONObject jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("code") == 20000) {
						// 同步成功
						continue;
					} else {
						errorNum++;
						// 同步失败，记录本条记录请求到数据库
						SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
						syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/report");
						syncErrorRecord.setParamsJson(JSON.toJSONString(params));
						syncErrorRecord.setRespStr(result);
						syncErrorRecord.setErrorReason("返回结果非20000");

						dataSyncMapper.saveErrorRecord(syncErrorRecord);
					}
				} catch (Exception e) {
					errorNum++;
					// 接口请求异常，则记录该次请求到数据库
					SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
					syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/report");
					syncErrorRecord.setParamsJson(JSON.toJSONString(params));
					syncErrorRecord.setErrorReason("同步接口调用异常");

					dataSyncMapper.saveErrorRecord(syncErrorRecord);
				}
			}
			// 同步数据条数
			jobRecordMap.put("errorNum", errorNum);
			// 记录当前任务执行记录
			dataSyncMapper.saveJobRecord(jobRecordMap);
		}

		// 同步后，修改当前job的同步时间
		Map<String, Object> map = new HashMap<>();
		map.put("jobName", "allUser");
		map.put("lastSyncTime", endTime);
		dataSyncMapper.updateSyncTime(map);

	}

	/**
	 * 第一次同步全量数据，之后的每10分钟同步增量数据到公安侧
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void syncDataToPolice3() {
		logger.info("系统开启任务，将【外来人员】疫情数据增量同步到公安侧");

		Map<String, Object> jobRecordMap = new HashMap<>();
		// 任务名称
		jobRecordMap.put("jobName", "外来人员");

		// 获取上次同步的时间点
		Date startTime = dataSyncMapper.getLastSyncTime("outside");
		Date endTime = new Date();

		// 同步时间
		jobRecordMap.put("syncTime", endTime);

		List<SyncOutsideVo> dateList = null;

		// startTime为,则为第一次，需要同步历史全量数据；之后每10分钟同步增量数据
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);

		dateList = dataSyncMapper.getOutSiteData(timeMap);

		if (dateList.size() > 0) {
			// 同步数据条数
			jobRecordMap.put("syncDataNum", dateList.size());

			int errorNum = 0;

			// 进行数据同步到公安API
			for (SyncOutsideVo syncData : dateList) {
				Map<String, String> params = new HashMap<>();
				params.put("name", syncData.getName());
				params.put("idCard", syncData.getIdCard());
				params.put("phone", syncData.getPhone());
				params.put("community", syncData.getCommunity());
				params.put("address", syncData.getAddress());
				params.put("arrivalDate", syncData.getArrivalDate());
				params.put("trackingRemark", syncData.getTrackingRemark());
				params.put("fromRegion", syncData.getFromRegion());

				String result = "";
				try {
					result = HttpUtil.sendPost("http://221.131.141.246:8088/mobile/mine/all/outside", params);
					JSONObject jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("code") == 20000) {
						// 同步成功
						continue;
					} else {
						errorNum++;
						// 同步失败，记录本条记录请求到数据库
						SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
						syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/outside");
						syncErrorRecord.setParamsJson(JSON.toJSONString(params));
						syncErrorRecord.setRespStr(result);
						syncErrorRecord.setErrorReason("返回结果非20000");

						dataSyncMapper.saveErrorRecord(syncErrorRecord);
					}
				} catch (Exception e) {
					errorNum++;
					// 接口请求异常，则记录该次请求到数据库
					SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
					syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/outside");
					syncErrorRecord.setParamsJson(JSON.toJSONString(params));
					syncErrorRecord.setErrorReason("同步接口调用异常");

					dataSyncMapper.saveErrorRecord(syncErrorRecord);
				}
			}
			// 同步数据条数
			jobRecordMap.put("errorNum", errorNum);
			// 记录当前任务执行记录
			dataSyncMapper.saveJobRecord(jobRecordMap);
		}

		// 同步后，修改当前job的同步时间
		Map<String, Object> map = new HashMap<>();
		map.put("jobName", "returnNanjing");
		map.put("lastSyncTime", endTime);
		dataSyncMapper.updateSyncTime(map);

	}

	/**
	 * 第一次同步全量数据，之后的每10分钟同步增量数据到公安侧
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void syncDataToPolice4() {
		logger.info("系统开启任务，将【健康状况】疫情数据增量同步到公安侧");

		Map<String, Object> jobRecordMap = new HashMap<>();
		// 任务名称
		jobRecordMap.put("jobName", "健康状况");

		// 获取上次同步的时间点
		Date startTime = dataSyncMapper.getLastSyncTime("health");
		Date endTime = new Date();

		// 同步时间
		jobRecordMap.put("syncTime", endTime);

		List<SyncHealthVo> dateList = null;

		// startTime为,则为第一次，需要同步历史全量数据；之后每10分钟同步增量数据
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);

		dateList = dataSyncMapper.getHealthData(timeMap);

		if (dateList.size() > 0) {
			// 同步数据条数
			jobRecordMap.put("syncDataNum", dateList.size());

			int errorNum = 0;

			// 进行数据同步到公安API
			for (SyncHealthVo syncData : dateList) {
				Map<String, String> params = new HashMap<>();
				params.put("name", syncData.getName());
				params.put("idCard", syncData.getIdCard());
				params.put("phone", syncData.getPhone());
				params.put("community", syncData.getCommunity());
				params.put("address", syncData.getAddress());
				params.put("arrivalDate", syncData.getArrivalDate());

				params.put("leaveResult", syncData.getLeaveResult());
				params.put("locationRegion", syncData.getLocationRegion());
				params.put("locationResult", syncData.getLocationResult());
				params.put("trackingResult", syncData.getTrackingResult());
				params.put("trackingReason", syncData.getTrackingReason());
				params.put("manageResult", syncData.getManageResult());
				params.put("currentState", syncData.getCurrentState());
				params.put("feverResult", syncData.getFeverResult());
				params.put("feverTemperature", syncData.getFeverTemperature());
				params.put("coughResult", syncData.getCoughResult());
				params.put("symptom", syncData.getSymptom());

				params.put("visitResult", syncData.getVisitResult());
				params.put("visitRemark", syncData.getVisitRemark());

				String result = "";
				try {
					result = HttpUtil.sendPost("http://221.131.141.246:8088/mobile/mine/all/health", params);
					JSONObject jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("code") == 20000) {
						// 同步成功
						continue;
					} else {
						errorNum++;
						// 同步失败，记录本条记录请求到数据库
						SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
						syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/health");
						syncErrorRecord.setParamsJson(JSON.toJSONString(params));
						syncErrorRecord.setRespStr(result);
						syncErrorRecord.setErrorReason("返回结果非20000");

						dataSyncMapper.saveErrorRecord(syncErrorRecord);
					}
				} catch (Exception e) {
					errorNum++;
					// 接口请求异常，则记录该次请求到数据库
					SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
					syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/health");
					syncErrorRecord.setParamsJson(JSON.toJSONString(params));
					syncErrorRecord.setErrorReason("同步接口调用异常");

					dataSyncMapper.saveErrorRecord(syncErrorRecord);
				}
			}
			// 同步数据条数
			jobRecordMap.put("errorNum", errorNum);
			// 记录当前任务执行记录
			dataSyncMapper.saveJobRecord(jobRecordMap);
		}

		// 同步后，修改当前job的同步时间
		Map<String, Object> map = new HashMap<>();
		map.put("jobName", "health");
		map.put("lastSyncTime", endTime);
		dataSyncMapper.updateSyncTime(map);

	}

	/**
	 * 第一次同步全量数据，之后的每10分钟同步增量数据到公安侧
	 */
	@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void syncDataToPolice5() {
		logger.info("系统开启任务，将【体温】疫情数据增量同步到公安侧");

		Map<String, Object> jobRecordMap = new HashMap<>();
		// 任务名称
		jobRecordMap.put("jobName", "体温");

		// 获取上次同步的时间点
		Date startTime = dataSyncMapper.getLastSyncTime("temperature");
		Date endTime = new Date();

		// 同步时间
		jobRecordMap.put("syncTime", endTime);

		List<SyncTemperatureVo> dateList = null;

		// startTime为,则为第一次，需要同步历史全量数据；之后每10分钟同步增量数据
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);

		dateList = dataSyncMapper.getTemperatureData(timeMap);

		if (dateList.size() > 0) {
			// 同步数据条数
			jobRecordMap.put("syncDataNum", dateList.size());

			int errorNum = 0;

			// 进行数据同步到公安API
			for (SyncTemperatureVo syncData : dateList) {
				Map<String, String> params = new HashMap<>();
				params.put("name", syncData.getName());
				params.put("idCard", syncData.getIdCard());
				params.put("phone", syncData.getPhone());
				params.put("arrivalDate", syncData.getArrivalDate());
				params.put("temp", syncData.getTemp());
				params.put("measureDatetime", syncData.getMeasureDatetime());

				String result = "";
				try {
					result = HttpUtil.sendPost("http://221.131.141.246:8088/mobile/mine/all/temperature", params);
					JSONObject jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("code") == 20000) {
						// 同步成功
						continue;
					} else {
						errorNum++;
						// 同步失败，记录本条记录请求到数据库
						SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
						syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/temperature");
						syncErrorRecord.setParamsJson(JSON.toJSONString(params));
						syncErrorRecord.setRespStr(result);
						syncErrorRecord.setErrorReason("返回结果非20000");

						dataSyncMapper.saveErrorRecord(syncErrorRecord);
					}
				} catch (Exception e) {
					errorNum++;
					// 接口请求异常，则记录该次请求到数据库
					SyncErrorRecordVo syncErrorRecord = new SyncErrorRecordVo();
					syncErrorRecord.setHttpUrl("http://221.131.141.246:8088/mobile/mine/all/temperature");
					syncErrorRecord.setParamsJson(JSON.toJSONString(params));
					syncErrorRecord.setErrorReason("同步接口调用异常");

					dataSyncMapper.saveErrorRecord(syncErrorRecord);
				}
			}
			// 同步数据条数
			jobRecordMap.put("errorNum", errorNum);
			// 记录当前任务执行记录
			dataSyncMapper.saveJobRecord(jobRecordMap);
		}

		// 同步后，修改当前job的同步时间
		Map<String, Object> map = new HashMap<>();
		map.put("jobName", "temperature");
		map.put("lastSyncTime", endTime);
		dataSyncMapper.updateSyncTime(map);

	}

}
