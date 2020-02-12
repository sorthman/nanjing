package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NjuserMapper;
import org.linlinjava.litemall.db.dao.OutsideuserMapper;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.NjuserExample;
import org.linlinjava.litemall.db.domain.Outsideuser;
import org.linlinjava.litemall.db.domain.OutsideuserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OutsideUserService {
    @Resource
    private OutsideuserMapper userMapper;

    public Outsideuser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Outsideuser findByIdcard(String idcard) {
        OutsideuserExample example = new OutsideuserExample();
        OutsideuserExample.Criteria criteria = example.createCriteria();

        criteria.andIdcardEqualTo(idcard);

        return userMapper.selectOneByExample(example);
    }

    public List<Outsideuser> querySelective(String area,
                                       String name, String phone,
                                       Integer page, Integer size, String sort, String order) {
        OutsideuserExample example = new OutsideuserExample();
        OutsideuserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(area)) {
            criteria.andDistrictEqualTo(area);
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

    public List<Outsideuser> querySelective(
                                       String name,
                                       String phone,
                                       String sex,
                                       Integer sage,
                                       Integer eage,
                                       String idcard,
                                       String community,
                                       String arrivedate,
                                       String province,
                                       String city,
                                       String area,
                                       String ifwh,
                                       String street,
                                       String addtime,

                                       Integer page, Integer size, String sort, String order) {
        OutsideuserExample example = new OutsideuserExample();
        OutsideuserExample.Criteria criteria = example.createCriteria();

        if (sage != 0) {
            criteria.andAgeGreaterThanOrEqualTo(sage);
        }
        if (eage != 0) {
            criteria.andAgeLessThanOrEqualTo(eage);
        }

        if (!StringUtils.isEmpty(area)) {
            criteria.andDistrictLike("%" + area + "%");
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

        if (!StringUtils.isEmpty(community)) {
            criteria.andCommunityLike("%" + community + "%");
        }

        if (!StringUtils.isEmpty(street)) {
            criteria.andStreetLike("%" + street + "%");
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
            criteria.andAddtimeBetween(date,dateend);
        }

        if (!StringUtils.isEmpty(province)) {
            criteria.andProvinceLike("%" + province + "%");
        }
        if (!StringUtils.isEmpty(city)) {
            criteria.andCityLike("%" + city + "%");
        }
        if (!StringUtils.isEmpty(ifwh)) {
            criteria.andIfwhLike("%" + ifwh + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }


    public void add(Outsideuser user) {
        user.setAddtime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(Outsideuser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
