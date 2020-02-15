import { mapGetters } from 'vuex'
import { info } from '@/api/dashboard'
import date from './date'

const orderMap = {
  '下关': 1,
  '热河南路': 2,
  '宁海路': 3,
  '幕府山': 4,
  '小市': 5,
  '挹江门': 6,
  '建宁路': 7,
  '中央门': 8,
  '江东': 9,
  '宝塔桥': 10,
  '凤凰': 11,
  '湖南路': 12,
  '华侨路': 13
}
export default {
  components: {},
  mixins: [],
  data() {
    return {
      loading: false,
      myChart1: null,
      myChart2: null,
      model: {},
      streets: [],
      timeinterval: null,
      num: 0,
      s1: null,
      s2: null,
      s3: null,
      s4: null
    }
  },
  computed: {
    ...mapGetters(['sidebar'])
  },
  watch: {
    'sidebar.opened'() {
      this.resize()
    }
  },
  created() {
  },
  mounted() {
    this.$nextTick(async() => {
      this.myChart1 = window.echarts.init(document.getElementById('charts1'))
      this.myChart2 = window.echarts.init(document.getElementById('charts2'))
      await this.initData()
      if (this.timeinterval) {
        clearInterval(this.timeinterval)
      }
      this.timeinterval = setInterval(async() => {
        await this.initData()
      }, 30000)
    })
  },
  beforeDestroy() {},
  destroyed() {
    if (this.timeinterval) {
      clearInterval(this.timeinterval)
    }
  },
  methods: {
    initData() {
      return new Promise((resolve, reject) => {
        this.loading = true
        info({
          'area': '鼓楼区'
        }).then(response => {
          this.model = response.data.Data
          // 鼓楼区疫情防控实时数据
          if (!this.s1) {
            this.s1 = new window.CountUp('s1', 0, this.model.s1)
            this.s1.start()
          } else {
            this.s1.update(this.model.s1)
          }
          if (!this.s2) {
            this.s2 = new window.CountUp('s2', 0, this.model.s2)
            this.s2.start()
          } else {
            this.s2.update(this.model.s2)
          }
          if (!this.s3) {
            this.s3 = new window.CountUp('s3', 0, this.model.s3)
            this.s3.start()
          } else {
            this.s3.update(this.model.s3)
          }
          if (!this.s4) {
            this.s4 = new window.CountUp('s4', 0, this.model.s4)
            this.s4.start()
          } else {
            this.s4.update(this.model.s4)
          }
          this.streets = this.model.s9.sort((a, b) => {
            return orderMap[a.street] - orderMap[b.street]
          })
          this.streets.forEach(x => {
            const find = this.model.s7.find(y => y.street === x.street)
            if (find) {
              x.a = find.num
            } else {
              x.a = 0
            }
          })
          this.draw()
          resolve(true)
          this.loading = false
        }).catch(() => {
          resolve(true)
          this.loading = false
        })
      })
    },
    resize() {
      setTimeout(() => {
        this.myChart1.resize()
        this.myChart2.resize()
      }, 300)
    },
    draw() {
      // 居家观察及解除观察人员统计
      const xData = date.dataScope(date.add(null, 1, 'day'), date.add(null, -15, 'day'))
      const d1 = []
      const d2 = []
      xData.forEach(x => {
        const f1 = this.model.s5.find(y1 => y1.date === x)
        if (f1) {
          d1.push(f1.num)
        } else {
          d1.push(0)
        }
        const f2 = this.model.s6.find(y2 => y2.date === x)
        if (f2) {
          d2.push(f2.num)
        } else {
          d2.push(0)
        }
      })
      this.myChart1.setOption({
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['居家观察', '解除观察']
        },
        grid: {
          top: '12%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '居家观察',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#f16873',
                lineStyle: {
                  color: '#f16873'
                }
              }
            },
            data: d1
          },
          {
            name: '解除观察',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#2dcd91',
                lineStyle: {
                  color: '#2dcd91'
                }
              }
            },
            data: d2
          }
        ]
      })
      // 居家观察人员街道分布
      this.myChart2.setOption({
        color: ['#e47881'],
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: '12%',
          left: '3%',
          right: '4%',
          bottom: '0%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.model.s7.map(x => x.street),
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              interval: 0,
              rotate: 40
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        // dataZoom: [{
        //   show: true,
        //   realtime: true,
        //   start: 0,
        //   end: 40
        // }, {
        //   type: 'inside'
        // }],
        series: [
          {
            type: 'bar',
            barWidth: '50%',
            label: {
              normal: {
                show: true,
                position: 'top',
                textStyle: {
                  color: '#e47881'
                }
              }
            },
            data: this.model.s7.map(x => x.num)
          }
        ]
      })
    }
  }
}
