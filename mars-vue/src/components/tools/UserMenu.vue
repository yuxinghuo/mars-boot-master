<template>
  <div class="user-wrapper" :class="theme">
    <!-- update_begin author:zhaoxin date:20191129 for: 做头部菜单栏导航 -->
    <!-- update-begin author:sunjianlei date:20191@20 for: 解决全局样式冲突的问题 -->
    <a-tooltip>
      <span class="action_recordZ" @click="showClick" title="搜索菜单" >
        <!-- <a-icon type="search"></a-icon> -->
          <smallUtils icon-style="icon" icon-class="icon-sousuo" class="magnifying" />
          <!-- <smallUtils icon-style="icon" icon-class="icon-sousuo-xuanting" v-show="this.fn === false?true:false"/> -->
      </span>
       
      <!-- <template slot="title">
        搜索菜单
      </template> -->
    </a-tooltip>
    <!-- update-begin author:sunjianlei date:20200219 for: 菜单搜索改为动态组件，在手机端呈现出弹出框 -->
    <component :is="searchMenuComp" v-if="searchMenuVisible || isMobile()" class="borders" :visible="searchMenuVisible" title="搜索菜单" :footer="null" @cancel="searchMenuVisible=false">
      <a-select
        class="search-input"
        showSearch
        :autoFocus ="true"
      
        :showArrow="false"
        placeholder="搜索菜单"
        optionFilterProp="children"
        :filterOption="filterOption"
        :open="isMobile()?true:null"
        :getPopupContainer="(node) => node.parentNode"
        :style="isMobile()?{width: '100%',marginBottom:'50px'}:{}"
        @change="searchMethods"
        @blur="hiddenClick"
        v-model="value"
      >
        <a-select-option v-for="(site,index) in searchMenuOptions" :key="index" :value="site.id">{{site.meta.title}}</a-select-option>
      </a-select>
    </component>
    <!-- update-end author:sunjianlei date:20200219 for: 菜单搜索改为动态组件，在手机端呈现出弹出框 -->
    <!-- update-end author:sunjianlei date:20191220 for: 解决全局样式冲突的问题 -->
    <!-- update_end  author:zhaoxin date:20191129 for: 做头部菜单栏导航 -->
   <!-- <span class="action">
      <a class="logout_title" target="_blank" href="http://doc.jeecg.com">
        <a-icon type="question-circle-o"></a-icon>
      </a>
    </span> -->
    <!-- <header-notice  class="action" title="消息"/> -->
    <header-notice class="teshu"  title="消息"/>
    <!-- <a-dropdown>
      <span class="action action-full ant-dropdown-link user-dropdown-menu"> -->
        <!-- <a-avatar class="avatar" size="small" :src="getAvatar()"/> -->
        <span class="action_UserName" v-if="isDesktop()">欢迎您，{{ nickname() }}</span>
      <!-- </span>
      <a-menu slot="overlay" class="user-dropdown-menu-wrapper"> -->
      <!--  <a-menu-item key="0">
          <router-link :to="{ name: 'account-center' }">
            <a-icon type="user"/>
            <span>个人中心</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="1">
          <router-link :to="{ name: 'account-settings-base' }">
            <a-icon type="setting"/>
            <span>账户设置</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="3"  @click="systemSetting">
           <a-icon type="tool"/>
           <span>系统设置</span>
        </a-menu-item> -->
        <!-- <a-menu-item key="4" @click="updatePassword">
          <a-icon type="setting"/>
          <span>密码修改</span>
        </a-menu-item> -->
      <!--  <a-menu-item key="5" @click="updateCurrentDepart">
          <a-icon type="cluster"/>
          <span>切换部门</span>
        </a-menu-item> -->
       <!-- <a-menu-item key="2" disabled>
          <a-icon type="setting"/>
          <span>测试</span>
        </a-menu-item>
        <a-menu-divider/>
        <a-menu-item key="3">
          <a href="javascript:;" @click="handleLogout">
            <a-icon type="logout"/>
            <span>退出登录</span>
          </a>
        </a-menu-item>-->
      <!-- </a-menu>
    </a-dropdown> -->
    <!-- zk 2020-12-09  切换部门 -->
    <a-tooltip>
      <span class="action_recordZ" key="4" @click="updateCurrentDepart" title="切换部门">
          <a-icon type="cluster" class="font-icon"/>   
      </span>
      <!-- <template slot="title">
        修改密码
      </template> -->
    </a-tooltip>
    <!-- zk加的 密码修改，设计图1 -->
    <a-tooltip>
      <span class="action_recordZ" key="4" @click="versionsVisibleChange" title="系统版本">
          <a-icon type="info-circle" class="font-icon"/>
      </span>
      <!-- <template slot="title">
        修改密码
      </template> -->
    </a-tooltip>
    <a-tooltip>
      <span class="action_recordZ" key="4" @click="updatePassword" title="修改密码">
  
     
        <smallUtils icon-style="icon" icon-class="icon-xiugaimima" class="changePassword" />
        <!-- <smallUtils icon-style="icon" icon-class="icon-xiugaimima-xuanting" v-show="this.fn1 === false"/> -->
            <!-- <a-icon type="setting"/>
            <span>密码修改</span> -->
      </span>
      <!-- <template slot="title">
        修改密码
      </template> -->
    </a-tooltip>

    <a-tooltip>
      <span class="action_recordZ">
        <a class="logout_title" href="javascript:;" @click="handleLogout" title="注销">
          <!-- <a-icon type="logout"/> -->
       
           
             <smallUtils icon-style="icon" icon-class="icon-tuichu" class="writeOff" />
            <!-- <smallUtils icon-style="icon" icon-class="icon-tuichu-xuanting" v-show="this.fn2 === false"/> -->
          <!-- <span v-if="isDesktop()">&nbsp;退出登录</span> -->
        </a>
      </span>
      <!-- <template slot="title">
        注销
      </template> -->
    </a-tooltip>
    <user-password ref="userPassword"></user-password>
    <depart-select ref="departSelect" :closable="true" title="部门切换"></depart-select>
     <a-modal
      title="系统版本"
      :visible="versionsVisible"
      :width="450"
      @cancel="VisiblehandleCancel"
      :footer="null"
      
    >
      <div class="versionsClassName">
          <p>
            <span>系统名称：</span>火星计划系统
          </p>
          <p>
            <span>系统版本：</span>{{version}}
          </p>
        
      </div>
     
     
    </a-modal>
    <!-- <setting-drawer ref="settingDrawer" :closable="false" title="系统设置"></setting-drawer> -->
  </div>
