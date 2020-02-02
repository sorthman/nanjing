package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.UsercheckMapper;
import org.linlinjava.litemall.db.dao.UsersignMapper;
import org.linlinjava.litemall.db.domain.Usercheck;
import org.linlinjava.litemall.db.domain.UsercheckExample;
import org.linlinjava.litemall.db.domain.Usersign;
import org.linlinjava.litemall.db.domain.UsersignExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserCheckService {
    @Resource
    private UsercheckMapper usercheckMapper;

    public Usercheck findById(Integer userId) {
        return usercheckMapper.selectByPrimaryKey(userId);
    }

    public List<Usercheck> querySelective(Integer uid, Integer page, Integer size, String sort, String order) {
        UsercheckExample example = new UsercheckExample();
        UsercheckExample.Criteria criteria = example.createCriteria();

        if (uid != 0) {
            criteria.andUidEqualTo(uid);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return usercheckMapper.selectByExample(example);
    }

    public int count() {
        UsercheckExample example = new UsercheckExample();
        return (int) usercheckMapper.countByExample(example);
    }

}
