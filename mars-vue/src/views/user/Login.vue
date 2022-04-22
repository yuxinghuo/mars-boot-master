<template>
  <div class="main main-login">
    <a-form :form="form" class="user-layout-login" ref="formLogin" id="formLogin">
      <a-tabs :activeKey="customActiveKey" :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }" @change="handleTabClick">
        <a-tab-pane key="tab1" tab="账号登录">
          <a-form-item>
            <a-input class="user_name" size="large" tabindex="1" v-decorator="['username',{rules: validatorRules.username.rules}]" type="text"
              placeholder="请输入用户名">
              <a-icon slot="prefix" type="user" :style="{ color: '#61E7EF' }" />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input class="user_password" tabindex="2" v-decorator="['password',{ rules: validatorRules.password.rules}]" size="large" type="password"
              autocomplete="false" placeholder="请输入密码">
              <!-- '#61E7EF'  青色，本来颜色 ，'#3CA6E6'划过颜色 -->
              <a-icon slot="prefix" type="lock" :style="{ color: '#61E7EF' }" />
            </a-input>
          </a-form-item>

          <a-row :gutter="0">
            <a-col :span="16">
              <a-form-item >
                <a-input class="input_Yzm" tabindex="3" v-decorator="['inputCode',validatorRules.inputCode]" size="large" type="text" @change="inputCodeChange"
                  placeholder="请输入验证码">
                  <!-- <a-icon slot="prefix" type="smile" :style="{ color: '#61E7EF' }" /> -->
                  <!-- <a-icon slot="prefix" v-if=" inputCodeContent==verifiedCode " type="smile" :style="{ color: '#61E7EF' }" />
							    <a-icon slot="prefix" v-else type="frown" :style="{ color: '#61E7EF' }" /> -->
                  <a-icon slot="prefix" type="safety" :style="{ color: '#61E7EF' }" />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8" class="yzmpointer" style="text-align: right">
              <img v-if="requestCodeSuccess" style="margin-top: 2px;" :src="randCodeImage" @click="handleChangeCheckCode" />
              <img v-else style="margin-top: 2px;" src="../../assets/checkcode.png" @click="handleChangeCheckCode" />
            </a-col>
          </a-row>


        </a-tab-pane>

      </a-tabs>
      <a-form-item class="login_btns">
        <a-button size="large" type="primary" htmlType="submit" class="login-button sumit change" :loading="loginBtn"
          @click.stop.prevent="handleSubmit" :disabled="loginBtn">登录
        </a-button>
        <span class="login-button reload" @click.stop.prevent="handleReload">重 置</span>
      </a-form-item>
    </a-form>

    <two-step-captcha v-if="requiredTwoStepCaptcha" :visible="stepCaptchaVisible" @success="stepCaptchaSuccess" @cancel="stepCaptchaCancel"></two-step-captcha>

    <a-modal  title="登录部门选择" :width="450" :visible="departVisible" @cancel="departcancal" :maskClosable="false">

      <template slot="footer">
        <a-button  @click="departcancal" >取消</a-button>
        <a-button type="primary" @click="departOk">确认</a-button>
      </template>

      <a-form class="deparModal">
        <a-form-item :labelCol="{span:4}" :wrapperCol="{span:20}" :style="{marginBottom:'10px',marginLeft: '15%'}" :validate-status="validate_status">
          <a-tooltip placement="topLeft">
            <template slot="title">
              <span>您隶属于多部门，请选择登录部门</span>
            </template>
            <a-avatar style="backgroundColor: #1890FF" icon="gold" />
          </a-tooltip>
          <a-select @change="departChange" :class="{'valid-error':validate_status=='error'}" placeholder="请选择登录部门"
            style="margin-left:10px;width: 80%">
            <a-icon slot="suffixIcon" type="gold" />
            <a-select-option v-for="d in departList" :key="d.id" :value="d.orgCode">
              {{ d.departName }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>



    </a-modal>

  </div>
</template>

<script>
  //import md5 from "md5"
  import api from '@/api'
  import TwoStepCaptcha from '@/components/tools/TwoStepCaptcha'
  import {
    mapActions
  } from "vuex"
  import {
    timeFix
  } from "@/utils/util"
  import Vue from 'vue'
  import {
    ACCESS_TOKEN,
    ENCRYPTED_STRING
  } from "@/store/mutation-types"
  import {
    putAction,
    postAction,
    getAction
  } from '@/api/manage'
  import {
    encryption,
    getEncryptedString
  } from '@/utils/encryption/aesEncrypt'
  import store from '@/store/'
  import {
    USER_INFO
  } from "@/store/mutation-types"
  import {
    queryIdTree,
  } from '@/api/api';
  import {
    tree
  } from '@/components/tree/tree';
  export default {
    components: {
      TwoStepCaptcha
    },
    data() {
      return {
        customActiveKey: "tab1",
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        requiredTwoStepCaptcha: false,
        stepCaptchaVisible: false,
        form: this.$form.createForm(this),
        encryptedString: {
          key: "",
          iv: "",
        },
        state: {
          time: 60,
          smsSendBtn: false,
        },
        validatorRules: {
          username: {
            rules: [{
              required: true,
              message: '请输入用户名!',
            }, {
              validator: this.handleUsernameOrEmail
            }]
            
          },
          password: {
            rules: [{
              required: true,
              message: '请输入密码!',
              validator: 'click'
            }]
          },
          mobile: {
            rules: [{
              validator: this.validateMobile
            }]
          },
          captcha: {
            rule: [{
              required: true,
              message: '请输入验证码!'
            }]
          },
          inputCode: {
            rules: [{
              required: true,
              message: '请输入验证码!'
            }]
          }
        },
        verifiedCode: "",
        inputCodeContent: "",
        inputCodeNull: true,

        departList: [],
        departVisible: false,
        departSelected: "",
        currentUsername: "",
        validate_status: "",
        currdatetime: '',
        randCodeImage: '',
        requestCodeSuccess: false
      }
    },
    created() {
      this.currdatetime = new Date().getTime();
      Vue.ls.remove(ACCESS_TOKEN)
      this.getRouterData();
      this.handleChangeCheckCode();
      // update-begin- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
      //this.getEncrypte();
      // update-end- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
    },
    methods: {
      ...mapActions(["Login", "Logout", "PhoneLogin", "ThirdLogin"]),

      // handler
      handleUsernameOrEmail(rule, value, callback) {
        const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (regex.test(value)) {
          this.loginType = 0
        } else {
          this.loginType = 1
        }
        callback()
      },
      handleTabClick(key) {
        this.customActiveKey = key
        // this.form.resetFields()
      },
      handleSubmit() {
        let that = this
        let loginParams = {};
        that.loginBtn = true;
        // 使用账户密码登录
        if (that.customActiveKey === 'tab1') {
          that.form.validateFields(['username', 'password', 'inputCode', 'rememberMe'], {
            force: true
          }, (err, values) => {
            if (!err) {
              loginParams.username = values.username
              // update-begin- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
              //loginParams.password = md5(values.password)
              //loginParams.password = encryption(values.password,that.encryptedString.key,that.encryptedString.iv)
              loginParams.password = values.password
              loginParams.remember_me = values.rememberMe
              // update-begin- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
              loginParams.captcha = that.inputCodeContent
              loginParams.checkKey = that.currdatetime
              console.log("登录参数", loginParams)
              that.Login(loginParams).then((res) => {
                this.departConfirm(res);
                this.globalNameInstitution();
              }).catch((err) => {
                that.requestFailed(err);
                this.form.resetFields('inputCode');
                this.handleChangeCheckCode();
              });
            } else {
              that.loginBtn = false;
            }
          })
          // 使用手机号登录
        } else {
          that.form.validateFields(['mobile', 'captcha', 'rememberMe'], {
            force: true
          }, (err, values) => {
            if (!err) {
              loginParams.mobile = values.mobile
              loginParams.captcha = values.captcha
              loginParams.remember_me = values.rememberMe
              that.PhoneLogin(loginParams).then((res) => {
                // console.log(res.result);
                this.departConfirm(res); 
              }).catch((err) => {
                that.requestFailed(err);
              })

            }
          })
        }
      },
      // 全局机构名称下拉框数据
      globalNameInstitution(){
        queryIdTree().then((res) => {
          // console.log(res);
          if (res.code == 200) {
            // console.log(tree(res));
            // let res=tree(res);
            sessionStorage.setItem('queryIdTree',JSON.stringify(tree(res)));
            // this.objTreeData = tree(res);
          }
        })
      },
      getCaptcha(e) {
        e.preventDefault();
        let that = this;
        this.form.validateFields(['mobile'], {
          force: true
        }, (err, values) => {
          if (!values.mobile) {
            that.cmsFailed("请输入手机号");
          } else if (!err) {
            this.state.smsSendBtn = true;
            let interval = window.setInterval(() => {
              if (that.state.time-- <= 0) {
                that.state.time = 60;
                that.state.smsSendBtn = false;
                window.clearInterval(interval);
              }
            }, 1000);

            const hide = this.$message.loading('验证码发送中..', 0);
            let smsParams = {};
            smsParams.mobile = values.mobile;
            smsParams.smsmode = "0";
            postAction("/sys/sms", smsParams)
              .then(res => {
                if (!res.success) {
                  setTimeout(hide, 0);
                  this.cmsFailed(res.message);
                }
                console.log(res);
                setTimeout(hide, 500);
              })
              .catch(err => {
                setTimeout(hide, 1);
                clearInterval(interval);
                that.state.time = 60;
                that.state.smsSendBtn = false;
                this.requestFailed(err);
              });
          }
        });
      },
      stepCaptchaSuccess() {
        this.loginSuccess()
      },
      stepCaptchaCancel() {
        this.Logout().then(() => {
          this.loginBtn = false
          this.stepCaptchaVisible = false
        })
      },
      handleChangeCheckCode() {
        this.currdatetime = new Date().getTime();
        getAction(`/sys/randomImage/${this.currdatetime}`).then(res => {
          if (res.success) {
            this.randCodeImage = res.result
            this.requestCodeSuccess = true
          } else {
            this.$message.error(res.message)
            this.requestCodeSuccess = false
          }
        }).catch(() => {
          this.requestCodeSuccess = false
        })
      },
      loginSuccess() {
        // update-begin- author:sunjianlei --- date:20190812 --- for: 登录成功后不解除禁用按钮，防止多次点击
        // this.loginBtn = false
        // update-end- author:sunjianlei --- date:20190812 --- for: 登录成功后不解除禁用按钮，防止多次点击
        this.$router.push({
          path: "/dashboard/analysis"
        })
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`,
          icon: <a-icon type="check" style="color: #1890FF" />,
        });
      },
      cmsFailed(err) {
        this.$notification['error']({
          message: "登录失败",
          description: err,
          duration: 4,
        });
      },
      requestFailed(err) {
        this.$notification['error']({
          message: '登录失败',
          description: ((err.response || {}).data || {}).message || err.message || "请求出现错误，请稍后再试",
          duration: 4,
        });
        this.loginBtn = false;
      },
      validateMobile(rule, value, callback) {
        if (!value || new RegExp(/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/).test(value)) {
          callback();
        } else {
          callback("您的手机号码格式不正确!");
        }

      },
      // validateInputCode(rule, value, callback) {
      //   if (!value || this.verifiedCode == this.inputCodeContent) {
      //     callback();
      //   } else {
      //     callback("您输入的验证码不正确!");
      //   }
      // },
      generateCode(value) {
        console.log(value,'1111111')
        this.verifiedCode = value.toLowerCase()
      },
      inputCodeChange(e) {
        this.inputCodeContent = e.target.value
      },
      departConfirm(res) {
        if (res.success) {
			sessionStorage.setItem('departSelectedList',JSON.stringify(res.result.departs[0]));
          let multi_depart = res.result.multi_depart
          //0:无部门 1:一个部门 2:多个部门
          if (multi_depart == 0) {
            this.loginSuccess()
            this.$notification.warn({
              message: '提示',
              description: `您尚未归属部门,请确认账号信息`,
              duration: 3
            });
          } else if (multi_depart == 2) {
            this.departVisible = true
            this.currentUsername = this.form.getFieldValue("username")
            this.departList = res.result.departs
          } else {
            this.loginSuccess()
          }
        } else {
          this.requestFailed(res)
          this.Logout();
        }
      },
      departOk() {
        if (!this.departSelected) {
          this.validate_status = 'error'
          return false
        }
        let obj = {
          orgCode: this.departSelected,
          username: this.form.getFieldValue("username")
        }
		// console.log(this.departList)
		// return;
        this.departList.forEach((valueAll,index)=>{
          if(valueAll.orgCode==this.departSelected){
            sessionStorage.setItem('departSelectedList',JSON.stringify(valueAll));
            // console.log(valueAll);
          }
          
        })
        putAction("/sys/selectDepart", obj).then(res => {
          if (res.success) {
            const userInfo = res.result.userInfo;
            Vue.ls.set(USER_INFO, userInfo, 7 * 24 * 60 * 60 * 1000);
            store.commit('SET_INFO', userInfo);
            //console.log("---切换组织机构---userInfo-------",store.getters.userInfo.orgCode);
            this.departClear()
            this.loginSuccess()
          } else {
            this.requestFailed(res)
            this.Logout().then(() => {
              this.departClear()
            });
          }
        })
      },
      departcancal(){
        this.departVisible = false
        this.loginBtn = false
      },
      departClear() {
        this.departList = []
        this.departSelected = ""
        this.currentUsername = ""
        this.departVisible = false
        this.validate_status = ''
      },
      departChange(value) {
        this.validate_status = 'success'
        this.departSelected = value
      },
      getRouterData() {
        this.$nextTick(() => {
          if (this.$route.params.username) {
            this.form.setFieldsValue({
              'username': this.$route.params.username
            });
          }
        })
      },
      //获取密码加密规则
      getEncrypte() {
        var encryptedString = Vue.ls.get(ENCRYPTED_STRING);
        if (encryptedString == null) {
          getEncryptedString().then((data) => {
            this.encryptedString = data
          });
        } else {
          this.encryptedString = encryptedString;
        }
      },
      handleReload() {
        this.form.resetFields();
      }
    }
  }
</script>

<style lang="less" scoped>
  // .deparModal{
  //   display: flex;
  //   justify-content: center;
  // }
  .yzmpointer{
    cursor:pointer
  }

  .user-layout-login {
    margin:  0 85px;
    // margin-left: 85px;
    width: 370px;

    /deep/  .ant-tabs-bar{
    margin-bottom: 24px;
    }

    /deep/ .ant-tabs-nav-container{
    line-height: 1.2;
    margin-top: 52px;
    font-size: 18px;
    } 
    /deep/ .ant-tabs-tab-active{
    // line-height: 1;
    font-family: 'MicrosoftYaHei Bold';
     font-size: 18px;
    color: #FF9C01;
    font-weight: bold;
    }
    /deep/.ant-form-item-control{
      height: 42px;
    }
    /deep/ .ant-form-item{
      margin-bottom: 21px;
    }
    /deep/ .login_btns{
      margin-bottom: 29px;
    }
    /deep/ .ant-form-explain{
      color: #FF0000;
      font-family: 'MicrosoftYaHei Bold';
      padding-top: 5px;
      font-size: 12px;
      line-height: 1;
    }
    /deep/ .has-error .ant-input:hover{
      border-color: #FF0000 !important;
    }
    .user_name{
      font-size: 14px;
      font-family:'MicrosoftYaHei Bold' ;
      color: #595854;
    }
    .user_name:hover>.ant-input-prefix>.anticon-user{
      color: #3CA6E6 !important;
    }
    .user_password{
      font-size: 14px;
      font-family:'MicrosoftYaHei Bold' ;
      color: #c3c3c3;
    }
    .user_password:hover>.ant-input-prefix>.anticon-lock{
      color: #3CA6E6 !important;
    }
    .input_Yzm:hover>.ant-input-prefix>.anticon-safety{
      color: #3CA6E6 !important;
    }

     input::placeholder{
            color:  #898989;
            font-family: 'MicrosoftYaHei Bold';
            font-size: 16px;
        }
    .ant-col-16{
      width: 200px;
    }
    .reload{
      float: right;
      font-family: 'MicrosoftYaHei Bold';
      font-size: 14px;
      color: #2C7BFF;
      padding-right: 2px;
      cursor:pointer
    }
    
 
    label {
      font-size: 14px;
    }

    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }

    .forge-password {
      font-size: 14px;
    }

    button.login-button {
      padding: 0 15px;
      font-size: 16px;
      font-family: 'Microsoft YaHei';
      height: 42px;
      width: 368px;
      // border-radius: 20px;
    }

    button.sumit {
      background: #0080F1 ;
    }
    button.sumit:hover{
      background-color: #5CC4FF;
    }

    .user-login-other {
      text-align: left;
      margin-top: 24px;
      line-height: 22px;

      .item-icon {
        font-size: 24px;
        color: rgba(0, 0, 0, .2);
        margin-left: 16px;
        vertical-align: middle;
        cursor: pointer;
        transition: color .3s;

        &:hover {
          color: #1890ff;
        }
      }

      .register {
        float: right;
      }
    }
  }
</style>
<style>
  .valid-error .ant-select-selection__placeholder {
    color: #f5222d;
   
  }
  /* 登录页面样式 */
  .main-login {
    /* margin: 20px 0 0 20px; */
    width: 100%;
    height: 100%;
  }
  .main-login .ant-tabs-nav-scroll {
    overflow: hidden;
    white-space: nowrap;
    text-align: left;
    /* margin-left: 10px; */
    margin-left: 0px;
  }

  .main-login .ant-tabs-ink-bar {
    display: none !important;
  }

  /* .main-login .ant-tabs-nav .ant-tabs-tab-active {
    font-family: 'MicrosoftYaHei Bold';
     font-size: 18px;
    color: #FF9C01;
    font-weight: bold;
   
  } */

  .main-login .ant-tabs-nav .ant-tabs-tab {
    position: relative;
    display: inline-block;
    box-sizing: border-box;
    height: 100%;
    margin: 0 32px 0 0;
    padding: 0;
    text-decoration: none;
    cursor: pointer;
    transition: color 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  }

  .main-login .ant-input {
    /* border: 1px solid #1C7BDC; */
    /* border-radius: 20px; */
  }

  .main-login .anticon {
    /* color: #1C7BDC !important; */
  }
  .main {
    border-radius: 10px;
  }
</style>
