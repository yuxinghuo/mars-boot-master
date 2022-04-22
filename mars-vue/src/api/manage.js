import Vue from 'vue'
import { axios } from '@/utils/request'

const api = {
  user: '/api/user',
  role: '/api/role',
  service: '/api/service',
  permission: '/api/permission',
  permissionNoPager: '/api/permission/no-pager'
}

export default api

//post
export function postAction(url,parameter) {
  return axios({
    url: url,
    method:'post' ,
    data: parameter
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  return axios({
    url: url,
    method:method ,
    data: parameter
  })
}

//put
export function putAction(url,parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}
// put 增加超时时间  设备维护=》税控设备维护
export function customPutAction(url,parameter,timer = 20000) {
  return axios({
    url: url,
    method:'put',
    data: parameter,
    timeout: timer,
  })
}

//get
export function getAction(url,parameter) {
  return axios({
    url: url,
    method: 'get',
    params: parameter
  })
}
// 1正单开具，2开具管理，3审批时,开具（审核页面是审核）超时时间设置为30秒
// 负单提交的超时也修改到30s吧
//post
export function postAction30S(url,parameter) {
  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    timeout: 30000,
  })
}

// 分为post超时    和  get超时

// 设备查询（已发行）=>新办证书、更新证书
// 税控服务器管理        =>初始化 、更新证书、新办证书
// 设备的发行，注销、（未）
// 税控服务器发行 （未）
// 印章申请，下载 (测试过)
// 原因： 需要响应时间
// 字典编号overTimer  默认拿第一条
export function getActionSpecialys(url,parameter) {
  let timer = 20000
  let lastname = JSON.parse(
    sessionStorage.getItem('pro__UI_CACHE_DB_DICT_DATA') || localStorage.getItem('pro__UI_CACHE_DB_DICT_DATA')
  )
  if (lastname === null || lastname === undefined) {
    timer = 20000
    // this.$message.error('暂时未发现绑定状态，本地存储没有成功，请从新登录。')
  } else {
    if(lastname.value.overTimer){
      timer = Number(lastname.value.overTimer[0].value) 
      }else{
      timer = 20000
    }
  }
  console.log(timer)
  
  return axios({
    url: url,
    method: 'get',
    params: parameter,
    timeout: timer,
  
  })
}
export function getActionSpecial(url,parameter) {
  let timer = 20000
  let lastname = JSON.parse(
    sessionStorage.getItem('pro__UI_CACHE_DB_DICT_DATA') || localStorage.getItem('pro__UI_CACHE_DB_DICT_DATA')
  )
  if (lastname === null || lastname === undefined) {
    timer = 20000
    // this.$message.error('暂时未发现绑定状态，本地存储没有成功，请从新登录。')
  } else {
    if(lastname.value.overTimer){
      timer = Number(lastname.value.overTimer[0].value) 
      }else{
      timer = 20000
    }
  }
  console.log(timer)
  return axios({
    url: url,
    method: 'get',
    params: parameter,
    timeout: timer,
    responseType: 'blob'
  })
}
export function getActionSpecial1(url,parameter) {
  let timer = 20000
  let lastname = JSON.parse(
    sessionStorage.getItem('pro__UI_CACHE_DB_DICT_DATA') || localStorage.getItem('pro__UI_CACHE_DB_DICT_DATA')
  )
  if (lastname === null || lastname === undefined) {
    timer = 20000
    // this.$message.error('暂时未发现绑定状态，本地存储没有成功，请从新登录。')
  } else {
    if(lastname.value.overTimer){
      timer = Number(lastname.value.overTimer[0].value) 
      }else{
      timer = 20000
    }
  }
  console.log(timer)
  return axios({
    url: url,
    method:'get' ,
    params: parameter,
    timeout: timer,
    responseType: 'arraybuffer'
  })
}
export function postActionSpecial(url,parameter) {
  let timer = 20000
  let lastname = JSON.parse(
    sessionStorage.getItem('pro__UI_CACHE_DB_DICT_DATA') || localStorage.getItem('pro__UI_CACHE_DB_DICT_DATA')
  )
  if (lastname === null || lastname === undefined) {
    timer = 20000
    // this.$message.error('暂时未发现绑定状态，本地存储没有成功，请从新登录。')
  } else {
    if(lastname.value.overTimer){
    timer = Number(lastname.value.overTimer[0].value) 
    }else{
    timer = 20000
    }
  }
  console.log(timer)
  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    timeout: timer
  })
}
// 税收分类编码特使用途  =》2分钟  120秒  1000是一秒
export function InvoiceClasspostAction(url,parameter) {

  return axios({
    url: url,
    method:'post' ,
    data: parameter,
    timeout: 120000
  })
}
//deleteAction
export function deleteAction(url,parameter) {
  return axios({
    url: url,
    method: 'delete',
    params: parameter
  })
}

export function getUserList(parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList(parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList(parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions(parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService(parameter) {
  return axios({
    url: api.service,
    method: parameter.id == 0 ? 'post' : 'put',
    data: parameter
  })
}

/**
 * 下载文件 用于excel导出
 * @param url
 * @param parameter
 * @returns {*}
 */
export function downFile(url,parameter){
  return axios({
    url: url,
    params: parameter,
    method:'get' ,
    responseType: 'blob'
  })
}
export function postDownFile(url,data){
  return axios({
    url: url,
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

/**
 * 下载文件
 * @param url 文件路径
 * @param fileName 文件名
 * @param parameter
 * @returns {*}
 */
export function downloadFile(url, fileName, parameter) {
  return downFile(url, parameter).then((data) => {
    if (!data || data.size === 0) {
      Vue.prototype['$message'].warning('文件下载失败')
      return
    }
    if (typeof window.navigator.msSaveBlob !== 'undefined') {
      window.navigator.msSaveBlob(new Blob([data]), fileName)
    } else {
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link) //下载完成移除元素
      window.URL.revokeObjectURL(url) //释放掉blob对象
    }
  })
}

/**
 * 文件上传 用于富文本上传图片
 * @param url
 * @param parameter
 * @returns {*}
 */
export function uploadAction(url,parameter){
  return axios({
    url: url,
    data: parameter,
    method:'post' ,
    headers: {
      'Content-Type': 'multipart/form-data',  // 文件上传
    },
  })
}

/**
 * 获取文件服务访问路径
 * @param avatar
 * @param subStr
 * @returns {*}
 */
export function getFileAccessHttpUrl(avatar,subStr) {
  if(!subStr) subStr = 'http'
  if(avatar && avatar.startsWith(subStr)){
    return avatar;
  }else{
    if(avatar &&　avatar.length>0 && avatar.indexOf('[')==-1){
      return window._CONFIG['staticDomainURL'] + "/" + avatar;
    }
  }
}
