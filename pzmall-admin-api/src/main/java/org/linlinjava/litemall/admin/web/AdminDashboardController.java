package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/adminapi/dashboard")
@Validated
public class AdminDashboardController {
    private final Log logger = LogFactory.getLog(AdminDashboardController.class);

    @Autowired
    private LitemallUserService userService;

    @RequiresPermissions("admin:dashboard:index")
    @RequiresPermissionsDesc(menu = { "疫情管控", "疫情管控" }, button = "查询")
    @GetMapping("index")
    public Object index() {
        int totalUser = userService.count();
        int todayUser = userService.countUser(true,0);
        int totalSelf = userService.countUser(false, 1);
        int totalComm = userService.countUser(false,2);

        Map<String, Object> data = new HashMap<>();

        data.put("todayUser", todayUser);
        data.put("totalSelf", totalSelf);
        data.put("totalComm", totalComm);
        data.put("totalUser", totalUser);

        return ResponseUtil.ok(data);
    }

}
