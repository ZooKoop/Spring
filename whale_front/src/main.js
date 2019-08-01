// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
// 同时使用 DataTables 和 DataTablesServer
import VueDataTables from 'vue-data-tables'
Vue.use(VueDataTables)

Vue.config.productionTip = false
Vue.use(ElementUI,{size: 'small'});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
