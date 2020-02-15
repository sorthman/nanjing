import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQNoStreet',
    method: 'post',
    params: query
  })
}

export function fetchNoSignList(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQNoPlayCard',
    method: 'post',
    params: query
  })
}
