package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.VisitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.linlinjava.litemall.core.util.RegexUtil;
//import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/adminapi/ncp")
public class AdminNCPController {
    private final Log logger = LogFactory.getLog(AdminNCPController.class);

	@RequiresPermissions("adminapi:ncp:index")
	@RequiresPermissionsDesc(menu = { "NCP分析管控平台", "NCP分析管控平台" }, button = "查看")
	@GetMapping("/index")
	public Object index() {
        return ResponseUtil.ok("success");
	}

}
