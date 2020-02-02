import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQNoStreet',
    method: 'post',
    params: query
  })
}
