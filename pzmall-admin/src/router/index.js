import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    perms: ['GET /aaa','POST /bbb']     will control the page perms (you can set multiple perms)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  }
  ,
  {
    path: '',
    hidden: true,
    component: Layout,
    redirect: '/user/user',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/user/user'),
        name: 'Dashboard',
        meta: { title: '登记列表', icon: 'dashboard', noCache: true }
      }
    ]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/user',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'userManage',
    meta: {
      title: '申报管理',
      icon: 'user'
    },
    children: [
      {
        path: 'import',
        component: () => import('@/views/user/import'),
        name: 'import',
        meta: {
          perms: ['GET /adminapi/user/import'],
          title: '数据导入',
          noCache: true
        }
      },
      {
        path: 'user',
        component: () => import('@/views/user/user'),
        name: 'user',
        meta: {
          perms: ['GET /adminapi/user/list'],
          title: '用户查询',
          noCache: true
        }
      },
      {
        path: 'nostreet',
        component: () => import('@/views/user/nostreet'),
        name: 'nostreet',
        meta: {
          title: '无街道用户',
          noCache: true
        }
      }
    ]
  },
  {
    path: '/stat',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'statManage',
    meta: {
      title: '统计管理',
      icon: 'chart'
    },
    children: [
      {
        path: 'whstat',
        component: () => import('@/views/stat/whstat'),
        name: 'whstat',
        meta: {
          title: '武汉统计报表',
          noCache: true
        }
      },
      {
        path: 'hbstat',
        component: () => import('@/views/stat/hbstat'),
        name: 'hbstat',
        meta: {
          title: '湖北统计报表',
          noCache: true
        }
      },
      {
        path: 'wzstat',
        component: () => import('@/views/stat/wzstat'),
        name: 'wzstat',
        meta: {
          title: '温州统计报表',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/sys',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'sysManage',
    meta: {
      title: '系统管理',
      icon: 'config'
    },
    children: [
      {
        path: 'account',
        component: () => import('@/views/sys/account'),
        name: 'account',
        meta: {
          perms: ['GET /adminapi/account/list', 'POST /adminapi/account/create', 'POST /adminapi/account/update', 'POST /adminapi/account/delete'],
          title: '街道管理员',
          noCache: true
        }
      },
      {
        path: 'admin',
        component: () => import('@/views/sys/admin'),
        name: 'admin',
        meta: {
          perms: ['GET /adminapi/admin/list', 'POST /adminapi/admin/create', 'POST /adminapi/admin/update', 'POST /adminapi/admin/delete'],
          title: '管理员',
          noCache: true
        }
      },
      {
        path: 'log',
        component: () => import('@/views/sys/log'),
        name: 'log',
        meta: {
          perms: ['GET /adminapi/log/list'],
          title: '操作日志',
          noCache: true
        }
      },
      {
        path: 'role',
        component: () => import('@/views/sys/role'),
        name: 'role',
        meta: {
          perms: ['GET /adminapi/role/list', 'POST /adminapi/role/create', 'POST /adminapi/role/update', 'POST /adminapi/role/delete', 'GET /adminapi/role/permissions', 'POST /adminapi/role/permissions'],
          title: '角色管理',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/profile',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    children: [
      {
        path: 'password',
        component: () => import('@/views/profile/password'),
        name: 'password',
        meta: { title: '修改密码', noCache: true }
      }
    ],
    hidden: true
  },

  { path: '*', redirect: '/404', hidden: true }
]
