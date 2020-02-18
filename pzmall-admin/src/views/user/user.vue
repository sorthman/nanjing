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
        v-permission="['POST /adminapi/user/create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >登记用户</el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>

      <br />
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

      <el-date-picker
        v-model="listQuery.arrivedate"
        type="date"
        style="width: 120px"
        class="filter-item"
        value-format="yyyy-MM-ddTHH:mm:ss"
        placeholder="来宁时间"
      ></el-date-picker>
      <br />

      <!-- <el-input
        v-model="listQuery.addsource"
        clearable
        class="filter-item"
        style="width: 150px;"
        placeholder="数据来源"
      />-->

      <el-select
        v-model="listQuery.addsource"
        clearable
        multiple
        collapse-tags
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
        placeholder="转过区域"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.iftransferstreet"
        clearable
        style="width: 150px"
        class="filter-item"
        placeholder="转过街道"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifsafe"
        clearable
        multiple
        collapse-tags
        style="width: 150px"
        class="filter-item"
        placeholder="当前状态"
      >
        <el-option v-for="(key, value) in zhuantaiMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.healthinfo"
        multiple
        clearable
        collapse-tags
        style="width: 150px"
        class="filter-item"
        placeholder="目前状况"
      >
        <el-option v-for="(key, value) in healthMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.usertype"
        clearable
        multiple
        collapse-tags
        style="width: 120px"
        class="filter-item"
        placeholder="用户类型"
      >
        <el-option v-for="(key, value) in userTypeMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifwh"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="是否去过"
      >
        <el-option v-for="(key, value) in statusLeaveMap" :key="key" :label="key" :value="value" />
      </el-select>
      <br />

      <el-select
        v-model="listQuery.ifleavenj"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="是否离宁"
      >
        <el-option v-for="(key, value) in statusLeaveMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifadmin"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="正在管理"
      >
        <el-option v-for="(key, value) in statusLeaveMap" :key="key" :label="key" :value="value" />
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
        <el-option v-for="(key, value) in statusLeaveMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-select
        v-model="listQuery.ifstay"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="是否当前区域"
      >
        <el-option v-for="(key, value) in statusLeaveMap" :key="key" :label="key" :value="value" />
      </el-select>

      <el-date-picker
        v-model="listQuery.addtime"
        type="date"
        style="width: 120px"
        class="filter-item"
        value-format="yyyy-MM-ddTHH:mm:ss"
        placeholder="登记时间"
      ></el-date-picker>

      <el-date-picker
        v-model="listQuery.managetime"
        type="date"
        style="width: 150px"
        class="filter-item"
        value-format="yyyy-MM-ddTHH:mm:ss"
        placeholder="管理开始时间"
      ></el-date-picker>

      <el-select
        v-model="listQuery.level"
        clearable
        style="width: 120px"
        class="filter-item"
        placeholder="登记等级"
      >
        <el-option v-for="(key, value) in levelMap" :key="key" :label="key" :value="value" />
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

      <el-table-column align="center" label="等级" prop="level" />
      <el-table-column align="center" min-width="120px" label="管理时间" prop="managetime">
        <template
          slot-scope="scope"
        >{{scope.row.managetime?scope.row.managetime.substring(0,10):""}}</template>
      </el-table-column>

      <el-table-column align="center" label="姓名" prop="name" />
      <el-table-column min-width="120px" align="center" label="手机号" prop="phone" />
      <el-table-column align="center" label="性别" prop="sex" />
      <el-table-column align="center" label="年龄" prop="age" />
      <el-table-column min-width="200px" align="center" label="身份证号" prop="idcard">
        <template slot-scope="scope">{{scope.row.idcard | idcardFilter}}</template>
      </el-table-column>

      <el-table-column align="center" label="是否在行政区" prop="ifstay" />
      <el-table-column align="center" label="用户类型" prop="usertype" />
      <el-table-column align="center" label="是否去过疫区" prop="ifwh" />
      <el-table-column align="center" label="是否接触疫区人员" prop="ifcontactSars" />
      <el-table-column align="center" label="是否接触发热咳嗽" prop="ifcontactHot" />

      <el-table-column align="center" label="是否离开南京" prop="ifleavenj" />
      <el-table-column align="center" label="是否正在管理" prop="ismanage" />
      <el-table-column align="center" label="是否超过14天" prop="ifover" />
      <el-table-column align="center" label="是否追访不到" prop="iflose" />
      <el-table-column align="center" label="追访不到原因" prop="loseinfo" />
      <el-table-column align="center" label="街道" prop="street" />
      <el-table-column align="center" label="社区" prop="njcommunity" />

      <el-table-column align="center" label="常驻地址" min-width="300px" prop="liveaddress" />
      <el-table-column align="center" min-width="300px" label="停留武汉时间" prop="whtime" />
      <el-table-column align="center" min-width="120px" label="来宁时间" prop="arrivedate">
        <template
          slot-scope="scope"
        >{{scope.row.arrivedate?scope.row.arrivedate.substring(0,10):""}}</template>
      </el-table-column>
      <el-table-column align="center" label="是否发热" prop="ifhot" />
      <el-table-column align="center" label="是否咳嗽" prop="ifkesou" />
      <el-table-column align="center" min-width="300px" label="目前症状" prop="healthinfo" />
      <el-table-column align="center" label="就诊情况" prop="docinfo" />
      <el-table-column align="center" label="数据来源" prop="addsource" />
      <el-table-column align="center" label="备注" min-width="600px" prop="remark" />

      <el-table-column align="center" label="街道领导" prop="jdname" />
      <el-table-column align="center" label="街道干部" prop="gbname" />
      <el-table-column align="center" label="社工" prop="sgname" />
      <el-table-column align="center" label="社区民警" prop="mjname" />
      <el-table-column align="center" label="医疗卫生员" prop="ylname" />
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
        width="250"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="handleDetail(scope.row)">详情查看</el-button>
          <el-button size="small" type="primary" @click="handleUpdate(scope.row)">编辑信息</el-button>
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
        label-width="120px"
        style="width: 600px; margin-left:50px;"
      >
        <el-form-item v-if="dialogStatus=='create'" label="数据来源" prop="addsource">
          <el-select v-model="dataForm.addsource" clearable class="filter-item" placeholder="数据来源">
            <el-option v-for="(key, value) in addsourceMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dataForm.name" />
        </el-form-item>
        <el-form-item label="身份证" prop="idcard">
          <el-input v-model="dataForm.idcard" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="dataForm.phone" />
        </el-form-item>
        <el-form-item label="户籍或常住地址" prop="liveaddress">
          <el-input v-model="dataForm.liveaddress" />
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-select v-model="dataForm.sex" clearable class="filter-item" placeholder="请选择性别">
            <el-option v-for="(key, value) in sexMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="dataForm.age" />
        </el-form-item>

        <el-form-item label="用户类型" prop="usertype">
          <el-select v-model="dataForm.usertype" clearable class="filter-item" placeholder="用户类型">
            <el-option v-for="(key, value) in userTypeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>

        <el-form-item label="是否去过" prop="ifwh">
          <el-select v-model="dataForm.ifwh" clearable class="filter-item" placeholder="是否去过">
            <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>

        <el-form-item label="停留时间" prop="whtime">
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
        <el-form-item label="开始管理时间" prop="managetime">
          <el-date-picker
            v-model="dataForm.managetime"
            type="date"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="开始管理时间"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="入住状态" prop="livetype">
          <el-radio-group v-model="dataForm.livetype">
            <el-radio label="常住">常住</el-radio>
            <el-radio label="暂住">暂住</el-radio>
          </el-radio-group>
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
        <el-form-item label="备注（跟踪详情）" prop="remark">
          <el-input type="textarea" :rows="6" v-model="dataForm.remark" />
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
import {
  fetchList,
  download,
  listSign,
  updateUser,
  createUser
} from "@/api/user";
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

