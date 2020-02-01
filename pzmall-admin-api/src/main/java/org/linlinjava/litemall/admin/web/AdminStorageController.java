package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.storage.StorageService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallStorage;
import org.linlinjava.litemall.db.service.LitemallStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/adminapi/storage")
@Validated
public class AdminStorageController {
    private final Log logger = LogFactory.getLog(AdminStorageController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private LitemallStorageService litemallStorageService;

    @RequiresPermissions("adminapi:storage:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "查询")
    @GetMapping("/list")
    public Object list(String key, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "add_time") String sort,
                       @RequestParam(defaultValue = "desc") String order) {
        List<LitemallStorage> storageList = litemallStorageService.querySelective(key, name, page, limit, sort, order);
        return ResponseUtil.okList(storageList);
    }

    @RequiresPermissions("adminapi:storage:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "上传")
    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        LitemallStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(),
                file.getContentType(), originalFilename);
        return ResponseUtil.ok(litemallStorage);
    }

    @RequiresPermissions("adminapi:storage:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "详情")
    @PostMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallStorage storageInfo = litemallStorageService.findById(id);
        if (storageInfo == null) {
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(storageInfo);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        LitemallStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    @RequiresPermissions("adminapi:storage:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallStorage litemallStorage) {
        if (litemallStorageService.update(litemallStorage) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(litemallStorage);
    }

    @RequiresPermissions("adminapi:storage:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallStorage litemallStorage) {
        String key = litemallStorage.getKey();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        litemallStorageService.deleteByKey(key);
        storageService.delete(key);
        return ResponseUtil.ok();
    }
}
