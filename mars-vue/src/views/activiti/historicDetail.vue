<style lang="less">
</style>
<template>
  <div class="search">
    <a-card style="margin-bottom:10px;">
      <p slot="title">
        <span>流程审批进度历史</span>
      </p>
      <a-row style="position:relative">
        <a-table :loading="loading" rowKey="id"
                 :dataSource="data"
                 :pagination="ipagination"
                  ref="table">
          <a-table-column title="序号"  width="50">
            <template slot-scope="t,r,i" >
              <span> {{i+1}} </span>
            </template>
          </a-table-column>
          <!-- <a-table-column title="任务名称" dataIndex="name"  width="150" align="center">
            <template slot-scope="t">
              <span> {{t}} </span>
            </template>
          </a-table-column> -->
          <a-table-column title="处理人" dataIndex="auditUserRaelname"  width="150" align="center">
            <template slot-scope="t">
              {{t}}
              <!-- <div v-if="t">
                <span v-for="item in t">
                  <span v-if="item.isExecutor" style="color: #00DB00;">{{item.username}} </span>
                  <span v-else style="color: #999;">{{item.username}} </span>
                </span>
              </div> -->
            </template>
          </a-table-column>
          <!-- <a-table-column title="审批操作" dataIndex="auditStatus"  width="150" align="center">
            <template slot-scope="t">
              <span v-if="t.toString().indexOf('通过')>-1" style="color: #00DB00">{{t}}</span>
              <span v-else-if="t.toString().indexOf('驳回')>-1" style="color: red;">{{t}}</span>
              <span v-else>{{t}}</span>
            </template>
          </a-table-column> -->
          <a-table-column title="审批意见" dataIndex="auditOpinion"  width="150" align="center">
            <template slot-scope="t">
              <span>{{t}}</span>
            </template>
          </a-table-column>
          <!-- <a-table-column title="耗时" dataIndex="duration"  width="150" align="center">
            <template slot-scope="t">
              <span>{{millsToTime(t)}}</span>
            </template>
          </a-table-column> -->
          <!-- <a-table-column title="创建时间" dataIndex="createTime"  width="150" align="center">
            <template slot-scope="t">
              <span>{{t}}</span>
            </template>
          </a-table-column> -->
          <a-table-column title="审批时间" dataIndex="auditTime"  width="150" align="center">
            <template slot-scope="t">
              <span>{{t}}</span>
            </template>
          </a-table-column>
          <!-- key="aaa" -->
          <a-table-column title="审批状态" dataIndex="auditStatus_Text"  width="150" align="center">
            <!-- <template slot-scope="t">
              <span v-if="t" style="color: blue;">已办理</span>
              <span v-else style="color: #999999">待处理</span>
            </template> -->
          </a-table-column>
        </a-table>
      </a-row>
    </a-card>
    <a-card>
      <p slot="title">
        <span>实时流程图</span>
      </p>
      <a-row style="position:relative">
        <img :src="imgUrl" />
        <a-spin size="large" fix v-if="loadingImg"></a-spin>
      </a-row>
    </a-card>
  </div>
</template>

<script>
import { getAction } from '@/api/manage'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { activitiMixin } from '@/views/activiti/mixins/activitiMixin'
export default {
  name: "historic_detail",
  mixins:[activitiMixin,JeecgListMixin],
  props: {
    /**/
    procInstId: {
      type: String,
      default: '',
      required: true
    },
    imgId: {
      type: String,
      default: '',
      required: true
    }
  },
  data() {
    return {
      shzt: [],
      url:{
        historicFlow:'/audit/process/auditRecords?auditUuid=',
        getHighlightImg:`${this.doMian}/activiti/models/getHighlightImg/`
      },
      type: 0,
      loading: false, // 表单加载状态
      loadingImg: false,
      data: [],
      id: "",
      imgUrl: "",
      backRoute: ""
    };
  },
  created() {
    this.init();
    this.getInvoice()
  },
  watch: {
  },
  methods: {
    loadData(){

    },
    getInvoice() {
      let lastname = JSON.parse(
        sessionStorage.getItem('pro__UI_CACHE_DB_DICT_DATA') || localStorage.getItem('pro__UI_CACHE_DB_DICT_DATA')
      )
      if (lastname === null || lastname === undefined) {
        this.$message.error('暂时未发现发票类型，本地存储没有成功，请从新登录。')
      } else {
        // 审核状态
        this.shzt = lastname.value.audit_status
      }
    },
    init() {
      this.id = this.procInstId;
      this.imgUrl =this.url.getHighlightImg + this.imgId + "?time=" + new Date();
      this.getDataList();
    },
    getDataList() {
      // this.loading = true;
      getAction(this.url.historicFlow+this.id).then(res => {
        // this.loading = false;
        if (res.success) {
            // 审核状态
            console.log(res.result.length,this.shzt)
            for(let i = 0;i<res.result.length;i++){
              this.shzt.map((item) => {
                console.log(res.result[i].auditStatus == item.value)
               if (res.result[i].auditStatus== item.value) {
                      res.result[i].auditStatus_Text = item.title
                 }
              })
            }
            console.log(res.result)
          this.data = res.result;
          if (!res.result || res.result.length == 0) {
            // this.$message.info( "未找到该记录审批历史数据,历史数据可能已被删除");
          }
        }else {
          this.$message.error( res.message);
        }
      });
    },
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field;
        this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
      }
      this.ipagination = pagination;
      // this.loadData();
    },

  },

};
</script>