<template>


  <div style="width: 400px;
              margin-left: auto;margin-right:auto;
              margin-top: 30px;
              text-align: center;">
    <el-card shadow="always" :body-style="{ padding: '40px 20 20 0 ',backgroundColor: '#F7F7F7'  }" >

      <el-divider/>
      <span>
        {{loginUser.name}}:是否接受团队[ {{teamName}} ]的邀请？
      </span>
      <el-divider/>

      <div style="width: 250px;
    height: 50px;
    text-align: center;
    margin: 0 auto">

        <el-button
            style="position: relative;"
            type="danger" @click="invite(1)">
          拒绝邀请
        </el-button>
        <el-button
            style="position: relative;"
            type="success" @click="invite(0)">
          接受邀请
        </el-button>

      </div>

    </el-card>
  </div>

</template>


<script>
// import {ElMessage} from "element-plus";

import axios from 'axios'

export default {
  name: "Invite.vue",

  data(){
    return {
      loginUser: '',
      teamName: 'TEST',
      flag: false,
    }
  },
  methods: {
    invite: function(flag) {
          const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/acceptInvitation?teamName="
          + tempthis.teamName + "&email=" + tempthis.loginUser.email
          + "&accept=" + flag)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0)
              tempthis.$message.success("成功")
            else
              tempthis.$message.error("失败")
            tempthis.$router.push({name: 'layout', params: {info: "my-" + tempthis.loginUser.email}})
          })
          .catch(function (error) {
            console.log(error);
          })
    },
  },

  created() {
    this.loginUser = this.$cookies.get('loginUser');
    if (this.loginUser === null || this.loginUser === undefined)
      this.$router.push({name: 'login'});
    this.teamName = this.$route.params.teamName;
    console.log(this.teamName)
  }
}
</script>

<style scoped>

</style>