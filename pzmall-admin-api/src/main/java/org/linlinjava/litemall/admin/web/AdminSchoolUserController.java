package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.BaseNanjing;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.SchoolUser;
import org.linlinjava.litemall.db.service.BaseNanjingService;
import org.linlinjava.litemall.db.service.SchoolUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/adminapi/schooluser")
public class AdminSchoolUserController {
	private final Log logger = LogFactory.getLog(AdminSchoolUserController.class);

	@Autowired
	private SchoolUserService userService;

	@Autowired
	private BaseNanjingService baseNanjingService;

	@RequiresPermissions("admin:schooluser:list")
	@RequiresPermissionsDesc(menu = { "外来人员查询", "高校外来人员查询" }, button = "查询")
	@GetMapping("/list")
	public Object list(@RequestParam(defaultValue = "") String name,
					   @RequestParam(defaultValue = "") String phone,
					   @RequestParam(defaultValue = "") String sex,
					   @RequestParam(defaultValue = "0") Integer sage,
					   @RequestParam(defaultValue = "0") Integer eage,
					   @RequestParam(defaultValue = "") String idcard,

					   @RequestParam(defaultValue = "") String usertype,
					   @RequestParam(defaultValue = "") String school,
					   @RequestParam(defaultValue = "") String faculty,
					   @RequestParam(defaultValue = "") String department,
					   @RequestParam(defaultValue = "") String ifwh,
					   @RequestParam(defaultValue = "") String iftouch,

					   @RequestParam(defaultValue = "1") Integer page,
					   @RequestParam(defaultValue = "10") Integer limit,
					   @RequestParam(defaultValue = "id") String sort,
					   @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<SchoolUser> items = userService.querySelective(name,phone,sex,sage,eage,idcard,usertype,school,faculty,department,ifwh,iftouch,page, limit, sort, order);

		return ResponseUtil.okList(items);
	}

	@GetMapping("/listarea")
	public Object listArea() {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<BaseNanjing> items = baseNanjingService.queryAreas();

		return ResponseUtil.okList(items);
	}

	@GetMapping("/liststreet")
	public Object listStreet() {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<BaseNanjing> items = baseNanjingService.queryStreet(admin.getArea());

		return ResponseUtil.okList(items);
	}

	@GetMapping("/listcommunity")
	public Object listCommunity(String street) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		List<BaseNanjing> items = baseNanjingService.queryCommunity(street);

		return ResponseUtil.okList(items);
	}

}
