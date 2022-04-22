<template>
  <!-- , width: fixedHeader ? `calc(100% - ${sidebarOpened ? 256 : 80}px)` : '100%'  -->
  <a-layout-header v-if="!headerBarFixed" :class="[fixedHeader && 'ant-header-fixedHeader', sidebarOpened ? 'ant-header-side-opened' : 'ant-header-side-closed', ]"
    :style="{ padding: '0' }">

    <div v-if="mode === 'sidemenu'" class="header" :class="theme">
      <a-icon v-if="device==='mobile'" class="trigger" :type="collapsed ? 'menu-fold' : 'menu-unfold'" @click="toggle"></a-icon>
      <a-icon v-else class="trigger" :type="collapsed ? 'menu-unfold' : 'menu-fold'" @click="toggle" />

      <span class="logoName" v-if="device === 'desktop'">火星计划</span>
      <span class="logoName" v-else>火星计划</span>

      <user-menu :theme="theme" />
    </div>
    <!-- 顶部导航栏模式 -->
    <div v-else :class="['top-nav-header-index', theme]">
      <div class="header-index-wide">
        <div class="header-index-left" :style="topMenuStyle.headerIndexLeft">
          <logo class="top-nav-header"  :show-title="device !== 'mobile'" :style="topMenuStyle.topNavHeader" />
            <div v-if="device !== 'mobile'" :style="topMenuStyle.topSmenuStyle">
              <s-menu mode="horizontal" :menu="menus" :theme="theme"></s-menu>
            </div>
            <a-icon v-else class="trigger" :type="collapsed ? 'menu-fold' : 'menu-unfold'" @click="toggle"></a-icon>
        </div>
        <user-menu class="header-index-right" :theme="theme" :style="topMenuStyle.headerIndexRight" />
      </div>
    </div>

  </a-layout-header>
</template>

<script>
  import UserMenu from '../tools/UserMenu'
  import SMenu from '../menu/'
  import Logo from '../tools/Logo'

  import {
    mixin
  } from '@/utils/mixin.js'
  export default {
    name: 'GlobalHeader',
    components: {
      UserMenu,
      SMenu,
      Logo
    },
    mixins: [mixin],
    props: {
      mode: {
        type: String,
        // sidemenu, topmenu
        default: 'sidemenu'
      },
      menus: {
        type: Array,
        required: true
      },
      theme: {
        type: String,
        required: false,
        default: 'dark'
      },
      collapsed: {
        type: Boolean,
        required: false,
        default: false
      },
      device: {
        type: String,
        required: false,
        default: 'desktop'
      }
    },
    data() {
      return {
        headerBarFixed: false,
        //update-begin--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
        topMenuStyle: {
          headerIndexLeft: {},
          topNavHeader: {},
          headerIndexRight: {},
          topSmenuStyle: {}
        },
      }
    },
    watch: {
      /** 监听设备变化 */
      device() {
        if (this.mode === 'topmenu') {
          this.buildTopMenuStyle()
        }
      },
      /** 监听导航栏模式变化 */
      mode(newVal) {
        if (newVal === 'topmenu') {
          this.buildTopMenuStyle()
        }
      }
    },
    //update-end--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
    mounted() {
      window.addEventListener('scroll', this.handleScroll)
      window.addEventListener('resize', this.handleResize)
      //update-begin--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
      if (this.mode === 'topmenu') {
        this.buildTopMenuStyle()
      }
      //update-end--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
    },
    methods: {
      handleResize: function(){
      
      let screenWidth
       screenWidth = document.body.clientWidth;
      this.$store.dispatch('TOSET_SCREENWIDTH',screenWidth)

      },
      handleScroll: function() {

        if (this.autoHideHeader) {
          let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
          console.log(scrollTop)
          if (scrollTop > 100) {
            this.headerBarFixed = true
          } else {
            this.headerBarFixed = false
          }
        } else {
          this.headerBarFixed = false
        }
      },
      toggle() {
        console.log(222)
        let ul = document.getElementsByClassName("ant-menu-light");
        let width = window.getComputedStyle(ul[0], null).width;
        if (width === '200px') {
          ul[0].style.cssText = "width:70px!important;"
          
        } else {
          ul[0].style.cssText = "width:200px!important;"
        }
        this.$store.dispatch('TOSET_REDACTMENUIMGCUR',this.collapsed)
        // console.log(a[0].style.width);
        // a[0].style.width='0';
        // console.log(a[0].style.width)
        this.$emit('toggle')
        console.log(this.$refs)
        // this.$refs.cutImgs()
      },
      //update-begin--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
      buildTopMenuStyle() {
        if (this.mode === 'topmenu') {
          if (this.device === 'mobile') {
            // 手机端需要清空样式，否则显示会错乱
            this.topMenuStyle.topNavHeader = {}
            this.topMenuStyle.topSmenuStyle = {}
            this.topMenuStyle.headerIndexRight = {}
            this.topMenuStyle.headerIndexLeft = {}
          } else {
            let rightWidth = '360px'
            this.topMenuStyle.topNavHeader = {
              'min-width': '165px'
            }
            this.topMenuStyle.topSmenuStyle = {
              'width': 'calc(100% - 165px)'
            }
            this.topMenuStyle.headerIndexRight = {
              'min-width': rightWidth
            }
            this.topMenuStyle.headerIndexLeft = {
              'width': `calc(100% - ${rightWidth})`
            }
          }
        }
      }
      //update-begin--author:sunjianlei---date:20190508------for: 顶部导航栏过长时显示更多按钮-----
    }
  }
</script>

<style lang="less" scoped>
  /* update_begin author:scott date:20190220 for: 缩小首页布局顶部的高度*/

  @height: 59px;
  .trigger{
      // color: #545e68 !important;
      color: rgb(116, 193, 255) !important;
      line-height: 1 !important;
      position: absolute;
      top: 22px;
      left: 0px;
  }
  .trigger:hover{
    background: rgba(0, 0, 0, 0) !important;
    // color: black !important;
    color: rgb(255, 255, 255) !important;
  }
  .header{
    position: relative;
  }
  .logoName{
    position: absolute;
    top: 22px;
    left: 41px;
    line-height: 1;
  }
  .layout {

    .top-nav-header-index {

      .header-index-wide {
        margin-left: 10px;

        .ant-menu.ant-menu-horizontal {
          height: @height;
          line-height: @height;
        }
      }

      .trigger {
        line-height: 64px;
        // &:hover {
        //   background: rgba(0, 0, 0, 0.05);
        // }
      }
    }

    .header {
      z-index: 2;
      color: #FFFFFF;
      height: @height;
      background-color: @primary-color;
      transition: background 300ms;
      font-size: 20px;

      /* dark 样式 */
      &.dark {
        color: #000000;
        box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
        background-color: white !important;
      }
    }

    .header,
    .top-nav-header-index {
      &.dark .trigger:hover {
        // background: rgba(0, 0, 0, 0.05);
      }
    }
  }

  .ant-layout-header {
    height: @height;
    line-height: @height;
  }

  /* update_end author:scott date:20190220 for: 缩小首页布局顶部的高度*/
</style>
