<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入课程名称"
      />
      <el-button
        v-permission="['GET /adminapi/course/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/course/create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >创建课程</el-button>
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

      <el-table-column align="center" min-width="60px" label="课程编号" prop="id" />

      <el-table-column align="center" min-width="200px" label="课程名称" prop="name" />

      <el-table-column align="center" label="分组" prop="ctype">
        <template slot-scope="scope">{{ scope.row.ctype | typeFilter }}</template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" property="封面" label="封面">
        <template slot-scope="scope">
          <img v-if="scope.row.img" :src="scope.row.img" width="80" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="课程老师" prop="teachername" />

      <el-table-column align="center" label="收费模式" prop="salemode">
        <template slot-scope="scope">{{ scope.row.salemode | saletypeFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">{{ scope.row.status | statusFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="原价" prop="sprice" />

      <el-table-column align="center" label="现价" prop="price" />

      <el-table-column align="center" label="购买次数" prop="buynum" />

      <el-table-column align="center" label="评分" prop="star" />

      <el-table-column align="center" label="新品" prop="isnew">
        <template slot-scope="scope">{{ scope.row.isnew | enableFilter }}</template>
      </el-table-column>

      <el-table-column align="center" label="新品" prop="ishot">
        <template slot-scope="scope">{{ scope.row.ishot | enableFilter }}</template>
      </el-table-column>

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="300"
        class-name="small-padding "
      >
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="handleShow(scope.row)"
          >内容管理</el-button>
          <el-button
            v-permission="['POST /adminapi/course/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/course/delete']"
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
    <el-dialog width="80%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 800px; margin-left:50px;"
      >
        <el-form-item label="分组类型" prop="ctype">
          <el-select
            v-model="dataForm.ctype"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择分组"
          >
            <el-option v-for="item in listtype" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="课程名称" prop="name">
          <el-input v-model="dataForm.name" />
        </el-form-item>

        <el-form-item label="所属品牌商">
          <el-select v-model="dataForm.brandid">
            <el-option v-for="item in brandList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>

        <el-form-item label="授课老师" prop="teacherid">
          <el-select
            v-model="dataForm.teacherid"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择分组"
          >
            <el-option
              v-for="item in listteacher"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="课程简介" prop="subject">
          <el-input v-model="dataForm.subject" />
        </el-form-item>

        <el-form-item label="课程封面" prop="img">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadPicUrl"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.img" :src="dataForm.img" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        
        <el-form-item label="课程详细描述" prop="detail">
          <tinymce :height="400" ref="editordetail" v-model="dataForm.detail"></tinymce>
        </el-form-item>

        <el-form-item label="课程状态" prop="status">
          <el-select
            v-model="dataForm.status"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择课程状态"
          >
            <el-option
              v-for="(key, value) in statusEditMap"
              :key="value"
              :label="value"
              :value="key"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="收费类型" prop="salemode">
          <el-select
            v-model="dataForm.salemode"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择收费类型"
          >
            <el-option
              v-for="(key, value) in saletypeEditMap"
              :key="value"
              :label="value"
              :value="key"
            />
          </el-select>
        </el-form-item>

        <el-form-item v-if="dataForm.salemode == 1" label="划线价格" prop="sprice">
          <el-input v-model="dataForm.sprice" />
        </el-form-item>

        <el-form-item v-if="dataForm.salemode == 1" label="售卖价格" prop="price">
          <el-input v-model="dataForm.price" />
        </el-form-item>
        
        <el-form-item label="是否新品" prop="isnew">
          <el-select
            v-model="dataForm.isnew"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择是否新品"
          >
            <el-option v-for="(key, value) in enableEditMap" :key="value" :label="value" :value="key" />
          </el-select>
        </el-form-item>
       
        <el-form-item label="是否热销" prop="ishot">
          <el-select
            v-model="dataForm.ishot"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择是否热销"
          >
            <el-option v-for="(key, value) in enableEditMap" :key="value" :label="value" :value="key" />
          </el-select>
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
import { listCourse, createCourse, updateCourse, deleteCourse } from "@/api/course";
import { publishGoods, listCatAndBrand } from '@/api/goods'

import { listType } from "@/api/ctype";
import { listTeacher } from "@/api/teacher";

import { uploadPath } from "@/api/storage";
import { getToken } from "@/utils/auth";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import Tinymce from "@/components/Tinymce";

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

const enableMap = {
  1: "是",
  2: "否"
};

const enableEditMap = {
  "是":1,
  "否":2
};

let that;

export default {
  name: "Brand",
  components: { Pagination, Tinymce },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      statusEditMap,
      cTypeMap,
      saletypeMap,
      saletypeEditMap,
      enableEditMap,
      enableMap,
      list: [],
      brandList: [],
      listtype: [],
      listteacher: [],
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
        img: ""
       
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ]
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
    this.getListTeacher();
    this.getBrandlist();
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
    },
    enableFilter(status) {
      return enableMap[status];
    }
  },
  methods: {
    getList() {
      this.listLoading = true;
      listCourse(this.listQuery)
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
    getBrandlist(){
       listCatAndBrand().then(response => {
        this.brandList = response.data.data.brandList
      })
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
    getListTeacher() {
      let param = {
        page: 1,
        limit: 100
      };
      listTeacher(param)
        .then(response => {
          this.listteacher = response.data.data.list;
        })
        .catch(() => {
          this.listteacher = [];
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
        img: ""
        
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
      this.dataForm.img = response.data.url;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createCourse(this.dataForm)
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
        this.$refs.editordetail.setContent(this.dataForm.detail);

      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          updateCourse(this.dataForm)
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
      deleteCourse(row)
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
    handleShow(row) {
      let course = Object.assign({}, row);
      this.$router.push({ name: 'courselesson', query: { id: course.id }})
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
