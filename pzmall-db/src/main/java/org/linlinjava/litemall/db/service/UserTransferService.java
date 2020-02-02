package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.UsersignMapper;
import org.linlinjava.litemall.db.dao.UsertransterMapper;
import org.linlinjava.litemall.db.domain.Usersign;
import org.linlinjava.litemall.db.domain.UsersignExample;
import org.linlinjava.litemall.db.domain.Usertranster;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserTransferService {
    @Resource
    private UsertransterMapper usertransterMapper;

    public Usertranster findById(Integer userId) {
        return usertransterMapper.selectByPrimaryKey(userId);
    }



}
