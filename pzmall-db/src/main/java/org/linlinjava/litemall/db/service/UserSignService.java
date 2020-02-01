package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.UsersignMapper;
import org.linlinjava.litemall.db.dao.WhuserMapper;
import org.linlinjava.litemall.db.domain.Usersign;
import org.linlinjava.litemall.db.domain.UsersignExample;
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
public class UserSignService {
    @Resource
    private UsersignMapper usersignMapper;

    public Usersign findById(Integer userId) {
        return usersignMapper.selectByPrimaryKey(userId);
    }

    public List<Usersign> querySelective(Integer uid, Integer page, Integer size, String sort, String order) {
        UsersignExample example = new UsersignExample();
        UsersignExample.Criteria criteria = example.createCriteria();

        if (uid!=0) {
            criteria.andUidEqualTo(uid);
        }


        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return usersignMapper.selectByExample(example);
    }

    public int count() {
        UsersignExample example = new UsersignExample();
        return (int) usersignMapper.countByExample(example);
    }

}
