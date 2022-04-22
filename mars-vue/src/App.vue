<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view v-if="isRouterAlive" />
    </div>
  </a-config-provider>
</template>
<script>
  
  import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
  import enquireScreen from '@/utils/device'


  export default {
    provide() {
      return {
        reload: this.reload
      }
    },
    data() {
      return {
        locale: zhCN,
        isRouterAlive: true
      }
    },
    created() {
      let that = this
      enquireScreen(deviceType => {
        // tablet
        if (deviceType === 0) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        }
        // mobile
        else if (deviceType === 1) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        } else {
          that.$store.commit('TOGGLE_DEVICE', 'desktop')
          that.$store.dispatch('setSidebar', true)
        }

      })
     
    },
    methods: {
      reload() {
        console.log(this.isRouterAlive)
        this.isRouterAlive = false;
        this.$nextTick(() => {
          this.isRouterAlive = true;
        })
      }
    }
  }
</script>
<style lang="less">
  // main的主体
  .ant-layout-content {
    display: flex;
    flex-direction: column;

    >div:nth-child(3) {
      flex: 1;
    }
  }

  // 添加子菜单 以及一些
  .ant-drawer-title {
    font-size: 18px !important;
    font-family: 'Microsoft YaHei';
    color: #595854 !important;
  }

  .ant-spin-container {
    /deep/ .ant-input::placeholder {
      font-size: 14px;
      font-family: 'Microsoft YaHei' !important;
      color: #c3c3c3 !important;
    }
  }

  // td操作
  .ant-table-row-cell-break-word {
    color: #595959;

    a {
      color: #008FFF !important;
      font-size: 14px;
      font-family: 'Microsoft YaHei';
    }
  }

  // 脚步颜色
  .ant-pagination-item-active {
    background: #fff;
    border-color: #008FFF !important;
    color: #008FFF !important;
  }

  .ant-pagination-item-active a {
    color: #008FFF !important;
  }

  // 搜索框字体
  .ant-select-selection__placeholder {
    font-size: 14px;
    font-family: 'Microsoft YaHei';
    color: #c3c3c3 !important;
  }

  .ant-input::placeholder {
    font-size: 14px;
    font-family: 'Microsoft YaHei';
    color: #c3c3c3 !important;
  }

  .ant-form-item-label>label {
    color: #595854 !important;
  }

  //划过
  .ant-table-row-hover {
    background-color: #E4F7FF;
  }

  // 按钮样式修改
  .ant-btn {
    height: 36px !important;
  }

  .customBtn {
    border-color: #5CC4FF !important;
    color: #5CC4FF !important;
  }

  .customBtn:hover {
    background-color: #0080F1 !important;
    color: white !important;
  }

  .ant-btn-primary {
    background-color: #0080F1 !important;
  }

  .ant-btn-primary:hover {
    // background-color: #5CC4FF !important;
    background-color: #5ae6ff !important;
    border-color: #5ae6ff !important;
    ;
    color: white !important;
  }

  .ant-btn-danger {
    background-color: #FF6965 !important;
  }

  .ant-btn-danger:hover {
    background-color: #FFAEA2 !important;
    border-color: #FFAEA2 !important;
  }

  .ant-btn[disabled] {
    // background-color: #B3B3B3 !important;
    color: #B3B3B3 !important;
    border-color: #B3B3B3 !important;
    ;
  }

  #app {

    height: 100%;
    font-size: 18px;
    font-family: 'Microsoft YaHei';
  }

  /* table分页位置 */
  .ant-pagination,
  .pagination_postion {
    margin-top: 20px !important;
    text-align: right;
  }

  .dark-row {
    background: #FaFaFa;
  }

  /* 下拉框清除iocn图标位置 */
  .ant-select-selection__clear {
    /* overflow-y: ;
      -ms-overflow-y: ;
      -webkit-overflow-y:; */
    position: absolute;
    top: 50%;
    right: 25px !important;
    z-index: 1;
    display: inline-block;
    width: 12px;
    height: 12px;
    margin-top: -6px;
    color: rgba(0, 0, 0, 0.25);
    font-size: 12px;
    font-style: normal;
    line-height: 12px;
    text-align: center;
    text-transform: none;
    background: #fff;
    cursor: pointer;
    opacity: 0;
    transition: color 0.3s ease, opacity 0.15s ease;
    text-rendering: auto;
  }

  


  .parent {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .refund {
    /* margin-right: 100px; */
  }

  .refund>p {
    margin-bottom: 2px;
  }

  .refund .first-p {
    color: red;
  }


  .ant-menu-light {
    width: 200px !important;
  }

  .center-title .ant-table-thead th {
    text-align: center !important;
  }

  // .ant-table-tbody {
  //   /deep/ td {
  //     padding: 6px 5px !important;
  //     // white-space:nowrap;
  //     // overflow-x: scroll;
  //   }
  // }

  .ant-table-tbody > tr > td {
    padding: 6px 5px !important
  }

  .ant-table-thead {
    >tr {
      >th {
        text-align: center !important;
        padding: 9px 5px !important;
        color: #282928 !important;
      }
    }
  }

  .ant-card-body {
    padding: 22px 22px 5px 22px !important;
  }

  .ant-pagination {
    margin-top: 5px !important;
  }

  // 表格浅红色提示
  .rowClass .ant-table-row-cell-break-word {
    color: red !important;
  }


  // 表格编辑下拉框层级问题
  .vxe-select--panel {
    z-index: 9997 !important
  }

  // 下拉框超出
  // .vxe-table--body-wrapper {
  //   z-index: 999998;
  //   // overflow: visible !important;
  // }

  .my-dropdown {
    // width: 300px;
    // overflow: auto;
    border-radius: 4px;
    background-color: #fff;
    border: 1px solid #dcdfe6;
    // z-index: 9997 !important
    // overflow-x: auto!important;
  }

  .list-item {
    // width: 100px;
    height: 35px;
    line-height: 35px;
    padding-left: 10px;

  }

  .list-item p {
    width: 300px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .list-item:hover {
    background-color: #f5f7fa;
  }

  // .ant-table-tbody > tr > td {

  // word-wrap: break-word;
  // word-break: break-all;
  // }

  // .ant-table-fixed {
  //     tr {
  //         height: auto !important;
  //     }
  // }

  // .ant-table-tbody > tr > td {
  //   white-space: break-spaces;
  // }
  // .ant-table-fixed-header .ant-table-scroll .ant-table-header{

  //    width: calc(100% - 100px);//减去滚动条的宽度

  // }
  // // 新增表格背景颜色
  // .my_table_insert .vxe-body--row.is--new {
  //   background-color: #f1fdf1;
  // }
  // .my-dropdown4 {
  //     width: 600px;
  //     height: 300px;
  //     background-color: #fff;
  //     border: 1px solid #dcdfe6;
  //     box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
  //     // z-index: 1 !important
  //   }
  //     .freeStyle_bt {
  //   width: 100%;
  //   /deep/ .ant-form-item-label {
  //     // padding-right: 0px !important;
  //     width: 110px !important;
  //   }
  // }

  //正数订单拆分-手动拆分要用
  .positiveNumHardSplit {
    background-color: yellow;
  }
  // 订单合并-订单调整用
  .AdjustmentModal >td{
      border: 0px solid white !important;
  }
  .AdjustmentModalRemove>td{
    color: #ccc;
  }
  .AdjustmentModalRemove>td>a{
    color: #ccc !important;
  }
  // 表格插槽超出省略号
  .tableTooltip {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #008fff;
    // cursor: pointer;
    cursor: pointer;
  }
  
  
  .ant-table-fixed .ant-table-tbody tr td,
  .ant-table-body .ant-table-tbody tr td {
    word-wrap: break-word;
    word-break: break-all;
  
  }
  // 差额输入框样式
  .numForm{
    height: 100%;
    display: flex;
    align-items: center;
  }
</style>
