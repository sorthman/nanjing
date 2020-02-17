<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
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
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>

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
      <el-table-column min-width="300px" align="center" label="详细地址" prop="liveaddress" />
      <el-table-column align="center" label="导入类型" prop="datatype" />
      <el-table-column align="center" label="数据来源" prop="addsource" />
      <el-table-column min-width="150px" align="center" label="添加时间" prop="addtime" />
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

export default {
  name: "NoStreet",
  components: { Pagination },
  data() {
    return {
      sourceMap,
      list: null,
      alllist: null,
      signlist: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        username: undefined,
        mobile: undefined,
        sort: "id",
        order: "desc"
      },
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
      this.listQuery.area = localStorage.getItem("adminarea")
      fetchList(this.listQuery)
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
