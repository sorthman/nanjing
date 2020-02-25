package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;

import org.linlinjava.litemall.db.dao.ExWhUserMapper;
import org.linlinjava.litemall.db.dao.WhuserMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LitemallUserService {
	@Resource
	private WhuserMapper userMapper;

	@Autowired
	private ExWhUserMapper exWhUserMapper;

	public Whuser findById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	public Whuser findByIdcard(String idcard) {
		WhuserExample example = new WhuserExample();
		example.or().andIdcardEqualTo(idcard);
		return userMapper.selectOneByExample(example);
	}

	public Whuser findByPhone(String phone) {
		WhuserExample example = new WhuserExample();
		example.or().andPhoneEqualTo(phone);
		return userMapper.selectOneByExample(example);
	}

	public List<Whuser> querySelective(String area, String username, String mobile, Integer page, Integer size,
			String sort, String order) {
		WhuserExample example = new WhuserExample();
		WhuserExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(area)) {
			criteria.andAreaEqualTo(area);
		}

		if (!StringUtils.isEmpty(username)) {
			criteria.andNameLike("%" + username + "%");
		}
		if (!StringUtils.isEmpty(mobile)) {
			criteria.andPhoneLike("%" + mobile + "%");
		}

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return userMapper.selectByExample(example);
	}

	public List<Whuser> querySelective(String area, String username, String mobile, String sex, Integer sage,
			Integer eage, String idcard, String street, String community, String arrivedate, String[] addsource,
			String iftransferarea, String iftransferstreet, String[] ifsafe, String healthinfo, String[] usertype,
			String ifwh, String ifhb, String ifleavenj, String ifadmin, String ifover, String iflose, String ifstay,
			String addtime, String managetime, String level,

			Integer page, Integer size, String sort, String order, String checkIsNot) {

		if (!StringUtils.isEmpty(checkIsNot)) {
			if ("是".equals(checkIsNot)) {

				List<String> list = new ArrayList<>();
				for (String s : usertype) {
					list.add(s);
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeSize", list.size());
				map.put("typeList", list);

				PageHelper.startPage(page, size);
				List<Whuser> userList = exWhUserMapper.getUserListBySearch(map);
				return userList;
			}
		}

		WhuserExample example = new WhuserExample();

		WhuserExample.Criteria criteria_ifsafe = example.createCriteria();

		if (ifsafe.length > 0) {
			boolean nullInIfsafe = false;
			List<String> list = new ArrayList<>();
			for (String s : ifsafe) {
				if (s.equals("无")) {
					nullInIfsafe = true;
					continue;
				}
				list.add(s);
			}
			if (nullInIfsafe) {
				if (list.size() > 0) {
					WhuserExample.Criteria criteria2 = example.createCriteria();
					criteria2.andIfsafeIsNull();
					otherSelections(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate,
							addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb,
							ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, example, criteria2,
							1, list);

					if (addsource.length <= 0) {
						example.or(criteria2);
					}

					WhuserExample.Criteria criteria3 = example.createCriteria();
					criteria3.andIfsafeIn(list);
					otherSelections(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate,
							addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb,
							ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, example, criteria3,
							2, list);

					if (addsource.length <= 0) {
						example.or(criteria3);
					}
				} else {
					WhuserExample.Criteria criteria2 = example.createCriteria();
					criteria2.andIfsafeIsNull();
					otherSelections(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate,
							addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb,
							ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, example,
							criteria_ifsafe, 2, list);

					if (addsource.length <= 0) {
						example.or(criteria2);
					}
				}

			} else {
				criteria_ifsafe.andIfsafeIn(list);
				otherSelections(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate,
						addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb,
						ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, example,
						criteria_ifsafe, 2, list);
			}
		} else {
			otherSelections(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate, addsource,
					iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb, ifleavenj, ifadmin,
					ifover, iflose, ifstay, addtime, managetime, level, example, criteria_ifsafe, 3, null);
		}

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return userMapper.selectByExample(example);
	}

	private void otherSelections(String area, String username, String mobile, String sex, Integer sage, Integer eage,
			String idcard, String street, String community, String arrivedate, String[] addsource,
			String iftransferarea, String iftransferstreet, String[] ifsafe, String healthinfo, String[] usertype,
			String ifwh, String ifhb, String ifleavenj, String ifadmin, String ifover, String iflose, String ifstay,
			String addtime, String managetime, String level, WhuserExample example, WhuserExample.Criteria criteria,
			int tag, List<String> ifsafelist) {
		if (addsource.length > 0) {
			for (String singlesource : addsource) {
				WhuserExample.Criteria criteria3 = example.createCriteria();
				if (1 == tag) {
					criteria3.andIfsafeIsNull();
				} else if (2 == tag) {
					criteria3.andIfsafeIn(ifsafelist);
				}

				otherSelectSon(area, username, mobile, sex, sage, eage, idcard, street, community, arrivedate,
						addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb,
						ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, example, criteria3,
						singlesource);
				example.or(criteria3);
			}

		} else {
			if (sage != 0) {
				criteria.andAgeGreaterThanOrEqualTo(sage);
			}
			if (eage != 0) {
				criteria.andAgeLessThanOrEqualTo(eage);
			}

			if (!StringUtils.isEmpty(area)) {
				criteria.andAreaEqualTo(area);
			}

			if (!StringUtils.isEmpty(username)) {
				criteria.andNameLike("%" + username + "%");
			}
			if (!StringUtils.isEmpty(mobile)) {
				criteria.andPhoneLike("%" + mobile + "%");
			}

			if (!StringUtils.isEmpty(sex)) {
				criteria.andSexEqualTo(sex);
			}
			if (!StringUtils.isEmpty(idcard)) {
				criteria.andIdcardLike("%" + idcard + "%");
			}
			if (!StringUtils.isEmpty(street)) {
				if ("有".equals(street)) {
					criteria.andStreetIsNotNull();
				} else if ("无".equals(street)) {
					criteria.andStreetIsNull();
				} else {
					criteria.andStreetLike("%" + street + "%");
				}
			}

			if (!StringUtils.isEmpty(community)) {
				if ("有".equals(community)) {
					criteria.andNjcommunityIsNotNull();
				} else if ("无".equals(community)) {
					criteria.andNjcommunityIsNull();
				} else {
					criteria.andNjcommunityLike("%" + community + "%");
				}
			}
			if (!StringUtils.isEmpty(arrivedate)) {
				arrivedate = arrivedate.replace(" ", "T");
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime date = LocalDateTime.parse(arrivedate);
				criteria.andArrivedateEqualTo(date);
			}
			if (!StringUtils.isEmpty(addtime)) {
				addtime = addtime.replace(" ", "T");
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime date = LocalDateTime.parse(addtime);
				LocalDateTime dateend = date.plusDays(1);
				criteria.andAddtimeBetween(date, dateend);
			}
			if (!StringUtils.isEmpty(managetime)) {
				managetime = managetime.replace(" ", "T");
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime date = LocalDateTime.parse(managetime);
				LocalDateTime dateend = date.plusDays(1);
				criteria.andManagetimeBetween(date, dateend);
			}

			if (!StringUtils.isEmpty(iftransferarea)) {
				criteria.andIftransferareaEqualTo(iftransferarea);
			}
			if (!StringUtils.isEmpty(iftransferstreet)) {
				criteria.andIftransferstreetEqualTo(iftransferstreet);
			}

//			if (ifsafe.length > 0) {
//				List<String> list = new ArrayList<>();
//				for (String s : ifsafe) {
//					list.add(s);
//				}
//				criteria.andIfsafeIn(list);
//			}
			if (!StringUtils.isEmpty(ifhb)) {
				criteria.andIfhbEqualTo(ifhb);
			}
//        if (!StringUtils.isEmpty(usertype)) {
//            criteria.andUsertypeEqualTo(usertype);
//        }
			if (usertype.length > 0) {
				List<String> list = new ArrayList<>();
				for (String s : usertype) {
					list.add(s);
				}
				criteria.andUsertypeIn(list);
			}
			if (!StringUtils.isEmpty(ifwh)) {
				criteria.andIfwhEqualTo(ifwh);
			}
			if (!StringUtils.isEmpty(ifleavenj)) {
				criteria.andIfleavenjEqualTo(ifleavenj);
			}
			if (!StringUtils.isEmpty(iflose)) {
				criteria.andIfloseEqualTo(iflose);
			}
			if (!StringUtils.isEmpty(ifstay)) {
				criteria.andIfstayEqualTo(ifstay);
			}
			if (!StringUtils.isEmpty(ifadmin)) {
				criteria.andIsmanageEqualTo(ifadmin);
			}
			if (!StringUtils.isEmpty(level)) {
				criteria.andLevelEqualTo(level);
			}
			if (!StringUtils.isEmpty(ifover)) {
				criteria.andManagetimeIsNotNull();
				if (ifover.equals("是")) {
//                LocalDateTime sTime = LocalDateTime.now().minusDays(15);
//                criteria.andArrivedateLessThan(sTime);
					criteria.andLefttimeLessThanOrEqualTo(0);

				} else {
//                LocalDateTime sTime = LocalDateTime.now().minusDays(15);
//                criteria.andArrivedateGreaterThan(sTime);
					criteria.andLefttimeGreaterThan(0);
				}
			}
			if (!StringUtils.isEmpty(healthinfo)) {
				if (healthinfo.indexOf("咳嗽") >= 0) {
					criteria.andIfkesouEqualTo("是");
				}
				if (healthinfo.indexOf("发热") >= 0) {
					criteria.andIfhotEqualTo("是");
				}
				healthinfo = healthinfo.replaceAll("咳嗽", "");
				healthinfo = healthinfo.replaceAll("发热", "");

				String[] infos = healthinfo.split(",");
				for (String s : infos) {
					if (s.length() > 1) {
						criteria.andHealthinfoLike("%" + s + "%");
					}
				}
			}
		}
	}

	private void otherSelectSon(String area, String username, String mobile, String sex, Integer sage, Integer eage,
			String idcard, String street, String community, String arrivedate, String[] addsource,
			String iftransferarea, String iftransferstreet, String[] ifsafe, String healthinfo, String[] usertype,
			String ifwh, String ifhb, String ifleavenj, String ifadmin, String ifover, String iflose, String ifstay,
			String addtime, String managetime, String level, WhuserExample example, WhuserExample.Criteria criteria,
			String singlesource) {

		if (!StringUtils.isEmpty(singlesource)) {
			criteria.andAddsourceLike("%" + singlesource + "%");
		}
		if (sage != 0) {
			criteria.andAgeGreaterThanOrEqualTo(sage);
		}
		if (eage != 0) {
			criteria.andAgeLessThanOrEqualTo(eage);
		}

		if (!StringUtils.isEmpty(area)) {
			criteria.andAreaEqualTo(area);
		}

		if (!StringUtils.isEmpty(username)) {
			criteria.andNameLike("%" + username + "%");
		}
		if (!StringUtils.isEmpty(mobile)) {
			criteria.andPhoneLike("%" + mobile + "%");
		}

		if (!StringUtils.isEmpty(sex)) {
			criteria.andSexEqualTo(sex);
		}
		if (!StringUtils.isEmpty(idcard)) {
			criteria.andIdcardLike("%" + idcard + "%");
		}
		if (!StringUtils.isEmpty(street)) {
			if ("有".equals(street)) {
				criteria.andStreetIsNotNull();
			} else if ("无".equals(street)) {
				criteria.andStreetIsNull();
			} else {
				criteria.andStreetLike("%" + street + "%");
			}
		}

		if (!StringUtils.isEmpty(community)) {
			if ("有".equals(community)) {
				criteria.andNjcommunityIsNotNull();
			} else if ("无".equals(community)) {
				criteria.andNjcommunityIsNull();
			} else {
				criteria.andNjcommunityLike("%" + community + "%");
			}
		}

		if (!StringUtils.isEmpty(arrivedate)) {
			arrivedate = arrivedate.replace(" ", "T");
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.parse(arrivedate);
			criteria.andArrivedateEqualTo(date);
		}
		if (!StringUtils.isEmpty(addtime)) {
			addtime = addtime.replace(" ", "T");
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.parse(addtime);
			LocalDateTime dateend = date.plusDays(1);
			criteria.andAddtimeBetween(date, dateend);
		}
		if (!StringUtils.isEmpty(managetime)) {
			managetime = managetime.replace(" ", "T");
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.parse(managetime);
			LocalDateTime dateend = date.plusDays(1);
			criteria.andManagetimeBetween(date, dateend);
		}
//    if (addsource.length > 0) {
//        List<String> list = new ArrayList<>();
//        WhuserExample.Criteria criteria2 = example.newAndCreateCriteria();
//        for (String s : addsource) {
//            list.add(s);
//            example.and(example.createCriteria()
//                    .andEqualTo("name", projectCatalogEntity.getName())
//                    .orEqualTo("code", projectCatalogEntity.getCode()));
//            criteria2..andAddsourceLike("%" + s + "%");
//        }
//        example.
////        criteria.andAddsourceIn(list);
//    }
		if (!StringUtils.isEmpty(iftransferarea)) {
			criteria.andIftransferareaEqualTo(iftransferarea);
		}
		if (!StringUtils.isEmpty(iftransferstreet)) {
			criteria.andIftransferstreetEqualTo(iftransferstreet);
		}

//			if (ifsafe.length > 0) {
//				List<String> list = new ArrayList<>();
//				for (String s : ifsafe) {
//					WhuserExample.Criteria criteria2 = example.createCriteria();
//					if (s.equals("无")) {
//						criteria2.andIfsafeIsNull();
//						continue;
//					}
//					list.add(s);
//					example.or(criteria2);
//				}
//				criteria.andIfsafeIn(list);
//			}

		if (!StringUtils.isEmpty(ifhb)) {
			criteria.andIfhbEqualTo(ifhb);
		}
//    if (!StringUtils.isEmpty(usertype)) {
//        criteria.andUsertypeEqualTo(usertype);
//    }
		if (usertype.length > 0) {
			List<String> list = new ArrayList<>();
			for (String s : usertype) {
				list.add(s);
			}
			criteria.andUsertypeIn(list);
		}
		if (!StringUtils.isEmpty(ifwh)) {
			criteria.andIfwhEqualTo(ifwh);
		}
		if (!StringUtils.isEmpty(ifleavenj)) {
			criteria.andIfleavenjEqualTo(ifleavenj);
		}
		if (!StringUtils.isEmpty(iflose)) {
			criteria.andIfloseEqualTo(iflose);
		}
		if (!StringUtils.isEmpty(ifstay)) {
			criteria.andIfstayEqualTo(ifstay);
		}
		if (!StringUtils.isEmpty(ifadmin)) {
			criteria.andIsmanageEqualTo(ifadmin);
		}
		if (!StringUtils.isEmpty(level)) {
			criteria.andLevelEqualTo(level);
		}
		if (!StringUtils.isEmpty(ifover)) {
			criteria.andManagetimeIsNotNull();
			if (ifover.equals("是")) {
//            LocalDateTime sTime = LocalDateTime.now().minusDays(15);
//            criteria.andArrivedateLessThan(sTime);
				criteria.andLefttimeLessThanOrEqualTo(0);

			} else {
//            LocalDateTime sTime = LocalDateTime.now().minusDays(15);
//            criteria.andArrivedateGreaterThan(sTime);
				criteria.andLefttimeGreaterThan(0);
			}
		}
		if (!StringUtils.isEmpty(healthinfo)) {
			if (healthinfo.indexOf("咳嗽") >= 0) {
				criteria.andIfkesouEqualTo("是");
			}
			if (healthinfo.indexOf("发热") >= 0) {
				criteria.andIfhotEqualTo("是");
			}
			healthinfo = healthinfo.replaceAll("咳嗽", "");
			healthinfo = healthinfo.replaceAll("发热", "");

			String[] infos = healthinfo.split(",");
			for (String s : infos) {
				if (s.length() > 1) {
					criteria.andHealthinfoLike("%" + s + "%");
				}
			}

		}
	}

	public int count() {
		WhuserExample example = new WhuserExample();
		return (int) userMapper.countByExample(example);
	}

	public int countUser(boolean ifToday, int type) {
		WhuserExample example = new WhuserExample();
		WhuserExample.Criteria criteria = example.createCriteria();
		if (ifToday) {
			LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
			criteria.andAddtimeGreaterThan(today_start);
		}
		if (type == 1) {
			criteria.andAddsourceEqualTo("自主");
		}
		if (type == 2) {
			criteria.andAddsourceEqualTo("社区");
		}
		return (int) userMapper.countByExample(example);
	}

	public void add(Whuser user) {
		user.setAddtime(LocalDateTime.now());
		userMapper.insertSelective(user);
	}

	public int updateById(Whuser user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	public int updateByIdWithNull(Whuser user) {
		return userMapper.updateByPrimaryKeySelectiveWithNull(user);
	}
	
	public void deleteOutSideUser(Integer uid, String remark) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("remark", remark);
		
		// 1、备份被删除数据到outsideuser_history_delete 
		exWhUserMapper.saveOutsideDeleteRecord(map);
		// 2、删除数据
		exWhUserMapper.deleteOutsideRecord(map);

	}

	public void deleteUser(Integer uid, String remark) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("remark", remark);
		
		// 1、备份被删除数据到whuser_history_delete
		exWhUserMapper.saveDeleteRecord(map);
		// 2、删除数据
		exWhUserMapper.deleteUserRecord(map);

	}
	
	public void updateUserIsSafe(String idCard) {
		exWhUserMapper.updateUserIsSafe(idCard);
	}
}
