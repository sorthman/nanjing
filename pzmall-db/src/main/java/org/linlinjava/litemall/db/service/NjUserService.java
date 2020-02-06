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

    public Njuser findByIdcard(String idcard) {
        NjuserExample example = new NjuserExample();
        example.or().andIdcardEqualTo(idcard);
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


    public void add(Njuser user) {
        user.setAddtime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(Njuser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
