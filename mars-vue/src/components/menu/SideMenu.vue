<template>
  <a-layout-sider :class="['sider', isDesktop() ? null : 'shadow', theme, fixSiderbar ? 'ant-fixed-sidemenu' : null ]"
    width="200px" :collapsible="collapsible" v-model="collapsed" :trigger="null">
    <logo title="火星" />

    <div :style="{ overflowY: 'scroll', height: 'calc(100% - 59px)', position: 'fixed', left: 0,top:'59px'}" ref="scrollMenu">
      <s-menu :collapsed="collapsed" :menu="menus" :theme="theme" @select="onSelect" :mode="mode" :style="smenuStyle">
      </s-menu>
    </div>
  </a-layout-sider>

</template>

<script>
  import ALayoutSider from "ant-design-vue/es/layout/Sider"
  import Logo from '../tools/Logo'
  import SMenu from './index'
  import {
    mixin,
    mixinDevice
  } from '@/utils/mixin.js'

  export default {
    name: "SideMenu",
    components: {
      ALayoutSider,
      Logo,
      SMenu
    },
    mixins: [mixin, mixinDevice],
    props: {
      mode: {
        type: String,
        required: false,
        default: 'inline'
      },
      theme: {
        type: String,
        required: false,
        default: 'dark'
      },
      collapsible: {
        type: Boolean,
        required: false,
        default: false
      },
      collapsed: {
        type: Boolean,
        required: false,
        default: false
      },
      menus: {
        type: Array,
        required: true
      }
    },
    mounted() {
      this.watchScroll()
    },
    computed: {
      smenuStyle() {
        let style = {
          'padding': '0'
        }
        if (this.fixSiderbar) {
          style['height'] = 'calc(100% - 59px)'
          style['overflow'] = 'auto'
          style['overflow-x'] = 'hidden'
        }
        return style
      }
    },
    methods: {
      onSelect(obj) {
        this.$emit('menuSelect', obj)
      },
      watchScroll() {
        window.addEventListener('scroll', (e) => {
          let top=window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
          this.scrollMenu = this.$refs.scrollMenu;
          if (top > 59) {
            this.scrollMenu.style.top = '0px';
          } else {
            this.scrollMenu.style.top = '59px';
          }
        }, true)
      }
    }
  }
</script>
<style lang="less" scoped>
  .box {
    position: relative;
    height: calc(100% - 59px);
    overflow: hidden;
  }

  .ant-menu-root {
    /deep/ .ant-menu-submenu {
      .ant-menu-submenu-title {
        >span {
          font-size: 16px !important;
          font-family: 'MicrosoftYaHei Bold';
          color: #595959;
        }
      }


    }

    /deep/ .homePage {
      >a {
        >span {
          font-size: 16px !important;
          font-family: 'MicrosoftYaHei Bold' !important;
          color: #595959 !important;
        }
      }

    }
  }

  .ant-menu {
    /deep/ .ant-menu-item {
      font-family: 'Microsoft YaHei';
      color: #595854;
      font-size: 14px;
    }

    // .ant-menu:not(.ant-menu-horizontal) .ant-menu-item-selected
    /deep/ .ant-menu-item-selected {
      // background-color: #E4F7FF ;
      color: #008FFF;
      background-color: #C3E6FB !important;
    }

    /deep/ .ant-menu-submenu-selected {
      .ant-menu-submenu-title {
        >span {
          color: #1890FF !important;
        }
      }

    }
  }

  /* update_begin author:sunjianlei date:20190509 for: 修改侧边导航栏滚动条的样式 */
  .sider {
    @scrollBarSize: 10px;

    ul.ant-menu {

      /* 定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
      &::-webkit-scrollbar {
        width: @scrollBarSize;
        height: @scrollBarSize;
        background-color: transparent;
        display: none;
      }

      & .-o-scrollbar {
        display: none;
      }

      /* 兼容IE */
      -ms-overflow-style: none;
      -ms-scroll-chaining: chained;
      -ms-content-zooming: zoom;
      -ms-scroll-rails: none;
      -ms-content-zoom-limit-min: 100%;
      -ms-content-zoom-limit-max: 500%;
      -ms-scroll-snap-type: proximity;
      -ms-scroll-snap-points-x: snapList(100%, 200%, 300%, 400%, 500%);

      /* 定义滚动条轨道 */
      &::-webkit-scrollbar-track {
        background-color: transparent;
      }

      /* 定义滑块 */
      &::-webkit-scrollbar-thumb {
        border-radius: @scrollBarSize;
        background-color: #eee;
        box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);

        &:hover {
          background-color: #dddddd;
        }

        &:active {
          background-color: #bbbbbb;
        }
      }
    }

    /** 暗色系滚动条样式 */
    &.dark ul.ant-menu {
      &::-webkit-scrollbar-thumb {
        background-color: #666666;

        &:hover {
          background-color: #808080;
        }

        &:active {
          background-color: #999999;
        }
      }
    }

  }

  /* update_end author:sunjianlei date:20190509 for: 修改侧边导航栏滚动条的样式 */
</style>

<!-- update_begin author:sunjianlei date:20190530 for: 选中首页的时候不显示背景颜色 -->
<style lang="less">
  .ant-menu.ant-menu-root {
    &>.ant-menu-item:first-child {
      background-color: transparent;

      &>a,
      &>a:hover {
        color: rgba(0, 0, 0, 0.65);
      }

      &.ant-menu-item-selected {

        &>a,
        &>a:hover {
          color: @primary-color;
        }
      }
    }

    &.ant-menu-dark>.ant-menu-item:first-child {

      &>a,
      &>a:hover {
        color: rgba(255, 255, 255, 0.65);
      }

      &.ant-menu-item-selected {

        &>a,
        &>a:hover {
          color: rgba(255, 255, 255, 1);
        }
      }
    }
  }
</style>
<!-- update_end author:sunjianlei date:20190530 for: 选中首页的时候不显示背景颜色 -->
