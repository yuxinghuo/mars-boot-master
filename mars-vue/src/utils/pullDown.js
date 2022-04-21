import {
  getAction
} from '@/api/manage';
import { FD } from '@/views/invoiceInformation/smallUtils/antiShake'
//   direction for use .md
//1、import { getContext, Inputduty, ScrollInfinite, Selectyou } from './smallUtils/pullDown'
//2、getContext(this)     
//3、 

let context = null;
export function getContext(that) {
  context = that
  context.formobj = {
    pageNo: 1,
    pageSize: 10,
    taxpayerId: '',
  }
  // 区分设备
   if(context.serverType){
  console.log(context.serverType,'context.serverType')

    context.formobj.serverType = context.serverType
  }
  context.nsrsbhList = []
  context.onOff = false
}

//Inputduty   动态输入，去请求
export const Inputduty = FD( function (InputValue) {
  console.log(InputValue,'pullDown InputValue')
  context.formobj.taxpayerId = InputValue
  context.formobj.pageNo = 1
  // 区分设备
  if(context.serverType){
    context.formobj.serverType = context.serverType
  }
  // Backspace删除
  if (InputValue === '' || InputValue === undefined) {
    // context.formobj.taxpayerId = '',
    // NumberList(context.formobj)
  } else {
    NumberList(context.formobj)
  }

},300)
//无限滚动下拉
export const ScrollInfinite = function (e) {
  const {
    target
  } = e;
  let length = context.nsrsbhList.length;
  let top = e.target.scrollTop
  if (top > (length - 9) * 32 && context.onOff) {
    context.onOff = false
    NumberList(context.formobj, e);
  }
}
//选中清零
export const Selectyou = function (e) {
  context.formobj.taxpayerId = undefined,
  context.formobj.pageNo = 1,
  context.formobj.pageSize = 10,
  context.nsrsbhList = []
  NumberList(context.formobj)

}

export const NumberList = function (message, e) {
  if (message) {
    message.isLoading = true
  }

  
  // 检查是不是空，分两种情况
  // 1、为空，我需要先添加message  然后看有没有sercerType加上
  if(!message){
    if(context.serverType){
      message = {
        serverType: context.serverType
      }
    }
  }else{
    // 2、不为空，我直接检查有没有sercerType  有就加上
    if(context.serverType){
      message.serverType= context.serverType
    }
  }
 
  console.log(message)
  getAction(context.scrollUrl, message).then((res) => {
    if (res.code === 200) {
      context.nsrsbhList.push(...res.result.records)
      let hash = {};
      context.nsrsbhList = context.nsrsbhList.reduce((preVal, curVal) => {
        hash[curVal.taxpayerId] ? '' : hash[curVal.taxpayerId] = true && preVal.push(curVal);
        return preVal
      }, [])
      if (res.result.records.length == 0) {
        context.onOff = false
      } else {
        context.onOff = true
        context.formobj.pageNo++
      }

      if (!e) {

      } else {
        e.target.scrollTop -= 30
      }
      console.log('执行完了')
    } else {
      context.$message.error(res.message);
    }

  })
}
//如果页面有多个scroll   的话
//标签
// @search ="Inputduty"
// @popupScroll ="fn2"
//  @select ="fn3"
// @change="getTaxNumber" 
//<a-select-option v-for="item in taxPayerNumberList" :key="item.taxpayerId" :value="item.taxpayerId">
// {{item.taxpayerId}}
// </a-select-option> 
//data       
// taxPayerNumberList:[],
// onOff:false,
// taxPayerNumber : {
//   pageNo: 1,
//   pageSize: 10,
//    taxpayerId: 1,
// },
//事件
// Inputduty(InputValue){
//     this.taxPayerNumber.taxpayerId = InputValue
//         if(InputValue == ''){
//             this.taxPayerNumber.taxpayerId = 1,
//             this.taxPayerNumber.pageNo = 1,
//             this.NumberList(this.taxPayerNumber)
//             }
//         else{
//             this.NumberList(this.taxPayerNumber)
//               }
//       },

//  fn2(e){
//   const { target } = e;
//   let length = this.taxPayerNumberList.length;
//   let top = e.target.scrollTop
//   if(top > (length-9)*32 && this.onOff ) {
//       this.onOff = false
//       this.NumberList(this.taxPayerNumber,e);	
//            }
//       },

// fn3(){

//     this.taxPayerNumber.taxpayerId = '',
//     this.taxPayerNumber.pageNo = 1,
//     this.taxPayerNumberList = []
//  
//         // },
// 慢慢改吧