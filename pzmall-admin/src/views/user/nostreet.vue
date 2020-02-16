<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="getList">刷新</el-button>
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
      <el-table-column align="center" width="100px" label="编号" prop="idNum" />
      <el-table-column align="center" label="姓名" prop="name" />
      <el-table-column min-width="120px" align="center" label="手机号" prop="phone" />
      <el-table-column align="center" label="详细地址" prop="currentaddress" />
      <el-table-column align="center" label="导入类型" prop="datatype" />
      <el-table-column align="center" label="数据来源" prop="addsource" />
      <el-table-column align="center" label="添加时间" prop="addtime" />
    </el-table>

    <!-- <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="page"
      :limit.sync="limit"
      @pagination="getList"
    />-->
  </div>
</template>

<script>
import { fetchList } from "@/api/users";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination

export default {
  name: "NoStreet",
  components: { Pagination },
  data() {
    return {
      list: null,
      alllist: null,
      signlist: null,
      listLoading: true,
      dialogFormVisible: false,
      downloadLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      fetchList({ area: localStorage.getItem("adminarea") })
        .then(response => {
          this.list = response.data.Data;
          for (var i = 0; i < this.list.length; i++) {
            this.list[i].idNum = i + 1;
          }
          this.listLoading = false;
        })
        .catch(() => {
          this.list = [];
          this.listLoading = false;
        });
    },
    handleFilter() {
      this.getList();
    }
  }
};
</script>
