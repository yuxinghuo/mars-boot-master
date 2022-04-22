// 发票合计小写转大写
import nzhcn from 'nzh/cn';
const toChies = (e) => {
  let money='';
  money+='ⓧ';
  if(e!=null){
    money+=nzhcn.toMoney(e,{outSymbol:false}).replace('负','（负数）').replace('元','圆');
     if (money.charAt(money.length-1) == "圆" || money.charAt(money.length-1) == "角"){
        money = money+"整";
     }
  }
 return money;
}



export {
  toChies
}