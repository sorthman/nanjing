package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NjaccountMapper;
import org.linlinjava.litemall.db.dao.NjaccountMapper;
import org.linlinjava.litemall.db.domain.Njaccount;
import org.linlinjava.litemall.db.domain.NjaccountExample;
import org.linlinjava.litemall.db.domain.Njaccount;
import org.linlinjava.litemall.db.domain.NjaccountExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class NjAccountService {
    @Resource
    private NjaccountMapper njaccountMapper;

    public Njaccount findById(Integer id) {
        return njaccountMapper.selectByPrimaryKey(id);
    }

    public List<Njaccount> querySelective(String username, Integer page, Integer limit, String sort,
                                          String order) {
        NjaccountExample example = new NjaccountExample();
        NjaccountExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return njaccountMapper.selectByExample(example);
    }

    public int countSelective(Integer page, Integer size, String sort, String order) {
        NjaccountExample example = new NjaccountExample();
        NjaccountExample.Criteria criteria = example.createCriteria();

        return (int) njaccountMapper.countByExample(example);
    }

    public void add(Njaccount merchant) {
        njaccountMapper.insertSelective(merchant);
    }

    public int updateById(Njaccount merchant) {
        return njaccountMapper.updateByPrimaryKeySelective(merchant);
    }

    public void deleteById(Integer id) {
        njaccountMapper.deleteByPrimaryKey(id);
    }

}
