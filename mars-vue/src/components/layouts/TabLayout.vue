<template>
  <global-layout @dynamicRouterShow="dynamicRouterShow">
    <!-- update-begin- author:sunjianlei --- date:20191009 --- for: 提升右键菜单的层级 -->
    <contextmenu
      :itemList="menuItemList"
      :visible.sync="menuVisible"
      style="z-index: 9999;"
      @select="onMenuSelect"
    />
    <!-- update-end- author:sunjianlei --- date:20191009 --- for: 提升右键菜单的层级 -->
    <a-tabs
      @contextmenu.native="e => onContextmenu(e)"
      v-if="multipage"
      :active-key="activePage"
      class="tab-layout-tabs"
      style="height:52px"
      :hide-add="true"
      type="editable-card"
      @change="changePage"
      @tabClick="tabCallBack"
      @edit="editPage"
    >
      <a-tab-pane :id="page.fullPath" :key="page.fullPath" v-for="page in pageList">
        <span slot="tab" :pagekey="page.fullPath">{{ page.meta.title }}</span>
      </a-tab-pane>
    </a-tabs>
  
    <div style="margin: 12px;" ref="box">
      <transition name="page-toggle">
        <keep-alive  :include="keepAliveList" v-if="multipage">
          <router-view v-if="reloadFlag" />
        </keep-alive>
        <!-- <keep-alive v-if="multipage"  >
          <router-view v-if="reloadFlag" />
        </keep-alive> -->
        <template v-else>
          <router-view v-if="reloadFlag" />
        </template>
      </transition>
    </div>
  </global-layout>
</template>

<script>
import GlobalLayout from '@/components/page/GlobalLayout'
import Contextmenu from '@/components/menu/Contextmenu'
import { mixin, mixinDevice } from '@/utils/mixin.js'
import { triggerWindowResizeEvent } from '@/utils/util'

import { mapState } from 'vuex'
const indexKey = '/dashboard/analysis'

