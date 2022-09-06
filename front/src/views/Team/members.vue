<template>
    <el-card shadow="always"
             style="
             margin: 0 auto;
             width: 75%"
             :body-style="{ padding: '40px 20 20 0 ',backgroundColor: '#F7F7F7'  }" >

      <span>
        全部成员-{{MemNum}}
      </span>

      <el-button
          @click="breakTeam"
          style="
          position: relative;
          margin-left: 10px;
          float: right"
          type="danger">
        解散团队
      </el-button>
      <el-button
          style="
          position: relative;
          float: right"
          type="primary"
          @click="inviteVisible = true">
        邀请成员
      </el-button>
      <el-dialog :visible.sync="inviteVisible" title="邀请成员">
        <el-input placeholder="邀请邮箱" v-model="newEmail"/>
        <el-button
            @click="invite"
            style="margin: 10px auto; width: 80px"
            type="success">
          邀请
        </el-button>
      </el-dialog>

      <el-divider/>

      <el-table :data="mems" stripe style="width: 100%">
        <el-table-column prop="name" label="姓名" width="180"/>
        <el-table-column prop="nickname" label="昵称" width="180"/>
        <el-table-column prop="email" label="邮箱" width="180"/>
        <el-table-column label="身份" width="180">
          <template #default="scope">
            <span>
              {{char[mems[scope.$index].role]}}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="scope">

            <el-button v-if="mems[scope.$index].role === 2"
                       size="small"
                       @click="delAdmin(scope.$index)">
              撤销管理员
            </el-button>

            <el-button v-if="mems[scope.$index].role === 1"
                       size="small"
                       type="primary"
                       @click="addAdmin(scope.$index)">
              授权管理员
            </el-button>
            <el-button v-if="mems[scope.$index].role === 1"
                       size="small"
                       type="danger"
                       @click="remove(scope.$index)">
              移除成员
            </el-button>

          </template>
        </el-table-column>
      </el-table>


    </el-card>
</template>


<script>

import axios from 'axios'

export default {
  name: "Invite.vue",

  data(){
    return {
      loginUser: '',
      newEmail: '',
      teamName: 'TEST',
      flag: false,
      MemNum: 0,
      inviteVisible: false,
      char: [
          "",
          "普通成员",
          "管理员",
          "群主",
          "其他成员"
      ],
      mems: [
        {
          name: "THR",
          nickName: "CCDD",
          email: 'yes@120.com',
          role: '群主',
        }
      ]
    }
  },
  methods: {

    addAdmin: function (id){
      // http://localhost:9091/team/authorizeAdmin?teamName=gaoyuan&fromEmail=2222&toEmail=3
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/authorizeAdmin?teamName="
          + tempthis.teamName + "&fromEmail=" + tempthis.loginUser.email
          + "&toEmail=" + tempthis.mems[id].email)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0){
              tempthis.$message.success("授权成功");
              tempthis.update()
            }
            else if (JsonResponse.data === 1)
              tempthis.$message.error("没有权限")
            else if (JsonResponse.data === 2)
              tempthis.$message.error("该成员已经是管理员")
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    delAdmin: function (id){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/cancelAdmin?teamName="
          + tempthis.teamName + "&fromEmail=" + tempthis.loginUser.email
          + "&toEmail=" + tempthis.mems[id].email)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0){
              tempthis.$message.success("撤销成功");
              tempthis.update()
            }
            else if (JsonResponse.data === 1)
              tempthis.$message.error("没有权限")
            else if (JsonResponse.data === 2)
              tempthis.$message.error("该成员不是管理员")
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    breakTeam: function (){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/disbandTeam?teamName="
          + this.teamName
      + "&email=" + this.loginUser.email)
      .then(function (JsonResponse) {
        console.log(JsonResponse);

        if (JsonResponse.data === 0) {
          tempthis.$message.success("解散成功")
          tempthis.$router.back();
          // tempthis.$router.push({name: 'layout', params: {info: "my-" + tempthis.loginUser.email}})
        }else if (JsonResponse.data === 1)
          tempthis.$message.error("没有权限")
        else
          tempthis.$message.error("解散失败")
      })
      .catch(function (error) {
        console.log(error);
      })
    },

    invite: function (){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/sendInvitation?teamName="
          + tempthis.teamName + "&fromEmail=" + tempthis.loginUser.email
          + "&toEmail=" + tempthis.newEmail)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0) {
              tempthis.$message.success("邀请已发送")
              // tempthis.$router.push({name: 'layout', params: {info: "my-" + tempthis.loginUser.email}})
            }else if (JsonResponse.data === 1)
              tempthis.$message.error("没有权限")
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })

      tempthis.inviteVisible = false;
      this.newEmail = '';
    },

    remove:function (id){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/detachMember?teamName="
          + tempthis.teamName + "&fromEmail=" + tempthis.loginUser.email
          + "&toEmail=" + tempthis.mems[id].email)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0){
              tempthis.$message.success("移除成功");
              tempthis.update()
            }
            else if (JsonResponse.data === 1)
              tempthis.$message.error("没有权限")
            else if (JsonResponse.data === 2)
              tempthis.$message.error("该成员不可被移除")
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    update(){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/showMember?teamName="
          + tempthis.teamName)
          .then(function (JsonResponse) {
            console.log(JsonResponse.data);
            if (JsonResponse.data.status === 0) {
              tempthis.mems = JsonResponse.data.members;
              tempthis.MemNum = JsonResponse.data.memNum;
            } else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    }

  },

  created() {
    this.loginUser = this.$cookies.get('loginUser');
    this.teamName = this.$route.params.teamName;
    console.log((this.teamName === "tem"));
    // 所有成员接口
    // http://localhost:9091/team/showMember?teamName=
    this.update()

  }
}
</script>

<style scoped>

</style>