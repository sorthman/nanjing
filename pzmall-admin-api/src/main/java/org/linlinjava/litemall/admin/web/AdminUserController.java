package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.admin.vo.KnowledgeVo;
import org.linlinjava.litemall.admin.vo.userVo;
import org.linlinjava.litemall.core.util.ExcelUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
//import sun.rmi.runtime.Log;
import org.springframework.beans.BeanUtils;

@RestController
@RequestMapping("/adminapi/user")
@Validated
public class AdminUserController {
	private final Log logger = LogFactory.getLog(AdminUserController.class);

	@Autowired
	private LitemallUserService userService;

	@Autowired
	private UserCheckService userCheckService;

	@Autowired
	private NjUserService njUserService;

	@Autowired
	private RUserService rUserService;

	@Autowired
	private UserTransferService userTransferService;

	@Autowired
	private LogHelper logHelper;

	@RequiresPermissions("adminapi:user:list")
	@RequiresPermissionsDesc(menu = { "申报管理", "用户查询" }, button = "查询")
	@GetMapping("/list")
	public Object list(String username, String phone, @RequestParam(defaultValue = "") String sex,
			@RequestParam(defaultValue = "0") Integer sage, @RequestParam(defaultValue = "0") Integer eage,
			@RequestParam(defaultValue = "") String idcard, @RequestParam(defaultValue = "") String street,
			@RequestParam(defaultValue = "") String community, @RequestParam(defaultValue = "") String arrivedate,
			@RequestParam(defaultValue = "") String[] addsource, @RequestParam(defaultValue = "") String iftransferarea,
			@RequestParam(defaultValue = "") String iftransferstreet, @RequestParam(defaultValue = "") String[] ifsafe,
			@RequestParam(defaultValue = "") String healthinfo, @RequestParam(defaultValue = "") String[] usertype,
			@RequestParam(defaultValue = "") String ifwh, @RequestParam(defaultValue = "") String ifhb,
			@RequestParam(defaultValue = "") String ifleavenj, @RequestParam(defaultValue = "") String ifadmin,
			@RequestParam(defaultValue = "") String ifover, @RequestParam(defaultValue = "") String iflose,
			@RequestParam(defaultValue = "") String ifstay, @RequestParam(defaultValue = "") String addtime,
			@RequestParam(defaultValue = "") String managetime, @RequestParam(defaultValue = "") String level,

			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "addtime") String sort, @RequestParam(defaultValue = "desc") String order) {

		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		List<Whuser> userList = userService.querySelective(admin.getArea(), username, phone, sex, sage, eage, idcard,
				street, community, arrivedate, addsource, iftransferarea, iftransferstreet, ifsafe, healthinfo,
				usertype, ifwh, ifhb, ifleavenj, ifadmin, ifover, iflose, ifstay, addtime, managetime, level, page,
				limit, sort, order);
		List<userVo> users = new ArrayList<>();
		for (Whuser user : userList) {
			userVo u = new userVo();
			BeanUtils.copyProperties(user, u);
			users.add(u);
//            LocalDateTime sTime = LocalDateTime.now().minusDays(15);
			if (user.getLefttime() != null && user.getManagetime() != null) {
				if (user.getLefttime() <= 0) {
					u.setIfover("是");
				} else {
					u.setIfover("否");
				}
			}

//            String strMobile = user.getPhone().toString();
//            strMobile = strMobile.replace(strMobile.substring(3, 9), "******");
//            user.setPhone(Long.parseLong(strMobile));
		}
		return ResponseUtil.okList(users, userList);
	}

	@RequiresPermissions("adminapi:user:listsign")
	@RequiresPermissionsDesc(menu = { "申报管理", "用户查询" }, button = "详情")
	@GetMapping("/listsign")
	public Object listsign(Integer uid, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "addtime") String sort,
			@RequestParam(defaultValue = "desc") String order) {
		List<Usercheck> signList = userCheckService.querySelective(uid, page, limit, sort, order);
		return ResponseUtil.ok(signList);
	}

	@RequiresPermissions("adminapi:user:create")
	@RequiresPermissionsDesc(menu = { "申报管理", "用户查询" }, button = "新增用户")
	@PostMapping("/create")
	public Object create(@RequestBody Whuser newuser) {
		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

		if (userService.findByIdcard(newuser.getIdcard()) != null) {
			return ResponseUtil.fail(-2, "该身份证号用户已经存在");
		}

		// 自主用户设置了管理时间
		if (newuser.getManagetime() != null) {
			newuser.setLefttimemodify(LocalDateTime.now());
			newuser.setEndsigntime(newuser.getManagetime().plusDays(14).minusMinutes(1));
			LocalDateTime endTime = newuser.getManagetime().plusDays(14);
			LocalDateTime nowTime = LocalDateTime.now();
			Duration duration = Duration.between(nowTime, endTime);
			newuser.setLefttime((int) duration.toDays());
		}

		newuser.setAddtime(LocalDateTime.now());
		newuser.setModifytime(LocalDateTime.now());
//        newuser.setAddsource("自主");
		newuser.setArea(admin.getArea());
		newuser.setAddaccount(admin.getId());

		userService.add(newuser);
		return ResponseUtil.ok(newuser);
	}

	@RequiresPermissions("adminapi:user:update")
	@RequiresPermissionsDesc(menu = { "申报管理", "用户查询" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody Whuser user) {

		Whuser suser = userService.findById(user.getId());

		// 如果设置到宁时间或者管理时间
		if (user.getAddsource().equals("省疾控")) {
			if (user.getManagetime() != null) {
				user.setLefttimemodify(LocalDateTime.now());
				user.setEndsigntime(user.getManagetime().plusDays(14).minusMinutes(1));
				LocalDateTime endTime = user.getManagetime().plusDays(14);
				LocalDateTime nowTime = LocalDateTime.now();
				Duration duration = Duration.between(nowTime, endTime);
				user.setLefttime((int) duration.toDays());
			}
		} else {
			if (user.getManagetime() != null) {
				user.setLefttimemodify(LocalDateTime.now());
				user.setEndsigntime(user.getManagetime().plusDays(14).minusMinutes(1));
				LocalDateTime endTime = user.getManagetime().plusDays(14);
				LocalDateTime nowTime = LocalDateTime.now();
				Duration duration = Duration.between(nowTime, endTime);
				user.setLefttime((int) duration.toDays());
			} else if (user.getArrivedate() != null) {
				user.setLefttimemodify(LocalDateTime.now());
				user.setManagetime(user.getArrivedate().plusDays(1));
				user.setEndsigntime(user.getArrivedate().plusDays(15).minusMinutes(1));
				LocalDateTime endTime = user.getArrivedate().plusDays(15);
				LocalDateTime nowTime = LocalDateTime.now();
				Duration duration = Duration.between(nowTime, endTime);
				user.setLefttime((int) duration.toDays());
			}
		}

		// 如果到宁时间设置为null
		if (user.getArrivedate() == null) {
			if (suser.getAddsource().equals("省疾控") && suser.getLevel().equals("红色")) {

			}
		}

		// 如果managetime 不为空则 ismanage 设置是
		if (user.getManagetime() != null) {
			user.setIsmanage("是");
		}

		user.setModifytime(LocalDateTime.now());

		if (userService.updateById(user) == 0) {
			return ResponseUtil.updatedDataFailed();
		}

		return ResponseUtil.ok(user);
	}

	@RequiresPermissions("adminapi:user:upload")
	@RequiresPermissionsDesc(menu = { "申报管理", "数据导入" }, button = "导入")
	@GetMapping("/upload")
	public Object upload(@RequestParam(defaultValue = "") String filename,
			@RequestParam(defaultValue = "") String addsource) {
		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		List<Whuser> users = new ArrayList<Whuser>();
		if (!StringUtils.isEmpty(filename)) {
			String rootPath = System.getProperty("user.dir");
			String filePath = rootPath + "/storage/" + filename;
			users = ExcelUtil.getList(filePath);

			if (users == null) {
				return ResponseUtil.fail(-2, "请上传正确的表格");
			}
		} else {
			return ResponseUtil.fail(-2, "请上传表格");
		}

		// 插入用户表
		int count = 0;
		int totalCount = users.size();
		String failUser = "";
		for (Whuser user : users) {
			Whuser suser = null;
			if (addsource.equals("公安") || addsource.equals("企业复工")) {
				suser = userService.findByIdcard(user.getIdcard());
			} else {
				suser = userService.findByPhone(user.getPhone());
			}
			if (suser == null) {
				if (user.getHealthinfo() != null) {
					if (user.getHealthinfo().indexOf("发热") >= 0) {
						user.setIfhot("是");
					}
					if (user.getHealthinfo().indexOf("咳嗽") >= 0) {
						user.setIfkesou("是");
					}
				}

				user.setSigncount(0);
				String strTime = "1970-01-01 00:00:00";
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime staTime = LocalDateTime.parse(strTime, df);
				user.setLastsigntime(staTime);
				user.setModifytime(LocalDateTime.now());

				if (user.getArrivedate() != null) {
					user.setManagetime(user.getArrivedate().plusDays(1));
					user.setLefttimemodify(LocalDateTime.now());
					user.setEndsigntime(user.getArrivedate().plusDays(15).minusMinutes(1));
					LocalDateTime endTime = user.getArrivedate().plusDays(15);
					LocalDateTime nowTime = LocalDateTime.now();
					Duration duration = Duration.between(nowTime, endTime);
					user.setLefttime((int) duration.toDays());
				}

				user.setArea(admin.getArea());
				user.setAddsource((addsource));
				if (!StringUtils.isEmpty(user.getName())) {
					userService.add(user);
				}
				count++;
			} else {
				failUser += "重复用户：" + suser.getName() + "/" + suser.getIdcard() + "<br/>";
			}
		}

		logHelper.logAdmin(3, "数据导入", true, "总计：" + totalCount + "条,导入成功" + count + "条", filename);

		return ResponseUtil.ok("总计：" + totalCount + "条,导入成功" + count + "条" + "<br/>" + failUser);
	}

	@RequiresPermissions("adminapi:user:uploadsjk")
	@RequiresPermissionsDesc(menu = { "申报管理", "数据导入" }, button = "导入省疾控")
	@GetMapping("/uploadsjk")
	public Object uploadsjk(@RequestParam(defaultValue = "") String filename,
			@RequestParam(defaultValue = "") String addsource) {
		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		List<Whuser> users = new ArrayList<Whuser>();
		if (!StringUtils.isEmpty(filename)) {
			String rootPath = System.getProperty("user.dir");
			String filePath = rootPath + "/storage/" + filename;
			users = ExcelUtil.getSJKList(filePath);

			if (users == null) {
				return ResponseUtil.fail(-2, "请上传正确的表格");
			}
		} else {
			return ResponseUtil.fail(-2, "请上传表格");
		}

		// 插入用户表
		int count = 0;
		int totalCount = users.size();
		String failUser = "";
		for (Whuser user : users) {

			Whuser suser = userService.findByPhone(user.getPhone());
//            if (suser == null || !addsource.equals("公安")) {
			if (suser == null) {
				user.setIdcard("");
				user.setSigncount(0);
				String strTime = "1970-01-01 00:00:00";
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime staTime = LocalDateTime.parse(strTime, df);
				user.setLastsigntime(staTime);
				user.setModifytime(LocalDateTime.now());

				if (user.getLevel().equals("红色")) { // 只有红色设置管理时间
					user.setManagetime(LocalDateTime.now());
				}

				if (user.getManagetime() != null) {
					user.setLefttimemodify(LocalDateTime.now());
					user.setEndsigntime(user.getManagetime().plusDays(15).minusMinutes(1));
					LocalDateTime endTime = user.getManagetime().plusDays(15);
					LocalDateTime nowTime = LocalDateTime.now();
					Duration duration = Duration.between(nowTime, endTime);
					user.setLefttime((int) duration.toDays());
				}

				user.setAddsource((addsource));
				userService.add(user);
				count++;
			} else {
				failUser += "重复用户：" + suser.getName() + "/" + suser.getPhone() + "<br/>";
			}
		}

		logHelper.logAdmin(3, "数据导入", true, "总计：" + totalCount + "条,导入成功" + count + "条", filename);

		return ResponseUtil.ok("总计：" + totalCount + "条,导入成功" + count + "条" + "<br/>" + failUser);
	}

	@RequiresPermissions("adminapi:user:download")
	@RequiresPermissionsDesc(menu = { "申报管理", "用户查询" }, button = "导出")
	@GetMapping("/download")
	public void download(HttpServletResponse response, String username, String mobile,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "addtime") String sort, @RequestParam(defaultValue = "desc") String order) {
		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		List<Whuser> userList = userService.querySelective(admin.getArea(), username, mobile, page, 1000000, sort,
				order);
		List<KnowledgeVo> xlslist = new ArrayList<KnowledgeVo>();
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//		Date now = new Date();

		for (Whuser m : userList) {
			KnowledgeVo vo = new KnowledgeVo();
			vo.setQrnumber(m.getName());
			vo.setKnname(m.getIdcard());
			vo.setUrl(m.getPhone().toString());
			xlslist.add(vo);
		}
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=登记列表.xls");
		OutputStream ouputStream;

		try {
			ouputStream = response.getOutputStream();
//            String[] headers = new String[]{"编号", "姓名", "身份证", "手机号", "民族", "性别", "生日", "目前居住地", "是否武汉", "交通方式", "交通班次", "出发地", "目的地", "离开日期", "武汉居住地", "停靠地点", "当前体温", "健康信息", "是否发热", "是否咳嗽", "是否接触发热人员", "是否接触感染人员", "同行人员姓名", "同行人员电话", "登记时间", "登记方式", "所属社区", "GPS定位信息", "微信openid", "登记人员编号"};
			String[] headers = new String[] { "姓名", "身份证", "手机号", };

			ExcelUtil<KnowledgeVo> util = new ExcelUtil<KnowledgeVo>();
			util.exportExcel("登记列表", headers, xlslist, ouputStream, "xls");

		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

	@RequiresPermissions("adminapi:user:nostreet")
	@RequiresPermissionsDesc(menu = { "申报管理", "无街道用户" }, button = "查询")
	@GetMapping("/nostreet")
	public void nostreet() {

	}

	@RequiresPermissions("adminapi:user:nosign")
	@RequiresPermissionsDesc(menu = { "申报管理", "今日未申报健康用户" }, button = "查询")
	@GetMapping("/nosign")
	public void nosign() {

	}

//    @RequiresPermissions("adminapi:user:uploadnj")
//    @RequiresPermissionsDesc(menu = {"市申报管理", "每日数据导入"}, button = "导入")
//    @GetMapping("/uploadnj")
//    public Object uploadnj(@RequestParam(defaultValue = "") String filename) {
//        Subject currentUser = SecurityUtils.getSubject();
//        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
//        List<Njuser> users = new ArrayList<Njuser>();
//        if (!StringUtils.isEmpty(filename)) {
//            String rootPath = System.getProperty("user.dir");
//            String filePath = rootPath + "/storage/" + filename;
//            users = ExcelUtil.getNJList(filePath);
//
//            if (users == null) {
//                return ResponseUtil.fail(-2, "请上传正确的表格");
//            }
//        } else {
//            return ResponseUtil.fail(-2, "请上传表格");
//        }
//
//        // 插入用户表
//        int count = 0;
//        int totalCount = users.size();
//        String failUser = "";
//        for (Njuser user : users) {
//            LocalDateTime reportDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
//            Njuser suser = njUserService.findByIdcard(user.getIdcard(), reportDate);
//            if (suser == null) {
////                user.setArea(admin.getArea());
//                //设置填报日期
//                user.setReportdate(reportDate);
//                user.setAddtime(LocalDateTime.now());
//                user.setAddaccount(admin.getUsername());
//                njUserService.add(user);
//                count++;
//            } else {
//                failUser += "当天重复用户：" + suser.getName() + "/" + suser.getIdcard() + "<br/>";
//            }
//        }
//
//        logHelper.logAdmin(3, "每日数据导入", true, "总计：" + totalCount + "条,导入成功" + count + "条", filename);
//
//        return ResponseUtil.ok("总计：" + totalCount + "条,导入成功" + count + "条" + "<br/>" + failUser);
//    }

	@RequiresPermissions("adminapi:user:uploadnj")
	@RequiresPermissionsDesc(menu = { "市申报管理", "每日数据导入" }, button = "导入市申报")
	@GetMapping("/uploadnj")
	public Object uploadnj(@RequestParam(defaultValue = "") String filename) {
		Subject currentUser = SecurityUtils.getSubject();
		LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
		List<Ruser> users = new ArrayList<Ruser>();
		if (!StringUtils.isEmpty(filename)) {
			String rootPath = System.getProperty("user.dir");
			String filePath = rootPath + "/storage/" + filename;
			users = ExcelUtil.getRUserList(filePath);

			if (users == null) {
				return ResponseUtil.fail(-2, "请上传正确的表格");
			}
		} else {
			return ResponseUtil.fail(-2, "请上传表格");
		}

		// 插入用户表
		int count = 0;
		int totalCount = users.size();
		String failUser = "";
		String op = "导入";
		boolean doOp = true;

		for (Ruser user : users) {
			// 进行规则校验
			if (!"常住".equals(user.getA13()) && !"暂住".equals(user.getA13())) {
				failUser += "规则【人员类别】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			if (!"往来地区".equals(user.getA15()) && !"病例同乘".equals(user.getA15()) && !"其他1".equals(user.getA15())
					&& !"其他2".equals(user.getA15())) {
				failUser += "规则【情况分类】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			List<String> removeStrList = new ArrayList<>();
			removeStrList.add("新增隔离");
			removeStrList.add("实际居住地不在本辖区、但在本市其他辖区");
			removeStrList.add("查无此人");
			removeStrList.add("已离开南京");
			removeStrList.add("尚在核查");
			removeStrList.add("已经实施居家隔离，非当日新增");
			removeStrList.add("已经解除隔离");
			removeStrList.add("居住在本市且不需要居家隔离");
			removeStrList.add("其他");

			if (!removeStrList.contains(user.getA19())) {
				failUser += "规则【排除类型】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			if ("实际居住地不在本辖区、但在本市其他辖区".equals(user.getA19()) && !"移交中".equals(user.getA18())) {
				failUser += "规则【核查结果】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}
			if ("尚在核查".equals(user.getA19()) && !"核查中".equals(user.getA18())) {
				failUser += "规则【核查结果】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}
			if (!"实际居住地不在本辖区、但在本市其他辖区".equals(user.getA19()) && !"尚在核查".equals(user.getA19())
					&& !"完成".equals(user.getA18())) {
				failUser += "规则【核查结果】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			if (!"实际居住地不在本辖区、但在本市其他辖区".equals(user.getA19()) && StringUtils.isEmpty(user.getA21())) {
				failUser += "规则【实际居住地址】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			// 实际居住地
			if (!user.getA22().equals(user.getA1())) {
				failUser += "规则【实际居住地（区）】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			List<String> areaList = new ArrayList<>();
			areaList.add("江宁区");
			areaList.add("浦口区");
			areaList.add("高淳区");
			areaList.add("建邺区");
			areaList.add("玄武区");
			areaList.add("六合区");
			areaList.add("溧水区");
			areaList.add("鼓楼区");
			areaList.add("栖霞区");
			areaList.add("秦淮区");
			areaList.add("雨花台区");
			areaList.add("江北新区");

			if (!areaList.contains(user.getA22())) {
				failUser += "规则【实际居住地（区）】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
				doOp = false;
			}

			if ("已经实施居家隔离，非当日新增".equals(user.getA19()) || "已经解除隔离".equals(user.getA19())) {
				if (StringUtils.isEmpty(user.getA26())) {
					failUser += "规则【开始隔离时间】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
					doOp = false;
				}
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("");
			if ("新增隔离".equals(user.getA19())) {
				if (!dateFormat.format(new Date()).equals(user.getA26())) {
					failUser += "规则【开始隔离时间】校验失败：" + user.getA2() + "/" + user.getA3() + "/" + user.getA6() + "</br>";
					doOp = false;
				}
			}

			if (doOp) {
				if (user.getId() != null) {
					op = "更新";
					rUserService.updateById(user);
				} else {
					op = "导入";
					rUserService.add(user);
				}
				count++;
			} else {
				continue;
			}
		}

		logHelper.logAdmin(3, "每日数据导入", true, "总计：" + totalCount + "条," + op + "成功" + count + "条", filename);

		return ResponseUtil.ok("总计：" + totalCount + "条," + op + "成功" + count + "条" + "<br/>" + failUser);
	}
}
