import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'
import store from '@/store'

const routes = [
  {
    path: '/',
    name: 'main',
    component: () => import('../views/MainView.vue'),
    redirect: 'home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/HomeView.vue'),
      },
      {
        path: '/information',
        name: 'information',
        component: () => import('../views/InformationView.vue')
      },
      {
        path: '/subsidy',
        name: 'subsidy',
        component: () => import('../views/SubsidyView.vue')
      },
      {
        path: '/compensation',
        name: 'compensation',
        component: () => import('../views/CompensationView.vue')
      },
      {
        path: '/business',
        name: 'business',
        component: () => import('../views/BusinessView.vue')
      },
      {
        path: '/urgent',
        name: 'urgent',
        component: () => import('../views/UrgentView.vue')
      },
      {
        path: '/unfinished',
        name: 'unfinished',
        component: () => import('../views/UnfinishedView.vue')
      },
      {
        path: '/finished',
        name: 'finished',
        component: () => import('../views/FinishedView.vue')
      },
      {
        path: '/detail',
        name: 'detail',
        component: () => import('../views/DetailView.vue'),
        meta: {
          keepAlive: false,
        }
      },
      {
        path: '/blank',
        name: 'blank',
        component: () => import('../views/BlankView.vue')
      },
      {
        path: '/documents',
        name: 'documents',
        component: () => import('../views/DocumentsView.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
]

const router = createRouter({
  //history: createWebHistory(process.env.BASE_URL),
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  store.commit('getToken')
  const token = store.state.token
  if (!token && to.name !== 'login') next({ name: 'login' })
  else next()
})

export default router
