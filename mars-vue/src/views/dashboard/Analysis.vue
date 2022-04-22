<template>
  <div class="HmoePageDiv">
    <user-password ref="userPassword"></user-password>
    <!-- 背景图 -->
<!--    <img src="../../assets/dashboard.jpg" alt="静态图片" class="staticImgBcg">-->
    <!-- 图标 -->
    <!-- <div class="signNo7" ></div> -->
    <!-- <div class="signNo1" @mouseover="moverOver(1)" @mouseout="moverOut(1)"></div>
    <div class="signNo2" @mouseover="moverOver(2)" @mouseout="moverOut(2)"></div>
    <div class="signNo3" @mouseover="moverOver(3)" @mouseout="moverOut(3)"></div>
    <div class="signNo4" @mouseover="moverOver(4)" @mouseout="moverOut(4)"></div>
    <div class="signNo5" @mouseover="moverOver(5)" @mouseout="moverOut(5)"></div>
    <div class="signNo6" @mouseover="moverOver(6)" @mouseout="moverOut(6)"></div> -->
    <!-- @mouseover="moverOver(1)" @mouseout="moverOut(1)" @mouseover="moverOver(2)"  -->
    <!-- <img
      src="../../assets/homePage/矢量智能对象.png"
      alt="图标1"
      class="signNo1"
      @mouseover="moverOver(1)"
      @mouseout="moverOut(1)"
    />-->
    <!-- <img
      src="../../assets/homePage/矢量智能对象1.png"
      alt="图标2"
      class="signNo2"
      @mouseover="moverOver(2)"
      @mouseout="moverOut(2)"
    />
    <img
      src="../../assets/homePage/矢量智能对象3.png"
      alt="图标3"
      class="signNo3"
      @mouseover="moverOver(3)"
      @mouseout="moverOut(3)"
    />
    <img
      src="../../assets/homePage/矢量智能对象4.png"
      alt="图标4"
      class="signNo4"
      @mouseover="moverOver(4)"
      @mouseout="moverOut(4)"
    />
    <img
      src="../../assets/homePage/矢量智能对象5.png"
      alt="图标5"
      class="signNo5"
      @mouseover="moverOver(5)"
      @mouseout="moverOut(5)"
    />
    <img
      src="../../assets/homePage/矢量智能对象6.png"
      alt="图标6"
      class="signNo6"
      @mouseover="moverOver(6)"
      @mouseout="moverOut(6)"
    />-->
    <!-- 图像 -->
    <!-- <img src="~@/assets/homePage/imgNo1.png" alt="静态图片" class="imgNo1" />
    <img src="~@/assets/homePage/imgNo2.png" alt="静态图片" class="imgNo2" />
    <img src="~@/assets/homePage/imgNo3.png" alt="静态图片" class="imgNo3" />
    <img src="~@/assets/homePage/imgNo4.png" alt="静态图片" class="imgNo4" />
    <img src="~@/assets/homePage/imgNo5.png" alt="静态图片" class="imgNo5" />
    <img src="~@/assets/homePage/imgNo6.png" alt="静态图片" class="imgNo6" />
    <img src="~@/assets/homePage/矢量智能对象8.png" alt="静态图片" class="imgNo7" /> -->


    <!-- 服务框 -->
    <!-- <SmallUtils class="modal1" name="税控服务器" lineIn="税控服务器管理"  :listPage="['新增','发行','制证']"></SmallUtils>
      <SmallUtils class="modal2" name="纳税人信息" lineIn="纳税人信息维护"  :listPage="['新增或导入','平台绑定']"></SmallUtils>
      <SmallUtils class="modal3" name="设备" lineIn="设备发行"  :listPage="['查询','发行设备制证','查询','新办证书']"></SmallUtils>
      <SmallUtils class="modal4" name="开票终端" lineIn="开票终端管理"  :listPage="['新 增']"></SmallUtils>
      <SmallUtils class="modal5" name="发票申请购领" lineIn="申领准备信息查询"  :listPage="['查询 发行设备制证']"></SmallUtils>
    <SmallUtils class="modal6" name="发票分发回收" lineIn="未分发库存管理"  :listPage="['查询','分发','退票 已分发库存管理']"></SmallUtils>-->
    <!-- <SmallUtils class="modal1" name="税控服务器" :listPage="borderNo1" ref="modal1"></SmallUtils>
    <SmallUtils class="modal2" name="纳税人信息" :listPage="borderNo2" ref="modal2"></SmallUtils>
    <SmallUtils class="modal3" name="设备" :listPage="borderNo3" ref="modal3"></SmallUtils>
    <SmallUtils class="modal4" name="开票终端" :listPage="borderNo4" ref="modal4"></SmallUtils>
    <SmallUtils class="modal5" name="发票申请购领" :listPage="borderNo5" ref="modal5"></SmallUtils>
    <SmallUtils class="modal6" name="发票分发、退票" :listPage="borderNo6" ref="modal6"></SmallUtils> -->

    <!-- 横线 -->
    <!-- <img src="../../assets/homePage/矢量智能对象16.png" alt class="staticLine" /> -->

    <!-- 圆 -->
    <!-- <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo1" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo2" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo3" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo4" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo5" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo6" />
    <img src="../../assets/homePage/矢量智能对象拷贝3.png" alt="静态图片" class="circleNo7" />
    <img src="../../assets/homePage/矢量智能对象7.png" alt="静态图片" :class="circleNo" ref="blackCircle" /> -->

    <!-- <index-chart v-if="indexStyle==1"></index-chart>
    <index-bdc v-if="indexStyle==2"></index-bdc>
    <index-task v-if="indexStyle==3"></index-task>
    <div style="width: 100%;text-align: right;margin-top: 20px">
      请选择首页样式：
      <a-radio-group v-model="indexStyle">
        <a-radio :value="1">统计图表</a-radio>
        <a-radio :value="2">统计图表2</a-radio>
        <a-radio :value="3">任务表格</a-radio>
      </a-radio-group>
    </div>-->
  </div>
