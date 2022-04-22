<template>
  <a-modal
    :title="title"
    :width="800"
    :ok="false"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="取消"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="机构名称"
          :hidden="false"
          hasFeedback
        >
          <a-input
            id="departName"
            placeholder="请输入机构/部门名称"
            v-decorator="['departName', validatorRules.departName ]"
          />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开票网点编码"
         
        >
        <!--  v-has="'dept:kpwdbm'" -->
          <a-input placeholder="请输入开票网点编码" v-decorator="['kpwdbm', validatorRules.kpwdbm]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地区"
          :hidden="false"
          hasFeedback
        >
          <a-select v-decorator="['provinceCode', validatorRules.provinceAbbreviation ]">
            <a-select-option
              v-for="items in regionList"
              :key="items.provinceCode"
              :value="items.provinceAbbreviation"
            >{{items.provinceAbbreviation}}</a-select-option>
          </a-select>
          <!-- <a-input placeholder="请输入地区" v-decorator="['provinceAbbreviation', validatorRules.provinceAbbreviation ]" /> -->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          :hidden="seen"
          label="上级部门"
          hasFeedback
        >
          <a-input placeholder="无" v-model="parentName" :disabled="condition" />
          <!-- <a-tree-select
          style="width:100%"
          :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
          :treeData="departTree"
          v-model="model.parentId"
          placeholder="请选择上级部门"
          :disabled="condition">
          </a-tree-select>-->
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="机构类型">
          <template v-if="seen">
            <a-radio-group
              v-decorator="['orgCategory',validatorRules.orgCategory]"
              placeholder="请选择机构类型"
            >
              <a-radio value="1">公司</a-radio>
            </a-radio-group>
          </template>
          <template v-else>
            <a-radio-group
              v-decorator="['orgCategory',validatorRules.orgCategory]"
              placeholder="请选择机构类型"
            >
              <a-radio value="2">部门</a-radio>
              <a-radio value="3">岗位</a-radio>
            </a-radio-group>
          </template>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机号">
          <a-input placeholder="请输入手机号" v-decorator="['mobile',validatorRules.mobile]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="传真">
          <a-input placeholder="请输入传真" v-decorator="['fax', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="地址">
          <a-input placeholder="请输入地址" v-decorator="['address', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="排序">
          <a-input-number v-decorator="[ 'departOrder',{'initialValue':0}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
          <a-textarea placeholder="请输入备注" v-decorator="['memo', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { queryIdTree } from '@/api/api'
import pick from 'lodash.pick'
import ATextarea from 'ant-design-vue/es/input/TextArea'
export default {
  name: 'SysDepartModal',
  inject: ['reload'],
  components: {
    ATextarea,
  },
  data() {
    return {
      verificationCode: 0,
      verificationMessage: '',
      departTree: [],
      regionList: [],
      orgTypeData: [],
      parentName: '',
      phoneWarning: '',
      departName: '',
      title: '操作',
      seen: false,
      visible: false,
      condition: true,
      disableSubmit: false,
      model: {},
      menuhidden: false,
      menuusing: true,
      labelCol: {
        xs: {
          span: 24,
        },
        sm: {
          span: 5,
        },
      },
      wrapperCol: {
        xs: {
          span: 24,
        },
        sm: {
          span: 16,
        },
      },

      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {
        departName: {
          rules: [
            {
              required: true,
              message: '请输入机构/部门名称!',
            },
            
          ],
        },
        provinceAbbreviation: {
          rules: [],
        },
        orgCode: {
          rules: [
            {
              required: true,
              message: '请输入机构编码!',
            },
          ],
        },
        kpwdbm: {
          rules: [
            {
              required: true,
              message: '请输入开票编码',
            },
              {pattern:/^[A-Za-z0-9]{0,32}$/g,message: '请控制在32个字符内'},
            {
              validator: this.handleConfirm,
            },
          ],
          validateTrigger: 'blur',
        },
        mobile: {
          rules: [
            {
              validator: this.validateMobile,
            },
          ],
        },
      },
      url: {
        add: '/sys/sysDepart/add',
        verification: "/sys/duplicate/check",
      },
      dictDisabled: true,
    }
  },
  created() {},
  mounted() {
    //this.getRegionList()
  },
  methods: {
    loadTreeData() {
      var that = this
      queryIdTree().then((res) => {
        if (res.success) {
          that.departTree = []
          for (let i = 0; i < res.result.length; i++) {
            let temp = res.result[i]
            that.departTree.push(temp)
          }
        }
      })
    },
    add(depart, parentName) {
      if (depart) {
        this.seen = false
        this.dictDisabled = false
      } else {
        this.seen = true
        this.dictDisabled = true
      }
      this.edit(depart, parentName)
    },
    edit(record, parentName) {
      this.form.resetFields()
      this.model = Object.assign({}, {})
      this.visible = true
      this.parentName = parentName
      // this.loadTreeData();
      this.model.parentId = record != null ? record.toString() : null
      if (this.seen) {
        this.model.orgCategory = '1'
      } else {
        this.model.orgCategory = '2'
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'orgCategory',
            'departName',
            'departNameEn',
            'departNameAbbr',
            'departOrder',
            'description',
            'orgType',
            'orgCode',
            'mobile',
            'fax',
            'address',
            'memo',
            'status',
            'delFlag'
          )
        )
      })
    },
    close() {
      this.$emit('close')
      this.disableSubmit = false
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          //时间格式化
          console.log(formData)
          httpAction(this.url.add, formData, 'post')
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.reload()
                // that.loadTreeData();
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    validateMobile(rule, value, callback) {
      if (!value || new RegExp(/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/).test(value)) {
        callback()
      } else {
        callback('您的手机号码格式不正确!')
      }
    },
    handleConfirm(rule, value, callback) {
				if (value == '' || value == null) {
					callback();
					return;
				} else {
					getAction(this.url.verification + `?fieldName=${rule.field}&fieldVal=${value}&tableName=sys_depart`).then((res) => {
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
  },
}
</script>

<style scoped>
</style>
