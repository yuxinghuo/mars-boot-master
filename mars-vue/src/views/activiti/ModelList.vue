<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="queryForm" >
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="关键词">
              <!-- <a-input  allowClear placeholder="模型名称/版本/备注描述" v-model="queryParam.keyWord"></a-input> -->
              <a-input  allowClear placeholder="模型名称/版本/备注描述"  v-decorator="['ModalName', { rules: [
                      {validator: buyVerify}
                    ],validateTrigger: 'blur' }]"
                     />
            </a-form-item>
          </a-col>

          <!--<a-col :md="6" :sm="10">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                style="width: 210px"
                v-model="queryParam.createTimeRange"
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']"
                @change="onDateChange"
                @ok="onDateOk"
              />
            </a-form-item>
          </a-col>-->
          <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
            <a-col :md="12" :sm="12">
              <!-- <a-button type="primary" @click="createObj.visible = true" icon="search">创建流程模型</a-button> -->
              <a-button type="primary" @click="createBtn" icon="search">创建流程模型</a-button>
            </a-col>
          </span>
          <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
            <a-col :md="6" :sm="12">
              <a-button type="primary" style="left: 10px" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px; left: 10px"
                >重置</a-button
              >
            </a-col>
          </span>
        </a-row>
      </a-form>
    </div>

    <!-- table区域-begin -->
    <a-table
      :scroll="{ x: 1280, y: innerHeight / 2 }"
      bordered
      ref="table"
      size="middle"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      :loading="loading"
      @change="handleTableChange"
      style="white-space: pre-wrap;"
    >
      <!-- <span slot="version" slot-scope="text, record"> v.{{ text }} </span> -->
      <span slot="version" slot-scope="text, record"> {{ text }} </span>
      <span slot="description" slot-scope="text, record">
        {{ text }}
        <!-- <j-ellipsis :value="JSON.parse(text).description" :length="10"/> -->
      </span>
      <!-- 字符串超长截取省略号显示-->
      <span slot="logContent" slot-scope="text, record">
        <j-ellipsis :value="text" :length="10" />
      </span>
      <span slot="make" slot-scope="text, record">
        <a href="javascript:void(0);" @click="deployment(record)">发布</a>
        <a-divider type="vertical" />
        <a href="javascript:void(0);" @click="updatelc(record.id)">设计流程</a>
        <a-divider type="vertical" />
        <a-popconfirm
          title="是否确认删除?"
          @confirm="deletelc(1, record)"
          @cancel="deletelc(0)"
          okText="是"
          cancelText="否"
        >
          <a href="javascript:void(0);">删除</a>
        </a-popconfirm>
      </span>
    </a-table>
    <!-- table区域-end -->
    <a-modal
      title="创建模型"
      :visible="createObj.visible"
      @ok="createObjOk"
      :confirmLoading="createObj.confirmLoading"
      @cancel="createObj.visible = false"
    >
      <a-form :form="form" class="box">
        <div>
          <a-form-item label="模型名称">
            <a-input
              placeholder="输入模型名称"
              v-decorator="[
                'name',
                {
                  rules: [
                    { required: true, message: '请输入模型名称' },
                    { pattern: /^.{0,200}$/g, message: '请控制在200个字符以内' },
                  ],
                },
              ]"
            ></a-input>
          </a-form-item>
          <a-form-item label="模型Key">
            <a-input
              placeholder="输入模型Key"
              v-decorator="[
                'key',
                {
                  rules: [
                    { required: true, message: '请输入模型Key' },
                    { pattern: /^.{0,200}$/g, message: '请控制在200个字符以内' },
                  ],
                },
              ]"
            ></a-input>
          </a-form-item>
          <a-form-item label="模型描述">
            <a-textarea
              placeholder="输入模型描述"
              v-decorator="[
                'description',  
                {
                  rules: [
                    { required: true, message: '请输入模型描述' },
                    { max:200, message: '请控制在200个字符以内' },
                  ],
                },
              ]"
              :auto-size="{ minRows: 3, maxRows: 5 }"
            />
          </a-form-item>
        </div>
      </a-form>
    </a-modal>
      <!-- v-if="updateObj.visible" -->
    <!-- :visible="updateObj.visible"  -->
    <a-modal
      title="设计模型"
      :footer="null"
      :visible="updateObj.visible"
      v-if="updateObj.visible"
      :maskClosable="false"
      width="90%"
      @cancel="cancelUpdate"
      style="top: 20px"
    >
      <iframe
        :src="iframUrl"
        frameborder="0"
        width="100%"
        height="800px"
        scrolling="auto"
        style="background-color: #fff"
      ></iframe>
    </a-modal>
    <a-modal  title="确认部署流程?" v-model="redactVisible"   @ok="redactVisibleOk"  @cancel="redactVisible = false"   width="450px">
        
        <a-form :form="redactForm" >
           <a-form-item label="流程名称">
              <a-input placeholder="输入流程名称"
              v-decorator="[
                'name',  
                {
                 rules: [
                      {validator: flowName}
                                  ],validateTrigger: 'blur' }]" />
            <!-- <a-input placeholder="输入流程名称"
              v-decorator="[
                'name',  
                {
                  rules: [
                    { required: true, message: '请输入流程名称' },
                    { max:200, message: '请控制在200个字符以内' },
                  ],
                },
              ]"></a-input> -->
          </a-form-item>
           <a-form-item label="备注描述">
              <a-textarea
              placeholder="输入备注描述"
              v-decorator="[
                'description',  
                {
                   rules: [
                      {validator: flowNote}
                                  ],validateTrigger: 'blur' }
              ]"
              :auto-size="{ minRows: 3, maxRows: 5 }"
            />
            <!-- <a-textarea
              placeholder="输入备注描述"
              v-decorator="[
                'description',  
                {
                  rules: [
                    { required: true, message: '请输入备注描述' },
                    { max:200, message: '请控制在200个字符以内' },
                  ],
                },
              ]"
              :auto-size="{ minRows: 3, maxRows: 5 }"
            /> -->
          </a-form-item>
        </a-form>
    </a-modal>
  </a-card>