</template>

<script>
  import HeaderNotice from './HeaderNotice'
  import UserPassword from './UserPassword'
  import SettingDrawer from "@/components/setting/SettingDrawer";
  import DepartSelect from './DepartSelect'
  import { mapActions, mapGetters,mapState } from 'vuex'
  import { mixinDevice } from '@/utils/mixin.js'
  import { getFileAccessHttpUrl, getAction } from "@/api/manage"


  import smallUtils from './configComponents'
  export default {
    name: "UserMenu",
    mixins: [mixinDevice],
    data(){
      return{
        // zk  清除选中信息
        value: undefined,
        // update-begin author:sunjianlei date:20200219 for: 头部菜单搜索规范命名 --------------
        searchMenuOptions:[],
        searchMenuComp: 'span',
        searchMenuVisible: false,
        // update-begin author:sunjianlei date:20200219 for: 头部菜单搜索规范命名 --------------
        // fn: true,
        // fn1: true,
        // fn2: true,
        versionsVisible: false,
        version: '',
      }
    },
    components: {
      HeaderNotice,
      UserPassword,
      DepartSelect,
      SettingDrawer,

      smallUtils
    },
    props: {
      theme: {
        type: String,
        required: false,
        default: 'dark'
      }
    },
    /* update_begin author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
    created() {
      let lists = []
      this.searchMenus(lists,this.permissionMenuList)
      this.searchMenuOptions=[...lists]
    },
    computed: {
      ...mapState({
        // 后台菜单
        permissionMenuList: state => state.user.permissionList

      })
    },
    /* update_end author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
    watch: {
     
      // update-begin author:sunjianlei date:20200219 for: 菜单搜索改为动态组件，在手机端呈现出弹出框
      device: {
        immediate: true,
        handler() {
          this.searchMenuVisible = false
          this.searchMenuComp = this.isMobile() ? 'a-modal' : 'span'
        },
      },
      // update-end author:sunjianlei date:20200219 for: 菜单搜索改为动态组件，在手机端呈现出弹出框
    },
    methods: {
      // moveOn(val){
      //     switch(val){
      //       case 1:
      //           this.fn = false
      //       break
      //       case 2:
      //           this.fn1 = false
      //       break

      //       case 3:
      //           this.fn2 = false
      //       break
      //     }
      // },
      // moveOut(val){
      //   switch(val){
      //     case 1:
      //       this.fn = true
      //     break
      //     case 2: 
      //       this.fn1 = true
      //     break
      //     case 3:
      //       this.fn2 = true
      //     break
      //   }
      // },
      /* update_begin author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
      showClick() {
        this.searchMenuVisible = !this.searchMenuVisible
      },
      hiddenClick(){
        this.shows = false
        // zk
        this.searchMenuVisible = false
      },
      /* update_end author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
      ...mapActions(["Logout"]),
      ...mapGetters(["nickname", "avatar","userInfo"]),
      getAvatar(){
        return getFileAccessHttpUrl(this.avatar())
      },
      handleLogout() {
        const that = this

        this.$confirm({
          title: '提示',
          content: '真的要注销登录吗 ?',
          onOk() {
            return that.Logout({}).then(() => {
                window.location.href="/";
              //window.location.reload()
            }).catch(err => {
              that.$message.error({
                title: '错误',
                description: err.message
              })
            })
          },
          onCancel() {
          },
        });
      },
      // 系统版本
      versionsVisibleChange(){
        this.versionsVisible = true
        getAction('sys/version').then(res=>{
          console.log(res)
          
          if(res.code === 200){
            this.version=res.message
          }else{
            this.$message.error(res.message)
          }
        })
      },
      VisiblehandleCancel(){
        this.versionsVisible = false
      },
      updatePassword(){
        let username = this.userInfo().username
        this.$refs.userPassword.show(username)
      },
      updateCurrentDepart(){
        this.$refs.departSelect.show()
      },
      // systemSetting(){
      //   this.$refs.settingDrawer.showDrawer()
      // },
      /* update_begin author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
      searchMenus(arr,menus){
        for(let i of menus){
          if(!i.hidden && "layouts/RouteView"!==i.component){
           arr.push(i)
          }
          if(i.children&& i.children.length>0){
            this.searchMenus(arr,i.children)
          }
        }
      },
      filterOption(input, option) {
        return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      },
      // update_begin author:sunjianlei date:20191230 for: 解决外部链接打开失败的问题
      searchMethods(value) {
        console.log(value)
        let route = this.searchMenuOptions.filter(item => item.id === value)[0]
        console.log(route)
        if (route.meta.internalOrExternal === true || route.component.includes('layouts/IframePageView')) {
          window.open(route.meta.url, '_blank')
        } else {
          this.$router.push({ path: route.path })
        }
        this.searchMenuVisible = false
        this.value = undefined
      }
      // update_end author:sunjianlei date:20191230 for: 解决外部链接打开失败的问题
      /*update_end author:zhaoxin date:20191129 for: 做头部菜单栏导航*/
    }
  }
