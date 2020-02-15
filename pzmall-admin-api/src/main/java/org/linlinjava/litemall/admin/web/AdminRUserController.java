package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.Ruser;
import org.linlinjava.litemall.db.service.NjUserService;
import org.linlinjava.litemall.db.service.RUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.linlinjava.litemall.core.util.RegexUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/adminapi/njuser")
public class AdminRUserController {
	private final Log logger = LogFactory.getLog(AdminRUserController.class);

	@Autowired
	private RUserService userService;

	@RequiresPermissions("admin:njuser:list")
	@RequiresPermissionsDesc(menu = { "市申报管理", "用户查询" }, button = "查询")
	@GetMapping("/list")
	public Object list(@RequestParam(defaultValue = "") String area,
					   @RequestParam(defaultValue = "") String name,
					   @RequestParam(defaultValue = "") String idcard,
					   @RequestParam(defaultValue = "") String phone,
					   @RequestParam(defaultValue = "") String addsource,
					   @RequestParam(defaultValue = "1") Integer page,
					   @RequestParam(defaultValue = "10") Integer limit,
					   @RequestParam(defaultValue = "addtime") String sort,
					   @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<Ruser> items = userService.querySelective(area, name,phone,idcard,addsource,page, limit, sort, order);

		return ResponseUtil.okList(items);
	}

	@RequiresPermissions("admin:njuser:update")
	@RequiresPermissionsDesc(menu = { "市申报管理", "用户查询" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody Ruser ruser) {

		if (userService.updateById(ruser) == 0) {
			return ResponseUtil.updatedDataFailed();
		}

		return ResponseUtil.ok(ruser);
	}

}
