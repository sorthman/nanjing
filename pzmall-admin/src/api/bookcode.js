import request from '@/utils/request'

export function listBookcode(query) {
  return request({
    url: '/bookcode/list',
    method: 'get',
    params: query
  })
}

export function createBookcode(data) {
  return request({
    url: '/bookcode/create',
    method: 'post',
    data
  })
}

export function readBookcode(data) {
  return request({
    url: '/bookcode/read',
    method: 'get',
    data
  })
}

export function updateBookcode(data) {
  return request({
    url: '/bookcode/update',
    method: 'post',
    data
  })
}

export function uploadBookCode(query) {
  return request({
    url: '/bookcode/upload',
    method: 'get',
    params: query
  })
}

export function setBook(query) {
  return request({
    url: '/bookcode/setbook',
    method: 'get',
    params: query
  })
}

export function deleteBookcode(data) {
  return request({
    url: '/bookcode/delete',
    method: 'post',
    data
  })
}
