package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.KnowledgeVo;
import org.linlinjava.litemall.admin.vo.userVo;
import org.linlinjava.litemall.core.util.ExcelUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.UserCheckService;
import org.linlinjava.litemall.db.service.UserSignService;
import org.linlinjava.litemall.db.service.UserTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private UserTransferService userTransferService;

    @RequiresPermissions("adminapi:user:list")
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username, String phone,
                       @RequestParam(defaultValue = "") String sex,
                       @RequestParam(defaultValue = "0") Integer sage,
                       @RequestParam(defaultValue = "0") Integer eage,
                       @RequestParam(defaultValue = "") String idcard,
                       @RequestParam(defaultValue = "") String street,
                       @RequestParam(defaultValue = "") String community,
                       @RequestParam(defaultValue = "") String arrivedate,
                       @RequestParam(defaultValue = "") String addsource,
                       @RequestParam(defaultValue = "") String iftransferarea,
                       @RequestParam(defaultValue = "") String iftransferstreet,
                       @RequestParam(defaultValue = "") String ifsafe,
                       @RequestParam(defaultValue = "") String healthinfo,
                       @RequestParam(defaultValue = "") String usertype,
                       @RequestParam(defaultValue = "") String ifwh,
                       @RequestParam(defaultValue = "") String ifhb,
                       @RequestParam(defaultValue = "") String ifleavenj,
                       @RequestParam(defaultValue = "") String ifadmin,
                       @RequestParam(defaultValue = "") String ifover,
                       @RequestParam(defaultValue = "") String iflose,

                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "addtime") String sort,
                       @RequestParam(defaultValue = "desc") String order) {

        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

        List<Whuser> userList = userService.querySelective(admin.getArea(), username, phone, sex, sage, eage, idcard, street, community, arrivedate, addsource, iftransferarea,
                iftransferstreet, ifsafe, healthinfo, usertype, ifwh, ifhb, ifleavenj, ifadmin, ifover, iflose, page, limit, sort, order);
        List<userVo> users = new ArrayList<>();
        for (Whuser user : userList) {
            userVo u = new userVo();
            BeanUtils.copyProperties(user, u);
            users.add(u);
            LocalDateTime sTime = LocalDateTime.now().minusDays(14);
            if (user.getArrivedate() != null) {
                if (user.getArrivedate().isBefore(sTime)) {
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
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "详情")
    @GetMapping("/listsign")
    public Object listsign(Integer uid,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @RequestParam(defaultValue = "addtime") String sort,
                           @RequestParam(defaultValue = "desc") String order) {
        List<Usercheck> signList = userCheckService.querySelective(uid, page, limit, sort, order);
        return ResponseUtil.ok(signList);
    }

    @RequiresPermissions("adminapi:user:update")
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody Whuser user) {

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(user);
    }

    @RequiresPermissions("adminapi:user:upload")
    @RequiresPermissionsDesc(menu = {"申报管理", "导入数据"}, button = "导入")
    @GetMapping("/upload")
    public Object upload(@RequestParam(defaultValue = "") String filename,
                         @RequestParam(defaultValue = "") String addsource) {
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
        List<Whuser> users = new ArrayList<Whuser>();
        if (!StringUtils.isEmpty(filename)) {
            String rootPath = System.getProperty("user.dir");
            String filePath = rootPath + "/storage/" + filename;
//			logger.debug("文件路径2：" + filePath);
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
            Whuser suser = userService.findByIdcard(user.getIdcard());
            if (suser == null) {
                if (user.getHealthinfo().indexOf("发热") >= 0) {
                    user.setIfhot("是");
                }
                if (user.getHealthinfo().indexOf("咳嗽") >= 0) {
                    user.setIfkesou("是");
                }
                user.setArea(admin.getArea());
                user.setAddsource((addsource));
                userService.add(user);
                count++;
            } else {
                failUser += "重复用户：" + suser.getName() + "/" + suser.getIdcard() + "<br/>";
            }
        }

        return ResponseUtil.ok("总计：" + totalCount + "条,导入成功" + count + "条" + "<br/>" + failUser);
    }

    @RequiresPermissions("adminapi:user:download")
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "导出")
    @GetMapping("/download")
    public void download(HttpServletResponse response, String username, String mobile,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit,
                         @RequestParam(defaultValue = "addtime") String sort,
                         @RequestParam(defaultValue = "desc") String order) {
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
        List<Whuser> userList = userService.querySelective(admin.getArea(), username, mobile, page, 1000000, sort, order);
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
            String[] headers = new String[]{"姓名", "身份证", "手机号",};

            ExcelUtil<KnowledgeVo> util = new ExcelUtil<KnowledgeVo>();
            util.exportExcel("登记列表", headers, xlslist, ouputStream, "xls");

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
