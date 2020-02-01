<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.uid"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户ID"
      />
      <el-input
        v-model="listQuery.orderSn"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入订单编号"
      />
      <el-select
        clearable
        v-model="listQuery.orderStatus"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择订单状态"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleAddCourse"
      >手工加单</el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>
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
      <el-table-column align="center" min-width="140" label="订单编号" prop="orderSn" />

      <el-table-column width="180" align="left" label="用户信息" prop="uid">
        <template slot-scope="scope">
          用户ID：{{ scope.row.uid}}
          <br />
          用户昵称：{{ scope.row.nickname}}
          <br />
          用户号码：{{ scope.row.mobile}}
        </template>
      </el-table-column>

      <el-table-column width="250" align="left" label="商品信息" prop="goodsid">
        <template slot-scope="scope">
          商品ID：{{ scope.row.goodsid}}
          <br />
          商品类别：{{ scope.row.goodstype | goodstypeFilter }}
          <br />
          商品名称：{{ scope.row.goodsname}}
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单状态" prop="orderstatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderstatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="goodsprice">
        <template slot-scope="scope">
          <el-tag>￥{{ scope.row.goodsprice}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="支付金额" prop="amount">
        <template slot-scope="scope">
          <el-tag>￥{{ scope.row.amount}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="180" align="left" label="支付信息" prop="payTime">
        <template slot-scope="scope">
          支付时间：{{ scope.row.payTime}}
          <br />
          支付渠道：{{ scope.row.paytype}}
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['POST /adminapi/order/refundcourse']"
            v-if="scope.row.orderstatus ===201 && scope.row.goodstype === 2 && scope.row.paytype!='手工加单'"
            type="primary"
            size="mini"
            @click="handleRefund(scope.row)"
          >标记退款</el-button>
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

    <!-- 订单详情对话框 -->
    <el-dialog :visible.sync="orderDialogVisible" title="订单详情" width="800">
      <el-form :data="orderDetail" label-position="left">
        <el-form-item label="订单编号">
          <span>{{ orderDetail.order.orderSn }}</span>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-tag>{{ orderDetail.order.orderStatus | orderStatusFilter }}</el-tag>
        </el-form-item>
        <el-form-item label="订单用户">
          <span>{{ orderDetail.user.nickname }}</span>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.order.message }}</span>
        </el-form-item>
        <el-form-item label="收货信息">
          <span>（收货人）{{ orderDetail.order.consignee }}</span>
          <span>（手机号）{{ orderDetail.order.mobile }}</span>
          <span>（地址）{{ orderDetail.order.address }}</span>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table :data="orderDetail.orderGoods" border fit highlight-current-row>
            <el-table-column align="center" label="商品名称" prop="goodsName" />
            <el-table-column align="center" label="商品编号" prop="goodsSn" />
            <el-table-column align="center" label="货品规格" prop="specifications" />
            <el-table-column align="center" label="货品价格" prop="price" />
            <el-table-column align="center" label="货品数量" prop="number" />
            <el-table-column align="center" label="货品图片" prop="picUrl">
              <template slot-scope="scope">
                <img :src="scope.row.picUrl" width="40" />
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item label="费用信息">
          <span>
            (实际费用){{ orderDetail.order.actualPrice }}元 =
            (商品总价){{ orderDetail.order.goodsPrice }}元 +
            (快递费用){{ orderDetail.order.freightPrice }}元 -
            (优惠减免){{ orderDetail.order.couponPrice }}元 -
            (积分减免){{ orderDetail.order.integralPrice }}元
          </span>
        </el-form-item>
        <el-form-item label="支付信息">
          <span>（支付渠道）{{ orderDetail.order.paytype }}</span>
          <span>（支付时间）{{ orderDetail.order.payTime }}</span>
        </el-form-item>
        <el-form-item label="快递信息">
          <span>（快递公司）{{ orderDetail.order.shipChannel }}</span>
          <span>（快递单号）{{ orderDetail.order.shipSn }}</span>
          <span>（发货时间）{{ orderDetail.order.shipTime }}</span>
        </el-form-item>
        <el-form-item label="收货信息">
          <span>（确认收货时间）{{ orderDetail.order.confirmTime }}</span>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 发货对话框 -->
    <el-dialog :visible.sync="shipDialogVisible" title="发货">
      <el-form
        ref="shipForm"
        :model="shipForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="快递公司" prop="shipChannel">
          <el-input v-model="shipForm.shipChannel" />
        </el-form-item>
        <el-form-item label="快递编号" prop="shipSn">
          <el-input v-model="shipForm.shipSn" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmShip">确定</el-button>
      </div>
    </el-dialog>

    <!-- 退款对话框 -->
    <el-dialog :visible.sync="refundDialogVisible" title="确认标记退款">
      <el-form
        ref="refundForm"
        :model="refundForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <!-- <el-form-item label="退款金额" prop="refundMoney">
          <el-input v-model="refundForm.refundMoney" :disabled="true" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRefund">确定</el-button>
      </div>
    </el-dialog>

    <!-- 手工加订单对话框 -->
    <el-dialog :visible.sync="addCourseDialogVisible" title="手工添加课程订单">
      <el-form
        ref="addCourseForm"
        :model="addCourseForm"
        :rules="rules"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="用户号码" prop="phone">
          <el-input v-model="addCourseForm.phone" />
        </el-form-item>
        <el-form-item label="课程编号" prop="goodsid">
          <el-input v-model="addCourseForm.goodsid" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCourseDialogVisible = false">取消</el-button>
        <el-button :loading="downloadLoading" type="primary" @click="confirmAddCourse">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOrder,
  listCourseOrder,
  shipOrder,
  refundCourseOrder,
  detailOrder,
  addCourse
} from "@/api/order";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import checkPermission from "@/utils/permission"; // 权限判断函数

