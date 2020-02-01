import request from '@/utils/request'

export function listType(query) {
  return request({
    url: '/listentype/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/listentype/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/listentype/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/listentype/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/listentype/delete',
    method: 'post',
    data
  })
}
