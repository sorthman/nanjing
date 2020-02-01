<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">今日新增用户数</div>
            <count-to
              :start-val="0"
              :end-val="todayUser"
              :duration="3000"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="shopping" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">自主申报用户</div>
            <count-to :start-val="0" :end-val="totalSelf" :duration="3000" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="user" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">社区登记用户</div>
            <count-to :start-val="0" :end-val="totalComm" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('shoppings')">
          <div class="card-panel-icon-wrapper icon-shoppingCard">
            <svg-icon icon-class="user" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">总登记用户数</div>
            <count-to :start-val="0" :end-val="totalUser" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>
    
    <el-row>
      <div class="card-chart">
        <ve-histogram :extend="chartExtend" :data="chartDataUser" :settings="chartSettingsUser" />
      </div>
    </el-row>
  </div>
</template>

<script>
import { info } from "@/api/dashboard";
import { statUser } from "@/api/stat";

import CountTo from "vue-count-to";
import VeHistogram from "v-charts/lib/histogram";
import VeLine from "v-charts/lib/line";

export default {
  components: {
    CountTo,
    VeLine,
    VeHistogram
  },
  data() {
    return {
      todayUser: 0,
      totalSelf: 0,
      totalComm: 0,
      totalUser: 0,

      chartData: {},
      chartSettings: {},
      chartExtend: {},

      chartDataUser: {},
      chartSettingsUser: {}
    };
  },
  created() {
    info().then(response => {
      this.todayUser = response.data.data.todayUser;
      this.totalSelf = response.data.data.totalSelf;
      this.totalComm = response.data.data.totalComm;
      this.totalUser = response.data.data.totalUser;
    });
    
    statUser().then(response => {
      this.chartDataUser = response.data.data;
      this.chartSettingsUser = {
        labelMap: {
          users: "用户增长趋势"
        }
      };
      this.chartExtend = {
        xAxis: { boundaryGap: true },
        series: {
          label: { show: true, position: "top" }
        }
      };
    });
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit("handleSetLineChartData", type);
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 20px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
  .card-chart {
    background: #fff;
    padding: 16px 16px 0;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }
      .icon-people {
        background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shoppingCard {
        background: #34bfa3;
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shoppingCard {
      color: #34bfa3;
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