export default {
  name: 'TabLayout',

  components: {
    GlobalLayout,
    Contextmenu,
  },
  mixins: [mixin, mixinDevice],
  data() {
    return {
      pageList: [],
      linkList: [],
      activePage: '',
      menuVisible: false,
      menuItemList: [
        { key: '4', icon: 'reload', text: '刷 新' },
        { key: '1', icon: 'arrow-left', text: '关闭左侧' },
        { key: '2', icon: 'arrow-right', text: '关闭右侧' },
        { key: '3', icon: 'close', text: '关闭其它' },
      ],
      reloadFlag: true,
    }
  },
  /* update_begin author:wuxianquan date:20190828 for: 关闭当前tab页，供子页面调用 ->望菜单能配置外链，直接弹出新页面而不是嵌入iframe #428 */
  provide() {
    return {
      closeCurrent: this.closeCurrent,
    }
  },
  /* update_end author:wuxianquan date:20190828 for: 关闭当前tab页，供子页面调用->望菜单能配置外链，直接弹出新页面而不是嵌入iframe #428 */
  computed: {
    multipage() {
      //判断如果是手机模式，自动切换为单页面模式
      if (this.isMobile()) {
        return false
      } else {
        return this.$store.state.app.multipage
      }
    },

    ...mapState({
      // thisParent: (state) => state.utils.thisParent,
      // everyThat: (state) => state.utils.everyThat,
      // thatList: (state) => state.utils.thatList,
      keepAliveList: state => state.utils.keepAliveList,
      
    }),
  },
  created() {
    if (this.$route.path != indexKey) {
      this.addIndexToFirst()
    }
    // update-begin-author:sunjianlei date:20191223 for: 修复刷新后菜单Tab名字显示异常
    let storeKey = 'route:title:' + this.$route.fullPath
    let routeTitle = this.$ls.get(storeKey)
    if (routeTitle) {
      this.$route.meta.title = routeTitle
    }
    console.log(this.$route)
    // update-end-author:sunjianlei date:20191223 for: 修复刷新后菜单Tab名字显示异常
    this.pageList.push(this.$route)
    this.linkList.push(this.$route.fullPath)
    this.activePage = this.$route.fullPath
  },
  mounted() {
    this.stateKeepList(this.$route)
  },
  watch: {
    $route: function (newRoute) {
      console.log("新的路由",newRoute)
     console.log(this.$route.meta.keepAlive,111111111111111111111)
      this.activePage = newRoute.fullPath
      if (!this.multipage) {
        console.log('1')
        this.linkList = [newRoute.fullPath]
        this.pageList = [Object.assign({}, newRoute)]
        // update-begin-author:taoyan date:20200211 for: TASK #3368 【路由缓存】首页的缓存设置有问题，需要根据后台的路由配置来实现是否缓存
      } else if (indexKey == newRoute.fullPath) {
         console.log('2')
        //首页时 判断是否缓存 没有缓存 刷新之
        // if (newRoute.meta.keepAlive === false) {
        //   this.routeReload()
        // }
        // update-end-author:taoyan date:20200211 for: TASK #3368 【路由缓存】首页的缓存设置有问题，需要根据后台的路由配置来实现是否缓存
      } else if (this.linkList.indexOf(newRoute.fullPath) < 0) {
        console.log('<0')
        this.linkList.push(newRoute.fullPath)
        this.pageList.push(Object.assign({}, newRoute))
        // update-begin-author:sunjianlei date:20200103 for: 如果新增的页面配置了缓存路由，那么就强制刷新一遍
        if (newRoute.meta.keepAlive) {
          this.routeReload()
        }
        
        // zk write 查看linkList有没有 新打开的是不是
        console.log('新新路由')
        if(newRoute.meta.keepAlive){
          let jcNumber = newRoute.name.split('-')[newRoute.name.split('-').length -1]
       
           this.$store.dispatch('TOSETPUSH_CHANGEKEEPALIVELIST',jcNumber)
        }

        // update-end-author:sunjianlei date:20200103 for: 如果新增的页面配置了缓存路由，那么就强制刷新一遍
      } else if (this.linkList.indexOf(newRoute.fullPath) >= 0) {
         console.log('3')
        let oldIndex = this.linkList.indexOf(newRoute.fullPath)
        let oldPositionRoute = this.pageList[oldIndex]
        this.pageList.splice(oldIndex, 1, Object.assign({}, newRoute, { meta: oldPositionRoute.meta }))
      }
    },
    activePage: function (key) {
      let index = this.linkList.lastIndexOf(key)
      let waitRouter = this.pageList[index]
      this.$router.push(Object.assign({}, waitRouter))
      this.changeTitle(waitRouter.meta.title)
    },
    multipage: function (newVal) {
      console.log(newVal)
      if (this.reloadFlag) {
        if (!newVal) {
          this.linkList = [this.$route.fullPath]
          this.pageList = [this.$route]
        }
      }
    },
    // update-begin-author:sunjianlei date:20191223 for: 修复从单页模式切换回多页模式后首页不居第一位的 BUG
    device() {
      if (this.multipage && this.linkList.indexOf(indexKey) === -1) {
        this.addIndexToFirst()
      }
    },
    // update-end-author:sunjianlei date:20191223 for: 修复从单页模式切换回多页模式后首页不居第一位的 BUG
    keepAliveList(val){
      console.log(val)
    }
  },
  methods: {
    // zk 初始化
    stateKeepList(value){
      if ( this.linkList.length >1){
        if(value.meta.keepAlive){
         let changKey1 = value.fullPath.split('/')[value.fullPath.split('/').length -1]
         this.$store.dispatch('TOSETPUSH_CHANGEKEEPALIVELIST',changKey1)
        }
      }
      let changKey2 = this.linkList[0].split('/')[this.linkList[0].split('/').length -1]
      if(changKey2 === 'analysis'){
        changKey2 = 'Analysis'
      }
      this.$store.dispatch('TOSETPUSH_CHANGEKEEPALIVELIST',changKey2)
      console.log(this.linkList)
      console.log(this.keepAliveList)
    },
    // zk  触发方法
    unifiedHandling(){
      console.log(this.linkList)
        let delLeftKeepAlive = [] 
        for(let i =0;i<this.linkList.length;i++){
          let arrOne = this.linkList[i].split('/')[this.linkList[i].split('/').length -1]
          delLeftKeepAlive.push(arrOne)
        }
       
        this.$store.dispatch('TOSET_CHANGEKEEPALIVELIST',delLeftKeepAlive)
        delLeftKeepAlive = []
    },
    // update-begin-author:sunjianlei date:20191223 for: 修复从单页模式切换回多页模式后首页不居第一位的 BUG
    // 将首页添加到第一位
    addIndexToFirst() {
      this.pageList.splice(0, 0, {
        name: 'dashboard-analysis',
        path: indexKey,
        fullPath: indexKey,
        meta: {
          icon: 'dashboard',
          title: '首页',
        },
      })
      this.linkList.splice(0, 0, indexKey)
    },
    // update-end-author:sunjianlei date:20191223 for: 修复从单页模式切换回多页模式后首页不居第一位的 BUG

    // update-begin-author:sunjianlei date:20200120 for: 动态更改页面标题
    changeTitle(title) {
      let projectTitle = '系统'
      // 首页特殊处理
      if (this.$route.path === indexKey) {
        document.title = projectTitle
      } else {
        document.title = title + ' · ' + projectTitle
      }
    },
    // update-end-author:sunjianlei date:20200120 for: 动态更改页面标题

    changePage(key) {
      this.activePage = key
    },
    tabCallBack() {
      this.$nextTick(() => {
        triggerWindowResizeEvent()
      })
    },
    editPage(key, action) {
      this[action](key)
    },
    remove(key) {
      console.log(key)
      if (key == indexKey) {
        this.$message.warning('首页不能关闭!')
        return
      }
      if (this.pageList.length === 1) {
        this.$message.warning('这是最后一页，不能再关闭了啦')
        return
      }

      console.log('this.pageList ', this.pageList)
      this.pageList = this.pageList.filter((item) => item.fullPath !== key)
      let index = this.linkList.indexOf(key)
      this.linkList = this.linkList.filter((item) => item !== key)
      index = index >= this.linkList.length ? this.linkList.length - 1 : index
      this.activePage = this.linkList[index]
      // zk  vuex
      this.unifiedHandling()
    },
    
    onContextmenu(e) {
      const pagekey = this.getPageKey(e.target)
      if (pagekey !== null) {
        e.preventDefault()
        this.menuVisible = true
      }
    },
    getPageKey(target, depth) {
      depth = depth || 0
      if (depth > 2) {
        return null
      }
      let pageKey = target.getAttribute('pagekey')
      pageKey =
        pageKey || (target.previousElementSibling ? target.previousElementSibling.getAttribute('pagekey') : null)
      return pageKey || (target.firstElementChild ? this.getPageKey(target.firstElementChild, ++depth) : null)
    },
    onMenuSelect(key, target) {
      console.log(key,'3333333333333333')
      let pageKey = this.getPageKey(target)
      switch (key) {
        case '1':
          this.closeLeft(pageKey)
          break
        case '2':
          this.closeRight(pageKey)
          break
        case '3':
          this.closeOthers(pageKey)
          break
        case '4':
          this.routeReload()
          break
        default:
          break
      }
    },
    /* update_begin author:wuxianquan date:20190828 for: 关闭当前tab页，供子页面调用->望菜单能配置外链，直接弹出新页面而不是嵌入iframe #428 */
    closeCurrent() {
      this.remove(this.activePage)
    },
    /* update_end author:wuxianquan date:20190828 for: 关闭当前tab页，供子页面调用->望菜单能配置外链，直接弹出新页面而不是嵌入iframe #428 */
    closeOthers(pageKey) {
      console.log(pageKey)
      let index = this.linkList.indexOf(pageKey)
      if (pageKey == indexKey || pageKey.indexOf('?ticke=') >= 0) {
        this.linkList = this.linkList.slice(index, index + 1)
        this.pageList = this.pageList.slice(index, index + 1)
        this.activePage = this.linkList[0]
      } else {
        let indexContent = this.pageList.slice(0, 1)[0]
        this.linkList = this.linkList.slice(index, index + 1)
        this.pageList = this.pageList.slice(index, index + 1)
        this.linkList.unshift(indexContent.fullPath)
        this.pageList.unshift(indexContent)
        this.activePage = this.linkList[1]
      }

      // zk write 关闭其他
       this.unifiedHandling()
    },
    closeLeft(pageKey) {
      console.log(pageKey)

      let tempList = [...this.pageList]
      let indexContent = tempList.slice(0, 1)[0]
      let index = this.linkList.indexOf(pageKey)
      this.linkList = this.linkList.slice(index)
      this.pageList = this.pageList.slice(index)
      this.linkList.unshift(indexContent.fullPath)
      this.pageList.unshift(indexContent)
      if (this.linkList.indexOf(this.activePage) < 0) {
        this.activePage = this.linkList[0]
      }

      // zk write 关闭左侧
       this.unifiedHandling()
    },
    closeRight(pageKey) {
      console.log(pageKey)
      let index = this.linkList.indexOf(pageKey)
      this.linkList = this.linkList.slice(0, index + 1)
      this.pageList = this.pageList.slice(0, index + 1)
      if (this.linkList.indexOf(this.activePage < 0)) {
        this.activePage = this.linkList[this.linkList.length - 1]
      }

      // zk wirte 关闭右侧
       this.unifiedHandling()
      
    },
    //update-begin-author:taoyan date:20190430 for:动态路由title显示配置的菜单title而不是其对应路由的title
    dynamicRouterShow(key, title) {
      let keyIndex = this.linkList.indexOf(key)
      if (keyIndex >= 0) {
        let currRouter = this.pageList[keyIndex]
        let meta = Object.assign({}, currRouter.meta, { title: title })
        this.pageList.splice(keyIndex, 1, Object.assign({}, currRouter, { meta: meta }))
        if (key === this.activePage) {
          this.changeTitle(title)
        }
      }
    },
    //update-end-author:taoyan date:20190430 for:动态路由title显示配置的菜单title而不是其对应路由的title

    //update-begin-author:taoyan date:20191008 for:路由刷新
    routeReload() {
      this.reloadFlag = false
      let ToggleMultipage = 'ToggleMultipage'
      this.$store.dispatch(ToggleMultipage, false)
      // this.$nextTick(()=>{

      this.$store.dispatch(ToggleMultipage, true)
      this.reloadFlag = true

      // })
    },
    //update-end-author:taoyan date:20191008 for:路由刷新
  },
}
</script>

