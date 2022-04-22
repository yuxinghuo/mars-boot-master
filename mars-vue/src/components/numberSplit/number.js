// 数字超出8位数截取方法
export function numberSplit(e) {
  console.log(e)
  let eStr = e.toString();
  let  interception = (eStr) => {
    if (eStr.length > 8) {
      return eStr.slice(0, 8)
    } else {
      return eStr
    }
  }
  if (eStr.indexOf('.') > 0) {
    return (eStr.split('.')[0] + '.' +  interception(eStr.split('.')[1])) - 0
  } else {
    return eStr - 0
  }
}
