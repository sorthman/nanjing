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
import org.linlinjava.litemall.db.domain.Njuser;
import org.linlinjava.litemall.db.domain.Outsideuser;
import org.linlinjava.litemall.db.service.BaseNanjingService;
import org.linlinjava.litemall.db.service.NjUserService;
import org.linlinjava.litemall.db.service.OutsideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.linlinjava.litemall.core.util.RegexUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/adminapi/outsideuser")
public class AdminOutsideUserController {
	private final Log logger = LogFactory.getLog(AdminOutsideUserController.class);

	@Autowired
	private OutsideUserService userService;

	@Autowired
	private BaseNanjingService baseNanjingService;

	@RequiresPermissions("admin:outsideuser:list")
	@RequiresPermissionsDesc(menu = { "外来人员查询", "外来人员查询" }, button = "查询")
	@GetMapping("/list")
	public Object list(@RequestParam(defaultValue = "") String name,
					   @RequestParam(defaultValue = "") String phone,
					   @RequestParam(defaultValue = "") String sex,
					   @RequestParam(defaultValue = "0") Integer sage,
					   @RequestParam(defaultValue = "0") Integer eage,
					   @RequestParam(defaultValue = "") String idcard,

					   @RequestParam(defaultValue = "") String community,
					   @RequestParam(defaultValue = "") String arrivedate,
					   @RequestParam(defaultValue = "") String province,
					   @RequestParam(defaultValue = "") String city,
					   @RequestParam(defaultValue = "") String area,
					   @RequestParam(defaultValue = "") String ifwh,
					   @RequestParam(defaultValue = "") String addtime,
					   @RequestParam(defaultValue = "") String street,

					   @RequestParam(defaultValue = "1") Integer page,
					   @RequestParam(defaultValue = "10") Integer limit,
					   @RequestParam(defaultValue = "id") String sort,
					   @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<Outsideuser> items = userService.querySelective(name,phone,sex,sage,eage,idcard,community,arrivedate,province,city,area,ifwh,street,addtime,page, limit, sort, order);

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
