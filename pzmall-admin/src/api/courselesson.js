import request from '@/utils/request'

export function listLesson(query) {
  return request({
    url: '/courselesson/list',
    method: 'get',
    params: query
  })
}

export function createLesson(query) {
  return request({
    url: '/courselesson/create',
    method: 'get',
    params: query
  })
}

export function deleteLesson(query) {
  return request({
    url: '/courselesson/delete',
    method: 'get',
    params: query
  })
}
