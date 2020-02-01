import request from '@/utils/request'

export function listBook(query) {
  return request({
    url: '/book/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/book/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/book/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/book/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/book/delete',
    method: 'post',
    data
  })
}
