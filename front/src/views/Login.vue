<template>
  <div class="building">
    <div class="container">
      <div class="form-box">

        <div class="register-box hidden">
          <h1>register</h1>
          <el-form
              label-position = top
              label-width="100px"
              style="max-width: 70%"
          >
            <el-form-item>
              <el-input type="text" placeholder="姓名" v-model="form.name"/>
            </el-form-item>
            <br>
            <el-form-item>
              <el-input type="nickname" placeholder="昵称" v-model="form.nickname"/>
            </el-form-item>
            <br>
            <el-form-item>
              <el-input type="password" placeholder="密码" v-model="form.password"/>
            </el-form-item>
            <br>
            <el-form-item>
              <el-input type="email" placeholder="邮箱" v-model="form.email">
                <template #append>
                  <el-button type="primary"
                             @click="sendCode(form.email)">
                    发送验证码
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
            <br>
            <el-form-item >
              <el-input v-model="form.checkout" placeholder="验证码" type="text" style="width:70%"/>
            </el-form-item>
            <br>
          </el-form>

          <button @click="toRegister()">确认注册</button>
        </div>

        <div class="forgetPassword_box hidden">
          <h1 style="color: #eefeed">forgetPassword</h1>

          <el-form
              label-position = top
              label-width="100px"
              style="max-width: 70%"
          >
            <el-form-item>
              <el-input type="email" placeholder="邮箱" id="em2" v-model="fgEmail">
                <template #append>
                  <el-button @click="sendCode(fgEmail)">
                    发送验证码
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
            <br>
            <el-form-item>
              <el-input type="password" placeholder="新密码" id="npw1" v-model="fgPwd"/>
            </el-form-item>
            <br>
            <el-form-item class="checkCodePart">
              <el-input type="checkCode" placeholder="验证码" id="ck2" v-model="fgCode"/>
            </el-form-item>
            <br>
            <el-button @click="toForget()">
              修改密码
            </el-button>
          </el-form>
        </div>

        <div class="login-box">
          <h1>login</h1>
          <el-form
              label-position = top
              label-width="100px"
              style="max-width: 70%"
          >
            <el-form-item>
              <el-input type="text" placeholder="邮箱" v-model="loginEmail"/>
            </el-form-item>
            <br>
            <el-form-item>
              <el-input type="password" placeholder="密码" v-model="loginPwd"/>
            </el-form-item>
            <br>
            <el-button @click="toLogin">登录</el-button>
          </el-form>
        </div>

      </div>
      <div class="con-box left">
        <h2>欢迎来到<span>网页</span></h2>
        <img src="../assets/image/wolfquin.jpg" alt="">
        <p>
          已有账号
          <button id="login">去登录</button>
        </p>
      </div>

      <div class="con-box right">
        <h2>欢迎来到<span>网页</span></h2>
        <img src="../assets/image/ghostquin.jpg" alt="">
        <span>没有账号？
            <button id="register">去注册</button>
      </span>
        <br>
        <button id="forgetPassword">忘记密码</button>
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "Login",
  data() {
    return {
      loginEmail: '',
      loginPwd: '',
      form: {},
      fgEmail:'',
      fgPwd:'',
      fgCode:'',
    }
  },
  mounted() {
    let _this = this
    _this.initPage()
  },
  methods: {
    sendCode: function(email){
      const tempthis = this;
      axios.post("http://47.97.158.6:9091/user/sendEmail?email="+email).then(function (JsonResponse){
        console.log(JsonResponse);
        if (JsonResponse.data.status === 0)
          tempthis.$message.success("发送成功");
        else
          tempthis.$message.success('发送成功');
      })
    },

    toLogin: function () {
      const tempthis = this;
      console.log("login is called: ");
      console.log(this.loginEmail);
      console.log(this.loginPwd);
      // tempthis.$router.push('/layout')

      axios.post("http://47.97.158.6:9091/user/login?email="+this.loginEmail+"&password="+this.loginPwd)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data.status === 0 ) {
              tempthis.$message.success("登录成功")
              tempthis.$cookies.set("loginUser", {
                email: tempthis.loginEmail,
                name: JsonResponse.data.name,
                nickname: JsonResponse.data.nickname,
              })
              // Cookies.set('loginUser', {
              //     email: tempthis.loginEmail,
              //     name: JsonResponse.data.name,
              //     nickname: JsonResponse.data.nickname,});
              // tempthis.$router.push({name: 'layout', params:{info: "my-"+tempthis.loginEmail}})
              tempthis.$router.push({name: 'layout', params:{info: 'my-'+tempthis.loginEmail}});
              tempthis.$router.push({name: 'mains'});
            } else
              tempthis.$message.error("用户名不存在或密码错误")
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    toRegister: function () {
      const tempthis = this;
      axios.post("http://47.97.158.6:9091/user/register",this.form).then(function (JsonResponse) {
        console.log(JsonResponse)
        if (JsonResponse.data === 1 ){
          tempthis.$message.error("邮箱被占用")
        }else  if (JsonResponse.data === 2 ){
          tempthis.$message.error("验证码错误")
        }
        else tempthis.$message.success("注册成功")
      })
          .catch(function (error) {
            console.log(error);
          })
    },

    toForget: function () {
      const tempthis = this;
      axios.post("http://47.97.158.6:9091/user/changePassword?email="+this.fgEmail+"&newPassword="+this.fgPwd+"&checkCode="+this.fgCode).then(function (JsonResponse) {
        console.log(JsonResponse);
        if(JsonResponse.data === 0 ){
          tempthis.$message.success("修改密码成功")
        }else{
          tempthis.$message.error("验证码错误")
        }
      })
          .catch(function (error) {
            console.log(error);
          })
    },

    initPage() {
      let login = document.getElementById('login');
      let register = document.getElementById('register');
      let forgetPassword = document.getElementById('forgetPassword');
      let form_box = document.getElementsByClassName('form-box')[0];
      let register_box = document.getElementsByClassName('register-box')[0];
      let login_box = document.getElementsByClassName('login-box')[0];
      let forgetPassword_box = document.getElementsByClassName('forgetPassword_box')[0];
      register.addEventListener('click', () => {
        form_box.style.transform = 'translateX(80%)';
        login_box.classList.add('hidden');
        forgetPassword_box.classList.add('hidden');
        register_box.classList.remove('hidden');
      })
      login.addEventListener('click', () => {
        form_box.style.transform = 'translateX(0%)';
        register_box.classList.add('hidden');
        forgetPassword_box.classList.add('hidden');
        login_box.classList.remove('hidden');
      })
      forgetPassword.addEventListener('click', () => {
        form_box.style.transform = 'translateX(80%)';
        login_box.classList.add('hidden');
        register_box.classList.add('hidden');
        forgetPassword_box.classList.remove('hidden');
      })
    }
  },

  created() {
    let loginUser = this.$cookies.get('loginUser')
    if(! (loginUser === undefined || loginUser === null)) this.$router.push({
      name: 'layout',
      params: {info: "my-"+loginUser.email}
    });
  }
}
</script>


