package org.linlinjava.litemall.db.dao;

import java.util.List;
import java.util.Map;

import org.linlinjava.litemall.db.domain.Whuser;

public interface ExWhUserMapper {

	List<Whuser> getUserListBySearch(Map<String, Object> usertypes);

}
