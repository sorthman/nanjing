package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.admin.util.Permission;
import org.linlinjava.litemall.admin.util.PermissionUtil;
import org.linlinjava.litemall.core.util.*;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallPermissionService;
import org.linlinjava.litemall.db.service.LitemallRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.*;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.ADMIN_INVALID_ACCOUNT;

@RestController
@RequestMapping("/adminapi/auth")
@Validated
public class AdminAuthController {
    private final Log logger = LogFactory.getLog(AdminAuthController.class);

    @Autowired
    private LitemallAdminService adminService;
    @Autowired
    private LitemallRoleService roleService;
    @Autowired
    private LitemallPermissionService permissionService;
    @Autowired
    private LogHelper logHelper;

    /*
     *  获取图形验证码
     */
    @GetMapping("/code")
    public Object code(@RequestParam String name, HttpServletResponse response) {
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String code = CaptcahCode.drawImageVerificate(response);
        CacheUtil.put(name, code);

        return code;

    }

    /*
     *  { username : value, password : value }
     */
    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        // 检测账户是否暂时锁定
        String iflock = CacheUtil.get(username + "_lock");
        if (!StringUtils.isEmpty(iflock)) {
            return ResponseUtil.fail(1, "错误达到5次,该账户暂时被锁定,请10分钟后再试");
        }

        String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDbSndDzxKZVQ+kD6v/SFZj8tsw\r\n"
                + "KyWXBcpStdjXekbboEqyC0ybogAavqmxLxxD4BTvlGkfeVO23zXAh8Ieb36+jiRW3rrLwbSR9hIn\r\n"
                + "1CXDagfmXBpvySc89v41A3ATta9x5FzMqHhcgR81yBdnIqtwNQikiw/qs1tnvQlhGFiJqCrIuxvf\r\n"
                + "2Jvh47pl/qeaeuXc08Z5bioWiK8250CV3kTTiC3a8IQSm5W3fHX9beVXMPaDPP2t+TOMW9bB8ZiM\r\n"
                + "cxxlk0dnWHUbrSyxIeHtYpXx/oSCU9FCyyKZQpdfgN7qDlJUAE+zowQ6PRFhjK6cny6cDjjy7v9M\r\n"
                + "JwyNX9u+ed0PAgMBAAECggEBALu/X6kQqw3YQ9HlEQ85sa1VCdCNwFsyu+xggMzLhcsXy5vwDKDa\r\n"
                + "7Ff5kXN/sCV0vrf4SqCF0eSp6m1yTgKc1tjy/a3ad/dnWt/028p52fMa3rSdeozmnc+WcEKXNOfL\r\n"
                + "D/Dk4YbfXl1kt4HmVBMbHcitvz6RSQlakhGZSWR14afIWzmcyWSkowXU+u/KDdS84kta0FN72KJp\r\n"
                + "XHG4vkn6cWBNCCtDg9Fso4MWXCn5/wq9N73M/y6u0Pl9grxMzmV5f/GwhoJnGklaPW8kQyh71oGB\r\n"
                + "783TLnp3t9uLWEwGNi5WJbrYcv3XhVqNqH9fKX2UD3cv6TZwfrXQyabNY/WzX8ECgYEA8iOUFAz9\r\n"
                + "3psYx5OdyKuxYPq242JOtq1UDhe71Z7linUD8oKF67rFDoaDkQUkpd6VBh/88nStA4a8nfLt3KN4\r\n"
                + "vKfC51doBVyD7W6rtbNuglaWxm+l335l482MLpjpu0l3YHvd/ku43Y7CQ01orMLmomNApM+dpo6H\r\n"
                + "PWI3uFGMk38CgYEA59gPdyQs++FsgtZML7P7nDlHPMJ6rBgHuxYcvFsYnQwigGt2KRscB0d76eL8\r\n"
                + "4HDTCtO9Lb6x7EuZTtP2Xze8XRSnYEI/Kc1SE90/zhcVH2GWcBn76mL+u/vsVp64osfe964A4DgK\r\n"
                + "/HnO3kL4q0K25DCOlAuMc+UliZ7GKRZMPnECgYBz4Gjl6422cRy7rb4NoXTCh1jsmkg3Dd2y3NLM\r\n"
                + "GT2aNAOOk74JIJlbKS5fQUnXgxxoEPjZAtMlY95x7Ca+txB5oAMa+O5T2oFcJ+FOlC7new6uqhGE\r\n"
                + "UhXqnZxfVFhOg4r4hm6nWo9+n+oBkVLtklwTDFKMTLsSURURYCZwM76FewKBgF6T7mMs98jdAfYx\r\n"
                + "06yWIKtvVlc8Au0XdHfmolxYUScn3x9CN1a3f2ycxbj45y3r+Lvu+rqhk1WQ1X6py1S2mPnjxg/n\r\n"
                + "lnrIDpaZ6EB7fBtsJQGgJ/tK3R30vEntv+Zv4M5rRmIxmpG6YyCac3Tx50gZYuCUAXz52qFUiwQR\r\n"
                + "25KRAoGAKiM+cNZLGq6ksesjvC5jpekZMAaiFwZa1EmYkDQ7MZeRBm8whOok2/h8nmw4uJVboipI\r\n"
                + "K7qjqSaHQrMJNLhNMZ0BsOR/I0vJkPuqKI38QHVxRMnaqWb/KXljqY+/b6PQ16RYjjN7V6+/IkOw\r\n"
                + "9Cks8paAHqEwho2Z/Q8sntNbyVQ=\r\n";

