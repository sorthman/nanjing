import request from '@/utils/request'

export function listLess(query) {
  return request({
    url: '/lesson/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/lesson/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/lesson/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/lesson/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/lesson/delete',
    method: 'post',
    data
  })
}
