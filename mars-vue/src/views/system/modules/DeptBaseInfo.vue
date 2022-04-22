<template>
  <a-card :visible="visible">
    <a-form :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构名称">
        <a-input style="border:0px;" disabled placeholder="" v-decorator="['departName', {}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级部门">
          <a-input style="border:0px;" disabled placeholder="无" v-decorator="['parentId', {}]" />
        <!-- <a-tree-select disabled style="width:100%;border: 0px;border: none;outline:none;" :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
         v-model="model.parentId" placeholder="无">
        </a-tree-select> -->
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构编码">
        <a-input style="border:0px;" disabled placeholder="" v-decorator="['orgCode', {}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开票网点编码">
        <a-input style="border:0px;" disabled placeholder="请输入开票网点编码" v-decorator="['kpwdbm' ,{ rules: [{pattern:/^[A-Za-z0-9]{0,32}$/g,message: '请控制在32个字符内'}]}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构类型">
        <a-radio-group :disabled="true" v-decorator="['orgCategory',{}]" placeholder="请选择机构类型">
          <a-radio value="1">
            公司
          </a-radio>
          <a-radio value="2">
            部门
          </a-radio>
          <a-radio value="3">
            岗位
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
        <a-input-number disabled style="border:0px;" v-decorator="[ 'departOrder',{}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机号">
        <a-input style="border:0px;" disabled placeholder="" v-decorator="['mobile', {}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="地址">
        <a-input style="border:0px;" disabled placeholder="" v-decorator="['address', {}]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
        <a-textarea style="border:0px;" disabled placeholder="" v-decorator="['memo', {}]" />
      </a-form-item>
    </a-form>
  </a-card>
</template>
<script>
  import pick from 'lodash.pick'
  import {
    queryIdTree
  } from '@/api/api'
  import {
    getAction
  } from '@/api/manage';
  export default {
    name: 'DeptBaseInfo',
    components: {},
    data() {
      return {
        superDepart: '暂无上级部门',
        departTree: [],
        id: '',
        model: {},
        visible: false,
        disable: true,
        treeData: [],
        form: this.$form.createForm(this),
        labelCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 3
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
        url: {
          rightList: "/sys/sysDepart/queryById",
           rihgtListparentName: "/sys/sysDepart/queryParentInfoById",
        }
      }
    },
    created() {
    },
    methods: {
  
     open(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        // 上级部门
        getAction(this.url.rihgtListparentName, {
          id: record.key
        }).then(
          (res) => {
            console.log(res,'222222222')
            if (res.code == 200) {
              if (res.result) {
                this.form.setFieldsValue({
                  parentId: res.result.departName,
                })
              } else {
                 this.form.setFieldsValue({
                  parentId: '暂无上级部门',
                })
              }
            } else {
              this.$message.error(res.message);
            }
          }).catch(() => {

        })
         getAction(this.url.rightList, {
          id: record.key
        }).then((res) => {
          console.log(res,'11111111111111');
          if (res.code == 200) {
            let newRecord = {
              // ...record,
              ...res.result
            }
            console.log(newRecord)
            this.$nextTick(() => {
              // setTimeout(() => {
                this.form.setFieldsValue(pick(newRecord, 'orgCategory', 'departName',  'orgCode','kpwdbm',
                  'departOrder',
                  'mobile', 'address', 'memo'));
              // },200)
            });
          } else {
            this.$message.error(res.message)
          }
        })
        // console.log("record:");
        // console.log(record);

      },
      clearForm() {
        this.form.resetFields();
        this.treeData = [];
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
