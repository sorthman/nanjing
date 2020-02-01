package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.WhuserMapper;
import org.linlinjava.litemall.db.domain.Whuser;
import org.linlinjava.litemall.db.domain.WhuserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LitemallUserService {
    @Resource
    private WhuserMapper userMapper;

    public Whuser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public List<Whuser> querySelective(String area,  String username, String mobile, Integer page, Integer size, String sort, String order) {
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

}
