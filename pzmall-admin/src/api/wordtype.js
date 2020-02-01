import request from '@/utils/request'

export function listBook(query) {
  return request({
    url: '/wordtype/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/wordtype/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/wordtype/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/wordtype/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/wordtype/delete',
    method: 'post',
    data
  })
}