<style scoped>
* {
  /* 初始化 */
  margin: 0;
  padding: 0;
}

body {
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg, #f3e7e9, #e3eeff);
}

.building{
  background:url("../assets/background4.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

.container {
  background-color: #fff;
  width: 50%;
  height: 450px;
  border-radius: 5px;
  /* 阴影 */
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  /* 相对定位 */
  position: relative;
  top: 50px;
  margin: auto auto;
}

.form-box {
  /* 绝对定位 */
  position: absolute;
  top: -10%;
  left: 5%;
  background-color: #d3b7d8;
  width: 50%;
  height: 500px;
  border-radius: 5px;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
  /* 动画过渡 加速后减速 */
  transition: 0.5s ease-in-out;
}

.register-box, .login-box, .forgetPassword_box {
  /* 弹性布局 垂直排列 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.hidden {
  display: none;
  transition: 0.5s;
}

h1 {
  text-align: center;
  margin-bottom: 25px;
  /* 大写 */
  text-transform: uppercase;
  color: #fff;
  /* 字间距 */
  letter-spacing: 5px;
}

input {
  background-color: transparent;
  width: 70%;
  color: #fff;
  border: none;
  /* 下边框样式 */
  border-bottom: 1px solid rgba(255, 255, 255, 0.4);
  padding: 10px 0;
  text-indent: 10px;
  margin: 8px 0;
  font-size: 14px;
  letter-spacing: 2px;
}

input::placeholder {
  color: #fff;
}

input:focus {
  color: #a262ad;
  outline: none;
  border-bottom: 1px solid #a262ad80;
  transition: 0.5s;
}

input:focus::placeholder {
  opacity: 0;
}

.checkCodePart {
  width: 70%;
}

#ck1 {
  background-color: transparent;
  width: 45%;
  color: #fff;
  border: none;
  /* 下边框样式 */
  border-bottom: 1px solid rgba(255, 255, 255, 0.4);
  padding: 10px 0;
  text-indent: 10px;
  margin: 8px 0;
  font-size: 14px;
  float: left;
  bottom: 0px;
  display: block;
  letter-spacing: 2px;
}

#check1 {
  width: 45%;
  background-color: #f6f6f6;
  outline: none;
  border-radius: 8px;
  padding: 13px;
  color: #a262ad;
  letter-spacing: 2px;
  border: none;
  cursor: pointer;
  float: right;
}

.form-box button {
  /*width: 70%;*/
  /*margin-top: 35px;*/
  background-color: #f6f6f6;
  outline: none;
  border-radius: 8px;
  padding: 13px;
  color: #a262ad;
  letter-spacing: 2px;
  border: none;
  cursor: pointer;
}

.form-box button:hover {
  background-color: #a262ad;
  color: #f6f6f6;
  transition: background-color 0.5s ease;
}

.con-box {
  width: 50%;
  /* 弹性布局 垂直排列 居中 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  /* 绝对定位 居中 */
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.con-box.left {
  left: -2%;
}

.con-box.right {
  right: -2%;
}

.con-box h2 {
  color: #8e9aaf;
  font-size: 25px;
  font-weight: bold;
  letter-spacing: 3px;
  text-align: center;
  margin-bottom: 4px;
}

.con-box p {
  font-size: 12px;
  letter-spacing: 2px;
  color: #8e9aaf;
  text-align: center;
}

.con-box span {
  color: #d3b7d8;
}

.con-box img {
  width: 150px;
  height: 150px;
  opacity: 0.9;
  margin: 40px 0;
}

.con-box button {
  margin-top: 3%;
  background-color: #fff;
  color: #a262ad;
  border: 1px solid #d3b7d8;
  padding: 6px 10px;
  border-radius: 5px;
  letter-spacing: 1px;
  outline: none;
  cursor: pointer;
}

.con-box button:hover {
  background-color: #d3b7d8;
  color: #fff;
}
</style>