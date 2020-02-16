<template>
  <div class="dashboard-editor-container">
    <div class="top-container">
      <el-card class="box-card b2">
        <div slot="header" class="card-head">
          <img src="../../assets/img/1.png" alt="">
          <span>鼓楼区疫情防控实时数据</span>
        </div>
        <div class="sc-datas">
          <div class="nums">
            <div class="box">
              <div id="s1" class="num n1"/>
              <div class="tips">登记人数</div>
            </div>
            <div class="box">
              <div id="s2" class="num n2"/>
              <div class="tips">外来人员人数</div>
            </div>
            <div class="box">
              <div id="s3" class="num n3"/>
              <div class="tips">居家观察人数</div>
            </div>
            <div class="box">
              <div id="s4" class="num n4"/>
              <div class="tips">解除观察人数</div>
            </div>
          </div>
          <div class="ms-tips">
            <p>截止{{ now }}</p>
            <el-button type="primary" round plain size="mini" class="mini-btns" @click="go">查看更多</el-button>
          </div>
        </div>
      </el-card>
      <el-card class="box-card card-charts">
        <div slot="header" class="card-head">
          <img src="../../assets/img/2.png" alt="">
          <span>居家观察及解除观察人员统计</span>
        </div>
        <div id="charts1" class="charts"/>
      </el-card>
      <el-card class="box-card big">
        <div slot="header" class="card-head">
          <img src="../../assets/img/3.png" alt="">
          <span>居家观察人员街道分布</span>
        </div>
        <div id="charts2" class="charts"/>
      </el-card>
    </div>
    <div class="dash-container">
      <el-card class="b1">
        <div slot="header" class="card-head">
          <img src="../../assets/img/4.png" alt="">
          <span>居家观察人员位置分析统计</span>
        </div>
        <div class="sa-head">
          <p class="m">位置</p>
          <p>人数</p>
          <!--          <p>异常</p>-->
          <!--          <p>正常</p>-->
        </div>
        <div class="scroll-content">
          <div v-for="(item,i) in model.s8" :key="i" class="sa-content">
            <p class="m">{{ item.njcommunity }}</p>
            <p>{{ item.num }}</p>
            <!--            <p>0</p>-->
            <!--            <p>{{ item.num }}</p>-->
          </div>
        </div>
      </el-card>
      <div class="big-sc">
        <img src="../../assets/img/p.png" alt="">
        <div v-for="(item,i) in streets" :key="i" :class="'t'+(i+1)" class="t">
          <div class="title">{{ item.street }}街道</div>
          <div class="txt">
            <div>居家观察<span>{{ item.a }}</span></div>
            <div>外来人员<span>{{ item.num }}</span></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./index.js">
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "./index";
</style>
