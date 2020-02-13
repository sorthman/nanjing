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
  // {
  //   path: '',
  //   hidden: true,
  //   component: Layout,
  //   redirect: '/user/user',
  //   children: [
  //     {
  //       path: 'dashboard',
  //       component: () => import('@/views/user/user'),
  //       name: 'Dashboard',
  //       meta: { title: '登记列表', icon: 'dashboard', noCache: true }
  //     }
  //   ]
  // }
  {
    path: '',
    component: Layout,
    redirect: 'welcome',
    hidden: true,
    children: [
      {
        path: 'welcome',
        component: () => import('@/views/dashboard/main'),
        name: 'welcome',
        meta: { title: '欢迎进入', icon: 'dashboard', noCache: true }
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
    path: '/dashboard/',
    component: Layout,
    // alwaysShow: true,
    name: 'dashboard',
    meta: {
      title: '疫情管控',
      icon: 'dashboard'
    },
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'dashboard',
        meta: {
          perms: ['GET /adminapi/dashboardindex'],
          title: '疫情管控', icon: 'dashboard', noCache: true
        }
      }
    ]
  },
  {
    path: '/njuser',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'njuser',
    meta: {
      title: '市申报管理',
      icon: 'course'
    },
    children: [
      {
        path: 'import',
        component: () => import('@/views/njuser/import'),
        name: 'import',
        meta: {
          perms: ['GET /adminapi/user/uploadnj'],
          title: '每日数据导入',
          noCache: true
        }
      },
      {
        path: 'user',
        component: () => import('@/views/njuser/ruser'),
        name: 'user',
        meta: {
          perms: ['GET /adminapi/njuser/list'],
          title: '用户查询',
          noCache: true
        }
      }
    ]
  },
  {
    path: '/outuser',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'outuser',
    meta: {
      title: '外来人员查询',
      icon: 'peoples'
    },
    children: [

      {
        path: 'outuser',
        component: () => import('@/views/njuser/outuser'),
        name: 'outuser',
        meta: {
          perms: ['GET /adminapi/outsideuser/list'],
          title: '外来人员查询',
          noCache: true
        }
      },
      {
        path: 'visituser',
        component: () => import('@/views/njuser/visituser'),
        name: 'visituser',
        meta: {
          perms: ['GET /adminapi/visituser/list'],
          title: '小区外来人员查询',
          noCache: true
        }
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'userManage',
    meta: {
      title: '申报管理',
      icon: 'excel'
    },
    children: [
      {
        path: 'import',
        component: () => import('@/views/user/import'),
        name: 'import',
        meta: {
          perms: ['GET /adminapi/user/upload'],
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
          perms: ['GET /adminapi/user/nostreet'],
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
          perms: ['GET /adminapi/stat/whstat'],
          title: '武汉统计报表',
          noCache: true
        }
      },
      {
        path: 'hbstat',
        component: () => import('@/views/stat/hbstat'),
        name: 'hbstat',
        meta: {
          perms: ['GET /adminapi/stat/hbstat'],
          title: '湖北(不含武汉)统计',
          noCache: true
        }
      },
      {
        path: 'hballstat',
        component: () => import('@/views/stat/hballstat'),
        name: 'hballstat',
        meta: {
          perms: ['GET /adminapi/stat/hballstat'],
          title: '湖北统计报表',
          noCache: true
        }
      },
      {
        path: 'wzstat',
        component: () => import('@/views/stat/wzstat'),
        name: 'wzstat',
        meta: {
          perms: ['GET /adminapi/stat/wzstat'],
          title: '温州统计报表',
          noCache: true
        }
      },
      
      {
        path: 'otherstat',
        component: () => import('@/views/stat/otherstat'),
        name: 'otherstat',
        meta: {
          perms: ['GET /adminapi/stat/otherstat'],
          title: '其他地区统计报表',
          noCache: true
        }
      },
      
      {
        path: 'nocontactstat',
        component: () => import('@/views/stat/nocontactstat'),
        name: 'nocontactstat',
        meta: {
          perms: ['GET /adminapi/stat/nocontactstat'],
          title: '无接触统计报表',
          noCache: true
        }
      },
      {
        path: 'sumstat',
        component: () => import('@/views/stat/sumstat'),
        name: 'sumstat',
        meta: {
          perms: ['GET /adminapi/stat/sumstat'],
          title: '汇总统计报表',
          noCache: true
        }
      },
      {
        path: 'wlstat',
        component: () => import('@/views/stat/wlstat'),
        name: 'wlstat',
        meta: {
          perms: ['GET /adminapi/stat/wlstat'],
          title: '外来人员统计',
          noCache: true
        }
      },
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
