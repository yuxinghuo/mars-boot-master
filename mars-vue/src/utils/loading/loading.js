 import Loading from './loading.vue';
 import Vue from 'vue';
 //   import {JlMax} from '../antiShake'
 export default () => {
   let LoadingComponent = Vue.extend(Loading);
   let child = new LoadingComponent({ 
       //动态创建挂载点
     el: document.createElement("div"),
     data: {
       flag: false,
     },
     methods: {
       //     handlemount:JlMax(function(){
       //         console.log('执行+1')
       //         this.flag=true;
       //    },5000),
       //     handleDestory:JlMax(function(){
       //         console.log('执行-1')

       //         this.flag=false;
       //     },5000),
       handlemount() {
         this.flag = true;
       },
       handleDestory() {
         this.flag = false;
       },
     }
   })
   document.body.appendChild(child.$mount().$el)
   return child;
 }