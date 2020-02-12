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
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column label="温州每日数据汇总" align="center">
        <el-table-column
          min-width="120px"
          align="center"
          label="总人数"
          prop="s1"
        />
        <el-table-column align="center" label="已核查人数" prop="s2" />
        <el-table-column align="center" label="当日正在管理人数" prop="s3" />
        <el-table-column
          align="center"
          label="超过14天观察期解除管理累计人数"
          prop="s4"
        />
        <el-table-column :label="title1" align="center">
          <el-table-column prop="inareaSum" width="70" label="小计" />
          <el-table-column prop="s5" width="70" label="未去温州" />
          <el-table-column prop="s6" width="70" label="去过或途径温州" />
        </el-table-column>
        <el-table-column label="已核查人数" align="center">
          <el-table-column align="center" label="合计" prop="hasCheckSum" />
          <el-table-column :label="title2" align="center">
            <el-table-column prop="inareaSum" width="70" label="小计" />
            <el-table-column prop="s7" width="70" label="当日正在管理人数" />
            <el-table-column
              prop="s8"
              width="70"
              label="超过14天观察期解除管理累计人数"
            />
            <el-table-column prop="followSum" width="70" label="待确认" />
          </el-table-column>
          <el-table-column :label="title3">
            <el-table-column prop="notinareaSum" width="70" label="小计" />
            <el-table-column prop="s9" width="70" label="外区" />
            <el-table-column prop="s10" width="70" label="外市" />
          </el-table-column>
          <el-table-column :label="title4" align="center" prop="s11" />
        </el-table-column>
        <el-table-column align="center" label="自查" prop="s12" />
        <el-table-column align="center" label="公安" prop="s13" />
        <el-table-column align="center" label="教育" prop="s14" />
        <el-table-column align="center" label="漫游" prop="s15" />
        <el-table-column align="center" label="南京籍" prop="s16" />
        <el-table-column align="center" label="武汉籍" prop="s17" />
        <el-table-column align="center" label="湖北籍" prop="s19" />
        <el-table-column align="center" label="温州籍" prop="s18" />
        <el-table-column align="center" label="其他" prop="othersSum" />
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
      title1: '',
      title2: '',
      title3: '',
      title4: '',
      listQuery: {
        stime: '',
        etime: '',
        area: '',
        userType: '温州'
      }
    }
  },
  created() {
    this.getList()
    this.adminarea = localStorage.getItem('adminarea')
    this.title1 = '在' + this.adminarea + '人数'
    this.title2 = '在' + this.adminarea
    this.title3 = '不在' + this.adminarea
    this.title4 = '不确定在' + this.adminarea + '或在宁'
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
            othersSum: parseInt(response.data.Data.s1) - parseInt(response.data.Data.s16) - parseInt(response.data.Data.s17) - parseInt(response.data.Data.s18) - parseInt(response.data.Data.s19)
          })
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    handleFilter() {
      if (this.listLoading) {
        return
      }
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
