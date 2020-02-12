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
    </div>

    <!-- 查询结果 -->
    <h2>累计数据</h2>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
            :data="props.row.detailList"
            border
            fit
            highlight-current-row
          >
            <el-table-column align="center" label="社区" prop="communitys" />
            <el-table-column align="center" label="外来人员总数" prop="num" />
            <el-table-column
              align="center"
              label="没有去重点疫情地区"
              prop="nullnum"
            />
            <el-table-column align="center" label="去过武汉" prop="whnum" />
            <el-table-column
              align="center"
              label="去过湖北（不含武汉）"
              prop="hbnum"
            />
            <el-table-column align="center" label="去过温州" prop="wznum" />
            <el-table-column
              align="center"
              label="没有外出"
              prop="nohasgoout"
            />
            <el-table-column align="center" label="有外出" prop="hasgoout" />
            <el-table-column
              align="center"
              label="没有接触重点疫情地区"
              prop="nohastouch"
            />
            <el-table-column
              align="center"
              label="有接触重点疫情地区"
              prop="hastouch"
            />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column align="center" label="街道" prop="streets" />
      <el-table-column align="center" label="外来人员总数" prop="num" />
      <el-table-column
        align="center"
        label="没有去重点疫情地区"
        prop="nullnum"
      />
      <el-table-column align="center" label="去过武汉" prop="whnum" />
      <el-table-column
        align="center"
        label="去过湖北（不含武汉）"
        prop="hbnum"
      />
      <el-table-column align="center" label="去过温州" prop="wznum" />
      <el-table-column align="center" label="没有外出" prop="nohasgoout" />
      <el-table-column align="center" label="有外出" prop="hasgoout" />
      <el-table-column
        align="center"
        label="没有接触重点疫情地区"
        prop="nohastouch"
      />
      <el-table-column
        align="center"
        label="有接触重点疫情地区"
        prop="hastouch"
      />
    </el-table>
    <h2>当天数据</h2>
    <el-table
      v-loading="listLoading"
      :data="list2"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
            :data="props.row.detailList"
            border
            fit
            highlight-current-row
          >
            <el-table-column align="center" label="社区" prop="communitys" />
            <el-table-column align="center" label="外来人员总数" prop="num" />
            <el-table-column
              align="center"
              label="没有去重点疫情地区"
              prop="nullnum"
            />
            <el-table-column align="center" label="去过武汉" prop="whnum" />
            <el-table-column
              align="center"
              label="去过湖北（不含武汉）"
              prop="hbnum"
            />
            <el-table-column align="center" label="去过温州" prop="wznum" />
            <el-table-column
              align="center"
              label="没有外出"
              prop="nohasgoout"
            />
            <el-table-column align="center" label="有外出" prop="hasgoout" />
            <el-table-column
              align="center"
              label="没有接触重点疫情地区"
              prop="nohastouch"
            />
            <el-table-column
              align="center"
              label="有接触重点疫情地区"
              prop="hastouch"
            />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column align="center" label="街道" prop="streets" />
      <el-table-column align="center" label="外来人员总数" prop="num" />
      <el-table-column
        align="center"
        label="没有去重点疫情地区"
        prop="nullnum"
      />
      <el-table-column align="center" label="去过武汉" prop="whnum" />
      <el-table-column
        align="center"
        label="去过湖北（不含武汉）"
        prop="hbnum"
      />
      <el-table-column align="center" label="去过温州" prop="wznum" />
      <el-table-column align="center" label="没有外出" prop="nohasgoout" />
      <el-table-column align="center" label="有外出" prop="hasgoout" />
      <el-table-column
        align="center"
        label="没有接触重点疫情地区"
        prop="nohastouch"
      />
      <el-table-column
        align="center"
        label="有接触重点疫情地区"
        prop="hastouch"
      />
    </el-table>
    <h2>省外人员累计</h2>
    <el-table
      v-loading="listLoading"
      :data="list3"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
    <h2>省外人员当天</h2>
    <el-table
      v-loading="listLoading"
      :data="list4"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
    <h2>省内人员累计</h2>
    <el-table
      v-loading="listLoading"
      :data="list5"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="市区" prop="city" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
    <h2>省内人员当天</h2>
    <el-table
      v-loading="listLoading"
      :data="list6"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="市区" prop="city" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
    <h2>其它地区累计</h2>
    <el-table
      v-loading="listLoading"
      :data="list7"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="地区" prop="other" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
    <h2>其它地区当天</h2>
    <el-table
      v-loading="listLoading"
      :data="list8"
      border
      fit
      highlight-current-row
      element-loading-text="正在查询中。。。"
    >
      <el-table-column align="center" label="地区" prop="other" />
      <el-table-column align="center" label="人数" prop="num" />
    </el-table>
  </div>
