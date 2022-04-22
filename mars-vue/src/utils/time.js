// 时间戳格式化
const formatDate=(date,type)=> {
  var date = new Date(date);
  var YY = date.getFullYear() + '-';
  var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
  var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
  var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
  var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
  // Y是YY-MM-DD格式 YH是YY-MM-DD hh + mm + ss格式
  if(type=='Y'){
    return YY + MM + DD;
  }else if(type=='YH'){
    return YY + MM + DD +" "+hh + mm + ss;
  }else{
    return '传入错误'
  }
  
}
export{
  formatDate
}