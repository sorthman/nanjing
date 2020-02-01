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
        v-permission="['GET /adminapi/lesson/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/lesson/create']"
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

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/lesson/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/lesson/delete']"
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

        <el-form-item label="课程类型" prop="lessontype">
          <el-select
            v-model="dataForm.lessontype"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择课程类型"
          >
            <el-option v-for="(key, value) in cTypeMap" :key="value" :label="value" :value="key" />
          </el-select>
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

        <el-form-item label="关联图书" prop="bookid">
          <el-select
            v-model="dataForm.bookid"
            clearable
            style="width: 400px"
            class="filter-item"
            placeholder="图书名称"
          >
            <el-option v-for="item in books" :key="item.id" :label="item.bname" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="课程名称" prop="title">
          <el-input v-model="dataForm.title" />
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

        <el-form-item label="授课老师" prop="teacherid">
          <el-select
            v-model="dataForm.teacherid"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择老师"
          >
            <el-option
              v-for="item in listteacher"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="预览图文内容" prop="knowledgedetil">
          <tinymce :height="400" ref="editordetail" v-model="dataForm.preArticle"></tinymce>
        </el-form-item>

        <el-form-item label="图文内容" prop="knowledgedetil">
          <tinymce :height="400" ref="editordetail" v-model="dataForm.article"></tinymce>
        </el-form-item>-->

        <el-form-item v-if="dataForm.lessontype===2" label="音频文件" prop="audioUrl">
          <el-upload
            ref="refAudio"
            :limit="1"
            :headers="headers"
            :action="uploadPath"
            :show-file-list="true"
            :on-success="uploadAudioUrl"
            accept=".mp3"
          >
            <el-button size="small" type="primary">点击上传音频</el-button>
          </el-upload>
          <el-input style="width:500px" v-model="dataForm.audioUrl" placeholder />
        </el-form-item>

        <el-form-item v-if="dataForm.lessontype===2" label="歌词文件" prop="audioLrc">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadLrcUrl"
            class="avatar-uploader"
            accept=".lrc"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:500px" disabled v-model="dataForm.audioLrc" placeholder />
        </el-form-item>

        <el-form-item v-if="dataForm.lessontype===2" label="音频详细描述" prop="audioDetail">
          <tinymce :height="400" ref="editorAudio" v-model="dataForm.audioDetail"></tinymce>
        </el-form-item>

        <!-- <el-form-item v-if="dataForm.lessontype===3" label="预览视频文件" prop="preVideo">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadPreVideoUrl"
            class="avatar-uploader"
            accept=".mp4"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:500px" disabled v-model="dataForm.preVideo" placeholder />
        </el-form-item>-->

        <el-form-item v-if="dataForm.lessontype===3" label="视频文件" prop="videoUrl">
          <el-upload
            ref="refVideo"
            :limit="1"
            :headers="headers"
            :action="uploadPath"
            :show-file-list="true"
            :on-success="uploadVideoUrl"
            class="avatar-uploader"
            accept=".mp4"
          >
            <el-button size="small" type="primary">点击上传视频</el-button>
          </el-upload>
          <el-input style="width:500px" v-model="dataForm.videoUrl" placeholder />
        </el-form-item>

        <el-form-item v-if="dataForm.lessontype===3" label="视频详细描述" prop="videoDetail">
          <tinymce :height="400" ref="editorVideo" v-model="dataForm.videoDetail"></tinymce>
        </el-form-item>

        <el-form-item label="收费类型" prop="saletype">
          <el-select
            v-model="dataForm.saletype"
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

        <el-form-item v-if="dataForm.saletype == 1" label="划线价格" prop="sprice">
          <el-input v-model="dataForm.sprice" />
        </el-form-item>

        <el-form-item v-if="dataForm.saletype == 1" label="售卖价格" prop="price">
          <el-input v-model="dataForm.price" />
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
import { listLess, createBook, updateBook, deleteBook } from "@/api/lesson";
import { listBook } from "@/api/book";
import { listType } from "@/api/ctype";
import { listTeacher } from "@/api/teacher";

import Tinymce from "@/components/Tinymce";

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
      books: [],
      list: [],
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

        floorPrice: undefined,
        picUrl: undefined
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        title: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        saletype: [
          { required: true, message: "请选择收费方式", trigger: "blur" }
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
    this.getListType();
    this.getListTeacher();
    this.getBookList();
    this.getList();
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
      this.listLoading = true;
      listLess(this.listQuery)
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
        limit: 1000
      };
      listBook(param)
        .then(response => {
          this.books = response.data.data.list;
        })
        .catch(() => {
          this.books = [];
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
        img: "",
        lessontype: 2,
        audioUrl: "",
        audioLrc: "",
        preVideo: "",
        videoUrl: ""
      };
    },
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
        this.$refs.refAudio.clearFiles();
        this.$refs.refVideo.clearFiles();
      });
    },
    uploadPicUrl: function(response) {
      this.dataForm.img = response.data.url;
    },
    uploadAudioUrl: function(response) {
      this.dataForm.audioUrl = response.data.url;
    },
    uploadLrcUrl: function(response) {
      this.dataForm.audioLrc = response.data.url;
    },
    uploadPreVideoUrl: function(response) {
      this.dataForm.preVideo = response.data.url;
    },
    uploadVideoUrl: function(response) {
      this.dataForm.videoUrl = response.data.url;
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
        this.$refs.refAudio.clearFiles();
        this.$refs.refVideo.clearFiles();
        this.$refs.editorAudio.setContent(this.dataForm.audioDetail);
        this.$refs.editorVideo.setContent(this.dataForm.videoDetail);
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
