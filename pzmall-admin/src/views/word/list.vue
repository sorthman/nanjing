<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.typeid"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择词库类型"
      >
        <el-option
          v-for="item in listtype"
          :key="item.id"
          :label="item.mainType+'——'+item.subType"
          :value="item.id"
        />
      </el-select>
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入单词"
      />
      <el-button
        v-permission="['GET /adminapi/word/list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['POST /adminapi/word/create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        v-permission="['POST /adminapi/word/upload']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleUpload"
      >导入词库</el-button>
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
      <el-table-column align="center" label="单词" prop="word" />

      <el-table-column align="center" label="英式发音" prop="britishPronunciation" />

      <el-table-column align="center" label="美式发音" prop="americanPronunciation" />

      <el-table-column align="center" label="精彩例句" prop="exampleSentence">
        <template slot-scope="scope">{{scope.row.exampleSentence |ellipsis }}</template>
      </el-table-column>

      <el-table-column align="center" label="简明解释" prop="conciseExplanation">
        <template slot-scope="scope">{{scope.row.conciseExplanation |ellipsis }}</template>
      </el-table-column>

      <el-table-column align="center" label="牛津词典" prop="oxfordDictionary">
        <template slot-scope="scope">{{scope.row.oxfordDictionary |ellipsis }}</template>
      </el-table-column>

      <el-table-column align="center" label="朗文解释" prop="langwenExplanation">
        <template slot-scope="scope">{{scope.row.langwenExplanation |ellipsis }}</template>
      </el-table-column>

      <!-- <el-table-column align="center" label="视频讲解" prop="videoExplanation">
        <template slot-scope="scope">
          <video id="myvideo" :src="scope.row.videoExplanation">
            your browser does not support the video tag
        </video>
        </template>
      </el-table-column>-->

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/word/update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['POST /adminapi/word/delete']"
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
        <el-form-item label="词库类型" prop="typeId">
          <el-select
            v-model="dataForm.typeId"
            clearable
            style="width: 200px"
            class="filter-item"
            placeholder="请选择类型"
          >
            <el-option
              v-for="item in listtype"
              :key="item.id"
              :label="item.mainType+'——'+item.subType"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="单词" prop="word">
          <el-input v-model="dataForm.word" />
        </el-form-item>
        <el-form-item label="英式发音" prop="britishPronunciation">
          <el-input v-model="dataForm.britishPronunciation" />
        </el-form-item>
        <el-form-item label="英式发音音频" prop="britishPronunciationMp3">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadEnUrl"
            class="avatar-uploader"
            accept=".mp3"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input
            style="width:500px"
            disabled
            v-model="dataForm.britishPronunciationMp3"
            placeholder
          />
        </el-form-item>
        <el-form-item label="美式发音" prop="americanPronunciation">
          <el-input v-model="dataForm.americanPronunciation" />
        </el-form-item>
        <el-form-item label="美式发音音频" prop="americanPronunciationMp3">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadUsUrl"
            class="avatar-uploader"
            accept=".mp3"
          >
            <i class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input
            style="width:500px"
            disabled
            v-model="dataForm.americanPronunciationMp3"
            placeholder
          />
        </el-form-item>
        <el-form-item label="精彩例句" prop="exampleSentence">
          <!-- <tinymce :height="400" ref="editordetail" v-model="dataForm.exampleSentence"></tinymce> -->
          <el-input type="textarea"  :rows="5" v-model="dataForm.exampleSentence" />
        </el-form-item>
        <el-form-item label="简明解释" prop="conciseExplanation">
          <tinymce :height="400" ref="editorConsise" v-model="dataForm.conciseExplanation"></tinymce>
          <!-- <el-input type="textarea" :rows="5" v-model="dataForm.conciseExplanation" /> -->
        </el-form-item>
        <el-form-item label="牛津词典" prop="oxfordDictionary">
          <tinymce :height="400" ref="editorOxford" v-model="dataForm.oxfordDictionary"></tinymce>
          <!-- <el-input type="textarea" :rows="5" v-model="dataForm.oxfordDictionary" /> -->
        </el-form-item>
        <el-form-item label="朗文解释" prop="langwenExplanation">
          <tinymce :height="400" ref="editorLangwen" v-model="dataForm.langwenExplanation"></tinymce>
          <!-- <el-input type="textarea" :rows="5" v-model="dataForm.langwenExplanation" /> -->
        </el-form-item>
        <el-form-item label="讲解视频" prop="videoExplanation">
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
          <el-input style="width:500px" disabled v-model="dataForm.videoExplanation" placeholder />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!-- 批量导入对话框 -->
    <el-dialog width="70%" title="导入词库" :visible.sync="dialogUploadFormVisible">
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
            :on-success="uploadWordsUrl"
            class="avatar-uploader"
            accept=".xls"
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
        name: "",
        desc: "",
        floorPrice: undefined,
        picUrl: undefined,
        britishPronunciationMp3: "",
        americanPronunciationMp3: "",
        videoExplanation: ""
      },
      filename: "",
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
  created() {
    this.getList();
    this.getListType();
  },
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
      let query = {
        filename: this.filename
      };
      this.importLoading = true;
      uploadWord(query)
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
          this.$notify.error({
            title: "失败",
            message: response.data.msg
          });
        });
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      
      this.$nextTick(() => {
      
        this.$refs.refVideo.clearFiles();
        this.$refs.editorConsise.setContent(this.dataForm.conciseExplanation)
        this.$refs.editorOxford.setContent(this.dataForm.oxfordDictionary)
        this.$refs.editorLangwen.setContent(this.dataForm.langwenExplanation)
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
