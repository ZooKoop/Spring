import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import BackHome from '@/components/back/BackHome'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/back',
      name: 'BackHome',
      component: BackHome
    }
  ]
})
