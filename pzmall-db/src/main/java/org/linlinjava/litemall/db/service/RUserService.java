package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NjuserMapper;
import org.linlinjava.litemall.db.dao.RuserMapper;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.NjuserExample;
import org.linlinjava.litemall.db.domain.Ruser;
import org.linlinjava.litemall.db.domain.RuserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RUserService {
    @Resource
    private RuserMapper userMapper;

    public Ruser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Ruser findByIdcard(String idcard) {
        RuserExample example = new RuserExample();
        RuserExample.Criteria criteria = example.createCriteria();

        criteria.andA2EqualTo(idcard);

        return userMapper.selectOneByExample(example);
    }

    public List<Ruser> querySelective(String area,
                                       String name, String phone,
                                       String idcard,String addsource,
                                       Integer page, Integer size, String sort, String order) {
        RuserExample example = new RuserExample();
        RuserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(area)) {
            criteria.andA1Like("%" + area + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andA2Like("%" + name + "%");
        }
        if (!StringUtils.isEmpty(phone)) {
            criteria.andA3Like("%" + phone + "%");
        }
        if (!StringUtils.isEmpty(idcard)) {
            criteria.andA5Like("%" + idcard + "%");
        }
        if (!StringUtils.isEmpty(addsource)) {
            criteria.andA10Like("%" + addsource + "%");
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public void add(Ruser user) {
        userMapper.insertSelective(user);
    }

    public int updateById(Ruser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