const statusMap = {
  101: "未付款",
  102: "用户取消",
  103: "系统取消",
  201: "已付款",
  203: "已退款"
};

const goodstypeMap = {
  1: "单课",
  2: "课程",
  3: "VIP会员",
  4: "作文卡"
};

const paytypeMap = {
  1: "微信支付",
  2: "支付宝",
  3: "手工加单"
};

export default {
  name: "Order",
  components: { Pagination },
  filters: {
    orderStatusFilter(status) {
      return statusMap[status];
    },
    goodstypeFilter(status) {
      return goodstypeMap[status];
    },
    paytypeFilter(status) {
      return paytypeMap[status];
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        name: undefined,
        orderStatusArray: [],
        sort: "add_time",
        order: "desc"
      },
      statusMap,
      orderDialogVisible: false,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orderId: undefined,
        shipChannel: undefined,
        shipSn: undefined
      },
      shipDialogVisible: false,
      refundForm: {
        orderId: undefined,
        refundMoney: undefined
      },
      refundDialogVisible: false,
      downloadLoading: false,
      addCourseDialogVisible: false,
      addCourseForm: {
        phone: undefined,
        goodsid: undefined
      },
      rules: {
        phone: [{ required: true, message: "不能为空", trigger: "blur" }],
        goodsid: [{ required: true, message: "不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true;
      listCourseOrder(this.listQuery)
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
    handleDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data;
      });
      this.orderDialogVisible = true;
    },
    handleShip(row) {
      this.shipForm.orderId = row.id;
      this.shipForm.shipChannel = row.shipChannel;
      this.shipForm.shipSn = row.shipSn;

      this.shipDialogVisible = true;
      this.$nextTick(() => {
        this.$refs["shipForm"].clearValidate();
      });
    },
    confirmShip() {
      this.$refs["shipForm"].validate(valid => {
        if (valid) {
          shipOrder(this.shipForm)
            .then(response => {
              this.shipDialogVisible = false;
              this.$notify.success({
                title: "成功",
                message: "确认发货成功"
              });
              this.getList();
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
    resetAddCourseForm() {
      this.addCourseForm = {
        phone: undefined,
        goodsid: undefined
      };
    },
    handleAddCourse(row) {
      // this.refundForm.refundMoney = row.actualPrice;
      this.resetAddCourseForm();
      this.addCourseDialogVisible = true;
      this.$nextTick(() => {
        this.$refs["addCourseForm"].clearValidate();
      });
    },
    confirmAddCourse() {
      this.$refs["addCourseForm"].validate(valid => {
        if (valid) {
          this.downloadLoading = true;
          addCourse(this.addCourseForm)
            .then(response => {
              this.downloadLoading = false;
              this.addCourseDialogVisible = false;
              this.$notify.success({
                title: "成功",
                message: "处理成功"
              });
              this.getList();
            })
            .catch(response => {
              this.downloadLoading = false;

              this.$notify.error({
                title: "失败",
                message: response.data.msg
              });
            });
        }
      });
    },
    handleRefund(row) {
      this.refundForm.orderId = row.id;
      // this.refundForm.refundMoney = row.actualPrice;

      this.refundDialogVisible = true;
      this.$nextTick(() => {
        this.$refs["refundForm"].clearValidate();
      });
    },
    confirmRefund() {
      this.$refs["refundForm"].validate(valid => {
        if (valid) {
          refundCourseOrder(this.refundForm)
            .then(response => {
              this.refundDialogVisible = false;
              this.$notify.success({
                title: "成功",
                message: "确认退款成功"
              });
              this.getList();
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
          "订单ID",
          "订单编号",
          "用户ID",
          "订单状态",
          "是否删除",
          "收货人",
          "收货联系电话",
          "收货地址"
        ];
        const filterVal = [
          "id",
          "orderSn",
          "userId",
          "orderStatus",
          "isDelete",
          "consignee",
          "mobile",
          "address"
        ];
        excel.export_json_to_excel2(tHeader, this.list, filterVal, "订单信息");
        this.downloadLoading = false;
      });
    }
  }
};
</script>
