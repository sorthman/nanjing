<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      年龄区间：
      <el-input
        v-model="listQuery.sage"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入开始年龄"
      />~
      <el-input
        v-model="listQuery.eage"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入结束年龄"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>
      <br />
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入姓名"
      />
      <el-select
        v-model="listQuery.sex"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="请选择性别"
      >
        <el-option v-for="(key, value) in sexMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-input
        v-model="listQuery.phone"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入联系方式"
      />
      <el-input
        v-model="listQuery.idcard"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入身份证"
      />

      <el-date-picker
        v-model="listQuery.addtime"
        type="date"
        style="width: 150px"
        class="filter-item"
        value-format="yyyy-MM-ddTHH:mm:ss"
        placeholder="登记时间"
      ></el-date-picker>

      <br />

      <el-input
        v-model="listQuery.street"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入街道"
      />
      <el-input
        v-model="listQuery.community"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入社区"
      />

      <!-- <el-select
        v-model="listQuery.sex"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="请选择街道"
      >
        <el-option v-for="(key, value) in sexMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.sex"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="请选择社区"
      >
        <el-option v-for="(key, value) in sexMap" :key="key" :label="key" :value="value" />
      </el-select>-->

      <!-- <el-input
        v-model="listQuery.addsource"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="数据来源"
      />-->

      <el-date-picker
        v-model="listQuery.arrivedate"
        type="date"
        style="width: 150px"
        class="filter-item"
        value-format="yyyy-MM-ddTHH:mm:ss"
        placeholder="到访时间"
      ></el-date-picker>

      <el-input
        v-model="listQuery.province"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入省份"
      />
      <el-input
        v-model="listQuery.city"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入城市"
      />
      <el-input
        v-model="listQuery.area"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入区域"
      />

      <el-select
        v-model="listQuery.ifwh"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="重点疫区"
      >
        <el-option v-for="(key, value) in ifwhMap" :key="key" :label="key" :value="value" />
      </el-select>
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
      <el-table-column align="center" width="100px" label="编号" prop="id" sortable />
      <!-- <el-table-column align="center" label="登记方式" prop="addsource" /> -->

      <el-table-column align="center" label="姓名" prop="name" />
      <el-table-column min-width="120px" align="center" label="手机号" prop="phone" />
      <el-table-column align="center" label="性别" prop="sex" />
      <el-table-column align="center" label="年龄" prop="age" />
      <el-table-column min-width="200px" align="center" label="身份证号" prop="idcard">
        <template slot-scope="scope">{{scope.row.idcard | idcardFilter}}</template>
      </el-table-column>

      <el-table-column align="center" label="生日" prop="birthday" />
      <el-table-column align="center" label="社区" prop="community" />
      <el-table-column align="center" label="常驻地址" min-width="300px" prop="liveaddress" />

      <el-table-column align="center" min-width="120px" label="到达时间" prop="arrivedate">
        <template
          slot-scope="scope"
        >{{scope.row.arrivedate?scope.row.arrivedate.substring(0,10):""}}</template>
      </el-table-column>

      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="城市" prop="city" />
      <el-table-column align="center" label="区域" prop="district" />
      <el-table-column align="center" label="14天内是否去过或经过重点疫区" prop="ifwh" />
      <el-table-column align="center" label="停留时间" prop="whtime" />

      <el-table-column align="center" label="是否外出" prop="ifgoout" />
      <el-table-column align="center" label="外出地址" prop="goaddress" />
      <el-table-column align="center" label="其他信息" prop="other" />
      <el-table-column align="center" label="添加时间" prop="addtime" />

      <!-- <el-table-column align="center" label="目前居住区域" prop="currentaddress" />
      <el-table-column align="center" label="武汉居住区域" prop="whaddress" />

      <el-table-column align="center" label="来宁交通方式" prop="traffictype" />
      <el-table-column align="center" label="交通班次信息" prop="trafficinfo" />
      <el-table-column align="center" label="出发地点" prop="startaddress" />
      <el-table-column align="center" label="目的地点" prop="endaddress" />
      <el-table-column min-width="180px" align="center" label="离开日期" prop="leavedate" />
      <el-table-column align="center" label="停靠地点" prop="stopaddress" />
      <el-table-column align="center" label="同行人姓名" prop="contact_name" />
      <el-table-column align="center" label="同行人号码" prop="contact_phone" />

      <el-table-column align="center" label="当前体温" prop="temperature" />
      <el-table-column align="center" label="是否发热" prop="ifhot" />
      <el-table-column align="center" label="是否咳嗽" prop="ifkesou" />
      <el-table-column align="center" label="是否接触发热人员" prop="ifcontactHot" />
      <el-table-column align="center" label="是否接触感染人员" prop="ifcontactSars" />
      <el-table-column min-width="200px" align="center" label="健康信息" prop="healthinfo" />-->

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!-- <el-button size="small" type="primary" @click="handleDetail(scope.row)">详情查看</el-button>
          <el-button size="small" type="primary" @click="handleUpdate(scope.row)">编辑信息</el-button> -->
          <el-button
            v-permission="['POST /adminapi/user/delete']"
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
    <el-dialog width="70%" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 600px; margin-left:50px;"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dataForm.name" />
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="dataForm.idcard" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="dataForm.phone" />
        </el-form-item>
        <el-form-item label="常驻地址" prop="liveaddress">
          <el-input v-model="dataForm.liveaddress" />
        </el-form-item>
        <el-form-item label="停留武汉时间" prop="whtime">
          <el-input v-model="dataForm.whtime" />
        </el-form-item>
        <el-form-item label="来宁时间" prop="arrivedate">
          <el-date-picker
            v-model="dataForm.arrivedate"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="来宁时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="街道领导信息" prop="jdname">
          <el-input v-model="dataForm.jdname" />
        </el-form-item>
        <el-form-item label="街道干部信息" prop="gbname">
          <el-input v-model="dataForm.gbname" />
        </el-form-item>
        <el-form-item label="社工信息" prop="sgname">
          <el-input v-model="dataForm.sgname" />
        </el-form-item>
        <el-form-item label="社区民警信息" prop="mjname">
          <el-input v-model="dataForm.mjname" />
        </el-form-item>
        <el-form-item label="医疗卫生员信息" prop="ylname">
          <el-input v-model="dataForm.ylname" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog width="80%" title="详情查看" :visible.sync="dialogSignFormVisible">
      <el-card style="line-height:30px;" class="box-card">
        <h3 style="color:orange">{{detailModel.name}} / {{detailModel.sex}} / {{detailModel.age}}</h3>

        <el-row :gutter="40" class="panel-group">
          <el-col :xs="12" :sm="20" :lg="12" class="card-panel-col">
            联系电话：{{detailModel.phone}}
            <br />
            身份证：{{detailModel.idcard }}
            <br />
            所在街道：{{detailModel.street}}
            <br />
            所在社区：{{detailModel.njcommunity}}
            <br />
            详细地址：{{detailModel.liveaddress}}
            <br />
            人在不在{{area}}：{{detailModel.ifstay}}
            <br />
            是否去过武汉：{{detailModel.ifwh}}
            <br />
            是否离宁：{{detailModel.ifleavenj}}
            <br />
            是否管理：{{detailModel.ismanage}}
          </el-col>
          <el-col :xs="12" :sm="12" :lg="12" class="card-panel-col">
            是否追访不到：{{detailModel.iflose}}
            <br />
            停留武汉时间：{{detailModel.whtime}}
            <br />
            来宁时间：{{detailModel.arrivedate}}
            <br />
            目前症状：{{detailModel.healthinfo}}
            <br />
            就诊情况：{{detailModel.liveaddress}}
            <br />
            数据来源：{{detailModel.addsource}}
            <br />
          </el-col>
        </el-row>
        备注信息：{{detailModel.remark}}
      </el-card>

      <br />
      <br />
      <el-card style="line-height:30px;" class="box-card" v-for="item in signlist" :key="item.id">
        <el-row :gutter="40" class="panel-group">
          <el-col :xs="12" :sm="12" :lg="12" class="card-panel-col">
            <b>
              {{item.signtime.substring(0,10)}}申报信息
              <br />
            </b>
            人在不在{{area}}：{{item.ifstay}}
            <br />
            是否追访不到：{{item.iflose}}
            <br />
            是否有发热：{{item.ifhot}}
            <br />
            是否有咳嗽：{{item.ifkesou}}
            <br />
            其他症状：{{item.healthinfo}}
            <br />
            就诊情况：{{item.docinfo}}
            <br />
            是否解除隔离：{{item.ifsafe}}
            <br />
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <br />
            是否离宁：{{item.ifleavenj}}
            <br />
            原因：{{item.loseinfo}}
            <br />
            最高体温：{{item.temperature}}
            <br />
          </el-col>
          <el-col
            style="color:#555555;float:right"
            :xs="12"
            :sm="12"
            :lg="6"
            class="card-panel-col"
          >
            申报时间：{{item.signtime}}
            <br />
            申报人：{{item.reportname}}
            <br />
            申报人电话：{{item.reportphone}}
            <br />
          </el-col>
        </el-row>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import { listUserOut,deleteUser } from "@/api/njuser";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import { formatDate } from "@/utils/time.js";

