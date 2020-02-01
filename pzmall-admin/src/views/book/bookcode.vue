<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="typename"
        clearable
        style="width: 120px"
        class="filter-item"
        @change="onSelectedType"
        placeholder="图书类型"
      >
        <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.bname"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="选择图书"
      >
        <el-option v-for="item in books" :key="item.id" :label="item.bname" :value="item.bname" />
      </el-select>
      <el-input
        v-model="listQuery.xlhnumber"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="输入序列号"
      />
      <el-input
        v-model="listQuery.uid"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="用户编号"
      />
      <el-input
        v-model="listQuery.account"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="注册手机号"
      />

      <el-button
        v-permission="['GET /adminapi/bookcode/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/bookcode/create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        v-permission="['GET /adminapi/bookcode/upload']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleUpload"
      >导入信息库</el-button>
      <el-button
        v-permission="['GET /adminapi/bookcode/setbook']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleSetBook"
      >批量设置</el-button>
      <!-- <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button> -->
    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" min-width="120px" label="序列号" prop="xlnumber" />

      <el-table-column align="center" min-width="120px" label="卡号" prop="xlcard" />

      <el-table-column align="center" min-width="120px" label="密码" prop="password" />

      <el-table-column align="center" label="图书类型" prop="typename" />

      <el-table-column align="center" min-width="300px" label="图书名称" prop="bname" />

      <el-table-column align="center" label="是否备用" prop="ifreserve" />

      <el-table-column align="center" label="状态" prop="xlstatus" />

      <el-table-column align="center" min-width="120px" label="注册手机号" prop="account" />

      <el-table-column align="center" min-width="120px" label="注册时间" prop="registertime" />

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/bookcode/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/bookcode/delete']"
            type="danger"
            size="mini"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="图书类型" prop="typename">
          <el-select
            v-model="dataForm.typename"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择图书类型"
            @change="onSelectedType"
          >
            <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="图书名称" prop="bname">
          <el-select
            v-model="dataForm.bname"
            clearable
            style="width: 400px"
            class="filter-item"
            placeholder="图书名称"
          >
            <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bname"
              :value="item.bname"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="序列号" prop="xlnumber">
          <el-input v-model="dataForm.xlnumber" />
        </el-form-item>
        <el-form-item label="卡号" prop="xlcard">
          <el-input v-model="dataForm.xlcard" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="dataForm.password" placeholder="填入该书作文个数" />
        </el-form-item>
        <el-form-item label="是否备用" prop="ifreserve">
          <el-select
            v-model="dataForm.ifreserve"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择"
          >
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否使用" prop="xlstatus">
          <el-select
            v-model="dataForm.xlstatus"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择"
          >
            <el-option v-for="(key, value) in useMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户编号" prop="uid">
          <el-input v-model="dataForm.uid" placeholder />
        </el-form-item>
        <el-form-item label="注册手机号" prop="account">
          <el-input v-model="dataForm.account" placeholder />
        </el-form-item>
        <el-form-item label="注册时间" prop="registertime">
          <el-input v-model="dataForm.registertime" placeholder />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!-- 批量导入对话框 -->
    <el-dialog width="70%" title="导入防伪库" :visible.sync="dialogUploadFormVisible">
      <el-form
        ref="uploaddataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="请上传文件" prop="filename">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadCodesUrl"
            class="avatar-uploader"
            accept=".txt"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:500px" disabled v-model="filename" placeholder />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="importLoading" type="primary" @click="uploadData">立即导入</el-button>
      </div>
    </el-dialog>

    <!-- 图书设定对话框 -->
    <el-dialog width="70%" title="批量设置" :visible.sync="dialogSetBookFormVisible">
      <el-form
        ref="setBookForm"
        :model="setBookForm"
        :rules="rulesSet"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="图书类型" prop="typename">
          <el-select
            v-model="setBookForm.typename"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择图书类型"
            @change="onSelectedType"
          >
            <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="图书名称" prop="bname">
          <el-select
            v-model="setBookForm.bname"
            clearable
            style="width: 400px"
            class="filter-item"
            placeholder="图书名称"
          >
            <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bname"
              :value="item.bname"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始序列号" prop="startnumber">
          <el-input v-model="setBookForm.startnumber" placeholder />
        </el-form-item>
        <el-form-item label="开始序列号" prop="endnumber">
          <el-input v-model="setBookForm.endnumber" placeholder />
        </el-form-item>
        <el-form-item label="是否备用" prop="ifreserve">
          <el-select
            v-model="setBookForm.ifreserve"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择是否备用"
          >
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="importLoading" type="primary" @click="setBook">立即设定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
</style>

<script>
import { listBook } from "@/api/book";
import {
  listBookcode,
  createBookcode,
  updateBookcode,
  deleteBookcode,
  uploadBookCode,
  setBook
} from "@/api/bookcode";
import { uploadPath } from "@/api/storage";
import { getToken } from "@/utils/auth";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination

const typeMap = {
  英语一: "英语一",
  英语二: "英语二",
  英语四级: "英语四级",
  英语六级: "英语六级",
  数学一: "数学一",
  数学二: "数学二",
  数学三: "数学三",
  政治: "政治"
};

const statusMap = {
  是: "是",
  否: "否"
};

const useMap = {
  已使用: "已使用",
  未使用: "未使用"
};

