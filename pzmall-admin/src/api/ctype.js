import request from '@/utils/request'

export function listType(query) {
  return request({
    url: '/coursetype/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/coursetype/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/coursetype/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/coursetype/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/coursetype/delete',
    method: 'post',
    data
  })
}
