package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.KnowledgeVo;
import org.linlinjava.litemall.core.util.ExcelUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.Usersign;
import org.linlinjava.litemall.db.domain.Whuser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import org.apache.shiro.SecurityUtils;

@RestController
@RequestMapping("/adminapi/user")
@Validated
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private UserSignService userSignService;


    @RequiresPermissions("adminapi:user:list")
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username, String mobile,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "addtime") String sort,
                       @RequestParam(defaultValue = "desc") String order) {

        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

        List<Whuser> userList = userService.querySelective(admin.getArea(), username, mobile, page, limit, sort, order);
        for (Whuser user : userList) {
//            String strMobile = user.getPhone().toString();
//            strMobile = strMobile.replace(strMobile.substring(3, 9), "******");
//            user.setPhone(Long.parseLong(strMobile));
        }
        return ResponseUtil.okList(userList);
    }

    @RequiresPermissions("adminapi:user:listsign")
    @RequiresPermissionsDesc(menu = {"申报管理", "登记管理"}, button = "打卡记录")
    @GetMapping("/listsign")
    public Object listsign(Integer uid,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "addtime") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<Usersign> signList = userSignService.querySelective(uid, page, limit, sort, order);

        return ResponseUtil.okList(signList);
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
