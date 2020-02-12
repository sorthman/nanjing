package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.BaseNanjingMapper;
import org.linlinjava.litemall.db.dao.VisituserMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BaseNanjingService {

    private final BaseNanjing.Column[] resultarea = new BaseNanjing.Column[]{BaseNanjing.Column.area};
    private final BaseNanjing.Column[] resultstreet = new BaseNanjing.Column[]{BaseNanjing.Column.streets};
    private final BaseNanjing.Column[] resultcommunity = new BaseNanjing.Column[]{BaseNanjing.Column.communitys};

    @Resource
    private BaseNanjingMapper userMapper;

    public BaseNanjing findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public List<BaseNanjing> queryAreas() {
        BaseNanjingExample example = new BaseNanjingExample();
        BaseNanjingExample.Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        return userMapper.selectByExampleSelective(example, resultarea);
    }

    public List<BaseNanjing> queryStreet(String area) {
        BaseNanjingExample example = new BaseNanjingExample();
        BaseNanjingExample.Criteria criteria = example.createCriteria();
        criteria.andAreaEqualTo(area);
        example.setDistinct(true);
        return userMapper.selectByExampleSelective(example, resultstreet);
    }

    public List<BaseNanjing> queryCommunity(String street) {
        BaseNanjingExample example = new BaseNanjingExample();
        BaseNanjingExample.Criteria criteria = example.createCriteria();
        criteria.andStreetsEqualTo(street);
        example.setDistinct(true);
        return userMapper.selectByExampleSelective(example, resultcommunity);
    }

}
