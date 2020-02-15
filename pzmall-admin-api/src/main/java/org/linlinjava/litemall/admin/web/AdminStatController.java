package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.StatVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminapi/stat")
@Validated
public class AdminStatController {
    private final Log logger = LogFactory.getLog(AdminStatController.class);

    @Autowired
    private StatService statService;

    @RequiresPermissions("adminapi:stat:whstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "武汉统计报表"}, button = "查询")
    @GetMapping("/whstat")
    public Object whstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:hbstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "湖北统计(不含武汉)报表"}, button = "查询")
    @GetMapping("/hbstat")
    public Object hbstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:hballstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "湖北统计报表"}, button = "查询")
    @GetMapping("/hballstat")
    public Object hballstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:wzstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "温州统计报表"}, button = "查询")
    @GetMapping("/wzstat")
    public Object wzstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:ahstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "安徽统计报表"}, button = "查询")
    @GetMapping("/ahstat")
    public Object ahstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:hnstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "河南统计报表"}, button = "查询")
    @GetMapping("/hnstat")
    public Object hnstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:jsstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "江苏统计报表"}, button = "查询")
    @GetMapping("/jsstat")
    public Object jsstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:otherstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "其他地区统计报表"}, button = "查询")
    @GetMapping("/otherstat")
    public Object otherstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:nocontactstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "无接触统计报表"}, button = "查询")
    @GetMapping("/nocontactstat")
    public Object nocontactstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:sumstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "汇总统计报表"}, button = "查询")
    @GetMapping("/sumstat")
    public Object sumstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("adminapi:stat:wlstat")
    @RequiresPermissionsDesc(menu = {"统计管理", "外来人员统计"}, button = "查询")
    @GetMapping("/wlstat")
    public Object wlstat() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

}
