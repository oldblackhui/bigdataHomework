import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// 使得项目中可以使用DataV组件
import dataV from '@jiaminghi/data-view';
Vue.use(dataV);

// 按需引入vue-awesome图标
import Icon from 'vue-awesome/components/Icon';
import 'vue-awesome/icons/chart-bar.js';
import 'vue-awesome/icons/chart-area.js';
import 'vue-awesome/icons/chart-pie.js';
import 'vue-awesome/icons/chart-line.js';
import 'vue-awesome/icons/align-left.js';

// 全局注册图标
Vue.component('icon', Icon);

// 适配flex
import '@/common/flexible.js';

// 引入全局css
import './assets/scss/style.scss';


//引入echart
//import * as echarts from 'echarts5'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts;

//引入api
import api from "./interface/index"
Vue.prototype.$api = api.commonUrl

//引入axios
import axios from 'axios';
//将axios绑定在vue原型上，这样可以在其他组件中通过this.$axios使用axios
Vue.prototype.$axios = axios;

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
