import request from '@/utils/request'

export function listUser(query) {
  return request({
    url: '/njuser/list',
    method: 'get',
    params: query
  })
}

export function listUserOut(query) {
  return request({
    url: '/outsideuser/list',
    method: 'get',
    params: query
  })
}

export function listUserVisit(query) {
  return request({
    url: '/visituser/list',
    method: 'get',
    params: query
  })
}

export function listUserSchool(query) {
  return request({
    url: '/schooluser/list',
    method: 'get',
    params: query
  })
}

export function deleteUser(query) {
  return request({
    url: '/user/delete',
    method: 'get',
    params: query
  })
}

