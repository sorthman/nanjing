<template>
  <div class="app-container">
    <el-form
      ref="uploaddataForm"
      status-icon
      label-position="left"
      label-width="150px"
      style="width: 600px; margin-left:50px;"
    >
      <el-form-item label="请选择数据类型" prop="addsource">
        <el-select
          v-model="addsource"
          clearable
          style="width: 150px"
          class="filter-item"
          placeholder="数据来源"
        >
          <el-option v-for="(key, value) in sourceMap" :key="key" :label="key" :value="value" />
        </el-select>
        <a v-show="addsource!='省疾控'" href="http://ning.pzunion.cn/manage/demo.xls">下载模板</a>
        <a v-show="addsource==='省疾控'" href="http://ning.pzunion.cn/manage/demosjk.xlsx">下载省疾控模板</a>
      </el-form-item>
      <el-form-item label="请上传文件" prop="filename">
        <el-upload
          :headers="headers"
          :action="uploadPath"
          :show-file-list="false"
          :on-success="uploadWordsUrl"
          class="avatar-uploader"
          accept=".xls,.xlsx"
        >
          <i class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
        <el-input style="width:500px" disabled v-model="filename" placeholder />
      </el-form-item>
      <el-form-item label prop="result">
        <el-button :loading="importLoading" type="primary" @click="uploadData">确认导入</el-button>
      </el-form-item>
      <el-form-item label="导入结果" prop="result">
        <p v-html="importresult"></p>
      </el-form-item>
    </el-form>
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
import {
  listWord,
  createWord,
  updateWord,
  deleteWord,
  uploadWord
} from "@/api/word";
import { importUser, importSJKUser } from "@/api/user";
import { listBook } from "@/api/wordtype";
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
  是: "是",
  否: "否"
};

const sourceMap = {
  自查: "自查",
  公安: "公安",
  教育: "教育",
  漫游: "漫游",
  省疾控: "省疾控",
  企业复工: "企业复工"
};

export default {
  name: "Brand",
  filters: {
    ellipsis(value) {
      if (!value) return "";
      if (value.length > 40) {
        return value.slice(0, 40) + "...";
      }
      return value;
    }
  },
  components: { Pagination, Tinymce },
  data() {
    return {
      uploadPath,
      typeMap,
      statusMap,
      sourceMap,
      list: [],
      listtype: [],
      total: 0,
      listLoading: true,
      importLoading: false,
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
        addsource: ""
      },
      filename: "",
      addsource: "",
      importresult: "",
      dialogUploadFormVisible: false,
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
  created() {},
  methods: {
    getList() {
      this.listLoading = true;
      listWord(this.listQuery)
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
    getListType() {
      let param = {
        page: 1,
        limit: 100
      };
      listBook(param)
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
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: "",
        imgurl: undefined,
        britishPronunciationMp3: "",
        americanPronunciationMp3: "",
        videoExplanation: ""
      };
    },
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
        this.$refs.refVideo.clearFiles();
      });
    },
    handleUpload() {
      this.dialogUploadFormVisible = true;
      this.filename = "";
      this.$nextTick(() => {
        this.$refs["uploaddataForm"].clearValidate();
      });
    },
    uploadEnUrl: function(response) {
      this.dataForm.britishPronunciationMp3 = response.data.url;
    },
    uploadUsUrl: function(response) {
      this.dataForm.americanPronunciationMp3 = response.data.url;
    },
    uploadVideoUrl: function(response) {
      this.dataForm.videoExplanation = response.data.url;
    },
    uploadWordsUrl: function(response) {
      this.filename = response.data.key;
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createWord(this.dataForm)
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
      if (!this.addsource || !this.filename) {
        this.$notify.error({
          title: "失败",
          message: "必须选择数据类型和上传文件"
        });
        return;
      }

      let query = {
        addsource: this.addsource,
        filename: this.filename
      };
      this.importLoading = true;
      if (this.addsource === "省疾控") {
        importSJKUser(query)
          .then(response => {
            this.importLoading = false;
            if (response.data.code === 0) {
              this.importresult = response.data.data;
              this.$notify.success({
                title: "成功",
                message: "导入成功"
              });
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
      } else {
        importUser(query)
          .then(response => {
            this.importLoading = false;
            if (response.data.code === 0) {
              this.importresult = response.data.data;
              this.$notify.success({
                title: "成功",
                message: "导入成功"
              });
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
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;

      this.$nextTick(() => {
        this.$refs.refVideo.clearFiles();
        this.$refs.editorConsise.setContent(this.dataForm.conciseExplanation);
        this.$refs.editorOxford.setContent(this.dataForm.oxfordDictionary);
        this.$refs.editorLangwen.setContent(this.dataForm.langwenExplanation);
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          updateWord(this.dataForm)
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
      deleteWord(row)
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