</template>

<script>
import { statOutPerson } from '@/api/stats'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { formatDate } from '@/utils/time.js'
export default {
  name: 'WZStat',
  components: { Pagination },
  data() {
    return {
      time: '',
      list: [],
      list2: [],
      list3: [],
      list4: [],
      list5: [],
      list6: [],
      list7: [],
      list8: [],
      listLoading: true,
      adminarea: '',
      listQuery: {
        stime: '',
        etime: '',
        area: ''
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
      this.list2 = []
      this.list3 = []
      this.list4 = []
      this.list5 = []
      this.list6 = []
      this.list7 = []
      this.list8 = []
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
      statOutPerson(this.listQuery)
        .then(response => {
          // 累积数据
          const allData = response.data.Data.data1
          const streetList = Array.from(new Set(allData.map(x => x.streets)))
          streetList.forEach(x => {
            const items = allData.filter(y => y.streets === x)
            this.list.push({
              streets: x,
              num: items.reduce(function(prev, cur) {
                return cur.num + prev
              }, 0),
              nullnum: items.reduce(function(prev, cur) {
                return cur.nullnum + prev
              }, 0),
              whnum: items.reduce(function(prev, cur) {
                return cur.whnum + prev
              }, 0),
              hbnum: items.reduce(function(prev, cur) {
                return cur.hbnum + prev
              }, 0),
              wznum: items.reduce(function(prev, cur) {
                return cur.wznum + prev
              }, 0),
              nohasgoout: items.reduce(function(prev, cur) {
                return cur.nohasgoout + prev
              }, 0),
              hasgoout: items.reduce(function(prev, cur) {
                return cur.hasgoout + prev
              }, 0),
              nohastouch: items.reduce(function(prev, cur) {
                return cur.nohastouch + prev
              }, 0),
              hastouch: items.reduce(function(prev, cur) {
                return cur.hastouch + prev
              }, 0),
              detailList: items
            })
          })

          // 当日数据
          const allData2 = response.data.Data.data2
          const streetList2 = Array.from(new Set(allData2.map(x => x.streets)))
          streetList2.forEach(x => {
            const items2 = allData2.filter(y => y.streets === x)
            this.list2.push({
              streets: x,
              num: items2.reduce(function(prev, cur) {
                return cur.num + prev
              }, 0),
              nullnum: items2.reduce(function(prev, cur) {
                return cur.nullnum + prev
              }, 0),
              whnum: items2.reduce(function(prev, cur) {
                return cur.whnum + prev
              }, 0),
              hbnum: items2.reduce(function(prev, cur) {
                return cur.hbnum + prev
              }, 0),
              wznum: items2.reduce(function(prev, cur) {
                return cur.wznum + prev
              }, 0),
              nohasgoout: items2.reduce(function(prev, cur) {
                return cur.nohasgoout + prev
              }, 0),
              hasgoout: items2.reduce(function(prev, cur) {
                return cur.hasgoout + prev
              }, 0),
              nohastouch: items2.reduce(function(prev, cur) {
                return cur.nohastouch + prev
              }, 0),
              hastouch: items2.reduce(function(prev, cur) {
                return cur.hastouch + prev
              }, 0),
              detailList: items2
            })
          })

          // 省外人员累计
          this.list3 = response.data.Data.data3
          // 省外人员当天
          this.list4 = response.data.Data.data4
          // 省内人员累计
          this.list5 = response.data.Data.data5
          // 省内人员当天
          this.list6 = response.data.Data.data6
          // 其它地区累计
          this.list7 = response.data.Data.data7
          // 其它地区当天
          this.list8 = response.data.Data.data8

          this.listLoading = false
        })
        .catch((e) => {
          this.list = []
          this.listLoading = false
        })
    },
    handleFilter() {
      if (this.listLoading) {
        return
      }
      this.getList()
    }
  }
}
</script>