</script>

<style lang="less" scoped>

.magnifying:hover{
  fill: white;
  cursor:pointer;
}
.changePassword:hover{
  fill: white;
  cursor:pointer;
}
.writeOff:hover{
 fill: white;
  cursor:pointer;
}
.icon {
  width: 20px;
  height: 20px;
  vertical-align: -0.15em;
  fill: #74c1ff;
  // fill: black;
  overflow: hidden;
}
.user-wrapper{
  display: flex;
  justify-content: center;
  align-items: center;
}
  /* update_begin author:zhaoxin date:20191129 for: 让搜索框颜色能随主题颜色变换*/
  /* update-begin author:sunjianlei date:20191220 for: 解决全局样式冲突问题 */
  .user-wrapper .search-input {
    width: 180px;
    color: black;

    /deep/ .ant-select-selection {
      // background-color: inherit;
      background-color: #fff;
      border: 0;
      border-bottom: 1px solid white;
      &__placeholder, &__field__placeholder {
        color: inherit;
      }
    }
  }
  .user-wrapper{
    font-size: 14px;
    font-family: 'Microsoft YaHei Bold';
    color: #FFF;
  }

.anticon-search{
  font-size: 22px;
}
.anticon-logout{
  font-size: 22px;
}
  /* update-end author:sunjianlei date:20191220 for: 解决全局样式冲突问题 */
  /* update_end author:zhaoxin date:20191129 for: 让搜索框颜色能随主题颜色变换*/
</style>

<style lang="less" scoped>
.versionsClassName{
  margin-bottom: 50px;
  font-size: 16px;
  >p{
  
    >span{
      display: inline-block;
      width: 160px;
      text-align: end;
    }
  }
}
.font-icon{
  font-size: 20px;
  color: #74c1ff;
}
.font-icon:hover{
  color: #fff;
}
.logout_title {
    color: inherit;
    text-decoration: none;
  }
.action_recordZ{
    cursor: pointer;
    padding: 0 14px 0 0;
    display: inline-block;
    transition: all .3s;
    height: 70%;
    line-height: 46px;
    color: black;
  }
 .action_UserName{
    padding-right: 14px;
  }
.teshu{
  cursor: pointer;
  margin: 0 14px 0 0;
}
</style>