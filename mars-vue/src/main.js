import Vue from 'vue';
import App from './App.vue';
import Storage from 'vue-ls';
import router from './router';
import store from './store/';

import {
  VueAxios
} from "@/utils/request";

import Antd from 'ant-design-vue';
// 2021-03-10  zk  优化减掉 antv   viser-vue依赖于antv
// import Viser from 'viser-vue'
import 'ant-design-vue/dist/antd.less'; // or 'ant-design-vue/dist/antd.less'

import '@/permission'; // permission control
import '@/utils/filter'; // base filter
import Print from 'vue-print-nb-jeecg';
/*import '@babel/polyfill'*/

// 预览图片的插件  zk 2021-03-24  优化减掉   卸载
// import preview from 'vue-photo-preview'
// import 'vue-photo-preview/dist/skin.css'


import 'xe-utils';
import './plugins/vxeTable';

import * as filters from "@/utils/filters";

// import 'xe-utils'
// import VXETable from 'vxe-table'
// import 'vxe-table/lib/style.css'

import XEUtils from 'xe-utils';
import VXEUtils from 'vxe-utils';


import './assets/addicon/iconfont';
// mounts 可以设置['cookie', 'browse', 'locat']
Vue.use(VXEUtils, XEUtils, {
  mounts: ['cookie']
});
// Vue.use(VXETable);

// import './plugins/utils'
// import './plugins/table'
// 生成icon图标
// const MyIcon = Icon.createFromIconfontCN({
//   scriptUrl: '//at.alicdn.com/t/font_8d5l8fzk5b87iudi.js', // 在 iconfont.cn 上生成
// });

// 纠正toFixed  的问题
Number.prototype.toFixed = function(exponent) {
  let num = Math.round(this * Math.pow(10, exponent)) / Math.pow(10, exponent);
  if (num.toString().indexOf('.') == '-1') {
    if (exponent > 0) {
      num = num + '.';
      for (let i = 0; i < exponent; i++) {
        num = num + '0';
      }
    }

  }
  return num;
}


Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key]) //插入过滤器名和对应方法
})

// console.log(filters)
// Object.keys(filters).forEach(key=>{
// Vue.filter('a',filters)//插入过滤器名和对应方法
// })

import {
  formatDate
} from '@/utils/time';

Vue.prototype.$formatDate = formatDate;
// 表格内容超出气泡提示方法
// columns 表格json isEllipsis 气泡关闭开启 默认打开
Vue.prototype.$tableTooltip = (columns, isEllipsis = true) => {
  columns.forEach((value, index) => {
    columns[index].ellipsis = isEllipsis;
  })
}
import {
  ACCESS_TOKEN,
  DEFAULT_COLOR,
  DEFAULT_THEME,
  DEFAULT_LAYOUT_MODE,
  DEFAULT_COLOR_WEAK,
  SIDEBAR_TYPE,
  DEFAULT_FIXED_HEADER,
  DEFAULT_FIXED_HEADER_HIDDEN,
  DEFAULT_FIXED_SIDEMENU,
  DEFAULT_CONTENT_WIDTH_TYPE,
  DEFAULT_MULTI_PAGE
} from "@/store/mutation-types";
import config from '@/defaultSettings';

import JDictSelectTag from './components/dict/index.js';
import hasPermission from '@/utils/hasPermission';
import vueBus from '@/utils/vueBus';
import JeecgComponents from '@/components/jeecg/index';
import '@/assets/less/JAreaLinkage.less';
import VueAreaLinkage from 'vue-area-linkage';

Vue.prototype.doMian = window._CONFIG.domianURL;



Vue.config.productionTip = false;
Vue.use(Storage, config.storageOptions);
Vue.use(Antd);
Vue.use(VueAxios, router);
// Vue.use(Viser)
Vue.use(hasPermission);
Vue.use(JDictSelectTag);
Vue.use(Print);
//  Vue.use(preview)
Vue.use(vueBus);
Vue.use(JeecgComponents);
Vue.use(VueAreaLinkage);
// Vue.use(VueAreaLinkage);
new Vue({
  router,
  store,
  // components: {
  //   'my-icon': MyIcon,
  // },
  // template: '<my-icon type="icon-example" />',
  mounted() {
    store.commit('SET_SIDEBAR_TYPE', Vue.ls.get(SIDEBAR_TYPE, true))
    store.commit('TOGGLE_THEME', Vue.ls.get(DEFAULT_THEME, config.navTheme))
    store.commit('TOGGLE_LAYOUT_MODE', Vue.ls.get(DEFAULT_LAYOUT_MODE, config.layout))
    store.commit('TOGGLE_FIXED_HEADER', Vue.ls.get(DEFAULT_FIXED_HEADER, config.fixedHeader))
    store.commit('TOGGLE_FIXED_SIDERBAR', Vue.ls.get(DEFAULT_FIXED_SIDEMENU, config.fixSiderbar))
    store.commit('TOGGLE_CONTENT_WIDTH', Vue.ls.get(DEFAULT_CONTENT_WIDTH_TYPE, config.contentWidth))
    store.commit('TOGGLE_FIXED_HEADER_HIDDEN', Vue.ls.get(DEFAULT_FIXED_HEADER_HIDDEN, config.autoHideHeader))
    store.commit('TOGGLE_WEAK', Vue.ls.get(DEFAULT_COLOR_WEAK, config.colorWeak))
    store.commit('TOGGLE_COLOR', Vue.ls.get(DEFAULT_COLOR, config.primaryColor))
    store.commit('SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))
    store.commit('SET_MULTI_PAGE', Vue.ls.get(DEFAULT_MULTI_PAGE, config.multipage))
  },
  render: h => h(App)
}).$mount('#app')
