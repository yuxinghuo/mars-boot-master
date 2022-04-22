<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="queryForm" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="流程名称">
              <!-- <a-input  allowClear placeholder="请输入流程名称" v-model="queryParam.lcmc"></a-input> -->
               <a-input  allowClear placeholder="请输入流程名称"  v-decorator="['lcmc', { rules: [
                      {validator: flowNameVerify}
                    ],validateTrigger: 'blur' }]"
                     />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="流程标识">
              <!-- <a-input  allowClear placeholder="请输入流程标识" v-model="queryParam.lckey"></a-input> -->
              <a-input  allowClear placeholder="请输入流程标识"  v-decorator="['lckey', { rules: [
                      {validator: flowFlagVerify}
                    ],validateTrigger: 'blur' }]"
                     />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="12">
            <a-form-item label="是否最新">
              <a-switch checkedChildren="是" unCheckedChildren="否" defaultChecked v-model="queryParam.zx"/>
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

          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :md="6" :sm="12" >
                <a-button type="primary"  style="left: 10px" @click="searchQuery" icon="search">查询</a-button>
                <a-button type="primary"  @click="searchReset" icon="reload" style="margin-left: 8px;left: 10px">重置</a-button>
            </a-col>
          </span>
          <!-- <span style="float: right;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :md="12" :sm="12" >
               <a-button type="primary"  style="left: 10px" @click="createObj.visible=true" icon="search">创建流程模型</a-button>
            </a-col>
          </span> -->

        </a-row>
      </a-form>
    </div>

    <!-- table区域-begin -->
    <a-table :scroll="{x:1600,y:500}" bordered
      ref="table"
      size="middle"
      rowKey="id"
        style="white-space: pre-wrap;"
      :dataSource="dataSource"
      :pagination="ipagination"
      
      :loading="loading"
      @change="handleTableChange">

      <a-table-column title="序号" :width="50" fixed='left' align="center"  key="a">
        <template slot-scope="t,r,i" >
          <span> {{i+1}} </span>
        </template>
      </a-table-column>
       <a-table-column title="流程标识" dataIndex="processKey" :width="200" align="center" fixed='left'>
        <template slot-scope="t,r,i">
          <span> {{t}} </span>
        </template>
      </a-table-column>
      <a-table-column title="流程名称" dataIndex="name" :width="200" align="center" fixed='left'>
        <template slot-scope="t,r,i">
          <span> {{t}} </span>
        </template>
      </a-table-column>
      <a-table-column title="备注描述" dataIndex="description" :width="400" align="center">
        <template slot-scope="t,r,i">
          <j-ellipsis :value="t" :length="30"/>
        </template>
      </a-table-column>
      <!-- <a-table-column title="部署id" dataIndex="deploymentId" :width="200" align="center">
        <template slot-scope="t,r,i">
          <span> {{t}} </span>
        </template>
      </a-table-column> -->
     
    
      <a-table-column title="版本" dataIndex="version" key="asa" :width="100" align="center"
                       :sorter="(a, b) => a.version - b.version">
        <template slot-scope="t,r,i">
          <span> v.{{t}} </span>
        </template>
      </a-table-column>
      <!-- <a-table-column title="所属分类" dataIndex="categoryId" :width="200" align="center" key="categoryId_"
                      :filters = "lcTypeF" @filter="filter_categoryId">
        <template slot-scope="t,r,i">
          <span> {{filterDictText(dictOptions, t)}} </span>
        </template>
      </a-table-column> -->
      <a-table-column title="流程图片" dataIndex="diagramName" :width="100" align="center">
        <template slot-scope="t,r,i">
          <span @click="showResource(r)" style="color: #00A0E9;cursor: pointer;">查看</span>
        </template>
      </a-table-column>
      <!-- <a-table-column title="表单路由" dataIndex="routeName" :width="200" align="center">
        <template slot-scope="t,r,i">
          <span @click="viewForm(t)" style="color: blue;cursor: pointer;"> {{getFormComponent(t).text}} </span>
        </template>
      </a-table-column> -->
      <a-table-column title="状态" dataIndex="status" :width="100" align="center">
        <template slot-scope="t,r,i">
          <span v-if="t==1" style="color: #00A0E9;"> 已启用 </span>
          <span v-if="t!=1" style="color: #999;"> 未启用 </span>
        </template>
      </a-table-column>
      
      <a-table-column title="部署时间" dataIndex="createTime" :width="200" align="center">
        <template slot-scope="t,r,i">
          <span> {{t}} </span>
        </template>
      </a-table-column>
      <a-table-column title="更新时间" dataIndex="updateTime" :width="200" align="center">
        <template slot-scope="t,r,i">
          <span> {{t}} </span>
        </template>
      </a-table-column>
      <a-table-column title="操作" dataIndex=""  align="center" :width="250" fixed='right'>
        <template slot-scope="t,r,i">

          <a href="javascript:void(0);" v-if="r.status!=1" @click="editStatus(1,r)" style="color: rgb(144,96,255);">启用</a>
          <a href="javascript:void(0);" v-if="r.status==1" @click="editStatus(0,r)" style="color: #cb892d">禁用</a>
          <a-divider type="vertical" />
          <a href="javascript:void(0);" @click="getNodeData(r)" >节点设置</a>
          <a-divider type="vertical" />
          <a href="javascript:void(0);" @click="convertToModel(r)" >转为模型</a>
          <br>
          <a href="javascript:void(0);"  @click="redact(r)">修改</a> <a-divider type="vertical" />
          <a href="javascript:void(0);"  @click="edit1(r)">关联</a> <a-divider type="vertical" />
          <a href="javascript:void(0);" style="color: red;" @click="remove(r)">删除</a>


        </template>
      </a-table-column>
    </a-table>
    <!-- table区域-end -->
    <!--zk 2021-1-26   编辑-->
    <a-modal
      :confirmLoading="confirmLoading"
      title="关联流程"
      :visible="editObj1.visible"
      @ok="editObjOk1"
      @cancel="editObj1.visible = false"
    >
      <a-form :form="changeEditForm" v-if="editObj1.visible">
        <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="关联菜单" >
          <a-tree-select
              style="width: 100%"
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              allow-clear
              v-decorator="[ 'routeName', {initialValue:upeditObj.fyrouteName,rules: [{ required: true, message: '不能为空' }] },]"
            >
             <a-tree-select-node :selectable="false" v-for="(item,index) in guanlianList" :key="item.path" :value="item.path" :title="item.meta.title">
                <a-tree-select-node v-for="(item1,index1) in item.children" :key="item1.path" :value="item1.path" :title="item1.meta.title">
                </a-tree-select-node>     
              </a-tree-select-node>
          </a-tree-select>
        </a-form-item>
      </a-form>
    </a-modal>
    <!--节点设置-->
    <a-modal
      title="编辑流程节点" width="900px" :maskClosable="false"
      :confirmLoading="confirmLoading"
      :visible="showProcessNodeEdit"
      :footer="null"
      @cancel="closeNode"
    >
      <a-row>
        <!--          选择流程节点-->
        <a-col :md="4" :sm="4" style="border-right: 1px solid #999">

          <a-steps direction="vertical" :current="current" size="small">
            <template  v-for="item,i in nodeList">
              <a-step style="cursor: pointer;" :title="item.title"
                      :description="item.description" :status="i==current?'process':'wait'"
                      @click="change_steps(item,i)"/>
            </template>
          </a-steps>
        </a-col>
        <a-col :md="20" :sm="20">
          <a-alert message="温馨提示：若流程运行至未分配审批人员的审批节点时，流程将自动中断取消！" banner />
          <span></span>
          <a-form :form="nodeForm" v-if="showProcessNodeEdit">
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="节点名称" >
              <span class="nodespan">{{editNode.title}}</span>
            </a-form-item>
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="节点类型" >
              <span class="nodespan">{{dictNodeType[editNode.type]}}</span>
            </a-form-item>
            <a-alert type="info" message="每个节点设置，如有修改都请保存一次，跳转节点后数据不会自动保存！" banner />
            <br/>
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol"  label="审批人员" v-show="editNode.type==1">
                 <!-- 0角色 1用户 2部门 3发起人 4发起人的部门负责人-->
              <!-- <a-checkbox-group @change="spryType" v-model="spryTypes" >
                <a-checkbox value="0"> 根据角色选择 </a-checkbox>
                <a-checkbox value="1"> 直接选择人员 </a-checkbox>
                <a-checkbox value="2"> 部门负责人 </a-checkbox>
                <a-checkbox value="3">
                  发起人
                  <a-tooltip placement="topLeft" title="自动获取发起人">
                    <a-icon type="exclamation-circle" />
                  </a-tooltip>
                </a-checkbox>
                <a-checkbox value="4">
                  发起人的部门负责人
                  <a-tooltip placement="topLeft" title="自动获取发起人所在部门的负责人，即其上级领导。（如果其本身就是部门负责人，则指向发起人自己。）">
                    <a-icon type="exclamation-circle" />
                  </a-tooltip>
                </a-checkbox>
                <a-checkbox value="5"> 发起人的上级部门负责人 </a-checkbox>

              </a-checkbox-group> -->
              <a-radio-group @change="spryType" v-model="spryTypes" >
                <a-radio value="0"> 根据角色选择 </a-radio>
                <a-radio value="1"> 直接选择人员 </a-radio>
                <a-radio value="2"> 部门负责人 </a-radio>
                <a-radio value="3">
                  发起人
                  <a-tooltip placement="topLeft" title="自动获取发起人">
                    <a-icon type="exclamation-circle" />
                  </a-tooltip>
                </a-radio>
                <a-radio value="4">
                  发起人的部门负责人
                  <a-tooltip placement="topLeft" title="自动获取发起人所在部门的负责人，即其上级领导。（如果其本身就是部门负责人，则指向发起人自己。）">
                    <a-icon type="exclamation-circle" />
                  </a-tooltip>
                </a-radio>
                <a-radio value="5"> 发起人的上级部门负责人 </a-radio>

              </a-radio-group>
            </a-form-item>
            <!--            0角色 1用户 2部门 3发起人 4发起人的部门负责人-->
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="选择角色" v-if="spryTypes.indexOf('0')>-1 || spryTypes.indexOf('4')>-1 ||spryTypes.indexOf('5')>-1 " >
              <j-select-role  v-model="spry.roleIds"/>
            </a-form-item>
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="选择人员" v-if="spryTypes.indexOf('1')>-1" >
              <!--  通过部门选择用户控件 -->
              <j-select-user-by-dep v-model="spry.userIds" :fenPage="true" :multi="true"></j-select-user-by-dep>
            </a-form-item>
            <!-- 选择部门 zk 2021-1-25 -->
            <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="选择部门" v-if="spryTypes.indexOf('2')>-1" >
              <j-select-depart v-model="spry.departmentIds" @choolseBuMen="choolseBuMen" :multi="true"></j-select-depart>
            </a-form-item>
            <!--btn-->
            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
              <a-button @click="sprySubmit" :type="primary" html-type="submit" :disabled="editNode.type==0||editNode.type==2||confirmLoading">
                提交并保存
              </a-button>

              <a-button @click="closeNode" style="margin-left: 10px">
                关闭
              </a-button>
            </a-form-item>
          </a-form>
        </a-col>
      </a-row>
    </a-modal>
    <!--查看图片-->
    <a-modal
      :title="viewTitle" width="90%"
      :visible="viewImage" :footer="null"
      @cancel="viewImage = false"
    >
      <div  class="Imgclass">
        <img :src="diagramUrl" :alt="viewTitle">
      </div>
    </a-modal>
    <!--流程表单 预览-->
    <a-modal  :title="lcModa.title" v-model="lcModa.visible" :footer="null" :maskClosable="false" width="80%">
      <component :is="lcModa.formComponent" :disabled="true"></component>
    </a-modal>
    <!-- 编辑流程名称和备注描述  编辑流程名称和备注描述-->
    <a-modal  :title="redactTitle" v-model="redactVisible"   @ok="redactVisibleOk"  @cancel="redactVisible = false"   width="450px">
        <span v-if="redactTitle === '确认转化'">
           您确认要转化流程   {{redactObj.name}}   为模型?
        </span>
        <a-form :form="redactForm" >
           <a-form-item label="流程名称">
            <!-- <a-textarea
              placeholder="输入流程名称"
              v-decorator="[
                'name',  
                {
                  rules: [
                    { required: true, message: '请输入流程名称' },
                    { max:200, message: '请控制在200个字符以内' },
                  ],
                },
              ]"
              :auto-size="{ minRows: 3, maxRows: 5 }"
            /> -->
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
              <a-input placeholder="输入流程名称"
              v-decorator="[
                'name',  
               {
                 rules: [
                      {validator: flowName}
                                  ],validateTrigger: 'blur' }]" />
          </a-form-item>
           <a-form-item label="备注描述">
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
          </a-form-item>
        </a-form>
    </a-modal>
  </a-card>