</template>

<script>
import UserPassword from '@/components/tools/UserPassword'
import SmallUtils from './smallUtils.vue'

import {
  // mapActions,
  mapGetters,
  mapState
} from 'vuex'
// 2021-03-10   zk  优化放弃掉antv
// import { CONSTANTS } from '@antv/data-set'
// import IndexChart from './IndexChart'
// import IndexTask from "./IndexTask"
// import IndexBdc from './IndexBdc'

export default {
  name: 'Analysis',
  components: {
    // 第一次进入改密码组件
    UserPassword,
    SmallUtils,
    // IndexChart,
    // IndexTask,
    // IndexBdc
  },
  computed: {
      ...mapState({
        // 后台菜单
        permissionMenuList: state => state.user.permissionList
      })
  },    
  data() {
    return {
      timer: null,
      jiShiNumber: 1,

      userPossessList: [],
// 可用不可用的限制
      ddhb: false,
      ddcf: false,
      ddcx: false,
      ddkpsq: false,

      zzsgtk: false,
      dzsgtk: false,
      dpsgtk: false,

      fpkjgl: false,
      ykfpcx: false,
      dbsx: false,
      // indexStyle:1
      // circleNo: 'circleNo8',
    }
  },
  created(){
      this.getCaiDanList()
  },
  mounted() {
      this.firstLogin = localStorage.getItem('FIRST_LOGIN')
      this.passwordEffective = localStorage.getItem('PASSWORD_EFFECTIVE')
      if (this.firstLogin == 'true') {
        this.show(0)
      }
      if (this.passwordEffective == 'false') {
        this.show(1)
      }
      // this.bannerImg(this)
      
  },
  beforeDestroy() {
    clearInterval(this.timer)
    this.jiShiNumber = 1
  },

  methods: {
    // 只遍历两层
    clickJumpRouter(jumpOrNo,routpath,item){
      if(jumpOrNo){
        this.$router.push({
          name: routpath,
          params: {
            id: item,
          },
        })
      }
       
    },
    getCaiDanList(){
      this.userPossessList = []

      console.log(this.permissionMenuList)
      // for(let i = 0;i<this.permissionMenuList.length;i++){
      //   if(this.permissionMenuList[i].children){

      //   }
      // }
      // 把整个树化为一维的
      this.permissionMenuList.map(item=>{
        if(item.children){
          this.userPossessList.push(...item.children)
        }
      })
      // 遍历这棵树，如果相等，就true
      for(let i = 0;i<this.userPossessList.length;i++){
        switch(this.userPossessList[i].name){
          // 订单合并
          case 'orderBusinessMange-orderToMerge':
            this.ddhb = true
            break
          // 订单拆分
          case 'orderBusinessMange-positiveNumSplit':
            this.ddcf = true
            break
          // 订单查询
          case 'orderBusinessMange-outAnInvoce':
            this.ddcx = true
            break
          // 订单开票申请
          case 'orderBusinessMange-addReceiptsInvoiceApply':
            this.ddkpsq = true
            break
          // 纸专手工填开
          case 'manuallyMakeInvoice-manualMakeInvoiceSpecialInvoice':
            this.zzsgtk = true
            break
          // 电专手工填开
          case 'manuallyMakeInvoice-electronicSpecialInvoice':
            this.dzsgtk = true
            break
          // 电普手工填开
          case 'manuallyMakeInvoice-manualMakeInvoice':
            this.dpsgtk = true
            break
          // 发票开具管理
          case 'ManagementMakeInvoice-electroniceOrdinary':
            this.fpkjgl = true
            break
          // 已开发票查询
          case 'ManagementMakeInvoice-handInvoiceOrdinary':
            this.ykfpcx = true
            break
          case 'auditAndControlSystem-uncleaned':
            this.dbsx = true
            break
        }
      }
      console.log(this.userPossessList)
    },
    moverOver(val) {
      switch (val) {
        case 1:
          this.$refs.modal1.$el.className = 'modal1Hover'
          break
        case 2:
          this.$refs.modal2.$el.className = 'modal2Hover'
          break
        case 3:
          this.$refs.modal3.$el.className = 'modal3Hover'
          break
        case 4:
          this.$refs.modal4.$el.className = 'modal4Hover'
          break
        case 5:
          this.$refs.modal5.$el.className = 'modal5Hover'
          break
        case 6:
          this.$refs.modal6.$el.className = 'modal6Hover'
          break
      }
      // 停止定时器
      // clearInterval(this.timer)
    },
    moverOut(val) {
      switch (val) {
        case 1:
          this.$refs.modal1.$el.className = 'modal1'
          break
        case 2:
          this.$refs.modal2.$el.className = 'modal2'
          break
        case 3:
          this.$refs.modal3.$el.className = 'modal3'
          break
        case 4:
          this.$refs.modal4.$el.className = 'modal4'
          break
        case 5:
          this.$refs.modal5.$el.className = 'modal5'
          break
        case 6:
          this.$refs.modal6.$el.className = 'modal6'
          break
      }
      // 启动函数
      // this.bannerImg()
    },
    bannerImg(val) {
    
      let that = val
      that.timer = setInterval(function () {
        that.jiShiNumber++
        switch (that.jiShiNumber) {
          case 1:
            that.$refs.blackCircle.style.left = '11%'
            break
          case 2:
            that.$refs.blackCircle.style.left = '24%'
            break
          case 3:
            that.$refs.blackCircle.style.left = '37.5%'
            break
          case 4:
            that.$refs.blackCircle.style.left = '51.5%'
            break
          case 5:
            that.$refs.blackCircle.style.left = '65%'
            break
          case 6:
            that.$refs.blackCircle.style.left = '78%'
            break
          case 7:
            that.$refs.blackCircle.style.left = '90%'
            that.jiShiNumber = 0
            break
        }
      }, 2500)
    },
    ...mapGetters(['userInfo']),
    show(e) {
      if (e == 0) {
        this.$notification['warning']({
          message: '提示',
          description: '第一次登录请修改密码',
        })
      } else {
        this.$notification['warning']({
          message: '提示',
          description: '密码到期请修改密码',
        })
      }

      let username = this.userInfo().username
      // console.log(this.userInfo().username);
      this.$refs.userPassword.show(username, 0)
    },
  },
}
</script>

