import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import {
  VueAxios
} from './axios'
import {
  Modal,
  notification
} from 'ant-design-vue'
import {
  ACCESS_TOKEN
} from "@/store/mutation-types"

import get from 'lodash.get'
import loading from './loading/loading'
let vm = loading()



/**
 * 【指定 axios的 baseURL】
 * 如果手工指定 baseURL: '/jeecg-boot'
 * 则映射后端域名，通过 vue.config.js
 * @type {*|string}
 */
let apiBaseUrl = window._CONFIG['domianURL'] || "/aisino-newtaxcontrol";
console.log("apiBaseUrl= ", apiBaseUrl)
// 创建 axios 实例
const service = axios.create({
  //baseURL: '/jeecg-boot',
  baseURL: apiBaseUrl, // api base_url
  timeout: 9000 // 请求超时时间
})

const err = (error) => {
  console.log(error)
  count = count - 1
  if (count === 0) {
    vm.handleDestory();
  } else if (count < 0) {
    count = 0
    vm.handleDestory()
  }

  if (error.message.includes('timeout')) {
    notification.error({
      message: '系统提示',
      description: '请求超时！',
      duration: 4
    })
  }
  if (error.response) {
    let data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    console.log("------异常响应------", token)
    console.log("------异常响应------", error.response.status)
    switch (error.response.status) {
      case 403:
        notification.error({
          message: '系统提示',
          description: '拒绝访问',
          duration: 4
        })
        break
      case 500:
        //notification.error({ message: '系统提示', description:'Token失效，请重新登录!',duration: 4})
        if (token && data.message == "Token失效，请重新登录") {
          // update-begin- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
          // store.dispatch('Logout').then(() => {
          //     window.location.reload()
          // })
          Modal.error({
            title: '登录已过期',
            content: '很抱歉，登录已过期，请重新登录',
            okText: '重新登录',
            mask: false,
            onOk: () => {
              store.dispatch('Logout').then(() => {
                Vue.ls.remove(ACCESS_TOKEN)
                window.location.reload()
              })
            }
          })
          // update-end- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
        }
        break
      case 404:
        notification.error({
          message: '系统提示',
          description: '很抱歉，资源未找到!',
          duration: 4
        })
        break
      case 504:
        notification.error({
          message: '系统提示',
          description: '网络超时'
        })
        break
      case 401:
        notification.error({
          message: '系统提示',
          description: '未授权，请重新登录',
          duration: 4
        })
        if (token) {
          store.dispatch('Logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
        break
      default:
        notification.error({
          message: '系统提示',
          description: data.message,
          duration: 4
        })
        break
    }
  }
  return Promise.reject(error)
};


let count = 0

// request interceptor
service.interceptors.request.use(config => {
  console.log(config)
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['X-Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  if (config.method == 'get') {
    if (config.url.indexOf("sys/dict/getDictItems") < 0) {
      config.params = {
        _t: Date.parse(new Date()) / 1000,
        ...config.params
      }
    }
  }

  switch (config.method) {
    case "get":
      // if(!config.params.isLoading){
      //   count = count +1
      //   vm.handlemount();
      // }
      if (config.params === undefined) {
        count = count + 1
        vm.handlemount();
      } else if (!config.params.isLoading) {
        
        count = count + 1
        console.log(count,'进来了')
        vm.handlemount();
      }
      break;
    case "post":
    
      // if(!config.data.isLoading ){
      //     count = count +1
      //     vm.handlemount();
      //   }
      console.log('post的请求',typeof config.data)
      // console.log(config.data.indexOf('isLoading'))

      // console.log(JSON.parse(config.data))
      if (config.data === undefined) {
        console.log('111111')
        count = count + 1
        vm.handlemount();
      } 
      // else if (!config.data.isLoading) {
      //   console.log('22222')
      //   count = count + 1
      //   vm.handlemount();
      // }
      else if (typeof config.data == 'string'){
        if(config.data.indexOf('isLoading') == '-1' ){
          count = count + 1
          vm.handlemount();
        }
      }else if (typeof config.data == 'object'){
        if(!config.data.isLoading){
          count = count + 1
          vm.handlemount();
        }
      }
      break;
    case "put":
      // if(!config.data.isLoading){
      //   count = count +1
      //   vm.handlemount()
      // }
      if (config.data === undefined) {
        count = count + 1
        vm.handlemount()
      } else if (!config.data.isLoading) {
        count = count + 1
        vm.handlemount()
      }
      break
    case "delete":
      // if(!config.params.isLoading){
      //   count = count +1
      //   vm.handlemount()
      // }
      if (config.params === undefined) {
        count = count + 1
        vm.handlemount()
      } else if (!config.params.isLoading) {
        count = count + 1
        vm.handlemount()
      }
      break
  }
  return config
}, (error) => {

  return Promise.reject(error)
})


// const G_siwtch = FdMax(function(){
//   vm.handleDestory();
// },1000)
// response interceptor
service.interceptors.response.use(
  (response) => {
    console.log(response)
    // loading
    // G_siwtch()
    switch (response.config.method) {
      case "get":

        if (response.config.params === undefined) {
          count = count - 1
        } else if (!response.config.params.isLoading) {
          count = count - 1
        }
        // if(!response.config.params.isLoading){
        //   count = count -1
        // }
        break;
      case "post":
        console.log(typeof response.config.data)
        
        if (response.config.data === undefined) {
          count = count - 1
        } 
        
        // else if (!res.isLoading) {
        //   count = count - 1
        //   console.log(count)

        // }
        else if (typeof response.config.data == 'string'){
          // console.log(response.config.data)
          console.log(response.config.data.indexOf('isLoading'))
          if(response.config.data.indexOf('isLoading') == '-1' ){
              count = count - 1
          }

        }else if (typeof response.config.data == 'object'){
          if(!response.config.data.isLoading){
            count = count - 1
          }
        }
        else if (!JSON.parse(response.config.data.isLoading)) {
       
          count = count - 1
        }
        else if(typeof response.config.data === 'string'){
          response.config.data = JSON.parse(response.config.data)
        }
        
        // if(!response.config.data.isLoading){
        //   count = count -1
        // }
        console.log(count)
        break;
      case "put":
        if (response.config.data === undefined) {
          count = count - 1
        } else if (!response.config.data.isLoading) {
          count = count - 1
        }
        // if(!response.config.data.isLoading){
        //   count = count -1
        // }
        break
      case "delete":
        if (response.config.params === undefined) {

        } else if (!response.config.params.isLoading) {
          count = count - 1
        }
        // if(!response.config.params.isLoading){
        //   count = count -1
        // }
        break
    }
    if (count === 0) {
      vm.handleDestory()
    }
    return response.data
  },
   err)

const installer = {
  vm: {},
  install(Vue, router = {}) {
    Vue.use(VueAxios, router, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}