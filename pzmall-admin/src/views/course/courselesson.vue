<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!-- <el-select v-model="listQuery.typename" clearable style="width: 200px" class="filter-item" placeholder="请选择图书类型">
        <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value"/>
      </el-select>-->
      <!-- <el-input v-model="listQuery.name" clearable class="filter-item" style="width: 200px;" placeholder="请输入图书名称"/>
      <el-button v-permission="['GET /admin/brand/list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>-->
      <el-button
        v-permission="['POST /adminapi/courselesson/add']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加单品课程</el-button>
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
      <el-table-column align="center" min-width="200px" label="单品名称" prop="title" />

      <el-table-column align="center" min-width="100px" label="封面" property="封面">
        <template slot-scope="scope">
          <img v-if="scope.row.img" :src="scope.row.img" width="80" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="分组" prop="ctype">
        <template slot-scope="scope">{{ scope.row.ctype | typeFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="类型" prop="lessontype">
        <template slot-scope="scope">{{ scope.row.lessontype | ctypeFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="课程老师" prop="teachername" />

      <el-table-column align="center" label="收费模式" prop="saletype">
        <template slot-scope="scope">{{ scope.row.saletype | saletypeFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="原价" prop="sprice" />

      <el-table-column align="center" label="现价" prop="price" />

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/courselesson/delete']"
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
    <el-dialog width="80%" title="添加单品课程" :visible.sync="dialogFormVisible">
      <el-input
        v-model="listQueryLesson.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入课程名称"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilterLesson"
      >查找</el-button>
      <el-table
        v-loading="listLoading"
        :data="listLesson"
        element-loading-text="正在查询中。。。"
        border
        fit
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />

        <el-table-column align="center" min-width="200px" label="课程名称" prop="title" />

        <el-table-column align="center" min-width="100px" label="封面" property="封面">
          <template slot-scope="scope">
            <img v-if="scope.row.img" :src="scope.row.img" width="80" />
          </template>
        </el-table-column>

        <el-table-column align="center" label="分组" prop="ctype">
          <template slot-scope="scope">{{ scope.row.ctype | typeFilter }}</template>
        </el-table-column>

        <el-table-column align="center" label="类型" prop="lessontype">
          <template slot-scope="scope">{{ scope.row.lessontype | ctypeFilter }}</template>
        </el-table-column>

        <el-table-column align="center" label="课程老师" prop="teachername" />

        <el-table-column align="center" label="收费模式" prop="saletype">
          <template slot-scope="scope">{{ scope.row.saletype | saletypeFilter }}</template>
        </el-table-column>

        <el-table-column align="center" label="原价" prop="sprice" />

        <el-table-column align="center" label="现价" prop="price" />

        <el-table-column align="center" label="状态" prop="status">
          <template slot-scope="scope">{{ scope.row.status | statusFilter }}</template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="totalLesson>0"
        :total="totalLesson"
        :page.sync="listQueryLesson.page"
        :limit.sync="listQueryLesson.limit"
        @pagination="getListLesson"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmAdd">确定添加</el-button>
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
import { listLesson, createLesson, deleteLesson } from "@/api/courselesson";
import { listLess } from "@/api/lesson";
import { listType } from "@/api/ctype";

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
  1: "在售",
  2: "下架"
};

const statusEditMap = {
  在售: 1,
  下架: 2
};

const cTypeMap = {
  音频: 2,
  视频: 3
};

const cTypeShowMap = {
  2: "音频",
  3: "视频"
};

const saletypeMap = {
  1: "收费",
  2: "免费"
};

const saletypeEditMap = {
  收费: 1,
  免费: 2
};

let that;

export default {
  name: "Brand",
  components: { Pagination },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      statusEditMap,
      cTypeMap,
      saletypeMap,
      saletypeEditMap,
      list: [],
      listtype: [],
      listLesson: [],
      total: 0,
      totalLesson: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 100,
        id: undefined,
        name: undefined,
        sort: "id",
        order: "desc"
      },
      listQueryLesson: {
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
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      selectedlist: [],

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
    this.getListType();
  },
  beforeCreate() {
    that = this;
  },
  filters: {
    typeFilter(status) {
      if (that.listtype.length > 0) {
        let item = that.listtype.find(item => item.id === status);
        return item.name;
      } else {
        return "";
      }
    },
    ctypeFilter(status) {
      return cTypeShowMap[status];
    },
    saletypeFilter(status) {
      return saletypeMap[status];
    },
    statusFilter(status) {
      return statusMap[status];
    }
  },
  methods: {
    getList() {
      if (this.$route.query.id == null) {
        return;
      }
      const courseid = this.$route.query.id;
      this.listQuery.courseid = courseid;
      this.listLoading = true;
      listLesson(this.listQuery)
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
    getListLesson() {
      this.listLoading = true;
      listLess(this.listQueryLesson)
        .then(response => {
          this.listLesson = response.data.data.list;
          this.totalLesson = response.data.data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.listLesson = [];
          this.totalLesson = 0;
          this.listLoading = false;
        });
    },
    getListType() {
      let param = {
        page: 1,
        limit: 100
      };
      listType(param)
        .then(response => {
          this.listtype = response.data.data.list;
        })
        .catch(() => {
          this.listtype = [];
        });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleFilterLesson() {
      this.listQueryLesson.page = 1;
      this.getListLesson();
    },
    handleSelectionChange(val) {
      this.selectedlist = val;
      console.log(this.selectedlist);
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: "",
        imgurl: undefined
      };
    },
    handleCreate() {
      this.resetForm();
      this.getListLesson();

      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        //this.$refs["dataForm"].clearValidate();
      });
    },
    uploadPicUrl: function(response) {
      this.dataForm.imgurl = response.data.url;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createLesson(this.dataForm)
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
    confirmAdd() {
      //批量添加到课程

      const lessids = [];
      this.selectedlist.forEach(item => {
        const id = item.id;
        lessids.push(id);
        
      });
      let query = {
          courseid: this.$route.query.id,
          lessids: lessids
        };
      createLesson(query)
        .then(response => {
          this.dialogFormVisible = false;
          this.$notify.success({
            title: "成功",
            message: "创建成功"
          });
          this.handleFilter()
        })
        .catch(response => {
          this.$notify.error({
            title: "失败",
            message: response.data.msg
          });
        });
    },
    handleDelete(row) {
      if (this.$route.query.id == null) {
        return;
      }
      let item = {
        courseid: this.$route.query.id,
        lessid: row.id
      };
      deleteLesson(item)
        .then(response => {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          this.handleFilter()
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
