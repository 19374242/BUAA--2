import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import axios from "axios";
import VueCookies from "vue-cookies";

import 'element-ui/lib/theme-chalk/index.css';
import { ElementTiptapPlugin } from 'element-tiptap';
import 'element-tiptap/lib/index.css';

import  VueParticles  from   'vue-particles';
import NgFormElement  from 'ng-form-element'
import 'ng-form-element/lib/ng-form-element.css'
Vue.use(NgFormElement);

Vue.use(VueParticles);
Vue.use(ElementUI);
Vue.use(VueCookies);
Vue.use(ElementTiptapPlugin, {
  lang: 'zh', // 设置语言为中文
});


Vue.config.productionTip = false

new Vue({
  router,
  store,
  axios,
  render: h => h(App)
}).$mount('#app')

