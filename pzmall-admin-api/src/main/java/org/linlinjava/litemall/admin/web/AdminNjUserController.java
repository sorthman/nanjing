package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.Njaccount;
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.service.NjAccountService;
import org.linlinjava.litemall.db.service.NjUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.ADMIN_NAME_EXIST;

//import org.linlinjava.litemall.core.util.RegexUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/adminapi/njuser")
public class AdminNjUserController {
	private final Log logger = LogFactory.getLog(AdminNjUserController.class);

	@Autowired
	private NjUserService userService;

	@RequiresPermissions("admin:njuser:list")
	@RequiresPermissionsDesc(menu = { "市申报管理", "用户查询" }, button = "查询")
	@GetMapping("/list")
	public Object list(@RequestParam(defaultValue = "") String name,
					   @RequestParam(defaultValue = "") String phone,
					   @RequestParam(defaultValue = "") String sex,
					   @RequestParam(defaultValue = "0") Integer sage,
					   @RequestParam(defaultValue = "0") Integer eage,
					   @RequestParam(defaultValue = "") String idcard,
					   @RequestParam(defaultValue = "") String street,
					   @RequestParam(defaultValue = "") String community,

					   @RequestParam(defaultValue = "") String zdyq,
					   @RequestParam(defaultValue = "") String glyy,
					   @RequestParam(defaultValue = "") String starttime,
					   @RequestParam(defaultValue = "") String stoptime,
					   @RequestParam(defaultValue = "") String endtime,
					   @RequestParam(defaultValue = "") String stopinfo,

					   @RequestParam(defaultValue = "1") Integer page,
					   @RequestParam(defaultValue = "10") Integer limit,
					   @RequestParam(defaultValue = "addtime") String sort,
					   @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<Njuser> items = userService.querySelective(admin.getArea(), name,phone,sex,sage,eage,idcard,street,community,zdyq,glyy
				,starttime,stoptime,endtime,stopinfo,page, limit, sort, order);

		return ResponseUtil.okList(items);
	}

	@RequiresPermissions("admin:njuser:update")
	@RequiresPermissionsDesc(menu = { "市申报管理", "用户查询" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody Njuser njuser) {

		if (userService.updateById(njuser) == 0) {
			return ResponseUtil.updatedDataFailed();
		}

		return ResponseUtil.ok(njuser);
	}

}
