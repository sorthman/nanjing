<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.typename"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择图书类型"
      >
        <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-input
        v-model="listQuery.bookname"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入图书名称"
      />
      <el-button
        v-permission="['GET /adminapi/book/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/book/create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
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
      <el-table-column align="center" label="编号" prop="id" />

      <el-table-column align="center" min-width="200px" label="图书名称" prop="bname" />

      <el-table-column align="center" label="图书类型" prop="typename" />

      <el-table-column align="center" min-width="100px" property="图片" label="图片">
        <template slot-scope="scope">
          <img :title="scope.row.imgurl" v-if="scope.row.imgurl" :src="scope.row.imgurl" width="80" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="责任编辑" prop="author" />

      <el-table-column align="center" label="浏览次数" prop="bookclicount" />

      <el-table-column align="center" label="作文" prop="composition" />

      <el-table-column align="center" label="原价" prop="oriprice" />

      <el-table-column align="center" label="现价" prop="preprice" />

      <el-table-column align="center" label="新品" prop="isnew" />

      <el-table-column align="center" label="热卖" prop="ishot" />

      <el-table-column align="center" label="推荐" prop="iscommend" />

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/book/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/book/delete']"
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
          >
            <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="图书名称" prop="bname">
          <el-input v-model="dataForm.bname" />
        </el-form-item>
        <el-form-item label="责任编辑" prop="author">
          <el-input v-model="dataForm.author" />
        </el-form-item>
        <el-form-item label="出版社" prop="pucompany">
          <el-input v-model="dataForm.pucompany" />
        </el-form-item>
        <el-form-item label="作文个数" prop="composition">
          <el-input v-model="dataForm.composition" placeholder="填入该书作文个数" />
        </el-form-item>
        <el-form-item label="是否新品" prop="isnew">
          <el-select
            v-model="dataForm.isnew"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择图书类型"
          >
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否推荐" prop="iscommend">
          <el-select
            v-model="dataForm.iscommend"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择图书类型"
          >
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否热销" prop="ishot">
          <el-select
            v-model="dataForm.ishot"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择图书类型"
          >
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="购买链接" prop="buyurl">
          <el-input v-model="dataForm.buyurl" placeholder />
        </el-form-item>
        <el-form-item label="原价" prop="oriprice">
          <el-input v-model="dataForm.oriprice" placeholder />
        </el-form-item>
        <el-form-item label="现价" prop="preprice">
          <el-input v-model="dataForm.preprice" placeholder />
        </el-form-item>
        <el-form-item label="图书图片" prop="imgurl">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadPicUrl"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.imgurl" :src="dataForm.imgurl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
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
import { listBook, createBook, updateBook, deleteBook } from "@/api/book";
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

export default {
  name: "Brand",
  components: { Pagination },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
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
        picUrl: undefined,
        imgurl: ""
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }]
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
  },
  methods: {
    getList() {
      this.listLoading = true;
      if (this.listQuery.bookname) {
        this.listQuery.name = encodeURIComponent(this.listQuery.bookname);
      } else {
        this.listQuery.name = this.listQuery.bookname;
      }
      // this.listQuery.name = encodeURIComponent(this.listQuery.name);
      listBook(this.listQuery)
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
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: "",
        imgurl: ""
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
    uploadPicUrl: function(response) {
      this.dataForm.imgurl = response.data.url;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createBook(this.dataForm)
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
          updateBook(this.dataForm)
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
      deleteBook(row)
        .then(response => {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          const index = this.list.indexOf(row);
          this.list.splice(index, 1);
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