const sexMap = {
  男: "男",
  女: "女"
};

const statusMap = {
  是: "是",
  否: "否"
};

const sourceMap = {
  自主: "自主",
  自查: "自查",
  公安: "公安",
  教育: "教育",
  漫游: "漫游"
};

const userTypeMap = {
  武汉: "武汉",
  湖北: "湖北",
  温州: "温州"
};

const ifwhMap = {
  否: "否",
  武汉: "武汉",
  湖北: "湖北",
  温州: "温州",
  安徽: "安徽",
  河南: "河南"
};

const zhuantaiMap = {
  居家观察: "居家观察",
  集中观察: "集中观察",
  确诊: "确诊"
};

const healthMap = {
  发热: "发热",
  咳嗽: "咳嗽",
  乏力: "乏力",
  咳痰: "咳痰",
  鼻塞: "鼻塞",
  流涕: "流涕",
  寒战: "寒战",
  气促: "气促",
  呼吸困难: "呼吸困难",
  咽痛: "咽痛",
  头痛: "头痛",
  胸闷: "胸闷"
};

const glyyMap = {
  "14天内往来重点疫区": "14天内往来重点疫区",
  其它: "其它"
};

const zdyqMap = {
  武汉: "武汉",
  湖北: "湖北(非武汉)",
  温州: "温州",
  其他: "其他"
};