        try {
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            // 加密后的内容Base64解码
            byte[] base642Byte = RSAUtil.base642Byte(password);
            // 用私钥解密
            byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
            // 解密后的明文
            password = new String(privateDecrypt);
        } catch (Exception e) {
            return ResponseUtil.fail(1, "用户名或者密码错误");
        }

        String code = JacksonUtil.parseString(body, "code");
        String codename = JacksonUtil.parseString(body, "codename");

        if (StringUtils.isEmpty(codename) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }

        String cachecode = CacheUtil.get(codename);
        if (StringUtils.isEmpty(cachecode)) {
            return ResponseUtil.fail(1, "请先获取验证码");
        }

        if (!cachecode.equals(code)) {
            return ResponseUtil.fail(1, "请填入正确的验证码");
        }

        Subject currentUser = SecurityUtils.getSubject();
        try {
//            logHelper.logAuthSucceed(password);
            currentUser.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException uae) {
            logHelper.logAuthFail("登录", "用户帐号或密码不正确");

            String errCount = CacheUtil.get(username + "_errcount");
            if (StringUtils.isEmpty(errCount)) {
                CacheUtil.put(username + "_errcount", "1");
            } else {
                int intErrCount = Integer.parseInt(errCount);
                intErrCount++;
                CacheUtil.put(username + "_errcount", String.valueOf(intErrCount));
                if (intErrCount >= 5) {
                    CacheUtil.put(username + "_lock", "yes", 60000);
                    // 计数清零
                    CacheUtil.remove(username + "_errcount");
                }
            }

            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            logHelper.logAuthFail("登录", "认证失败");
            return ResponseUtil.fail(ADMIN_INVALID_ACCOUNT, "认证失败");
        }

        // 删除验证码缓存
        CacheUtil.remove(codename);
        CacheUtil.remove(username + "_errcount");

        currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(LocalDateTime.now());

        adminService.updateById(admin);

        logHelper.logAuthSucceed("登录");

        // userInfo
        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("nickName", admin.getUsername());
        adminInfo.put("avatar", admin.getAvatar());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", currentUser.getSession().getId());
        result.put("adminInfo", adminInfo);
        return ResponseUtil.ok(result);
    }

    /*
     *
     */
    @RequiresAuthentication
    @PostMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();

        logHelper.logAuthSucceed("退出");
        currentUser.logout();
        return ResponseUtil.ok();
    }


    @RequiresAuthentication
    @GetMapping("/info")
    public Object info() {
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);
        data.put("roles", roles);
        // NOTE
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms", toApi(permissions));
        return ResponseUtil.ok(data);
    }

    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    private Collection<String> toApi(Set<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "org.linlinjava.litemall.admin";
            List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                systemPermissionsMap.put(perm, api);
            }
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
                //                return systemPermissionsMap.values();

            }
        }
        return apis;
    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }
}
