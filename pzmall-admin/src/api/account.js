import request from '@/utils/request'

export function listAccount(query) {
  return request({
    url: '/account/list',
    method: 'get',
    params: query
  })
}

export function createAccount(data) {
  return request({
    url: '/account/create',
    method: 'post',
    data
  })
}

export function updateAccount(data) {
  return request({
    url: '/account/update',
    method: 'post',
    data
  })
}

export function deleteAccount(data) {
  return request({
    url: '/account/delete',
    method: 'post',
    data
  })
}