</template>

<script>
  import { filterObj } from '@/utils/util';
  import { JeecgListMixin } from '@/mixins/JeecgListMixin';
  import { activitiMixin } from '@/views/activiti/mixins/activitiMixin';
  import JEllipsis from '@/components/jeecg/JEllipsis';
  // import { deleteAction, getAction,downFile } from '@/api/manage'
  import qs from 'qs';
  import { getAction, postAction, deleteAction, putAction, downFile } from '@/api/manage';
  import { mapState } from 'vuex'

  import pick from "lodash.pick";
  import JTreeSelect from '@/components/jeecg/JTreeSelect';
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil';
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep';
  import JSelectRole from '@/components/jeecgbiz/JSelectRole';
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart';

  export default {
    name: "ProcessModelList",
    mixins:[activitiMixin,JeecgListMixin],
    components: {
      JEllipsis,JSelectUserByDep,JSelectRole,JSelectDepart
      ,JTreeSelect
    },
    data () {
      return {
        queryForm: this.$form.createForm(this),
        // zk 2021-02-18
        redactTitle: '',
        onSelectId: '',
        // zk 2021-02-09
        redactObj: {},
        redactVisible: false,
        redactForm: this.$form.createForm(this),
        // zk 2021-1-26
        bmids: '',
        primary: '',
        guanlianList: [],
        changeEditForm: this.$form.createForm(this),
        editObj1:{
          visible: false,
        },
        upeditObj:{
          routeName: '',
          id: '',
          fyrouteName: '',
        },

        viewImage:false,
        viewTitle:"",
        diagramUrl:"",
        /*编辑流程*/
        editObj:{
          visible: false,
        },
        editForm: this.$form.createForm(this),

        description: '已部署模型',
        // 查询条件
        queryParam: {
          createTimeRange:[],
          keyWord:'',
        },
        tabKey: "1",
        // 表头
        labelCol: {
          xs: { span: 4 },
          sm: { span: 4 },
        },
        wrapperCol: {
          xs: { span: 20 },
          sm: { span: 20 },
        },
        confirmLoading:false,
        current:0,
        nodeForm: this.$form.createForm(this),
        dictOptions:[],
        nodeList:[],
        editNode:{},
        showProcessNodeEdit:false,
        dictNodeType:{
          '0':'开始节点',
          '1':'审批节点',
          '2':'结束节点',
        },
        spryTypes:[],
        url: {
          list: "/activiti_process/listData",
          img: "/activiti/models/export",
          updateStatus: "/activiti_process/updateStatus",
          delByIds: "/activiti_process/delByIds",
          convertToModel: "/activiti_process/convertToModel",
          updateInfo: "/activiti_process/updateInfo",
          getProcessNode: "/activiti_process/getProcessNode",
          editNodeUser: "/activiti_process/editNodeUser",
          // zk  2021-1-26
          updateInfo1: "/activiti_process/updateRoute"
        },
        spry:{
          //选中的用户
          userIds:'',
          roleIds:'',
          departmentIds:'',
          chooseSponsor:false,
          chooseDepHeader:false,
          chooseParentDepHeader:false,
        },
        lcModa: {
          title:'流程表单预览',
          visible:false,
          formComponent : null
        },
        lcTypeF:[],
        dataList: []
      }
    },
    // zk  2021-01-26
    watch:{
      editNode(val){
        console.log(val)
        if(val.type == 0 || val.type == 2){
          this.primary = ''
        }else{
          this.primary = 'primary'
        }
      },
      confirmLoading(val){
        console.log(val)
        if(val){
          this.primary = ''
        }else{
           this.primary = 'primary'
        }
      }
    }, 
    computed:{
      // zk  2021-1-26
       ...mapState({
        // 主路由
        mainRouters: state => state.permission.addRouters,
        // 后台菜单
        permissionMenuList: state => state.user.permissionList
      }),
      //可行性测试，根据文件路径动态加载组件
      LcDict:function(){
        var myComponent =() => import(`@/components/dict/JDictSelectTag`);
        return myComponent;
      },
    },
    mounted(){
      this.menuList()
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
      // zk 2021-07-06
      searchQuery(){
        this.loadData(1)
      },
       flowNameVerify(rule, value, callback) {
        if (value) {
          this.queryForm.setFieldsValue({
            lcmc: this.$utils.trim(value)
          })
          if (value.length > 200) {
            callback('请控制在200个字符以内');
          }
        }
        callback();
      },
      flowFlagVerify(rule, value, callback) {
        if (value) {
          this.queryForm.setFieldsValue({
            lckey: this.$utils.trim(value)
          })
          if (value.length > 21) {
            callback('请控制在21个字符以内');
          }
        }
        callback();
      },
      // zk 2021-02-09
      redact(r){
        this.redactTitle = '编辑流程名称和备注描述',
        this.redactObj = r
        this.redactVisible = true
        this.$nextTick(() => {
            // this.redactForm.setFieldsValue(pick(r,'name',' description'))
            this.redactForm.setFieldsValue({
              name: r.name,
              description: r.description
            })
        })
      },
      redactVisibleOk(){
        console.log(this.redactObj)
        
         this.redactForm.validateFields((err, value) => {
           value.description = value.description.replace(/(^\s*)|(\s*$)/g, "")
           value.name = value.name.replace(/(^\s*)|(\s*$)/g, "")
           console.log(value)
           if(!err && value.name != "" && value.description != ""){
             this.redactObj.description = value.description
             this.redactObj.name = value.name
             console.log(this.redactTitle)
             console.log(this.redactTitle == '编辑流程名称和备注描述')
             if(this.redactTitle == '编辑流程名称和备注描述'){
                getAction('/activiti_process/update',this.redactObj).then(
                  res=>{
                    if(res.code === 200){
                      this.$message.success(res.message)
                      this.redactVisible = false
                      this.loadData()
                    }else{
                      this.$message.error(res.message)
                    }
                  }
                )
             }else{
                // getAction(`${that.url.convertToModel}?id=${row.id}`).then(res => {
                getAction('/activiti_process/convertToModel',this.redactObj).then(res => {
                  console.log(res)
                  if (res.success) {
                    let that = this
                    setTimeout(function() {
                      that.$message.success("转化成功");
                    }, 300);
                     this.redactVisible = false
                      this.loadData()
                  }else {
                    this.$message.error(res.message);
                  }
                });
             }
            
           }else{
             this.$message.error('请输入有效的信息！')
           }
         })
      
        // redactForm
      },
      // zk 2021-01-26
      menuList(){
        console.log(this.permissionMenuList)
        let arr = []
        this.permissionMenuList.map(item=>{
          if(!(item.name == 'dashboard-analysis' || item.name == 'online' || item.name == 'isystem')){
            arr.push(item)
          }
        })
        this.guanlianList = arr
        console.log(arr)
      },
      choolseBuMen(val){
        console.log(val)
        this.bmids = val
      },
      editObjOk1(){
        this.changeEditForm.validateFields((err, values) => {
          
          if (!err) {
            console.log(this.upeditObj)
            console.log(values)
            if(this.upeditObj.fyrouteName != values.routeName){
              console.log('ji')
                  this.upeditObj.routeName = values.routeName
            }
            console.log(this.upeditObj)
            postAction(this.url.updateInfo1,qs.stringify(this.upeditObj)).then(res => {
              console.log(res)
              if (res.success) {
                this.$message.success(res.message)
                this.editObj1.visible = false;
                this.loadData()
              }else {
                this.$message.error(res.message)
              }

            })
          }
        })
      },
      edit1(row){
        this.upeditObj.id = row.id
        this.upeditObj.routeName = row.routeName
   
        this.guanlianList.map(item=>{
          if(item.children){
            for(let i =0;i<item.children.length;i++){
              if(item.children[i].path == row.routeName){
                this.upeditObj.fyrouteName = item.children[i].meta.title
                break
              }
              // arr.push(item.children[i])
            }
          }
        })

        this.editObj1.visible = true;
        console.log(row)
        // this.editObj = Object.assign(this.editObj, row)

        console.log(this.editObj)
      },
// ===========================
      initDictConfig() {
        //初始化字典 - 流程分类
        initDictOptions('bpm_process_type').then((res) => {
          if (res.success) {
            var lcTypes = [];
            this.dictOptions = res.result||[];
            for (const dict of this.dictOptions) {
              lcTypes.push({text:dict.text,value:dict.value})
            }
            this.lcTypeF = lcTypes;
            console.log(lcTypes)
          }
        });
      },
      filterDictText(dictOptions, text) {
        if (dictOptions instanceof Array) {
          for (let dictItem of dictOptions) {
            if (text === dictItem.value) {
              return dictItem.text
            }
          }
        }
        return text
      },
      closeNode() {
        this.showProcessNodeEdit = false,
        this.current=0,
        this.spryTypes=[],
        this.spry={}
      },
      /*编辑流程节点*/

      change_steps(node,index){
        this.spryTypes = [];
        console.log('onChange:', node);
        this.current = index;
        this.editNode = node;
        /* 0角色 1用户 2部门 3发起人 4发起人的部门负责人 5发起人部门*/
        this.spry.chooseParentDepHeader = node.chooseParentDepHeader||false;
        // if (this.spry.chooseParentDepHeader) this.spryTypes.push('5') ;
        if (this.spry.chooseParentDepHeader) this.spryTypes = '5'
        this.spry.chooseDepHeader = node.chooseDepHeader||false;
        // if (this.spry.chooseDepHeader) this.spryTypes.push('4') ;
        if (this.spry.chooseDepHeader) this.spryTypes = '4'

        this.spry.chooseSponsor = node.chooseSponsor||false;
        // if (this.spry.chooseSponsor) this.spryTypes.push('3') ;
        if (this.spry.chooseSponsor) this.spryTypes = '3'

        var userIds = [];
        for (const user of node.users||[]) {
          userIds.push(user.username);
        }
        this.spry.userIds = userIds.join(",");
        // if (userIds.length>0) this.spryTypes.push('1') ;
          if (userIds.length>0) this.spryTypes = '1'

        console.log(userIds.length)
       
        var roleIds = []
        for (const role of node.roles||[]) {
          roleIds.push(role.roleCode)
        }
        this.spry.roleIds = roleIds.join(",")
         if(this.spry.chooseParentDepHeader || this.spry.chooseDepHeader){

        }else{
         if (roleIds.length>0) this.spryTypes='0';
        }
        // if (roleIds.length>0) this.spryTypes.push('0') ;
        var departmentIds = []
        for (const department of node.departments||[]) {
          departmentIds.push(department.id)
        }
        this.spry.departmentIds = departmentIds.join(",")
        // if (departmentIds.length>0) this.spryTypes.push('2') ;
        if (departmentIds.length>0) this.spryTypes = '2' ;


        console.log("回显this.spryTypes",this.spryTypes)
        console.log("回显this.spry",this.spry)

      },
      spryType(types){
        console.log(this.spryTypes)
        console.log(this.spry)
        /* 0角色 1用户 2部门 3发起人 4发起人的部门负责人*/
        // this.spryTypes = types;
        if (this.spryTypes.indexOf('0')==-1) this.spry.roleIds = '';
        if (this.spryTypes.indexOf('1')==-1) this.spry.userIds = '';
        if (this.spryTypes.indexOf('2')==-1) this.spry.departmentIds = '';
        //是否选中发起人
        this.spry.chooseSponsor = this.spryTypes.indexOf('3')>-1 ;
        //是否选中发起人的部门领导
        this.spry.chooseDepHeader = this.spryTypes.indexOf('4')>-1 ;
        //是否选中发起人的部门
         this.spry.chooseParentDepHeader = this.spryTypes.indexOf('5')>-1 ;
        console.log("this.spry",this.spry)
      },
      sprySubmit() {
        console.log(this.bmids)
        console.log(this.spry)
        this.spry.departmentIds = this.bmids
        var _this = this;
        if (this.spryTypes.length==0){
          _this.$message.error("必须选择审批人！");
          return;
        }
        _this.confirmLoading = true;
        this.spry.nodeId = this.editNode.id;
        console.log(this.spry)
        this.spry.procDefId = this.onSelectId.id
       // console.log(this.spry)
        postAction(_this.url.editNodeUser,qs.stringify(this.spry)).then(res => {
          if (res.success) {
            _this.$message.success("操作成功");
            // _this.getData();
// zk 2021-02-19   薛超要求提交并保存后数据更改
              // let  _this = this;
              postAction(_this.url.getProcessNode + '?id' + `=${this.onSelectId.id}`).then(res => {
                if (res.success) {
                  // 转换null为""
                  _this.nodeList = res.result||[];
                  // console.log("_this.nodeList",_this.nodeList);
                  if (_this.nodeList.length>0){
                    // _this.editNode = _this.nodeList[0];
                    // _this.showProcessNodeEdit = true;
                  }
                }else {
                  _this.$message.error(res.message);
                }
              });
          }else {
            _this.$message.error(res.message);
          }
        }).finally(() => _this.confirmLoading = false);
      },
      getNodeData(row){
        this.onSelectId = row
        console.log(row)
        var _this = this;

        postAction(_this.url.getProcessNode + '?id' + `=${row.id}`).then(res => {
          if (res.success) {
            // 转换null为""
            _this.nodeList = res.result||[];
            console.log("_this.nodeList",_this.nodeList);
            if (_this.nodeList.length>0){
              _this.editNode = _this.nodeList[0];
              _this.showProcessNodeEdit = true;
            }
          }else {
            _this.$message.error(res.message);
          }
        });
      },
      /*编辑流程信息*/
      edit(row){
        this.editObj = Object.assign(this.editObj, row)
        console.log(this.editObj)
        this.editObj.visible = true;
      },
      editObjOk(){
        var _this = this;
        this.editForm.validateFields((err, values) => {
          if (!err) {
            let formData = Object.assign(this.editObj, values)
            console.log("formData",formData)
            this.confirmLoading = true;
            postAction(this.url.updateInfo,formData).then(res => {
              if (res.success) {
                _this.$message.success("操作成功");
                _this.loadData();
                _this.editObj.visible = false;
              }else {
                _this.$message.error(res.message);
              }
            }).finally(() => _this.confirmLoading = false);;
          }
        })
      },
      change_routeName(){
        this.$nextTick(()=>{
          let routeName = this.editForm.getFieldValue('routeName');
          console.log("routeName",routeName)
          var route = this.getFormComponent(routeName);
          this.editObj.businessTable = route.businessTable;
          this.editObj.routeName = route.routeName;
          console.log("this.editObj",this.editObj)
        })
      },
      viewForm(routeName) {
        if (!routeName) routeName = this.editObj.routeName;
        if (!routeName) {
          this.$message.warning(
            "请先选择表单！"
          );
          return;
        }
        let formComponent = this.getFormComponent(routeName);
        this.lcModa.formComponent = formComponent.component;
        this.lcModa.title = '流程表单预览：'+formComponent.text;
        this.lcModa.visible = true;
      },
      convertToModel(row){
        this.redactObj = row
        this.redactTitle = '确认转化',
        this.redactVisible = true

        this.$nextTick(() => {
            this.redactForm.setFieldsValue({
              name: row.name,
              description: row.description
            })
        })
        // let that = this;
        // this.$confirm({
        //   title: "确认转化",
        //   content: "您确认要转化流程 " + row.name + " 为模型?",
        //   loading: true,
        //   onOk: () => {
        //     postAction(`${that.url.convertToModel}?id=${row.id}`).then(res => {
        //       if (res.success) {
        //         setTimeout(function() {
        //           that.$message.success("转化成功");
        //         }, 300);
        //       }else {
        //         that.$message.error(res.message);
        //       }
        //     });
        //   }
        // });
      },
      remove(row) {
        var _this = this;
        _this.$confirm({
          title: "确认删除",
          content: "您确认要删除流程 " + row.name + " ?",
          loading: true,
          onOk: () => {
           postAction(_this.url.delByIds,qs.stringify({ids:row.id})).then(res => {
              if (res.success) {
                _this.$message.success("操作成功");
                _this.loadData();
              }else {
                _this.$message.error(res.message);
              }
            });
          }
        });
      },
      editStatus(status, row) {
        let _this = this;
          console.log(getAction)
          console.log(postAction)
        let operation = "";
        if (status == 0) {
          operation = "不启用";
        } else {
          operation = "启用";
        }
        console.log(this)
        this.$confirm({
          title: "确认" + operation+"?",
          content:  `您确认要${operation}流程${row.name}?`,
          onOk() {
            let params = {
              status: status,
              id: row.id
            };
            console.log(this)
           postAction(_this.url.updateStatus,qs.stringify(params)).then(res => {
              if (res.success) {
                _this.$message.success("操作成功");
                _this.loadData();
              }else {
                _this.$message.error(res.message);
              }
            });
          }
          ,
          onCancel() {},
        });
      },
      /*查看流程图片*/
      showResource(row) {
        console.log(row)
          this.viewTitle = "流程图片预览(" + row.name + ")";
          console.log(`${this.doMian}${this.url.img}?id=${row.id}`)
          this.diagramUrl = `${this.doMian}${this.url.img}?id=${row.id}`;
          this.viewImage = true;
      },
      /*删除模型*/
      deletelc(y,row) {
        console.log(y,row);
        if (y){
          
          getAction(this.url.delete+row.id).then((res) => {
            if (res.success){
              this.$message.success(res.message);
            }else {
              this.$message.error(res.message);
            }
            this.loadData();
          });
        }
      },
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
          this.loadData()
        console.log(pagination,filters,sorter)
        /*if (Object.keys(filters).length>0&&this.dataList.length>0){
          for (const filterField in filters) {
            let fiterVals = filters[filterField]||[];

          }
        }*/
        // this.loadData();
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        // .replace(/(^\s*)|(\s*$)/g, "")
        var params = this.getQueryParams();//查询条件
        console.log(params,'1')
        if(!params){
          return
        }
        // if(params.lcmc){
        //   params.lcmc = params.lcmc.replace(/(^\s*)|(\s*$)/g, "")
        // }
        // if(params.lckey){
        //   params.lckey = params.lckey.replace(/(^\s*)|(\s*$)/g, "")
        // }
        params.pageNo = this.ipagination.current
      params.pageSize = this.ipagination.pageSize
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            let records = res.result.records ||[];
            this.dataSource = records;
            // this.dataList = records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      getQueryParams(){
        let onOff = false
        this.queryForm.validateFields((err,value)=>{
          if(err){
            onOff = true
          }else{
            console.log(value)
            this.queryParam.lcmc = value.lcmc
            this.queryParam.lckey = value.lckey
            
          }
        })
        if(onOff){
          return false
        }
        var param = Object.assign({}, this.queryParam,this.isorter);
        delete param.createTimeRange; // 时间参数不传递后台
        return filterObj(param);
      },

      // 重置
      searchReset(){
        this.queryForm.resetFields()
        var that = this;
        var logType = that.queryParam.logType;
        that.queryParam = {}; //清空查询区域参数
        that.queryParam.logType = logType;
        // that.loadData(this.ipagination.current);
      },
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.createTime_begin=dateString[0];
        this.queryParam.createTime_end=dateString[1];
      },
      onDateOk(value) {
        console.log(value);
      },

      filter_categoryId(v,r) {
        // console.log(v,r)
        return r.categoryId == v;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .nodespan{
    color: #999;
  }
  .ant-checkbox-wrapper + .ant-checkbox-wrapper{
    margin-left: 0;
    margin-right: 8px;
  }
  .Imgclass{
    min-height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
  };
</style>