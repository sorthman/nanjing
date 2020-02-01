import request from '@/utils/request'

export function listBook(query) {
  return request({
    url: '/composition/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/composition/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/composition/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/composition/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/composition/delete',
    method: 'post',
    data
  })
}
