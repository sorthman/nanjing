import request from '@/utils/request'

export function listCourse(query) {
  return request({
    url: '/course/list',
    method: 'get',
    params: query
  })
}

export function createCourse(data) {
  return request({
    url: '/course/create',
    method: 'post',
    data
  })
}

export function readCourse(data) {
  return request({
    url: '/course/read',
    method: 'get',
    data
  })
}

export function updateCourse(data) {
  return request({
    url: '/course/update',
    method: 'post',
    data
  })
}

export function deleteCourse(data) {
  return request({
    url: '/course/delete',
    method: 'post',
    data
  })
}
