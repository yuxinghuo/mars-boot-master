<template>
  <div class="logo">
    <router-link :to="{name:'dashboard'}">
      <div class="utilsBox">
        <img class="box" v-if="menuImgCur" src="~@/assets/marsplan.png" alt="logo">
        <img class="shrink_box" v-else src="~@/assets/marslogo.png" alt="logo">
      </div>

    </router-link>
  </div>
</template>

<script>
  import {
    mixin
  } from '@/utils/mixin.js'
  import {
    mapState
  } from 'vuex';
  export default {
    name: 'Logo',
    mixins: [mixin],
    props: {
      title: {
        type: String,
        default: '',
        required: false
      },
      showTitle: {
        type: Boolean,
        default: true,
        required: false
      }
    },
    computed: {
      ...mapState({
        menuImgCur: state => state.utils.menuImgCur
      })
    }
  }
</script>
<style lang="less" scoped>
  /*缩小首页布 局顶部的高度*/
  @height: 59px;

  .box {
    height: 22px !important;
    // width: 110px;
    width: 176px;
    position: absolute;
    top: 19px;
    left: 12px;
    // height: 30% !important;
    // height: 20px !important;
  }

  .zhan_box {
    position: absolute;
    bottom: 18px;
    left: 110px;
    line-height: 1;
  }

  // .shrink_box{
  //   padding-left: 24px;
  // }
  .shrink_box {
    width: 31px !important;
    height: 35px !important;
    position: absolute;
    top: 13px;
    left: 24px;
    // margin-left: 24px;
  }

  .sider {
    box-shadow: none !important;

    .logo {
      height: @height  !important;
      line-height: @height  !important;
      box-shadow: none !important;
      transition: background 300ms;

      // padding-right: 29px;
      a {
        color: white;
        display: inline-block;
        width: 100%;
        height: 100%;

        .utilsBox {
          width: 100%;
          height: 100%;
          display: flex;
          position: relative;
          padding-right: 12px;
        }

        // &:hover {
        //   color: rgba(255, 255, 255, 0.8);
        // }
      }
    }

    &.light .logo {
      background-color: @primary-color;
    }
  }
</style>
