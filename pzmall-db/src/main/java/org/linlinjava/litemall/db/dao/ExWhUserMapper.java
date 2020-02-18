package org.linlinjava.litemall.db.dao;

import java.util.List;

import org.linlinjava.litemall.db.domain.Whuser;

public interface ExWhUserMapper {

	List<Whuser> getUserListBySearch(List<String> usertypes);

}
