import request from '@/utils/request'

export function listBook(query) {
  return request({
    url: '/listen/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/listen/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/listen/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/listen/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/listen/delete',
    method: 'post',
    data
  })
}
