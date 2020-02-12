package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.Outsideuser;
import org.linlinjava.litemall.db.domain.Visituser;
import org.linlinjava.litemall.db.service.OutsideUserService;
import org.linlinjava.litemall.db.service.VisitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import org.linlinjava.litemall.core.util.RegexUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/adminapi/visituser")
public class AdminVisitUserController {
    private final Log logger = LogFactory.getLog(AdminVisitUserController.class);

    @Autowired
    private VisitUserService userService;

    @RequiresPermissions("admin:visituser:list")
    @RequiresPermissionsDesc(menu = {"外来人员查询", "小区外来人员查询"}, button = "查询")
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String phone,
                       @RequestParam(defaultValue = "") String sex,
                       @RequestParam(defaultValue = "0") Integer sage,
                       @RequestParam(defaultValue = "0") Integer eage,
                       @RequestParam(defaultValue = "") String idcard,
                       @RequestParam(defaultValue = "") String traffictype,
                       @RequestParam(defaultValue = "") String trafficinfo,
                       @RequestParam(defaultValue = "") String ifwh,
                       @RequestParam(defaultValue = "") String ifxq,
                       @RequestParam(defaultValue = "") String visitaddress,
                       @RequestParam(defaultValue = "0") Integer visitcode,

                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "id") String sort,
                       @RequestParam(defaultValue = "desc") String order) {

        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

        List<Visituser> items = userService.querySelective(name, phone, sex, sage, eage, idcard, traffictype, trafficinfo, ifwh, ifxq, visitaddress, visitcode, page, limit, sort, order);

        return ResponseUtil.okList(items);
    }

//	@RequiresPermissions("admin:njuser:update")
//	@RequiresPermissionsDesc(menu = { "市申报管理", "外来人员查询" }, button = "编辑")
//	@PostMapping("/update")
//	public Object update(@RequestBody Njuser njuser) {
//
//		if (userService.updateById(njuser) == 0) {
//			return ResponseUtil.updatedDataFailed();
//		}
//
//		return ResponseUtil.ok(njuser);
//	}

}