const statusLeaveMap = {
  是: "是",
  否: "否",
  不确定: "不确定"
};

const sourceMap = {
  自主: "自主",
  自查: "自查",
  公安: "公安",
  教育: "教育",
  漫游: "漫游",
  省疾控: "省疾控",
  企业复工: "企业复工",
  外来人员登记: "外来人员登记",
  宁归来: "宁归来"
};

const addsourceMap = {
  自查: "自查",
  公安: "公安",
  教育: "教育",
  漫游: "漫游",
  省疾控: "省疾控",
  企业复工: "企业复工",
  外来人员登记: "外来人员登记",
  宁归来: "宁归来"
};

const levelMap = {
  红色: "红色",
  黄色: "黄色",
  蓝色: "蓝色"
};

const userTypeMap = {
  武汉: "武汉",
  湖北: "湖北",
  温州: "温州",
  安徽: "安徽",
  河南: "河南",
  江苏: "江苏",
  其它地区: "其它地区",
  无接触: "无接触"
};

const zhuantaiMap = {
  居家观察: "居家观察",
  集中观察: "集中观察",
  确诊: "确诊",
  无: "无"
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
      area: "",
      statusMap: statusMap,
      statusLeaveMap: statusLeaveMap,
      sourceMap: sourceMap,
      addsourceMap,
      healthMap: healthMap,
      userTypeMap: userTypeMap,
      zhuantaiMap: zhuantaiMap,
      levelMap: levelMap,
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
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        phone: [
          { required: true, message: "联系方式不能为空", trigger: "blur" }
        ],
        idcard: [
          { required: true, message: "身份证不能为空", trigger: "blur" }
        ],
        liveaddress: [
          { required: true, message: "常住地址不能为空", trigger: "blur" }
        ]
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
      let listQueryAll = Object.assign({}, this.listQuery);
      listQueryAll.limit = 100000;
      fetchList(listQueryAll)
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
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: ""
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
    createData() {
      //验证到宁时间与管理时间逻辑
      if (
        this.dataForm.arrivedate == null &&
        this.dataForm.managetime != null
      ) {
        this.$notify.error({
          title: "失败",
          message: "请设置到宁时间"
        });
        return;
      }

      if (
        this.dataForm.arrivedate != null &&
        this.dataForm.managetime == null
      ) {
        this.$notify.error({
          title: "失败",
          message: "请设置管理时间"
        });
        return;
      }

      if (
        this.dataForm.arrivedate != null &&
        this.dataForm.managetime != null &&
        this.dataForm.managetime < this.dataForm.arrivedate
      ) {
        this.$notify.error({
          title: "失败",
          message: "管理时间不能在来宁时间前面"
        });
        return;
      }

      if (this.dataForm.addsource == null) {
        this.$notify.error({
          title: "失败",
          message: "请先选择数据来源"
        });
        return;
      }

      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          createUser(this.dataForm)
            .then(response => {
              this.dialogFormVisible = false;
              this.$notify.success({
                title: "成功",
                message: "创建成功"
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
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {

      //验证到宁时间与管理时间逻辑
      if (
        this.dataForm.arrivedate == null &&
        this.dataForm.managetime != null
      ) {
        this.$notify.error({
          title: "失败",
          message: "请设置到宁时间"
        });
        return;
      }

      if (
        this.dataForm.arrivedate != null &&
        this.dataForm.managetime == null
      ) {
        this.$notify.error({
          title: "失败",
          message: "请设置管理时间"
        });
        return;
      }

      if (
        this.dataForm.arrivedate != null &&
        this.dataForm.managetime != null &&
        this.dataForm.managetime < this.dataForm.arrivedate
      ) {
        this.$notify.error({
          title: "失败",
          message: "管理时间不能在来宁时间前面"
        });
        return;
      }

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
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "编号",
          "姓名",
          "性别",
          "年龄",
          "身份证",
          "人在不在区内",
          "用户类型",
          "是否去过",
          "是否离宁",
          "当日正在管理",
          "是否超过14天",
          "是否追访不到",
          "追访不到原因",
          "街道",
          "社区",
          "户籍或常住地址",
          "停留武汉时间",
          "来宁时间",
          "目前症状",
          "就诊情况",
          "联系方式",
          "数据来源",
          "备注",
          "街道领导",
          "街道干部",
          "社工",
          "社区民警",
          "医疗卫生员",
          "添加时间"
        ];
        const filterVal = [
          "id",
          "name",
          "sex",
          "age",
          "idcard",
          "ifstay",
          "usertype",
          "ifwh",
          "ifleavenj",
          "ismanage",
          "ifover",
          "iflose",
          "loseinfo",
          "street",
          "njcommunity",
          "liveaddress",
          "whtime",
          "arrivedate",
          "healthinfo",
          "docinfo",
          "phone",
          "addsource",
          "remark",
          "jdname",
          "gbname",
          "sgname",
          "mjname",
          "ylname",
          "addtime"
        ];
        excel.export_json_to_excel2(
          tHeader,
          this.alllist,
          filterVal,
          "登记列表"
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