import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/user/list',
    method: 'get',
    params: query
  })
}

export function listSign(query) {
  return request({
    url: '/user/listsign',
    method: 'get',
    params: query
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function createUser(data) {
  return request({
    url: '/user/create',
    method: 'post',
    data
  })
}

export function deleteUser(query) {
  return request({
    url: '/user/delete',
    method: 'get',
    params: query
  })
}


export function importUser(query) {
  return request({
    url: '/user/upload',
    method: 'get',
    params: query
  })
}

export function importSJKUser(query) {
  return request({
    url: '/user/uploadsjk',
    method: 'get',
    params: query
  })
}

export function importNjUser(query) {
  return request({
    url: '/user/uploadnj',
    method: 'get',
    params: query
  })
}

export function importHsjc(query) {
  return request({
    url: '/user/uploadhsjc',
    method: 'get',
    params: query
  })
}

export function download(query) {
  return request({
    url: '/user/download',
    method: 'get',
    params: query
  })
}

export function listAddress(query) {
  return request({
    url: '/address/list',
    method: 'get',
    params: query
  })
}

export function listCollect(query) {
  return request({
    url: '/collect/list',
    method: 'get',
    params: query
  })
}

export function listFeedback(query) {
  return request({
    url: '/feedback/list',
    method: 'get',
    params: query
  })
}

export function listFootprint(query) {
  return request({
    url: '/footprint/list',
    method: 'get',
    params: query
  })
}

export function listHistory(query) {
  return request({
    url: '/history/list',
    method: 'get',
    params: query
  })
}