export default {
  name: "Brand",
  components: { Pagination },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      useMap,
      books: [],
      typename: "",
      list: [],
      total: 0,
      listLoading: true,
      importLoading: false,
      listQuery: {
        page: 1,
        limit: 10,
        id: undefined,
        name: undefined,
        sort: "id",
        order: "desc"
      },
      dataForm: {
        id: undefined,
        name: "",
        desc: "",
        floorPrice: undefined,
        picUrl: undefined
      },
      setBookForm: {
        typename: "",
        bname: ""
      },
      filename: "",
      dialogUploadFormVisible: false,
      dialogSetBookFormVisible: false,
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        bname: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      rulesSet: {
        typename: [{ required: true, message: "不能为空", trigger: "blur" }],
        bname: [{ required: true, message: "不能为空", trigger: "blur" }],
        startnumber: [{ required: true, message: "不能为空", trigger: "blur" }],
        endnumber: [{ required: true, message: "不能为空", trigger: "blur" }],
        ifreserve: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      downloadLoading: false
    };
  },
  computed: {
    headers() {
      return {
        "X-Litemall-Admin-Token": getToken()
      };
    }
  },
  created() {
    this.getList();
    this.getBookList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      if (this.listQuery.bname) {
        this.listQuery.bookname = encodeURIComponent(this.listQuery.bname);
      } else {
        this.listQuery.bookname = this.listQuery.bname;
      }
      listBookcode(this.listQuery)
        .then(response => {
          this.list = response.data.data.list;
          this.total = response.data.data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.list = [];
          this.total = 0;
          this.listLoading = false;
        });
    },
    getBookList() {
      let param = {
        page: 1,
        limit: 1000,
        typename: this.typename
      };
      listBook(param)
        .then(response => {
          this.books = response.data.data.list;
        })
        .catch(() => {
          this.books = [];
        });
    },
    onSelectedType(event, item) {
      this.typename = event;
      this.getBookList();
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: "",
        imgurl: undefined
      };
    },
    resetSetBookForm() {
      this.setBookForm = {
        typename: "",
        bname: "",
        startnumber: "",
        endnumber: "",
        ifreserve: ""
      };
    },
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    handleUpload() {
      this.dialogUploadFormVisible = true;
      this.filename = "";
      this.$nextTick(() => {
        this.$refs["uploaddataForm"].clearValidate();
      });
    },
    handleSetBook() {
      this.resetSetBookForm();
      this.dialogSetBookFormVisible = true;
      this.$nextTick(() => {
        this.$refs["setBookForm"].clearValidate();
      });
    },
    uploadPicUrl: function(response) {
      this.dataForm.imgurl = response.data.url;
    },
    uploadCodesUrl: function(response) {
      this.filename = response.data.key;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createBookcode(this.dataForm)
            .then(response => {
              this.list.unshift(response.data.data);
              this.dialogFormVisible = false;
              this.$notify.success({
                title: "成功",
                message: "创建成功"
              });
            })
            .catch(response => {
              this.$notify.error({
                title: "失败",
                message: response.data.msg
              });
            });
        }
      });
    },
    uploadData() {
      let query = {
        filename: this.filename
      };
      this.importLoading = true;
      uploadBookCode(query)
        .then(response => {
          this.importLoading = false;
          if (response.data.code === 0) {
            this.dialogUploadFormVisible = false;
            this.$notify.success({
              title: "成功",
              message: "导入成功"
            });
            this.handleFilter();
          } else {
            this.$notify.error({
              title: "失败",
              message: response.data.msg
            });
          }
        })
        .catch(response => {
          this.importLoading = false;
          this.$notify.error({
            title: "失败",
            message: response.data.msg
          });
        });
    },
    setBook() {
      this.$refs["setBookForm"].validate(valid => {
        if (valid) {
          this.importLoading = true;
          setBook(this.setBookForm)
            .then(response => {
              this.importLoading = false;
              if (response.data.code === 0) {
                this.dialogSetBookFormVisible = false;
                this.$notify.success({
                  title: "成功",
                  message: "设定成功"
                });
                this.handleFilter();
              } else {
                this.$notify.error({
                  title: "失败",
                  message: response.data.msg
                });
              }
            })
            .catch(response => {
              this.importLoading = false;
              this.$notify.error({
                title: "失败",
                message: response.data.msg
              });
            });
        }
      });
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          if (this.dataForm.uid === "") {
            this.dataForm.uid = 0;
          }
          updateBookcode(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v);
                  this.list.splice(index, 1, this.dataForm);
                  break;
                }
              }
              this.dialogFormVisible = false;
              this.$notify.success({
                title: "成功",
                message: "更新成功"
              });
              this.handleFilter();
            })
            .catch(response => {
              this.$notify.error({
                title: "失败",
                message: response.data.msg
              });
            });
        }
      });
    },
    handleDelete(row) {
      deleteBookcode(row)
        .then(response => {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          const index = this.list.indexOf(row);
          this.list.splice(index, 1);
          this.getList();
        })
        .catch(response => {
          this.$notify.error({
            title: "失败",
            message: response.data.msg
          });
        });
    },
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "品牌商ID",
          "品牌商名称",
          "介绍",
          "低价",
          "品牌商图片"
        ];
        const filterVal = ["id", "name", "desc", "floorPrice", "picUrl"];
        excel.export_json_to_excel2(
          tHeader,
          this.list,
          filterVal,
          "品牌商信息"
        );
        this.downloadLoading = false;
      });
    }
  }
};
</script>