<style lang="less">
/*
 * The following styles are auto-applied to elements with
 * transition="page-transition" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the page transition by editing
 * these styles.
 */

.page-transition-enter {
  opacity: 0;
}

.page-transition-leave-active {
  opacity: 0;
}

.page-transition-enter .page-transition-container,
.page-transition-leave-active .page-transition-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

/*美化弹出Tab样式*/
.ant-tabs-nav-container {
  margin-top: 4px;
}

/* 修改 ant-tabs 样式 */
.tab-layout-tabs.ant-tabs {
  border-bottom: 1px solid #ccc;
  border-left: 1px solid #ccc;
  background-color: white;
  padding: 0 20px;

  .ant-tabs-bar {
    margin: 4px 0 0;
    border: none;
  }
}

.ant-tabs {
  &.ant-tabs-card .ant-tabs-tab {
    padding: 0 24px !important;
    background-color: white !important;
    margin-right: 10px !important;

    .ant-tabs-close-x {
      width: 12px !important;
      height: 12px !important;
      opacity: 0 !important;
      cursor: pointer !important;
      font-size: 12px !important;
      margin: 0 !important;
      position: absolute;
      top: 36%;
      right: 6px;
    }

    &:hover .ant-tabs-close-x {
      opacity: 1 !important;
    }
  }
}

.ant-tabs.ant-tabs-card > .ant-tabs-bar {
  .ant-tabs-tab {
    border: none !important;
    border-bottom: 1px solid transparent !important;
  }
  .ant-tabs-tab-active {
    border-color: @primary-color!important;
  }
}
</style>