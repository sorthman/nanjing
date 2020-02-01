<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="typename"
        clearable
        style="width: 200px"
        class="filter-item"
        @change="onSelectedType"
        placeholder="请选择图书类型"
      >
        <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.bname"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择图书"
      >
        <el-option v-for="item in books" :key="item.id" :label="item.bname" :value="item.bname" />
      </el-select>
      <el-input
        v-model="listQuery.knowledgename"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入知识点名称"
      />
      <el-input
        v-model="listQuery.orcode"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入二维码编号"
      />

      <el-button
        v-permission="['GET /adminapi/knowledge/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/knowledge/create']"
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
      <el-table-column align="center" min-width="300px" label="知识点名称" prop="knname" />

      <el-table-column align="center" min-width="150px" label="二维码编码" prop="qrnumber" />

      <el-table-column align="center" label="知识点类型" prop="knowledgetype" />

      <el-table-column align="center" label="图书类型" prop="typename" />

      <el-table-column align="center" min-width="300px" label="图书名称" prop="bname" />

      <el-table-column align="center" label="章节" prop="chapter" />

      <el-table-column align="center" label="文章" prop="article" />

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="260"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/knowledge/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/knowledge/delete']"
            type="danger"
            size="mini"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            v-loading="downloadLoading"
            type="primary"
            size="mini"
            @click="handleDownload(scope.row.id)"
          >导出</el-button>
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
        <el-form-item label="知识点类型" prop="knowledgetype">
          <el-select
            v-model="dataForm.knowledgetype"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择知识点类型"
          >
            <el-option
              v-for="(key, value) in knowledgetypeMap"
              :key="key"
              :label="key"
              :value="value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="知识点名称" prop="knname">
          <el-input v-model="dataForm.knname" />
        </el-form-item>
        <el-form-item label="二维码编号" prop="qrnumber">
          <el-input v-model="dataForm.qrnumber" />
        </el-form-item>

        <el-form-item v-if="dataForm.knowledgetype=='视频课程'" label="在线课程" prop="chapter">
          <el-select
            v-model="dataForm.chapter"
            filterable
            clearable
            style="width: 400px"
            class="filter-item"
            placeholder="请选择在线课程"
          >
            <el-option v-for="item in course" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item v-if="dataForm.knowledgetype=='视频单课'" label="视频单课" prop="chapter">
          <el-select
            v-model="dataForm.chapter"
            filterable
            clearable
            style="width: 400px"
            class="filter-item"
            placeholder="请选择视频单课"
          >
            <el-option v-for="item in lessons" :key="item.id" :label="item.title" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item v-if="dataForm.knowledgetype!='视频课程'" label="章节" prop="chapter">
          <el-input v-model="dataForm.chapter" />
        </el-form-item>
        <el-form-item label="文章" prop="article">
          <el-input v-model="dataForm.article" />
        </el-form-item>
        <el-form-item label="文章链接" prop="detilurl">
          <el-input v-model="dataForm.detilurl" placeholder />
        </el-form-item>

        <el-form-item label="知识点音频" prop="mp3url">
          <el-alert title="音频、图文选择其中一个" type="warning" show-icon></el-alert>
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadmp3url"
            class="avatar-uploader"
            accept=".mp3"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:500px" disabled v-model="dataForm.mp3url" placeholder />
        </el-form-item>

        <el-form-item label="知识点音频原文" prop="mp3lrcurl">
          <el-alert title="上传内容为LRC格式" type="warning" show-icon></el-alert>
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadmp3lrcurl"
            class="avatar-uploader"
            accept=".lrc"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:500px" disabled v-model="dataForm.mp3lrcurl" placeholder />
        </el-form-item>

        <!-- <el-form-item label="知识点视频" prop="videourl">
          <el-alert title="音频、图文选择其中一个" type="warning" show-icon></el-alert>
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadvideourl"
            class="avatar-uploader"
            accept=".mp4"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input style="width:600px" disabled v-model="dataForm.videourl" />
        </el-form-item>-->

        <el-form-item label="知识图文内容" prop="knowledgedetil">
          <tinymce :height="400" ref="editordetail" v-model="dataForm.knowledgedetil"></tinymce>
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
import Tinymce from "@/components/Tinymce";

import { listBook } from "@/api/book";
import { listLess } from "@/api/lesson";

import {
  listKnowledge,
  createKnowledge,
  updateKnowledge,
  deleteKnowledge,
  download
} from "@/api/knowledge";
import { listCourse } from "@/api/course";
import { uploadPath } from "@/api/storage";
import { getToken } from "@/utils/auth";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import { formatDate } from "@/utils/time.js";

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

