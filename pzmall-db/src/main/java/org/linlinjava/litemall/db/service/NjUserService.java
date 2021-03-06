package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NjuserMapper;
import org.linlinjava.litemall.db.dao.WhuserMapper;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.NjuserExample;
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
public class NjUserService {
    @Resource
    private NjuserMapper userMapper;

    public Njuser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Njuser findByIdcard(String idcard, LocalDateTime reportdate) {
        NjuserExample example = new NjuserExample();
        NjuserExample.Criteria criteria = example.createCriteria();

        criteria.andIdcardEqualTo(idcard);
        criteria.andReportdateEqualTo(reportdate);

        return userMapper.selectOneByExample(example);
    }

    public List<Njuser> querySelective(String area,
                                       String name, String phone,
                                       Integer page, Integer size, String sort, String order) {
        NjuserExample example = new NjuserExample();
        NjuserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(area)) {
            criteria.andAreaEqualTo(area);
        }

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public List<Njuser> querySelective(String area,
                                       String name,
                                       String phone,
                                       String sex,
                                       Integer sage,
                                       Integer eage,
                                       String idcard,
                                       String street,
                                       String community,
                                       String zdyq,
                                       String glyy,
                                       String starttime,
                                       String stoptime,
                                       String endtime,
                                       String stopinfo,


                                       Integer page, Integer size, String sort, String order) {
        NjuserExample example = new NjuserExample();
        NjuserExample.Criteria criteria = example.createCriteria();

        if (sage != 0) {
            criteria.andAgeGreaterThanOrEqualTo(sage);
        }
        if (eage != 0) {
            criteria.andAgeLessThanOrEqualTo(eage);
        }

        if (!StringUtils.isEmpty(area)) {
            criteria.andAreaEqualTo(area);
        }

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
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
            criteria.andCommunityLike("%" + community + "%");
        }
        if (!StringUtils.isEmpty(zdyq)) {
            criteria.andZdyqinfoEqualTo(zdyq);
        }
        if (!StringUtils.isEmpty(glyy)) {
            criteria.andGlinfoEqualTo(sex);
        }
        if (!StringUtils.isEmpty(stopinfo)) {
            criteria.andStopinfoEqualTo(stopinfo);
        }

        if (!StringUtils.isEmpty(starttime)) {
            starttime = starttime.replace(" ", "T");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(starttime);
            criteria.andStarttimeEqualTo(date);
        }

        if (!StringUtils.isEmpty(stoptime)) {
            stoptime = stoptime.replace(" ", "T");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(stoptime);
            criteria.andStoptimeEqualTo(date);
        }

        if (!StringUtils.isEmpty(endtime)) {
            endtime = endtime.replace(" ", "T");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(endtime);
            criteria.andEndtimeEqualTo(date);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }


    public void add(Njuser user) {
        user.setAddtime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(Njuser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
