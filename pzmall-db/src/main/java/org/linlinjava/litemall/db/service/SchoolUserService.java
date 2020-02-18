package org.linlinjava.litemall.db.service;

import java.util.List;

import javax.annotation.Resource;

import org.linlinjava.litemall.db.dao.SchoolUserMapper;
import org.linlinjava.litemall.db.domain.SchoolUser;
import org.linlinjava.litemall.db.domain.SchoolUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;

@Service
public class SchoolUserService {

	@Resource
	private SchoolUserMapper userMapper;

	public List<SchoolUser> querySelective(String name, String phone, String sex, Integer sage, Integer eage,
			String idcard, String usertype, String school, String faculty, String department, String ifwh,
			String iftouch, Integer page, Integer limit, String sort, String order) {
        SchoolUserExample example = new SchoolUserExample();
        SchoolUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        
        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }

        if (!StringUtils.isEmpty(sex)) {
            criteria.andSexEqualTo(sex);
        }
        
        if (sage != 0) {
            criteria.andAgeGreaterThanOrEqualTo(sage);
        }
        
        if (eage != 0) {
            criteria.andAgeLessThanOrEqualTo(eage);
        }
        
        if (!StringUtils.isEmpty(idcard)) {
            criteria.andIdcardLike("%" + idcard + "%");
        }

        if (!StringUtils.isEmpty(usertype)) {
            criteria.andUsertypeEqualTo(usertype);
        }

        if (!StringUtils.isEmpty(school)) {
            criteria.andSchoolNotLike("%" + school + "%");
        }

        if (!StringUtils.isEmpty(faculty)) {
            criteria.andFacultyEqualTo(faculty);
        }

        if (!StringUtils.isEmpty(department)) {
        	criteria.andDepartmentEqualTo(department);
        }

        if (!StringUtils.isEmpty(ifwh)) {
            criteria.andIfwhEqualTo(ifwh);
        }
        
        if (!StringUtils.isEmpty(iftouch)) {
            criteria.andIftouchEqualTo(iftouch);
        }
        

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return userMapper.selectByExample(example);
    }
	
	
}