const knowledgetypeMap = {
  背景知识: "背景知识",
  词句注释与难句分析: "词句注释与难句分析",
  技巧补充: "技巧补充",
  语篇分析: "语篇分析",
  考题速览: "考题速览",
  考点补充: "考点补充",
  精彩句式: "精彩句式",
  应用模板: "应用模板",
  原文朗读音频: "原文朗读音频",
  知识补充: "知识补充",
  视频课程: "视频课程",
  视频单课: "视频单课",
  其它: "其它"
};

const statusMap = {
  是: "是",
  否: "否"
};

export default {
  name: "Brand",
  components: { Pagination, Tinymce },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      knowledgetypeMap,
      books: [],
      course: [],
      lessons: [],
      typename: "",
      list: [],
      total: 0,
      listLoading: true,
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
        picUrl: undefined,
        mp3url: "",
        mp3lrcurl: "",
        videourl: ""
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        bname: [{ required: true, message: "不能为空", trigger: "blur" }],
        knname: [{ required: true, message: "不能为空", trigger: "blur" }],
        qrnumber: [{ required: true, message: "不能为空", trigger: "blur" }]
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
    this.getCourseList();
    this.getLessonList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      if (this.listQuery.bname) {
        this.listQuery.bookname = encodeURIComponent(this.listQuery.bname);
      } else {
        this.listQuery.bookname = this.listQuery.bname;
      }
      listKnowledge(this.listQuery)
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
    getCourseList() {
      let param = {
        page: 1,
        limit: 1000
      };
      listCourse(param)
        .then(response => {
          this.course = response.data.data.list;
        })
        .catch(() => {
          this.course = [];
        });
    },
    getLessonList() {
      let param = {
        page: 1,
        limit: 10000
      };
      listLess(param)
        .then(response => {
          this.lessons = response.data.data.list;
        })
        .catch(() => {
          this.lessons = [];
        });
    },
    onSelectedType(event, item) {
      console.log(event);
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
        imgurl: undefined,
        mp3url: "",
        mp3lrcurl: "",
        videourl: ""
      };
    },
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
        this.$refs["editordetail"].setContent("");
      });
    },
    uploadPicUrl: function(response) {
      this.dataForm.imgurl = response.data.url;
    },
    uploadmp3url: function(response) {
      this.dataForm.mp3url = response.data.url;
    },
    uploadmp3lrcurl: function(response) {
      this.dataForm.mp3lrcurl = response.data.url;
    },
    uploadvideourl: function(response) {
      this.dataForm.videourl = response.data.url;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          if (this.dataForm.knowledgetype == "视频课程") {
            this.dataForm.detilurl = "sjgj://courses/" + this.dataForm.chapter;
          }
          if (this.dataForm.knowledgetype == "视频单课") {
            this.dataForm.detilurl =
              `sjgj://coursedetail/0/` + this.dataForm.chapter;
          }

          createKnowledge(this.dataForm)
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
        this.$refs["editordetail"].setContent(this.dataForm.knowledgedetil);
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          if (this.dataForm.knowledgetype == "视频课程") {
            this.dataForm.detilurl = "sjgj://courses/" + this.dataForm.chapter;
          }
          if (this.dataForm.knowledgetype == "视频单课") {
            this.dataForm.detilurl =
              `sjgj://coursedetail/0/` + this.dataForm.chapter;
          }
          updateKnowledge(this.dataForm)
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
      deleteKnowledge(row)
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
    handleDownload(id) {
      let query = {
        id
      };
      this.downloadLoading = true;
      download(query).then(res => {
        this.downloadLoading = false;
        var blob = new Blob([res.data], {
          type: "application/vnd.ms-excel;charset=UTF-8"
        });

        var date = new Date();
        var fdate = formatDate(date, "yyyyMMddhhmmss");
        // 针对于IE浏览器的处理, 因部分IE浏览器不支持createObjectURL
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob, "二维码列表" + fdate);
        } else {
          var downloadElement = document.createElement("a");
          var href = window.URL.createObjectURL(blob); // 创建下载的链接
          downloadElement.href = href;
          downloadElement.download = "二维码列表" + fdate; // 下载后文件名
          document.body.appendChild(downloadElement);
          downloadElement.click(); // 点击下载
          document.body.removeChild(downloadElement); // 下载完成移除元素
          window.URL.revokeObjectURL(href); // 释放掉blob对象
        }
      });
    }
  }
};
</script>
