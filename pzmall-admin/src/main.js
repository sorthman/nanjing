import Vue from 'vue'

import Cookies from 'js-cookie'
import jsencrypt, { JSEncrypt } from 'jsencrypt' //rsa 加密

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import './icons' // icon
import './permission' // permission control

import * as filters from './filters' // global filters

import permission from '@/directive/permission/index.js' // 权限判断指令

Vue.prototype.$getEnCode = function(password){
  let encrypt = new JSEncrypt()
  encrypt.setPublicKey('MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA20p3Q88SmVUPpA+r/0hWY/LbMCsllwXKUrXY13pG26BKsgtMm6IAGr6psS8cQ+AU75RpH3lTtt81wIfCHm9+vo4kVt66y8G0kfYSJ9Qlw2oH5lwab8knPPb+NQNwE7WvceRczKh4XIEfNcgXZyKrcDUIpIsP6rNbZ70JYRhYiagqyLsb39ib4eO6Zf6nmnrl3NPGeW4qFoivNudAld5E04gt2vCEEpuVt3x1/W3lVzD2gzz9rfkzjFvWwfGYjHMcZZNHZ1h1G60ssSHh7WKV8f6EglPRQssimUKXX4De6g5SVABPs6MEOj0RYYyunJ8unA448u7/TCcMjV/bvnndDwIDAQAB')
  let data = encrypt.encrypt(password)
  return data
}

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.directive('permission', permission)

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
