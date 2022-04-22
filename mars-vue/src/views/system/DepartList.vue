<template xmlns:background-color="http://www.w3.org/1999/xhtml">
  <a-row :gutter="10">
    <a-col :md="12" :sm="24">
      <a-card :bordered="false" class="tree-slit">

        <!-- 按钮操作区域 -->
        <a-row style="margin-left: 14px">
          <a-button @click="handleAdd(1)" v-has="'adDepartment:add'" type="primary">添加部门</a-button>
          <a-button @click="handleAdd(2)" v-has="'adDepartment:addNext'"  type="primary">添加下级</a-button>

          <!-- <a-button type="primary" icon="download" @click="handleExportXls('部门信息')">导出</a-button>
          <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
            @change="handleImportExcel">
            <a-button type="primary" icon="import">导入</a-button>
          </a-upload> -->
          <a-button title="删除多条数据" v-has="'adDepartment:del'" @click="batchDel" type="default">批量删除</a-button>
          <!--<a-button @click="refresh" type="default" icon="reload" :loading="loading">刷新</a-button>-->
        </a-row>
        <div style="background: #fff;padding-left:16px;height: 100%; margin-top: 5px">
          <a-alert type="info" :showIcon="true">
            <div slot="message">
              当前选择：<span v-if="this.currSelected.title">{{ getCurrSelectedTitle() }}</span>
              <a v-if="this.currSelected.title" style="margin-left: 10px" @click="onClearSelected">取消选择</a>
            </div>
          </a-alert>
          <a-input-search @search="onSearch" style="width:100%;margin-top: 10px" placeholder="请输入部门名称" />
          <!-- 树-->
          <a-col :md="10" :sm="24">
            <template>
              <a-dropdown :trigger="[this.dropTrigger]" @visibleChange="dropStatus">
                <span style="user-select: none">
                  <a-tree checkable multiple @select="onSelect" @check="onCheck" @rightClick="rightHandle"
                    :selectedKeys="selectedKeys" :checkedKeys="checkedKeys" :treeData="departTree" :checkStrictly="checkStrictly"
                    :loadData="onLoadData" :expandedKeys="iExpandedKeys" :autoExpandParent="autoExpandParent" @expand="onExpand" />
                </span>
                <!--新增右键点击事件,和增加添加和删除功能-->
                <a-menu slot="overlay">
                  <a-menu-item @click="handleAdd(3)" v-has="'adDepartment:addNext'" key="1">新增</a-menu-item>
                  <a-menu-item @click="handleDelete" v-has="'adDepartment:del'" key="2">删除</a-menu-item>
                  <!-- <a-menu-item @click="closeDrop" key="3">取消</a-menu-item> -->
                </a-menu>
              </a-dropdown>
            </template>
          </a-col>
        </div>
      </a-card>
      <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>
      <div class="drawer-bootom-button">
        <a-dropdown :trigger="['click']" placement="topCenter">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="switchCheckStrictly(1)">父子关联</a-menu-item>
            <a-menu-item key="2" @click="switchCheckStrictly(2)">取消关联</a-menu-item>
            <!-- <a-menu-item key="3" @click="checkALL">全部勾选</a-menu-item>
            <a-menu-item key="4" @click="cancelCheckALL">取消全选</a-menu-item> -->
            <a-menu-item key="5" @click="expandAll">展开所有</a-menu-item>
            <a-menu-item key="6" @click="closeAll">合并所有</a-menu-item>
          </a-menu>
          <a-button>
            树操作
            <a-icon type="up" />
          </a-button>
        </a-dropdown>
      </div>
      <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>
    </a-col>
    <a-col :md="12" :sm="24">
      <div style="background: #fff;padding-left:16px;height: 100%;">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="基本信息" key="1">
            <a-card :bordered="false">
              <a-form :form="form">
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构名称">
                  <a-input placeholder="请输入机构/部门名称" v-decorator="['departName', validatorRules.departName ]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="地区">
                  <a-select  allowClear v-decorator="['provinceCode', validatorRules.provinceAbbreviation ]">
                    <a-select-option v-for="items in regionList" :key="items.provinceAbbreviation" :value="items.provinceAbbreviation">
                      {{items.provinceAbbreviation}}
                    </a-select-option>
                  </a-select>
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级部门">
                  <a-input placeholder="无" v-model="model.parentName" :disabled="disable" />
                  <!-- <a-tree-select style="width:100%" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}" :treeData="treeData"
                    :disabled="disable" v-model="model.parentId" placeholder="无">
                  </a-tree-select> -->
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构编码">
                  <a-input disabled placeholder="请输入机构编码" v-decorator="['orgCode', validatorRules.orgCode ]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开票网点编码">
                  <a-input placeholder="请输入开票网点编码" v-decorator="['kpwdbm', validatorRules.kpwdbm]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构类型">
                  <template v-if="orgCategoryDisabled">
                    <a-radio-group v-decorator="['orgCategory',validatorRules.orgCategory]" placeholder="请选择机构类型">
                      <a-radio value="1">
                        公司
                      </a-radio>
                    </a-radio-group>
                  </template>
                  <template v-else>
                    <a-radio-group v-decorator="['orgCategory',validatorRules.orgCategory]" placeholder="请选择机构类型">
                      <a-radio value="2">
                        部门
                      </a-radio>
                      <a-radio value="3">
                        岗位
                      </a-radio>
                    </a-radio-group>
                  </template>
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
                  <a-input-number v-decorator="[ 'departOrder',{'initialValue':0}]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机号">
                  <a-input placeholder="请输入手机号" v-decorator="['mobile', { rules: [{pattern:/^1(3|4|5|6|7|8|9)\d{9}$/g,message: '只能输入正确的手机号'}],'initialValue':''}]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="地址">
                  <a-input placeholder="请输入地址" v-decorator="['address', {'initialValue':''}]" />
                </a-form-item>
                <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
                  <a-textarea placeholder="请输入备注" v-decorator="['memo', {'initialValue':''}]" />
                </a-form-item>
              </a-form>
              <div class="anty-form-btn">
                <!-- <a-button @click="emptyCurrForm" type="default" htmlType="button" icon="sync">重置</a-button> -->
                <a-button @click="submitCurrForm" type="primary" htmlType="button" icon="form">保存</a-button>
              </div>
            </a-card>
            <!-- <a-card v-else>
              <a-empty>
                <span slot="description"> 请先选择一个部门! </span>
              </a-empty>
            </a-card> -->
          </a-tab-pane>
          <!-- <a-tab-pane tab="" key="2" > -->
          <!-- <depart-auth-modal ref="departAuth" /> -->
          <!-- </a-tab-pane> -->
        </a-tabs>
      </div>
    </a-col>
    <depart-modal ref="departModal" @ok="loadTree"></depart-modal>
  </a-row>
