import request from '@/utils/request'

export function loginByUsername(username, password, code, codename) {
  const data = {
    username,
    password,
    code,
    codename
  }
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/auth/info',
    method: 'get',
    params: { token }
  })
}

export function getCode(name) {
  return request({
    url: '/auth/code',
    method: 'get',
    params: { name }
  })
}
