<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    title="部门搜索"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
  >
    <!--部门树-->
    <template>
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级部门">
          <a-tree
            multiple
            treeCheckable="tree"
            checkable
            @expand="onExpand"
            :expandedKeys="expandedKeysss"
            :checkedKeys="checkedKeys"
            allowClear="true"
            :checkStrictly="true"
            @check="onCheck"
            :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
            :treeData="departTree"
            placeholder="请选择上级部门"
          ></a-tree>
           <!-- <a-tree
            multiple
            treeCheckable="tree"
            checkable
            @expand="onExpand"
            :expandedKeys="expandedKeysss"
            :checkedKeys="checkedKeys"
            allowClear="true"
            :checkStrictly="false"
            @check="onCheck"
            :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
            :treeData="departTree"
            placeholder="请选择上级部门"
          ></a-tree> -->
        </a-form-item>
      </a-form>
    </template>
  </a-modal>
</template>

<script>

import pick from 'lodash.pick'
import { getAction } from '@/api/manage'
import { queryIdTree , queryMyDepartTreeList,} from '@/api/api'
import userModal from './UserModal'
export default {
  name: 'DepartWindow',
  components: {
    userModal,
  },
  data() {
    return {
   

      remberKet: undefined,
      checkedKeys: [], // 存储选中的部门id
      expandedKeysss: [], //展开的节点
      userId: '', // 存储用户id
      model: {}, // 存储SysUserDepartsVO表
      userDepartModel: { userId: '', departIdList: [] }, // 存储用户id一对多部门信息的对象
      departList: [], // 存储部门信息
      modalWidth: 400,
      departTree: [],
      title: '操作',
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      headers: {},
      form: this.$form.createForm(this),
      url: {
        userId: '/sys/user/generateUserId', // 引入生成添加用户情况下的url
      },
    }
  },
  methods: {
    // zk 202-10-21    从部门进来的add
    add(checkedDepartKeys, userId, value) {
      console.log(value)
      this.checkedKeys = checkedDepartKeys
      this.userId = userId
  
      this.edit({})
    },
    edit(record) {
      this.departList = []

         this.queryDepartTree()
  
     
      this.form.resetFields()
      this.visible = true
      this.model = Object.assign({}, record)
      let filedsVal = pick(this.model, 'id', 'userId', 'departIdList')
      this.$nextTick(() => {
        this.form.setFieldsValue(filedsVal)
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.departList = []
      this.checkedKeys = []
    },
    handleSubmit() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err) => {
        if (!err) {
          that.confirmLoading = true
          if (this.userId == null) {
            getAction(this.url.userId)
              .then((res) => {
                if (res.success) {
                  let formData = { userId: res.result, departIdList: this.departList }
                  console.log(formData)
                  that.$emit('ok', formData)
                }
              })
              .finally(() => {
                that.departList = []
                that.confirmLoading = false
                that.close()
              })
          } else {
            let formData = { userId: this.userId, departIdList: this.departList }
            console.log(formData)
            that.departList = []
            that.$emit('ok', formData)
            that.confirmLoading = false
            that.close()
            that.checkedKeys = []
          }
        }
      })
    },
    handleCancel() {
      this.close()
    },

    // 选择部门时作用的API
    onCheck(checkedKeys, info) {
      // console.log(checkedKeys)
      // console.log(info)
      this.departList = []
      this.checkedKeys = checkedKeys.checked
      // this.checkedKeys = checkedKeys;
      let checkedNodes = info.checkedNodes
      for (let i = 0; i < checkedNodes.length; i++) {
        let de = checkedNodes[i].data.props
        let depart = { key: '', value: '', title: '' }
        depart.key = de.value
        depart.value = de.value
        depart.title = de.title
        this.departList.push(depart)
      }
      
      console.log('onCheck', checkedKeys, info)
    },
    //   onCheck(checkedKeys, info) {
    //   this.departList = []
    //   // this.checkedKeys = checkedKeys.checked
    //   this.checkedKeys = checkedKeys;
    //   let checkedNodes = info.checkedNodes
    //   for (let i = 0; i < checkedNodes.length; i++) {
    //     let de = checkedNodes[i].data.props
    //     let depart = { key: '', value: '', title: '' }
    //     depart.key = de.value
    //     depart.value = de.value
    //     depart.title = de.title
    //     this.departList.push(depart)
    //   }
    //   console.log(info.halfCheckedKeys)
    //   console.log(this.departTree)
    //   for(let i = 0;i <info.halfCheckedKeys.length;i++){
    //   this.remberKet = info.halfCheckedKeys[i]
    //   let a =  this.recursion(this.departTree)
    //   let b = {
    //     key: a.key,
    //     title: a.title,
    //     value: a.value
    //   }
    //   this.departList.push(b)
    //   console.log(a)
    //   console.log(a)
    //     // for(let j = 0 ;j<this.departTree.length;j++){
    //     //   if(info.halfCheckedKeys[i] === this.departTree[j].key){
    //     //     let obj = {
    //     //         key: this.departTree[j].key,
    //     //         title: this.departTree[j].title,
    //     //         value: this.departTree[j].value
    //     //     }
    //     //     info.halfCheckedKeys[i] = obj
    //     //   }
    //     //   else{
    //     //       if(this.departTree[j].children){
    //     //         this.recursion(this.departTree[j].children)
    //     //       }
    //     //   }
    //     // }
    //   }
    //   // if(info.halfCheckedKeys){
    //   //   this.departList = [...this.departList,...info.halfCheckedKeys]
    //   //   console.log(this.departList,'111111111')
    //   // }
    //   console.log('onCheck', checkedKeys, info)
    // },
    
    // recursion(arr){
    //   for(let i = 0 ; i< arr.length;i++){
    //       if(arr[i].key === this.remberKet){
    //         return arr[i]
    //       }else{
    //         if(arr[i].children){
    //           this.recursion(arr[i].children)
    //         }else{
    //           // 如果arr[i]项的children不存在
    //         }
    //       }
    //   }
    // },
    queryDepartTree() {
      queryIdTree().then((res) => {
        if (res.success) {
          this.departTree = res.result
          console.log(this.departTree)
          console.log(this.checkedKeys)
          if (this.checkedKeys && this.checkedKeys.length > 0) {
            let treekey = []
            let arr = res.result
            if (arr && arr.length > 0) {
              arr.forEach((item) => {
                treekey.push(item.key)
                /*     if(item.children&&item.children.length>0){
                    item.children.forEach(item1 => {
                      treekey.push(item1.key);
                    })
                  }*/
              })
              this.expandedKeysss = treekey
            }
          }
        }
      })
    },
 
    onExpand(expandedKeys) {
      this.expandedKeysss = expandedKeys
    },
    modalFormOk() {},
  },
}
</script>

<style scoped>
.ant-table-tbody .ant-table-row td {
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>