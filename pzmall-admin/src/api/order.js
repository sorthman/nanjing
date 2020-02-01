import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function listCourseOrder(query) {
  return request({
    url: '/order/listcourse',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function detailOrder(id) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: { id }
  })
}

export function shipOrder(data) {
  return request({
    url: '/order/ship',
    method: 'post',
    data
  })
}

export function refundOrder(data) {
  return request({
    url: '/order/refund',
    method: 'post',
    data
  })
}

export function refundCourseOrder(data) {
  return request({
    url: '/order/refundcourse',
    method: 'post',
    data
  })
}

export function addCourse(data) {
  return request({
    url: '/order/addcourse',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/order/reply',
    method: 'post',
    data
  })
}
