<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      年龄区间：<el-input
        v-model="listQuery.sage"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入开始年龄"
      />~
      <el-input
        v-model="listQuery.eage"
        clearable
        class="filter-item"
        style="width: 120px;"
        placeholder="请输入结束年龄"
      /><br/>
      <el-input
        v-model="listQuery.username"
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

      <el-input
        v-model="listQuery.street"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="请输入街道"
      />

      <el-input
        v-model="listQuery.community"
        clearable
        class="filter-item"
        style="width: 150px;"
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
      </el-select> -->

      <br/>

      <el-date-picker
        v-model="listQuery.arrivedate"
        type="date"
        style="width: 150px"
        class="filter-item"
        placeholder="来宁时间">
      </el-date-picker>

      <el-select
        v-model="listQuery.addsource"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="数据来源"
      >
        <el-option v-for="(key, value) in sourceMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.iftransferarea"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="转过街道"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.iftransferstreet"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="转过区域"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifsafe"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="解除隔离"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.healthinfo"
        multiple
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="目前状况"
      >
        <el-option v-for="(key, value) in healthMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifwh"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="去过武汉"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <br/>
      <el-select
        v-model="listQuery.ifhb"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="去过湖北"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifleavenj"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="是否离宁"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifadmin"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="正在管理"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifover"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="超过14天"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.iflose"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="追访不到"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
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

      <el-table-column min-width="120px" align="center" label="身份证号" prop="idcard" />
      <el-table-column align="center" label="是否在行政区" prop="ifstay" />
      <el-table-column align="center" label="是否去过武汉" prop="ifwh" />
      <el-table-column align="center" label="是否去过湖北" prop="ifhb" />
      <el-table-column align="center" label="是否离开南京" prop="ifleavenj" />

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
      <el-table-column min-width="200px" align="center" label="健康信息" prop="healthinfo" /> -->

      <el-table-column
        fixed="right"
        align="center"
        label="操作"
        width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="handleUpdate(scope.row)"
          >详情查看</el-button>
          
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
  </div>
</template>

<script>
import { fetchList,download } from "@/api/user";
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
  自查: "自查",
  公安: "公安",
  教育: "教育"
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

export default {
  name: "User",
  components: { Pagination },
  data() {
    return {
      sexMap: sexMap,
      statusMap:statusMap,
      sourceMap:sourceMap,
      healthMap:healthMap,
      list: null,
      alllist:null,
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
      listQueryAll: {
        page: 1,
        limit: 100000,
        username: undefined,
        mobile: undefined,
        sort: "id",
        order: "desc"
      },
      downloadLoading: false,
      genderDic: ["未知", "男", "女"],
      levelDic: ["普通用户", "VIP用户", "高级VIP用户"],
      statusDic: ["可用", "禁用", "注销"]
    };
  },
  created() {
    this.getList();
    this.getListAll();
  },
  methods: {
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery)
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
      
      fetchList(this.listQueryAll)
        .then(response => {
          this.alllist = response.data.data.list;
        })
        .catch(() => {
          this.alllist = [];
        });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ["编号", "姓名", "身份证", "手机号", "民族", "性别", "生日", "目前居住地", "是否武汉", "交通方式", "交通班次", "出发地", "目的地", "离开日期", "武汉居住地", "停靠地点", "当前体温", "健康信息", "是否发热", "是否咳嗽", "是否接触发热人员", "是否接触感染人员", "同行人员姓名", "同行人员电话", "登记时间", "登记方式", "所属社区", "GPS定位信息", "登记账号"]
        const filterVal = ['id', 'name', 'idcard', 'phone', 'nation', 'sex', 'birthday', 'currentaddress', 'ifwh','traffictype','trafficinfo','startaddress','endaddress','leavedate','whaddress','stopaddress','temperature','healthinfo','ifhot','ifkesou','ifcontactHot','ifcontactSars','contactName','contactPhone','addtime','addsource','njcommunity','gpsaddrrss','addaccount']
        excel.export_json_to_excel2(tHeader, this.alllist, filterVal, '登记列表')
        this.downloadLoading = false
      })
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