const stopinfoMap = {
  转送集中隔离: "转送集中隔离",
  转送定点医院: "转送定点医院",
  其它: "其它"
};

export default {
  name: "User",
  components: { Pagination },
  data() {
    return {
      sexMap: sexMap,
      area: "",
      statusMap: statusMap,
      sourceMap: sourceMap,
      healthMap: healthMap,
      userTypeMap: userTypeMap,
      zhuantaiMap: zhuantaiMap,
      ifwhMap: ifwhMap,
      glyyMap,
      zdyqMap,
      stopinfoMap,
      list: null,
      alllist: null,
      signlist: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        username: undefined,
        mobile: undefined,
        sort: "id",
        order: "desc"
      },
      dataForm: {
        id: undefined,
        name: ""
      },
      detailModel: {
        id: undefined,
        name: ""
      },
      textMap: {
        update: "编辑",
        create: "创建"
      },
      dialogFormVisible: false,
      dialogStatus: "",
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }]
      },
      dialogSignFormVisible: false,
      downloadLoading: false,
      genderDic: ["未知", "男", "女"],
      levelDic: ["普通用户", "VIP用户", "高级VIP用户"],
      statusDic: ["可用", "禁用", "注销"]
    };
  },
  created() {
    this.getList();
    this.getListAll();
    this.area = window.localStorage.getItem("adminarea");
  },
  filters: {
    idcardFilter(idcard) {
      if (idcard) {
        return idcard.substring(0, 3) + "********" + idcard.substring(15);
      } else {
        return "";
      }
    }
  },
  methods: {
    getList() {
      this.listLoading = true;
      listUserOut(this.listQuery)
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
    getListAll() {
      let listQueryAll = Object.assign({}, this.listQuery);
      listQueryAll.limit = 100000;
      listUserOut(listQueryAll)
        .then(response => {
          this.alllist = response.data.data.list;
        })
        .catch(() => {
          this.alllist = [];
        });
    },
    getSignList(id) {
      let query = {
        uid: id,
        page: 1,
        limit: 10000,
        sort: "id",
        order: "desc"
      };
      listSign(query)
        .then(response => {
          this.signlist = response.data.data;
        })
        .catch(() => {
          this.signlist = [];
        });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
      this.getListAll();
    },
    handleDetail(row) {
      this.getSignList(row.id);
      this.dialogSignFormVisible = true;
      this.detailModel = Object.assign({}, row);
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
          updateUser(this.dataForm)
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

      this.$confirm('此操作删除该用户,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let query = {
          uid: row.id,
          tag: "outsideuser",
          remark: "后台删除"
        }
        deleteUser(query)
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
      })

      
    },
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "姓名",
          "手机号",
          "性别",
          "年龄",
          "身份证号",
          "生日",
          "社区",
          "常住地址",
          "到达时间",
          "省份",
          "城市",
          "区域",
          "14天内是否去过或经过重点疫区",
          "停留时间",
          "是否外出",
          "外出地址",
          "其他信息",
          "添加时间"
        ];
        const filterVal = [
          "name",
          "phone",
          "sex",
          "age",
          "idcard",
          "birthday",
          "community",
          "liveaddress",
          "arrivedate",
          "province",
          "city",
          "district",
          "ifwh",
          "whtime",
          "ifgoout",
          "goaddress",
          "other",
          "addtime"
        ];
        excel.export_json_to_excel2(
          tHeader,
          this.alllist,
          filterVal,
          "外来人员列表"
        );
        this.downloadLoading = false;
      });
    }
    // handleDownload() {

    //   this.downloadLoading = true;
    //   download(this.listQuery).then(res => {
    //     this.downloadLoading = false;
    //     var blob = new Blob([res.data], {
    //       type: "application/vnd.ms-excel;charset=UTF-8"
    //     });

    //     var date = new Date();
    //     var fdate = formatDate(date, "yyyyMMddhhmmss");
    //     // 针对于IE浏览器的处理, 因部分IE浏览器不支持createObjectURL
    //     if (window.navigator && window.navigator.msSaveOrOpenBlob) {
    //       window.navigator.msSaveOrOpenBlob(blob, "登记列表" + fdate);
    //     } else {
    //       var downloadElement = document.createElement("a");
    //       var href = window.URL.createObjectURL(blob); // 创建下载的链接
    //       downloadElement.href = href;
    //       downloadElement.download = "登记列表" + fdate; // 下载后文件名
    //       document.body.appendChild(downloadElement);
    //       downloadElement.click(); // 点击下载
    //       document.body.removeChild(downloadElement); // 下载完成移除元素
    //       window.URL.revokeObjectURL(href); // 释放掉blob对象
    //     }
    //   });
    // }
  }
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.box-card {
  line-height: 40px;
}
</style>