<style lang="less" scoped>
.firstPages{
  width: 70%;
  height: 25%;
  border: 1px solid #3fa9f5;
  // margin-bottom: 50px;
  margin-bottom: 0.55em;
  position: relative;
  border-radius: 10px;
}
.firstPages1{
  width: 70%;
  height: 25%;
  display: flex;
  justify-content: space-between;
  // border: 1px solid #3fa9f5;
  // border-radius: 10px;
}
.firstPagesEvery{
  width: 30%;
  // height: 20%;
  border: 1px solid #3fa9f5;
  position: relative;
  border-radius: 10px;
  position: relative;

}
.icon {
  // width: 0.4rem;
  // height: 0.4rem;
  // vertical-align: -0.15rem;
  width: 0.6em;
  height: 0.6em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  color: #3fa9f5;
}
.icon:hover{
  color: #a0d9f5;
}

.icon:active{
  color: #0071bc;
}
.iconheihua{
  //  width: 0.4rem;
  // height: 0.4rem;
  // vertical-align: -0.15rem;
   width: 0.6em;
  height: 0.6em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  color: #b3b3b3;
}

.firstPages_shu{
  position: absolute;
  left: 50%;
  top: -5%;
  border-left: 1px solid #3fa9f5;
  border-right: 1px solid #3fa9f5;
  height: 15px;
  transform: translate(-50%);
}
.firstPages_Kb{
  width: 150px;
  height: 15px;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
.firstPages_bt{
  width: 80%;
  // height: 40px;
  height: 2em;
  display: flex;
  justify-content: center;
  align-items: center;
  // line-height: 35px;
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  color: #3fa9f5;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #3fa9f5 ;
  border-radius: 20px;
}
.firstPages_shu1{
  position: absolute;
  left: 50%;
  top: -5%;
  transform: translate(-50%);
  border-left: 1px solid #3fa9f5;
  border-right: 1px solid #3fa9f5;
  height: 15px;
}
.firstPages_Kb1{
  width: 120px;
  height: 15px;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
.firstPages_bt1{
  width: 80%;
  // height: 30px;
  height: 2em;
  display: flex;
  justify-content: center;
  align-items: center;
  // line-height: 35px;
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  color: #3fa9f5;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #3fa9f5 ;
  border-radius: 20px;
}
.firstPages_second{
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  // align-items: flex-end;
}
.icon_and_fontbox{
  //  width: 150px;
  // height: 100px;
   width: 1.8em;
  height: 1.1em;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.icon_and_fontbox_copy{
  //  width: 150px;
  // height: 100px;
   width: 1.8em;
  height: 1.1em;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.icon_and_fontbox:hover{
  // width: 150px;
  // height: 100px;
    width: 1.8em;
  height: 1.1em;
  border-radius: 10px;
  background-color: #E5FFFE;
  cursor: pointer;
}
.icon_bt_zidingying{
  font-size: 14px ;
  color: #666666;
  margin-top: 10px;
}
// .HmoePageDiv { font-size:100px; }
@media (min-width: 1920px) {
  .HmoePageDiv {
    font-size: 100px;
  }
}
@media (max-width: 1920px) {
  .HmoePageDiv {
    font-size: 100px;
  }
}
@media (max-width: 1680px) {
  .HmoePageDiv {
    font-size: 87.5px;
  }
}
@media (max-width: 1440px) {
  .HmoePageDiv {
    font-size: 75px;
  }
}
@media (max-width: 1366px) {
  .HmoePageDiv {
    font-size: 71px;
  }
}
@media (max-width: 1208px) {
  .HmoePageDiv {
    font-size: 63px;
  }
}
@media (max-width: 1024px) {
  .HmoePageDiv {
    font-size: 53px;
  }
}
@media (max-width: 800px) {
  .HmoePageDiv {
    font-size: 42px;
  }
}
// 盒子
.HmoePageDiv {
  display: flex;
  // justify-content: center;
  align-items: center;
  flex-direction: column;
  background: #fff;
  height: 100%;
  position: relative;
  padding-top: 50px;
}
// .HmoePageDiv {
//   padding: 117px 116px 167px 95px;
//   background: #fff;
//   height: 100%;
//   position: relative;
// }
// 图片
// .staticImgBcg{
//   width: 100%;
//   height: 100%;
// }
// 横线
.staticLine {
  width: 78%;
  position: absolute;
  top: 44.5%;
  left: 51%;
  transform: translateX(-50%);
  z-index: 1;
}

// img 图像

.imgNo1 {
  width: 6%;
  position: absolute;
  top: 48%;
  left: 9%;
  z-index: 2;
}
.imgNo2 {
  width: 6%;
  position: absolute;
  top: 28%;
  left: 22.5%;
  z-index: 2;
}
.imgNo3 {
  width: 6%;
  position: absolute;
  top: 48%;
  left: 35%;
  z-index: 2;
}
.imgNo4 {
  width: 6%;
  position: absolute;
  top: 28%;
  left: 49%;
  z-index: 2;
}
.imgNo5 {
  width: 6%;
  position: absolute;
  top: 48%;
  left: 63%;
  z-index: 2;
}
.imgNo6 {
  width: 6%;
  position: absolute;
  top: 27%;
  left: 76%;
  z-index: 2;
}
.imgNo7 {
  width: 4%;
  position: absolute;
  top: 29%;
  left: 88%;
  z-index: 2;
}
// modle 边框
.modal1 {
  position: absolute;
  top: 11%;
  left: 5%;
  //  top: 1em;
  // left: 0.8em;
  z-index: 1;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal1Hover {
  position: absolute;
   top: 11%;
  left: 5%;
  //  top: 1em;
  // left: 0.8em;
  z-index: 1;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
.modal2 {
  position: absolute;
  top: 63%;
  left: 18%;
  z-index: 1;
  // height: 14%;
  //  width: 15%;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal2Hover {
  position: absolute;
  top: 63%;
  left: 18%;
  z-index: 1;
  // height: 14%;
  //  width: 15%;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
.modal3 {
  position: absolute;
   top: 10%;
  left: 31%;
  z-index: 1;
  //  height: 14%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.4em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal3Hover {
  position: absolute;
 top: 10%;
  left: 31%;
  z-index: 1;
  //  height: 14%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.4em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
.modal4 {
  position: absolute;
  top: 63%;
  left: 46%;
  z-index: 1;
  // height: 14%;
  //  width: 15%;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal4Hover {
  position: absolute;
  top: 63%;
  left: 46%;
  z-index: 1;
  // height: 14%;
  //  width: 15%;
  // width: 2.5em;
  // height: 1.3em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
.modal5 {
  position: absolute;
  top: 5%;
  left: 58%;
  z-index: 1;
  //  height: 20%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.6em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal5Hover {
  position: absolute;
  top: 5%;
  left: 58%;
  z-index: 1;
  //  height: 20%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.6em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
.modal6 {
  position: absolute;
  top: 63%;
  left: 72%;
  z-index: 1;
  //  height: 14%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.5em;
  border: 2px solid #ceecf8;
  border-radius: 10px;
}
.modal6Hover {
  position: absolute;
  top: 63%;
  left: 72%;
  z-index: 1;
  //  height: 14%;
  //   width: 15%;
  // width: 2.5em;
  // height: 1.5em;
  border: 2px solid skyblue;
  border-radius: 10px;
}
//
// .signNo7{
//   width: 0.5em;
//   height: 0.7em;
//   background:  url(../../assets/homePage/图层55.png) ;
//   background-repeat:no-repeat;
//     background-size:100% 100%;
//   position: absolute;
//   top: 32%;
//   left: 10%;
//   z-index: 3;
// }
// .signNo7:hover{
//     // width: 1em;
//     // height: 1.4em;
//   //   width: 0.5em;
//   // height: 0.7em;
//     transform: scale(1.1);
//     background:  url(../../assets/homePage/图层50.png) ;
//     background-repeat:no-repeat;
//     background-size:100% 100%;
// }

.signNo1 {
  width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo1.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 31%;
  left: 9.9%;
  z-index: 2;
}
.signNo1:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo1Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.signNo2 {
   width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo2.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 47%;
  left: 22.9%;
  z-index: 2;
}
.signNo2:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo2Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.signNo3 {
 width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo3.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 31%;
  left: 36.4%;
  z-index: 2;
}
.signNo3:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo3Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.signNo4 {
  width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo4.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 47%;
  left: 50.4%;
  z-index: 2;
}
.signNo4:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo4Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.signNo5 {
  width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo5.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 31%;
  left: 63.9%;
  z-index: 2;
}
.signNo5:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo5Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.signNo6 {
    width: 0.6em;
  height: 0.8em;
  background: url(../../assets/homePage/signNo6.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: absolute;
  top: 47%;
  left: 77%;
  z-index: 2;
}
.signNo6:hover {
  transform: scale(1.1);
  background: url(../../assets/homePage/signNo6Hover.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

// .signNo1 {
//   width: 0.5em;
//   position: absolute;
//   top: 32%;
//   left: 10%;
//   z-index: 2;
// }
// .signNo1:hover {
//   transform: scale(1.1);
// }
// .signNo2 {
//   // width: 3%;
//   width: 0.5em;
//   position: absolute;
//   top: 47%;
//   left: 23.2%;
//   z-index: 2;
// }
// .signNo2:hover {
//   transform: scale(1.1);
// }
// .signNo3 {
//   // width: 3%;
//   width: 0.5em;
//   position: absolute;
//   top: 32%;
//   left: 36.8%;
//   z-index: 2;
// }
// .signNo3:hover {
//   transform: scale(1.1);
// }
// .signNo4 {
//   //  width: 3%;
//   width: 0.5em;
//   position: absolute;
//   top: 47%;
//   left: 50.8%;
//   z-index: 2;
// }
// .signNo4:hover {
//   transform: scale(1.1);
// }
// .signNo5 {
//   // width: 3%;
//   width: 0.5em;
//   position: absolute;
//   top: 32%;
//   left: 64.3%;
//   z-index: 2;
// }
// .signNo5:hover {
//   transform: scale(1.1);
// }
// .signNo6 {
//   //  width: 3%;
//   width: 0.5em;
//   position: absolute;
//   top: 47%;
//   left: 77.3%;
//   z-index: 2;
// }
// .signNo6:hover {
//   transform: scale(1.1);
// }
// 圆
.circleNo1 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 11%;
  z-index: 2;
}
.circleNo2 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 24%;
  z-index: 2;
}
.circleNo3 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 37.5%;
  z-index: 2;
}
.circleNo4 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 51.5%;
  z-index: 2;
}
.circleNo5 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 65%;
  z-index: 2;
}
.circleNo6 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 78%;
  z-index: 2;
}
.circleNo7 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 90%;
  z-index: 2;
}
.circleNo8 {
  width: 1.5%;
  position: absolute;
  top: 43%;
  left: 11%;
  z-index: 2;
  // opacity: 1;
  opacity: 0;
  animation: goOut 2.5s infinite;
}

@keyframes goOut {
  0% {
    transform: scale(0.1);
    // opacity:0;
    opacity: 1;
  }
  10% {
    transform: scale(0.2);
    // opacity:0;
    opacity: 1;
  }
  15% {
    transform: scale(0.3);
    // opacity:0;
    opacity: 1;
  }
  20% {
    transform: scale(0.4);
    // opacity:0;
    opacity: 1;
  }

  25% {
    transform: scale(0.5);
    opacity: 1;
  }
  50% {
    transform: scale(0.6);
    // opacity:0;
    opacity: 1;
  }
  60% {
    transform: scale(0.5);
    // opacity:0;
    opacity: 1;
  }
  75% {
    transform: scale(0.4);
    opacity: 1;
  }
  80% {
    transform: scale(0.3);
    opacity: 1;
  }
  85% {
    transform: scale(0.2);
    opacity: 1;
  }
  90% {
    transform: scale(0.1);
    opacity: 1;
  }
  95% {
    transform: scale(0);
    opacity: 0;
  }
  100% {
    opacity: 0;
  }
}
</style>