<template>
  <div>
    <!-- <div class="box">
        <div class="box1">{{name}}</div>
        <div class="boxmargin" v-for="(item,index) in listPage" :key="index" >
            <div class="box2">{{item.lineIn}}</div>
            <div class="box3">
                <div class="hoverBox3" v-for="(item1,index) in item.childrenlist" :key="index" @click="goPage(item.lineIn,item1)">
                    <span class="box4"></span>
                    {{item1}}
                </div>
            </div>
           
        </div>
    </div>-->
    <div class="box">
      <div class="box1">{{name}}</div>
      <div class="box2">
        
        <div class="boxmargin" v-for="(item,index) in listPage" :key="index" >
          <span class="box4"></span>
          <span class="hoverBox3" @click="goPage(item)">{{item}}</span>
          <!-- <div class="box2">{{item.lineIn}}</div>
                <div class="box3">
                    <div class="hoverBox3" v-for="(item1,index) in item.childrenlist" :key="index" >
                        <span class="box4"></span>
                        {{item1}}
                    </div>
          </div>-->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState} from 'vuex'
export default {
  name: 'AnalysisUtils',
  data() {
    return {
      onOff: '',
      nowClick: '',
    }
  },
  props: {
    name: {
      type: String,
      default: '',
    },
    lineIn: {
      type: String,
      default: '',
    },
    listPage: {
      type: Array,
    },
  },
   computed: {
      ...mapState({
        // 后台菜单
        permissionMenuList: state => state.user.permissionList
      })
    },
  mounted(){
    this.controlMenuList()
  },
  methods: {
   controlMenuList(){
      console.log(this.permissionMenuList)
    },
  
    goPage(item) {
      console.log(item)
      let routpath = null
      // 1
      if (item === '新增税控服务器' || item === '发行制证(需UKey)') {
        this.nowClick = "税控服务器管理"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
          routpath = 'invoiceInformation-addresstaxControlServer'
        }
      }
      // 2
      else if (item === '新增纳税人信息' || item === '平台绑定纳税人') {
         this.nowClick = "纳税人信息维护（自建平台）"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
          routpath = 'invoiceInformation-TaxpayerInformationMaintenanceSelfBuild'
        }
      }
      // 3
      else if (item === '设备发行查询') {
        this.nowClick = "设备发行"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
            routpath = 'invoiceInformation-VirtualDeviceInformation'
         }
      } else if (item === '设备制证查询') {
        this.nowClick = "设备查询（已发行）"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
          routpath = 'invoiceInformation-virtualEquipmentCertification'
        }
      }
      // 4
      else if (item === '新增开票终端') {
        this.nowClick = "开票终端添加"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
          routpath = 'invoiceInformation-makeinvoiceterminal'
        }
      }
      //    5
      else if (item === '申领准备信息查询') {
        this.nowClick = "申领准备信息查询"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){ 
          routpath = 'invoiceInformation-applymessagefind'
        }
      } else if (item === '发票申领' || item === '发票申领结果查询') {
        this.nowClick = "发票申领管理"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){
          routpath = 'invoiceInformation-invoiceApplyAdmin'
        }
      } else if (item === '发票购领查询') {
        this.nowClick = "发票购领管理"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){
          routpath = 'invoiceInformation-invoicePurchasingManagement'
        }
      }
      // 6 
      else if (item === '未分发库存查询' || item === '发票分发至终端' || item === '设备发票退回局端') {
        this.nowClick = "未分发库存管理"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){
          routpath = 'invoiceInformation-undistributedStock'
        }
      } else if (item === '已分发库存查询' || item === '终端发票退回设备') {
        this.nowClick = "已分发库存管理"
        this.mapMenuList(this.permissionMenuList)
        if(this.onOff){
          routpath = 'invoiceInformation-haveBeenDistributed'
        }
      }

      this.$router.push({
        name: routpath,
        params: {
          id: item,
        },
      })
    },
    mapMenuList(data){
      console.log(data)
      let arr = []
      for(let i = 0 ;i < data.length ; i++){
        if(data[i].children && data[i].path != '/online' && data[i].path != '/isystem'){
          arr = arr.concat(data[i].children)
        }
      }
      for(let j =0 ;j<arr.length;j++){
        if(arr[j].meta.title === this.nowClick){
          this.onOff = true
          return
        }else{
            this.onOff = false
        }
      }

      if(!this.onOff){
          this.$message.error('暂无此权限')
      }
      console.log(arr)
    },

  },
}
</script>
<style lang="less" scoped>
// #D4D4D4  qian   #999999  yuan
.box {
  // width: 250px;
  width: 100%;
  height: 100%;
  // height: 120px;
//   border: 2px solid #ceecf8;
//   border-radius: 10px;
  // position: absolute;
  // left: 0;
  // top: 0;
  position: relative;
}
.box1 {
  width: 8em;
  font-size: 0.2em;
  color: #41aaf5;
  position: absolute;
  // top: -20px;
  // left: 50%;
    top: -1.4em;
    left: 50%;
  transform: translateX(-50%);
  background-color: #fff;
  // background-color: rgba(#fff, #fff, #fff);
  font-weight: bold;
  text-align: center;
}
// .box2{
//     width: 100%;
//     display: flex;
//     justify-content: center;

//     // position: absolute;
//     // left: 50%;
//     // top: 10%;
//     // transform: translateX(-50%);
//     text-align: center;
//     color: #D4D4D4;
//     font-size: 0.16em;
// }
.box2 {
  margin: 0.1em 0.4em 0.5em;
}
// .box3{
//     width: 100%;
//     display: flex;
//     justify-content: center;
//      flex-wrap: wrap;
//     // position: absolute;
//     // left: 50%;
//     // transform: translateX(-50%);
//     // top: 35%;
//     font-size: 0.14em;

// }
// .hoverBox3{
//       color:black ;
//       font-family:"Microsoft YaHei";
//       display: flex;
//       align-items: center;
//         justify-content: center;
//         // line-height: 1;
// }
.hoverBox3{
    color: #595959;
}

// .hoverBox3:hover{
//     background-color: orange;
//     .box4{
//         background-color: red;
//     }
// }
.box4 {
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin-left: 5px;
  background-color: #999999;
  margin-right: 5px;
}
.boxmargin {
  font-size: 0.14em;
  margin-top: 0.5em;
  display: flex;
  align-items: center;
}
.boxmargin:hover>.box4{
   cursor: pointer;
  background-color: #1890FF;
}
.boxmargin:hover>.hoverBox3{
  cursor: pointer;
  color: #1890FF;
}
</style>