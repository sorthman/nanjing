<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      时间：
      <el-date-picker
        v-model="time"
        type="date"
        style="width: 150px"
        class="filter-item"
        value-format="yyyy-MM-dd"
        placeholder="时间"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查询</el-button
      >
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button
      >
    </div>

    <!-- 查询结果 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="省统计表-湖北" align="center">
        <el-table-column align="center" label=" " prop="s2" />
        <el-table-column align="center" label="总数" prop="s3" />

        <el-table-column label="上级下交人数" align="center">
          <el-table-column prop="inareaSum" width="70" label="小计" />
          <el-table-column label="已核查" align="center">
            <el-table-column label="居家观察" align="center">
              <el-table-column prop="s5" width="70" label="人数" />
              <el-table-column prop="s6" width="70" label="其中解除关注" />
            </el-table-column>
            <el-table-column align="center" label="送医" prop="s3" />
          </el-table-column>
          <el-table-column align="center" label="查否数" prop="s3" />
          <el-table-column align="center" label="正在核查" prop="s3" />
        </el-table-column>
        <el-table-column label="本地自排人数" align="center">
          <el-table-column align="center" label="小计" prop="hasCheckSum" />
          <el-table-column label="已核查" align="center">
            <el-table-column label="居家观察" align="center">
              <el-table-column prop="s5" width="70" label="人数" />
              <el-table-column prop="s6" width="70" label="其中解除关注" />
            </el-table-column>
            <el-table-column align="center" label="送医" prop="s3" />
          </el-table-column>
          <el-table-column align="center" label="查否数" prop="s3" />
          <el-table-column align="center" label="正在核查" prop="s3" />
        </el-table-column>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { statWH } from '@/api/stats'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { formatDate } from '@/utils/time.js'
export default {
  name: 'WZStat',
  components: { Pagination },
  data() {
    return {
      time: '',
      list: null,
      listInfo: null,
      total: 0,
      listLoading: true,
      adminarea: '',
      listQuery: {
        stime: '',
        etime: '',
        area: '',
        userType: ''
      }
    }
  },
  created() {
    this.getList()
    this.adminarea = localStorage.getItem('adminarea')
  },
  methods: {
    getList() {
      this.listLoading = true
      this.list = []
      if (this.time === '') {
        var date = new Date()
        var fdate = formatDate(date, 'yyyy-MM-dd')
        this.listQuery.stime = fdate + ' 00:00:00'
        this.listQuery.etime = fdate + ' 23:59:59'
        this.time = fdate
      } else {
        this.listQuery.stime = this.time + ' 00:00:00'
        this.listQuery.etime = this.time + ' 23:59:59'
      }
      this.listQuery.area = localStorage.getItem('adminarea')
      statWH(this.listQuery)
        .then(response => {
          this.list.push({ ...response.data.Data,
            inareaSum: parseInt(response.data.Data.s5) + parseInt(response.data.Data.s6),
            hasCheckSum: parseInt(response.data.Data.s5) + parseInt(response.data.Data.s6) + parseInt(response.data.Data.s9) + parseInt(response.data.Data.s10),
            followSum: parseInt(response.data.Data.s5) + parseInt(response.data.Data.s6) - parseInt(response.data.Data.s7) - parseInt(response.data.Data.s8),
            notinareaSum: parseInt(response.data.Data.s9) + parseInt(response.data.Data.s10),
            othersSum: parseInt(response.data.Data.s1) - parseInt(response.data.Data.s16) - parseInt(response.data.Data.s17) - parseInt(response.data.Data.s18)
          })
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    handleFilter() {
      this.getList()
    },
    handleDownload() {
      let str = ''
      for (let i = 0; i < document.getElementsByTagName('table').length; i++) {
        str = str + document.getElementsByTagName('table')[i].outerHTML
      }
      str = str.replace('border="0"', 'border="1"').replace('border="0"', 'border="1"')
      var html = "<html><head><meta charset='utf-8' /></head><body>" + str + '<style>table,tr,td{border: 1px solid gray} .el-table__row{border: 1px solid gray}</style></body></html>'
      console.log(html)
      // 实例化一个Blob对象，其构造函数的第一个参数是包含文件内容的数组，第二个参数是包含文件类型属性的对象
      var blob = new Blob([html], { type: 'application/vnd.ms-excel' })
      const link = document.createElement('a')
      // 利用URL.createObjectURL()方法为a元素生成blob URL
      link.href = URL.createObjectURL(blob)
      // 设置文件名
      link.download = '温州' + this.time + '数据汇总.xls'
      link.click()
      URL.revokeObjectURL(blob)
    }
  }
}
</script>
