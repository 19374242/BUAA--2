<template>
  <div>

    <el-table
        :data="projects"
        style="margin: 0 auto;
        width: 75% "
        :row-class-name="tableRowClassName">
      <el-table-column
          prop="pjctName"
          label="项目名"
          width="500px">
      </el-table-column>

      <el-table-column >
        <template slot-scope="scope">
          <el-button type="primary"
                     size="small"
                     @click="recover(scope.$index)">
            恢复项目
          </el-button>

          <el-popconfirm
              style="margin-left: 5px"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗"
              @confirm="handleDel(scope.$index)"
          >
            <el-button type="danger" size="small" slot="reference">删除项目</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

  </div>

</template>
<script>

import axios from 'axios'

export default {
  data() {
    return {
      loginUser: '',
      isTeam: false,
      teamName: '',
      projects: [
        {
          pjctName: "年年有余"
        }
      ]
    }
  },
  methods: {

    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    recover(id){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/recoverProject?teamName="
          + tempthis.teamName
          + "&pjctName=" + tempthis.projects[id].pjctName)
          .then(function (JsonResponse) {
            console.log(JsonResponse);
            console.log(JsonResponse.data);
            if (JsonResponse.data === 0){
              tempthis.$message.success("成功恢复")
              tempthis.update()
            }
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    handleDel(id) {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/removeProject?teamName="
          + tempthis.teamName
          + "&pjctName=" + tempthis.projects[id].pjctName)
          .then(function (JsonResponse) {
            console.log(JsonResponse);
            console.log(JsonResponse.data);
            if (JsonResponse.data === 0)
              tempthis.$message.success("彻底删除")
            else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    update(){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/getDelProjects?teamName=" + tempthis.teamName)
          .then(function (JsonResponse) {
            console.log(JsonResponse);
            console.log(JsonResponse.data);
            if (JsonResponse.data.status === 0) {
              tempthis.num = JsonResponse.data.num;
              tempthis.projects = JsonResponse.data.projects;
            } else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },
  },
  created() {
    this.loginUser = this.$cookies.get('loginUser');
    this.teamName = this.$route.params.teamName;
    this.update();
  }
}
</script>

