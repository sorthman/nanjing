import request from '@/utils/request'

export function info(query) {
  return request({
    url: 'https://super.51pinzhi.cn/poc_extend/ac/acYQBigScreen',
    method: 'post',
    params: query
  })
}
