import request from '@/utils/request'

export function statWH(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQDayTable',
    method: 'post',
    params: query
  })
}

export function statHB(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQDayTable',
    method: 'post',
    params: query
  })
}
export function statOutPerson(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQOutSideTable',
    method: 'post',
    params: query
  })
}

export function statDay(query) {
  return request({
    url: '/stat/daystat',
    method: 'get',
    params: query
  })
}
