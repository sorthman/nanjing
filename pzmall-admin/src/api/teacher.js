import request from '@/utils/request'

export function listTeacher(query) {
  return request({
    url: '/teacher/list',
    method: 'get',
    params: query
  })
}

export function createBook(data) {
  return request({
    url: '/teacher/create',
    method: 'post',
    data
  })
}

export function readBook(data) {
  return request({
    url: '/teacher/read',
    method: 'get',
    data
  })
}

export function updateBook(data) {
  return request({
    url: '/teacher/update',
    method: 'post',
    data
  })
}

export function deleteBook(data) {
  return request({
    url: '/teacher/delete',
    method: 'post',
    data
  })
}
