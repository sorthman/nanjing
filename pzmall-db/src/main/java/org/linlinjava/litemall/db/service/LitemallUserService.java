package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.WhuserMapper;
import org.linlinjava.litemall.db.domain.LitemallRole;
import org.linlinjava.litemall.db.domain.Whuser;
import org.linlinjava.litemall.db.domain.WhuserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LitemallUserService {
    @Resource
    private WhuserMapper userMapper;

    public Whuser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public List<Whuser> querySelective(String area,
                                       String username, String mobile,
                                       Integer page, Integer size, String sort, String order) {
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

    public List<Whuser> querySelective(String area,
                                       String username, String mobile,
                                       String sex,
                                       Integer sage,
                                       Integer eage,
                                       String idcard,
                                       String street,
                                       String community,
                                       String arrivedate,
                                       String addsource,
                                       String iftransferarea,
                                       String iftransferstreet,
                                       String ifsafe,
                                       String healthinfo,
                                       String ifwh,
                                       String ifhb,
                                       String ifleavenj,
                                       String ifadmin,
                                       String ifover,
                                       String iflose,
                                       Integer page, Integer size, String sort, String order) {
        WhuserExample example = new WhuserExample();
        WhuserExample.Criteria criteria = example.createCriteria();

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
            criteria.andStreetLike("%" + street + "%");
        }
        if (!StringUtils.isEmpty(community)) {
            criteria.andNjcommunityLike("%" + community + "%");
        }
        if (!StringUtils.isEmpty(arrivedate)) {
            arrivedate = arrivedate.replace(" ", "T");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(arrivedate);
            criteria.andArrivedateEqualTo(date);
        }
        if (!StringUtils.isEmpty(addsource)) {
            criteria.andAddsourceLike("%" + addsource + "%");
        }
        if (!StringUtils.isEmpty(iftransferarea)) {
            criteria.andIftransferareaEqualTo(iftransferarea);
        }
        if (!StringUtils.isEmpty(iftransferstreet)) {
            criteria.andIftransferstreetEqualTo(iftransferstreet);
        }
        if (!StringUtils.isEmpty(ifsafe)) {
            criteria.andIfsafeEqualTo(ifsafe);
        }
        if (!StringUtils.isEmpty(ifhb)) {
            criteria.andIfhbEqualTo(ifhb);
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
        if (!StringUtils.isEmpty(ifadmin)) {
            criteria.andIsmanageEqualTo(ifadmin);
        }
        if (!StringUtils.isEmpty(ifover)) {
            if (ifover == "是") {
                LocalDateTime sTime = LocalDateTime.now().minusDays(14);
                criteria.andArrivedateLessThan(sTime);
            } else {
                LocalDateTime sTime = LocalDateTime.now().minusDays(14);
                criteria.andArrivedateGreaterThan(sTime);
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

            String[] infos = healthinfo.split("、");
            for (String s : infos) {
                if (s.length() > 1) {
                    criteria.andHealthinfoLike("%" + s + "%");
                }
            }
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
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

    public int updateById(Whuser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
