package org.linlinjava.litemall.db.dao;

import java.util.List;
import java.util.Map;

import org.linlinjava.litemall.db.domain.Whuser;

public interface ExWhUserMapper {

	List<Whuser> getUserListBySearch(Map<String, Object> usertypes);

	void saveDeleteRecord(Map<String, Object> map);

	void deleteUserRecord(Map<String, Object> map);

	void saveOutsideDeleteRecord(Map<String, Object> map);

	void deleteOutsideRecord(Map<String, Object> map);

	void updateUserIsSafe(String idCard);

}
