import request from '@/utils/request'

export function listWord(query) {
  return request({
    url: '/word/list',
    method: 'get',
    params: query
  })
}

export function createWord(data) {
  return request({
    url: '/word/create',
    method: 'post',
    data
  })
}

export function uploadWord(query) {
  return request({
    url: '/word/upload',
    method: 'get',
    params: query
  })
}

export function readWord(data) {
  return request({
    url: '/word/read',
    method: 'get',
    data
  })
}

export function updateWord(data) {
  return request({
    url: '/word/update',
    method: 'post',
    data
  })
}

export function deleteWord(data) {
  return request({
    url: '/word/delete',
    method: 'post',
    data
  })
}
