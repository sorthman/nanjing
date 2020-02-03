package org.linlinjava.litemall.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.CharUtil;
//import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAdmin;

import org.linlinjava.litemall.db.domain.Njaccount;
import org.linlinjava.litemall.db.service.NjAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
//import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.*;

@RestController
@RequestMapping("/adminapi/account")
public class AdminAccountController {
	private final Log logger = LogFactory.getLog(AdminAccountController.class);

	@Autowired
	private NjAccountService accountService;

	@RequiresPermissions("admin:account:list")
	@RequiresPermissionsDesc(menu = { "系统管理", "街道管理员管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(@RequestParam(defaultValue = "") String name,
					   @RequestParam(defaultValue = "") String username,
					   @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<Njaccount> items = accountService.querySelective(username, page, limit, sort, order);

		return ResponseUtil.okList(items);
	}


	@RequiresPermissions("admin:account:create")
	@RequiresPermissionsDesc(menu = { "系统管理", "街道管理员管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody Njaccount njaccount) {

		List<Njaccount> list = accountService.querySelective(njaccount.getUsername(),1,1,"id","desc");
		if (list.size() > 0) {
			return ResponseUtil.fail(ADMIN_NAME_EXIST, "已经存在该账户");
		}

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		njaccount.setArea(admin.getArea());
		njaccount.setLasttime(LocalDateTime.now());
		accountService.add(njaccount);
		return ResponseUtil.ok(njaccount);
	}

	@RequiresPermissions("admin:account:update")
	@RequiresPermissionsDesc(menu = { "系统管理", "街道管理员管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody Njaccount njaccount) {

		if (accountService.updateById(njaccount) == 0) {
			return ResponseUtil.updatedDataFailed();
		}

		return ResponseUtil.ok(njaccount);
	}

	@RequiresPermissions("admin:account:delete")
	@RequiresPermissionsDesc(menu = { "系统管理", "街道管理员管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody Njaccount merchant) {
		Integer mid = merchant.getId();
		if (mid == null) {
			return ResponseUtil.badArgument();
		}

		accountService.deleteById(mid);
		return ResponseUtil.ok();
	}

}