</template>
<script>
  import DepartModal from './modules/DepartModal'
  import pick from 'lodash.pick'
  import {
    queryDepartTreeList,
    searchByKeywords,
    deleteByDepartId
  } from '@/api/api'
  import {
    httpAction,
    deleteAction,
    getAction
  } from '@/api/manage'
  import {
    JeecgListMixin
  } from '@/mixins/JeecgListMixin'
  import DepartAuthModal from './modules/DepartAuthModal'
  import {
    tree
  } from '@/components/tree/tree'
  // 表头
  const columns = [{
      title: '机构名称',
      dataIndex: 'departName'
    },
    {
      title: '机构类型',
      align: 'center',
      dataIndex: 'orgType'
    },
    {
      title: '机构编码',
      dataIndex: 'orgCode',
    },
    {
      title: '手机号',
      dataIndex: 'mobile'
    },
    {
      title: '传真',
      dataIndex: 'fax'
    },
    {
      title: '地址',
      dataIndex: 'address'
    },
    {
      title: '排序',
      align: 'center',
      dataIndex: 'departOrder'
    },
    {
      title: '操作',
      align: 'center',
      dataIndex: 'action',
      scopedSlots: {
        customRender: 'action'
      }
    }
  ]
  export default {
    name: 'DepartList',
    mixins: [JeecgListMixin],
    components: {
      DepartAuthModal,
      DepartModal
    },
    data() {
      return {
        onseleList: [],
        verificationCode: 0,
        verificationMessage: '',
        // 选中
        onSeletRight: '',
        iExpandedKeys: [],
        loading: false,
        regionList: [],
        autoExpandParent: true,
        currFlowId: '',
        currFlowName: '',
        disable: true,
        treeData: [],
        visible: false,
        departTree: [],
        rightClickSelectedKey: '',
        rightClickSelectedOrgCode: '',
        hiding: true,
        model: {},
        dropTrigger: '',
        depart: {},
        columns: columns,
        disableSubmit: false,
        checkedKeys: [],
        selectedKeys: [],
        autoIncr: 1,
        currSelected: {},

        child: [],
        allTreeKeys: [],
        checkStrictly: true,
        form: this.$form.createForm(this),
        labelCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 5
          }
        },
        wrapperCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 16
          }
        },
        model: {
          parentName: ''
        },
        parentName: '',
        graphDatasource: {
          nodes: [],
          edges: []
        },
        validatorRules: {
          departName: {
            rules: [{
              required: true,
              message: '请输入机构/部门名称!'
            }]
          },
          provinceAbbreviation: {
            rules: []
          },
          orgCode: {
            rules: [{
              required: true,
              message: '请输入机构编码!'
            }]
          },
          orgCategory: {
            rules: [{
              required: true,
              message: '请输入机构类型!'
            }]
          },
          mobile: {
            rules: [{
              validator: this.validateMobile
            }]
          },
          kpwdbm: {
            rules: [{
                required: true,
                message: '请输入开票编码',
              },
              {
                pattern: /^[A-Za-z0-9]{0,32}$/g,
                message: '请控制在32个字符内'
              },
              {
                validator: this.handleConfirm,
              },
            ],
            validateTrigger: 'blur',
          },
        },
        url: {
          verification: "/sys/duplicate/check",
          delete: '/sys/sysDepart/delete',
          edit: '/sys/sysDepart/edit',
          deleteBatch: '/sys/sysDepart/deleteBatch',
          exportXlsUrl: "sys/sysDepart/exportXls",
          importExcelUrl: "sys/sysDepart/importExcel",
          rihgtList: "sys/sysDepart/queryById",
          rihgtListparentName: "/sys/sysDepart/queryParentInfoById",
        },
        orgCategoryDisabled: false,
      }
    },
    computed: {
      importExcelUrl: function() {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    mounted() {
      //this.regionName();
    },
    methods: {
      loadData() {
        this.refresh();
      },
      loadTree() {
        var that = this
        that.treeData = []
        that.departTree = []
        getAction("/sys/sysDepart/deptIdTree").then((res) => {
          if (res.success) {
            let objData = tree(res);
            // that.treeData = objData.data;
            that.child = objData.child;
            that.treeData = objData.data
            that.departTree = objData.data
            that.setThisExpandedKeys(objData.data)
            that.getAllKeys(objData.data);
            this.loading = false
          }
        })
      },
      onLoadData(treeNode) {
        return new Promise(resolve => {
          // console.log(treeNode)
          if (treeNode.dataRef.children) {
            resolve();
            return;
          } else {
            resolve();
          }
          for (var i = 0; i < this.child.length; i++) {
            if (treeNode.dataRef.key == this.child[i].key) {
              treeNode.dataRef.children = this.child[i].children;
              resolve();
              return;
            }
          }
        });
      },
      setThisExpandedKeys(node) {
        if (node.children && node.children.length > 0) {
          this.iExpandedKeys.push(node.key)
          for (let a = 0; a < node.children.length; a++) {
            this.setThisExpandedKeys(node.children[a])
          }
        }
      },
      refresh() {
        this.loading = true
        this.loadTree()
      },
      // 右键操作方法
      rightHandle(node) {
        console.log(node.node.dataRef)
        this.dropTrigger = 'contextmenu'
        this.onSeletRight = node.node.title
        this.rightClickSelectedKey = node.node.eventKey
        this.rightClickSelectedOrgCode = node.node.dataRef.orgCode
      },
      onExpand(expandedKeys) {
        console.log('onExpand', expandedKeys)
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.iExpandedKeys = expandedKeys
        this.autoExpandParent = false
      },
      backFlowList() {
        this.$router.back(-1)
      },
      // 右键点击下拉框改变事件
      dropStatus(visible) {
        if (visible == false) {
          this.dropTrigger = ''
        }
      },
      // 右键店家下拉关闭下拉框
      closeDrop() {
        this.dropTrigger = ''
      },
      addRootNode() {
        this.$refs.nodeModal.add(this.currFlowId, '')
      },
      batchDel: function() {
        console.log(this.checkedKeys)
        if (this.checkedKeys.length <= 0) {
          this.$message.warning('请选择一条记录！')
        } else {
          var ids = ''
          for (var a = 0; a < this.checkedKeys.length; a++) {
            ids += this.checkedKeys[a] + ','
          }
          var that = this
          this.$confirm({
            title: '确认删除',
            content: '确定要删除所选中的 ' + this.checkedKeys.length + ' 条数据，以及子节点数据吗?',
            onOk: function() {
              deleteAction(that.url.deleteBatch, {
                ids: ids
              }).then((res) => {
                if (res.success) {
                  that.$message.success(res.message)
                  that.loadTree()
                  that.onClearSelected()
                } else {
                  that.$message.warning(res.message)
                }
              })
            }
          })
        }
      },
      onSearch(value) {
        let that = this
        if (value) {
          getAction("/sys/sysDepart/deptIdTree", {
            name: value
          }).then((res) => {
            if (res.success) {
              that.departTree = []
              for (let i = 0; i < res.result.length; i++) {
                let temp = res.result[i]
                that.departTree.push(temp)
              }
            } else {
              that.$message.warning(res.message)
            }
          })
          // searchByKeywords({
          //   keyWord: value
          // }).then((res) => {
          //   if (res.success) {
          //     that.departTree = []
          //     for (let i = 0; i < res.result.length; i++) {
          //       let temp = res.result[i]
          //       that.departTree.push(temp)
          //     }
          //   } else {
          //     that.$message.warning(res.message)
          //   }
          // })
        } else {
          that.loadTree()
        }

      },
      nodeModalOk() {
        this.loadTree()
      },
      nodeModalClose() {},
      hide() {
        console.log(111)
        this.visible = false
      },
      onCheck(checkedKeys, info) {
        console.log('onCheck', checkedKeys, info)
        this.hiding = false
        //this.checkedKeys = checkedKeys.checked
        // <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>
        if (this.checkStrictly) {
          this.checkedKeys = checkedKeys.checked;
        } else {
          this.checkedKeys = checkedKeys
        }
        // <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>
      },
      onSelect(selectedKeys, e) {
        // console.log('selected', selectedKeys, e)
        // this.hiding = false
        // let record = e.node.dataRef
        // console.log('onSelect-record', record)
        // this.currSelected = Object.assign({}, record)
        // this.model = this.currSelected
        // this.selectedKeys = [record.key]
        // this.model.parentId = record.parentId
        // this.setValuesToForm(record)

        // console.log('selected', selectedKeys, e)
        this.hiding = false
        let record = e.node.dataRef
        console.log('onSelect-record', record)
        // this.$refs.departAuth.show(record.key);
        this.currSelected = Object.assign({}, record)
        // this.model = this.currSelected
        this.selectedKeys = [record.key]
        this.parentName = record.title;
        // console.log(this.parentName)
        // this.model.parentName = record.parentId
        getAction(this.url.rihgtList, {
          id: record.key
        }).then(
          (res) => {
            if (res.code == 200) {
              this.onseleList = res.result
              this.setValuesToForm(res.result);
            } else {
              this.$message.error(res.message);
            }
          }).catch(() => {
          // this.$message.error('服务器错误！');
        })
        // 上级部门名称
        getAction(this.url.rihgtListparentName, {
          id: record.key
        }).then(
          (res) => {
            if (res.code == 200) {
              if (res.result) {
                this.model.parentName = res.result.departName;
              } else {
                this.model.parentName = '暂无上级部门';
              }
            } else {
              this.$message.error(res.message);
            }
          }).catch(() => {

        })

      },
      // 触发onSelect事件时,为部门树右侧的form表单赋值
      setValuesToForm(record) {
        if (record.orgCategory == '1') {
          this.orgCategoryDisabled = true;
        } else {
          this.orgCategoryDisabled = false;
        }
        // let newRecord = {
        //   ...record,
        //   provinceAbbreviation: record.provinceCode
        // }
        this.$nextTick(() => {
          this.form.getFieldDecorator('fax', {
            initialValue: ''
          })
          this.form.setFieldsValue(pick(record, 'departName', 'kpwdbm', 'orgCategory', 'orgCode', 'departOrder',
            'mobile',
            'fax', 'address', 'memo', 'provinceCode'))
        })
      },
      getCurrSelectedTitle() {
        return !this.currSelected.title ? '' : this.currSelected.title
      },
      onClearSelected() {
        this.hiding = true
        this.checkedKeys = []
        this.currSelected = {}
        this.form.resetFields()
        this.selectedKeys = []
        // this.$refs.departAuth.departId = ''
      },
      handleNodeTypeChange(val) {
        this.currSelected.nodeType = val
      },
      notifyTriggerTypeChange(value) {
        this.currSelected.notifyTriggerType = value
      },
      receiptTriggerTypeChange(value) {
        this.currSelected.receiptTriggerType = value
      },
      submitCurrForm() {
        this.form.validateFields((err, values) => {
          if (!err) {
            if (!this.currSelected.key) {
              this.$message.warning('请点击选择要修改部门!')
              return
            }

            let formData = Object.assign(values)
            let formDatas = {
              ...values,
              id: this.currSelected.key
            }
            console.log('Received values of form: ', formData)
            httpAction(this.url.edit, formDatas, 'put').then((res) => {
              if (res.success) {
                this.$message.success('保存成功!')
                this.loadTree()
              } else {
                this.$message.error(res.message)
              }
            })
          }
        })
      },
      emptyCurrForm() {
        this.form.resetFields()
      },
      nodeSettingFormSubmit() {
        this.form.validateFields((err, values) => {
          if (!err) {
            console.log('Received values of form: ', values)
          }
        })
      },
      openSelect() {
        this.$refs.sysDirectiveModal.show()
      },
      handleAdd(num, val) {

        if (num == 1) {
          this.$refs.departModal.add()
          this.$refs.departModal.title = '新增'
        } else if (num == 2) {
          let key = this.currSelected.key
          if (!key) {
            this.$message.warning('请先点击选中上级部门！')
            return false
          }
          console.log(this.selectedKeys, this.parentName)
          this.$refs.departModal.add(this.selectedKeys, this.parentName)
          this.$refs.departModal.title = '新增'
        } else {
          // onSeletRight  _z

          this.$refs.departModal.add(this.rightClickSelectedKey, this.onSeletRight)
          this.$refs.departModal.title = '新增'
        }
      },
      handleDelete() {
        var that = this
        this.$confirm({
          title: '确认删除',
          content: '确定要删除此部门以及子节点数据吗?',
          onOk: function() {
            deleteByDepartId({
              id: that.rightClickSelectedKey
            }).then((resp) => {
              if (resp.success) {
                //删除成功后，去除已选中中的数据
                that.checkedKeys.splice(that.checkedKeys.findIndex(key => key === that.rightClickSelectedKey),
                  1);
                that.$message.success('删除成功!')
                that.loadTree()
                //删除后同步清空右侧基本信息内容
                let orgCode = that.form.getFieldValue("orgCode");
                if (orgCode && orgCode === that.rightClickSelectedOrgCode) {
                  that.onClearSelected()
                }
              } else {
                that.$message.warning('删除失败!')
              }
            })
          }
        })
      },
      selectDirectiveOk(record) {
        console.log('选中指令数据', record)
        this.nodeSettingForm.setFieldsValue({
          directiveCode: record.directiveCode
        })
        this.currSelected.sysCode = record.sysCode
      },
      getFlowGraphData(node) {
        this.graphDatasource.nodes.push({
          id: node.id,
          text: node.flowNodeName
        })
        if (node.children.length > 0) {
          for (let a = 0; a < node.children.length; a++) {
            let temp = node.children[a]
            this.graphDatasource.edges.push({
              source: node.id,
              target: temp.id
            })
            this.getFlowGraphData(temp)
          }
        }
      },
      // <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>
      expandAll() {
        this.iExpandedKeys = this.allTreeKeys
      },
      closeAll() {
        this.iExpandedKeys = []
      },
      checkALL() {
        this.checkStriccheckStrictlytly = false
        this.checkedKeys = this.allTreeKeys
      },
      cancelCheckALL() {
        //this.checkedKeys = this.defaultCheckedKeys
        this.checkedKeys = []
      },
      switchCheckStrictly(v) {
        if (v == 1) {
          this.checkStrictly = false
        } else if (v == 2) {
          this.checkStrictly = true
        }
      },
      getAllKeys(node) {
        // console.log('node',node);
        this.allTreeKeys.push(node.key)
        if (node.children && node.children.length > 0) {
          for (let a = 0; a < node.children.length; a++) {
            this.getAllKeys(node.children[a])
          }
        }
      },
      handleConfirm(rule, value, callback) {
        console.log(this.onseleList)
        if (value == '' || value == null) {
          callback();
          return;
        } else {
          getAction(this.url.verification +
            `?fieldName=${rule.field}&fieldVal=${value}&tableName=sys_depart&dataId=${this.onseleList.id}`).then((res) => {
            if (res.code == 500) {
              this.verificationCode = res.code;
              this.verificationMessage = res.message;
            } else {
              this.verificationCode = res.code;
              this.verificationMessage = res.message;
            }
          }).catch(() => {
            // this.$message.error('服务器错误！');
          })
          setTimeout(() => {
            if (this.verificationCode == 200) {
              callback();
            } else {
              callback(this.verificationMessage);
            }
          }, 300)
        }
      },
      // <!---- author:os_chengtgen -- date:20190827 --  for:切换父子勾选模式 =======------>

    },
    created() {
      this.currFlowId = this.$route.params.id
      this.currFlowName = this.$route.params.name
      // this.loadTree()
    },

  }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin: 15px;
  }

  .anty-form-btn {
    width: 100%;
    text-align: center;
  }

  .anty-form-btn button {
    margin: 0 5px;
  }

  .anty-node-layout .ant-layout-header {
    padding-right: 0
  }

  .header {
    padding: 0 8px;
  }

  .header button {
    margin: 0 3px
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }

  #app .desktop {
    height: auto !important;
  }

  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }

  .drawer-bootom-button {
    /*position: absolute;*/
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: left;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }

  .tree-slit {
    height: 600px;
    overflow: auto;
  }
</style>
