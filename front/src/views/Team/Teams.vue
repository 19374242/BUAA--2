<template>
  <div>
    <el-button style="position: relative;margin-left: 170px;margin-bottom: 50px" type="primary" @click="preAdd">
      创建团队
    </el-button>
    <el-dialog title="新建团队" :visible.sync="addTeamVision" width="30%">

      <el-form label-width="80px">
        <el-form-item label="团队名称">
          <el-input v-model="newTeamName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createTeam">确 定</el-button>
        <el-button @click="addTeamVision = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-table
        :data="Teams"
        style="
      margin: 0 auto;
      width: 75%"
    >
      <el-table-column
          prop="teamName"
          label="团队名称"
          width="400px">
      </el-table-column>

      <el-table-column >
        <template #default="scope">
          <el-popconfirm
              style="margin-left: 5px"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定要退出团队吗"
              @confirm="leaveTeam(scope.$index)"
          >
            <el-button type="danger"
                       style="
                   float: right"
                       slot="reference"
            >
              退出团队
            </el-button>
          </el-popconfirm>
          <el-button type="success"
                     style="
                   margin-right: 20px;
                   float: right"
                     @click="jumpTable(scope.$index)">
            进入团队
          </el-button>

          <el-button type="primary"
                     style="
                   margin-right: 20px;
                   float: right"
                     @click="jumpMem(scope.$index)">
            查看成员
          </el-button>

        </template>
      </el-table-column>
    </el-table>

  </div>

</template>

<script>

import axios from 'axios'

export default {
  name: 'teamList',

  data() {
    return {
      loginUser: {},
      addTeamVision: false,
      newTeamName: '',
      teamNum: '',
      Teams: [
      ]
    }
  },
  methods: {
    leaveTeam: function (id) {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/leaveTeam?teamName=" + this.Teams[id].teamName + "&email=" + this.loginUser.email)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0) {
              tempthis.$message.success("退出成功")
              location.reload()
            }
            else if (JsonResponse.data === 1)
              tempthis.$message.error("退出失败，群主无法退出")
            else
              tempthis.$message.error("退出失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    jumpTable: function (id) {
      console.log(id)
      this.$router.push("/layout/team-"+this.Teams[id].teamName+"/teamprojects/"+this.Teams[id].teamName);
      this.$root.$emit('update');
    },

    jumpMem: function (id) {
      console.log(id)
      this.$router.push({name: 'members', params: {teamName: this.Teams[id].teamName}});
    },

    preAdd: function () {
      this.addTeamVision = true;
      this.newTeamName = '';
    },

    createTeam: function () {
      const tempthis = this;
      console.log("login is called: ");
      console.log(this.loginUser.email);
      console.log(this.newTeamName);

      axios.get("http://47.97.158.6:9091/team/createTeam?teamName=" + this.newTeamName
          + "&email=" + this.loginUser.email)
          .then(function (JsonResponse) {
            console.log(JsonResponse);

            if (JsonResponse.data === 0) {
              tempthis.$message.success("创建成功")
              tempthis.$router.push({name: 'layout', params: {info: "team-" + tempthis.newTeamName}})
              location.reload();
            } else
              tempthis.$message.error("创建失败")
          })
          .catch(function (error) {
            console.log(error);
          })

      this.addTeamVision = false;
    }
  },
  created() {
    this.$root.$emit('update')
    this.loginUser = this.$cookies.get('loginUser');
    // 需要返回所有团队的接口
    // http://localhost:9091/team/showTeam?email=2

    const tempthis = this;
    axios.get("http://47.97.158.6:9091/team/showTeam?email=" + this.loginUser.email)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          console.log(JsonResponse.data)
          if (JsonResponse.data.status === 0) {
            tempthis.teamNum = JsonResponse.data.len;
            tempthis.Teams = JsonResponse.data.Teams;
          } else
            tempthis.$message.error("失败")
        })
        .catch(function (error) {
          console.log(error);
        })
  }
}

</script>

<style>

</style>