</template>

<script>
import { filterObj } from '@/utils/util'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import { deleteAction, getAction, downFile } from '@/api/manage'

export default {
  name: 'ModelList',
  mixins: [JeecgListMixin],
  components: {
    JEllipsis,
  },
  data() {
    return {
      redactObj: {},
       queryForm: this.$form.createForm(this),
      redactForm: this.$form.createForm(this),
      redactVisible: false,
      form: this.$form.createForm(this),

      /*流程设计器连接*/
      iframUrl: '',
      /*新增流程框参数*/
      createObj: {
        visible: false,
        confirmLoading: false,
      },
      /*设计流程框参数*/
      updateObj: {
        visible: false,
        confirmLoading: false,
      },
      description: '这是流程模型列表页面',
      // 查询条件
      queryParam: {
        createTimeRange: [],
        keyWord: '',
      },
      tabKey: '1',
      // 表头
      columns: [
        {
          title: '序号',
           width: 80,
          dataIndex: '',
          key: 'rowIndex',
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        // {
        //   title: '模型id',
        //   width: 150,
        //   align: 'center',
        //   dataIndex: 'id',
        // },
        {
          title: '模型名称',
          width: 150,
          align: 'center',
          dataIndex: 'name',
          scopedSlots: { customRender: 'logContent' },
        },
        // {
        //   title: '模型key',
        //   width: 150,
        //   dataIndex: 'key',
        //   align: 'center',
        // },
        //   {
        //   title: '流程id',
        //   width: 150,
        //   dataIndex: 'processId',
        //   align: 'center',
        // },
          {
          title: '备注描述',
          width: 300,
          dataIndex: 'description',
          // align: 'center',
           align: 'left',
          scopedSlots: { customRender: 'description' },
        },
        {
          title: '版本',
          width: 80,
          dataIndex: 'version',
          align: 'center',
          scopedSlots: { customRender: 'version' },
        },
      
        {
          title: '创建时间',
          width: 150,
          dataIndex: 'createTime',
          align: 'center',
          // sorter:true
        },
        {
          title: '最后更新时间',
          width: 150,
          dataIndex: 'lastUpdateTime',
          align: 'center',
        },
        {
          title: '操作',
          width: 250,
          dataIndex: '',
          scopedSlots: { customRender: 'make' },
          align: 'center',
        },
      ],
      labelCol: {
        xs: { span: 1 },
        sm: { span: 2 },
      },
      wrapperCol: {
        xs: { span: 10 },
        sm: { span: 16 },
      },
      url: {
        list: '/activiti/models/modelListData',
        delete: '/activiti/models/delete/',
        deployment: '/activiti/models/deployment/',
        create: '/activiti/models/create',
        update: '/activiti/modeler.html?modelId=',
      },
    }
  },
  mounted() {
    this.doMian = window._CONFIG.domianURL
  },
  methods: {
    // 2021-07-06  增加校验
     flowName(rule, value, callback) {
       console.log(value)
        if (value) {
          this.redactForm.setFieldsValue({
            name: this.$utils.trim(value)
          })
          // queryForm buyerName
          // let valueLength = value.replace(/[^\u4e00-\u9fa5]/g, '').length + value.length;

          if (value.length > 200) {
            callback('请控制在200个字符以内');
          }
        }
        callback();
      },
    flowNote(rule, value, callback) {
       console.log(value)
        if (value) {
          this.redactForm.setFieldsValue({
            description: this.$utils.trim(value)
          })
          // queryForm buyerName
          // let valueLength = value.replace(/[^\u4e00-\u9fa5]/g, '').length + value.length;

          if (value.length > 200) {
            callback('请控制在200个字符以内');
          }
        }
        callback();
      },
    buyVerify(rule, value, callback) {
        if (value) {
          this.queryForm.setFieldsValue({
            ModalName: this.$utils.trim(value)
          })
          // queryForm buyerName
          // let valueLength = value.replace(/[^\u4e00-\u9fa5]/g, '').length + value.length;

          if (value.length > 200) {
            callback('请控制在200个字符以内');
          }
        }
        callback();
      },
    handleSubmit(e) {
      console.log(e)
    },
    // zk 2021-02-10   发布重写
    redactVisibleOk(){
        this.redactForm.validateFields((err, value) => {
           value.description = value.description.replace(/(^\s*)|(\s*$)/g, "")
           value.name = value.name.replace(/(^\s*)|(\s*$)/g, "")
           console.log(value)
           if(!err){
             if(value.description != "" && value.name != ""){
                this.redactObj.description = value.description
                this.redactObj.name = value.name
                // getAction('/activiti_process/update',this.redactObj).then(
                //   res=>{
                //     if(res.code === 200){
                //       this.$message.success(res.message)
                //       this.redactVisible = false
                //       this.loadData()
                //     }else{
                //       this.$message.error(res.message)
                //     }
                //   }
                // )
                getAction(this.url.deployment + this.redactObj.id ,this.redactObj).then((res) => {
                  if (res.success) {
                    this.$message.success(res.message)
                    this.redactVisible = false
                      this.loadData()
                  } else {
                    this.$message.error(res.message)
                  }
                  this.loadData()
                })
             }else{
               this.$message.error('请输入有效的信息！')
              }
             
           }
         })
    },
    // zk 2021-02-09  创建流程修改，不用填参数
    createBtn(){
        let that = this
        this.$confirm({
          title: '创建模型',
          content: h => <div >确定要创建一个流程模型吗？</div>,
          onOk() {
            console.log('OK');
            getAction('/activiti/models/modelId',).then(
              res=>{
                console.log(res)
                if(res.code === 200){
                   that.updatelc(res.result)
                }else{
                  that.$message.error(res.message)
                }
              }
            )
          },
          onCancel() {
            console.log('Cancel');
          },
          class: 'test',
        });
      
    },
    /*创建流程*/
    createObjOk(e) {
      this.form.validateFields((err, values) => {
        console.log(values)
        if (!err) {
          let obj = {
            description: values.description.replace(/(^\s*\n*)|(\s*\n*$)/g, ""),
            key: values.key.replace(/(^\s*)|(\s*$)/g, ""),
            name: values.name.replace(/(^\s*)|(\s*$)/g, "")
          }
          if(obj.description == "" || obj.key == "" || obj.name == ""){
            this.$message.error('请输入有效的信息！')
          }else{
             getAction('/activiti/models/create', values).then((res) => {
              console.log(res)
              if (res.code === 200) {
                this.createObj.visible = false
                this.updatelc(res.result)
              } else {
              }
            })
          }
        //  console.log(obj)
        //   getAction('/activiti/models/create', values).then((res) => {
        //     console.log(res)
        //     if (res.code === 200) {
        //       this.createObj.visible = false
        //       this.updatelc(res.result)
        //     } else {
        //     }
        //   })
        }
      })

      // console.log(this.doMian)
      // this.createObj.confirmLoading = true;
      // this.updateObj.visible = true;
      // this.iframUrl = `${this.doMian}${this.url.create}?name=${this.createObj.name||""}&key=${this.createObj.key||""}&description=${this.createObj.description||""}`
      // this.createObj.visible = false;
      // this.createObj.confirmLoading = false;
    },
    /*修改流程*/
    updatelc(id) {
      console.log(id)
      var _this = this
      console.log(`${_this.doMian}${_this.url.update}${id}`)
      this.$message.loading('稍等。。。', 0.8).then(() => {
        _this.createObj.confirmLoading = true
        _this.iframUrl = `${_this.doMian}${_this.url.update}${id}`
        _this.updateObj.visible = true
        _this.createObj.confirmLoading = false
      })
    },
    cancelUpdate() {
      var _this = this
      this.$confirm({
        title: '关闭前请确认已保存修改?',
        content: '关闭后未保存的修改将丢失！',
        okText: '确认关闭',
        okType: 'danger',
        cancelText: '再看看',
        onOk() {
          _this.updateObj.visible = false
          _this.loadData()
        },
        onCancel() {
          console.log('Cancel')
        },
      })
    },
    /*发布流程*/
    deployment(row) {
      this.redactObj = row
      this.redactVisible = true
      this.$nextTick(() => {
            // this.redactForm.setFieldsValue(pick(r,'name',' description'))
            this.redactForm.setFieldsValue({
              name: row.name,
              description: row.description
            })
      })
      // var _this = this
      // var id = row.id
      // var name = row.name
      // this.$confirm({
      //   title: '确认部署流程?',
      //   // content: `确认部署流程：${name}`,
      //   onOk() {
      //     getAction(_this.url.deployment + id).then((res) => {
      //       if (res.success) {
      //         _this.$message.success(res.message)
      //       } else {
      //         _this.$message.error(res.message)
      //       }
      //       _this.loadData()
      //     })
      //   },
      //   onCancel() {},
      // })
    },
    /*删除模型*/
    deletelc(y, row) {
      console.log(y, row)
      if (y) {
        getAction(this.url.delete + row.id).then((res) => {
          if (res.success) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.loadData()
        })
      }
    },
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
    },
    searchQuery() {
      console.log(1)
      this.loadData(1)
      // this.loadData();
    },
    loadData(arg) {
  
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      if(!params){
        return
      }
      this.loading = true
      console.log(this.ipagination)
      params.pageNo = this.ipagination.current
      params.pageSize = this.ipagination.pageSize
      console.log(params)
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          let records = res.result.records || []
          this.dataSource = records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    getQueryParams() {
      let onOff = false
      this.queryForm.validateFields((err,value)=>{
        if(err){
          onOff = true
        }else{
          this.queryParam.keyWord = value.ModalName
        }
      })
      if(onOff){
        return false
      }
     
      var param = Object.assign({}, this.queryParam, this.isorter)
      delete param.createTimeRange // 时间参数不传递后台
      return filterObj(param)
    },

    // 重置
    searchReset() {
      var that = this
      var logType = that.queryParam.logType
      that.queryParam = {} //清空查询区域参数
      that.queryParam.logType = logType
      this.queryForm.resetFields()
      // that.loadData(this.ipagination.current)
    },
    onDateChange: function (value, dateString) {
      console.log(dateString[0], dateString[1])
      this.queryParam.createTime_begin = dateString[0]
      this.queryParam.createTime_end = dateString[1]
    },
    onDateOk(value) {
      console.log(value)
    },
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.box {
  ::v-deep .ant-form-item-label {
    // display: flex;
    height: 20px;
    line-height: 20px;
  }
}
</style>