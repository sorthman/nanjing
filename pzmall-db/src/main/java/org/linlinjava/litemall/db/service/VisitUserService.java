package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.OutsideuserMapper;
import org.linlinjava.litemall.db.dao.VisituserMapper;
import org.linlinjava.litemall.db.domain.Outsideuser;
import org.linlinjava.litemall.db.domain.OutsideuserExample;
import org.linlinjava.litemall.db.domain.Visituser;
import org.linlinjava.litemall.db.domain.VisituserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VisitUserService {
    @Resource
    private VisituserMapper userMapper;

    public Visituser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Visituser findByIdcard(String idcard) {
        VisituserExample example = new VisituserExample();
        VisituserExample.Criteria criteria = example.createCriteria();

        criteria.andIdcardEqualTo(idcard);

        return userMapper.selectOneByExample(example);
    }

    public List<Visituser> querySelective(String name, String phone,
                                          Integer page, Integer size, String sort, String order) {
        VisituserExample example = new VisituserExample();
        VisituserExample.Criteria criteria = example.createCriteria();

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

    public List<Visituser> querySelective(
            String name,
            String phone,
            String sex,
            Integer sage,
            Integer eage,
            String idcard,
            String traffictype,
            String trafficinfo,
            String ifwh,
            String ifxq,
            String visitaddress,
            Integer visitcode,

            Integer page, Integer size, String sort, String order) {
        VisituserExample example = new VisituserExample();
        VisituserExample.Criteria criteria = example.createCriteria();

        if (sage != 0) {
            criteria.andAgeGreaterThanOrEqualTo(sage);
        }
        if (eage != 0) {
            criteria.andAgeLessThanOrEqualTo(eage);
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

        if (!StringUtils.isEmpty(traffictype)) {
            criteria.andTraffictypeLike("%" + traffictype + "%");
        }

        if (!StringUtils.isEmpty(trafficinfo)) {
            criteria.andTrafficinfoLike("%" + trafficinfo + "%");
        }
        if (!StringUtils.isEmpty(ifxq)) {
            criteria.andIfxqLike("%" + ifxq + "%");
        }
        if (!StringUtils.isEmpty(ifwh)) {
            criteria.andIfwhLike("%" + ifwh + "%");
        }
        if (!StringUtils.isEmpty(visitaddress)) {
            criteria.andVisitaddressLike("%" + visitaddress + "%");
        }
        if (visitcode != 0) {
            criteria.andVisitcodeEqualTo(visitcode);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }


    public void add(Visituser user) {
        user.setAddtime(LocalDateTime.now());
        user.setModifytime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }
}
