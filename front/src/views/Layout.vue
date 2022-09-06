<template>
  <div>
    <el-container style="height: 100%; border: 1px solid #eee;background-color: rgb(238, 241, 246);">
      <el-aside width="200px"
                :key="Dkey"
                style="background-color: rgb(48, 65, 86);">

        <div style="height: 60px;line-height: 60px;text-align: center">
          <b v-if="!(this.isTeam === true)" :key="isTeam" style="color: white">
            个人工作台
          </b>
          <b v-else :key="isTeam" style="color: white">
            [{{this.tableName}}]团队
          </b>
        </div>

        <el-menu :default-openeds="['1', '3']"
                   background-color="rgb(48, 65, 86)"
                   text-color="#fff"
                   active-text-color="#ffd04b" >
            <el-menu-item  v-if="isTeam === false" index="4"  @click="turnMains">
              <template slot="title">
                <i class="el-icon-message">
                </i>
                首页
              </template>
            </el-menu-item>
            <el-menu-item  v-else index="4"  @click="turnBack">
              <template slot="title">
                <i class="el-icon-message">
                </i>
                返回工作台
              </template>
            </el-menu-item>

            <el-menu-item v-if="isTeam === false" index="1" @click="$router.push({name: 'projects'})">
              <template slot="title" >
                <i class="el-icon-message"></i>
                我的项目
              </template>
            </el-menu-item>


            <el-menu-item v-else index="1" @click="$router.push({
          name: 'teamprojects',
          params:{teamName: tableName}
          })">
              <template slot="title" >
                <i class="el-icon-message"></i>
                我的项目(团队)
              </template>
            </el-menu-item>

            <el-menu-item   index="2"  @click="$router.push({name: 'teams'})">
              <template slot="title">
                <i class="el-icon-message">
                </i>
                我的团队
              </template>
            </el-menu-item>

            <el-submenu  v-if="isTeam === true" index="3"  >
              <template slot="title" >
                <i class="el-icon-message"/>
                文档中心
              </template>

              <el-menu-item
                  index="3-1"
                  @click="$router.push({
            name: 'TeamFiles',
            params: {
              teamName: tableName
            }
            })">
                <template #title>
                  团队文档
                </template>
              </el-menu-item>

              <el-submenu index="3-2" >
                <template #title>
                  <div >
                    项目文档
                  </div>
                </template>

                <el-submenu v-for="(project, index1) in tableData"
                            :key="project.pjctName"
                            :index="'3-'+project.pjctName">
                  <template #title>{{project.pjctName}}</template>
                  <el-menu-item v-for="(file, index2) in tableData[index1].files"
                                :index="'3-'+project.pjctName+'-'+file.fileName"
                                @click="$router.push({name: 'word' ,params:{curName:file.fileName,
                                                pjctName:project.pjctName,
                                                teamName:tableName}})">
                    <template #title>{{file.fileName}}</template>

                  </el-menu-item>
                </el-submenu>
              </el-submenu>
            </el-submenu>
          </el-menu>

      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <div class="demo-type">
          </div>
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="dialogFormVisible=true">个人信息</el-dropdown-item>
              <el-dropdown-item  @click.native="logout"> 退出登录</el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>
          <span>{{ loginUser.nickname }}</span>
        </el-header>
        <br>


        <el-dialog title="" :visible.sync="dialogFormVisible"
                   :close-on-click-modal='false'
                   :close-on-press-escape='false'
                   :modal-append-to-body="false"
                   width="30%">
          <el-descriptions title="个人信息" direction="vertical" :column="1" border>
            <el-descriptions-item label="昵称">{{ loginUser.nickname}}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ loginUser.email }}</el-descriptions-item>
            <el-descriptions-item label="真实姓名" :span="2">{{ loginUser.name }}</el-descriptions-item>

          </el-descriptions>
        </el-dialog>

        <el-main>
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>

  </div>
</template>


<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  height:100vh;
}
</style>

<script>
import axios from 'axios'

export default {
  methods: {
    turnMains(){
      this.$router.push({name: 'layout', params:{info: 'my-'+this.loginUser.email}});
      this.$router.push({name: 'mains'});
    },
    logout(){
      this.$cookies.remove('loginUser');
      this.$router.push({name: 'login'})
    },
    getFiles(){
      const tempthis = this;
      console.log("getFiles")
      for (let i = 0; i < this.tableData.length; i++) {
        let project = this.tableData[i];
        // console.log(project)
        axios.get("http://47.97.158.6:9091/document/showAll?pjctName="+project.pjctName+"&teamName="+tempthis.tableName)
            .then(function (JsonResponse){
              // console.log("add element");
              // console.log(JsonResponse)
              project['files'] = JsonResponse.data;
              tempthis.Dkey++;
            })
      }
      // console.log(this.tableData)
    },

    getProjects(){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/getProjects?teamName=" + tempthis.tableName)
          .then(function (JsonResponse) {
            // console.log("getProjects:");
            // console.log(JsonResponse);
            if (JsonResponse.data.status === 0) {
              // console.log(JsonResponse.data.projects);
              tempthis.tableData = JsonResponse.data.projects;
              console.log("getProjects");
              // console.log(tempthis.tableData)
              tempthis.getFiles();
              console.log("Dkey is ")
              tempthis.Dkey++;
              console.log(tempthis.Dkey);
            } else
              tempthis.$message.error("失败")
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    updated() {
      console.log("update in layout")
      const tempthis = this;
      this.loginUser = this.$cookies.get('loginUser')
      this.isTeam = !(this.$route.params.info.split('-')[0] === 'my');
      this.tableName = this.$route.params.info.split('-')[1];
      console.log("isTeam:");
      console.log(this.isTeam);
      tempthis.getProjects();
      tempthis.Dkey++;
      console.log("after getProjects")
    },
    turnBack(){
      this.$router.push({name: 'layout', params:{info: 'my-'+this.loginUser.email}});
      this.$router.push({name: 'projects'});
      this.updated()
    }
  },
  data() {
    return {
      loginUser: {},
      isTeam: false,
      tableName: 'TEST',
      dialogFormVisible:false,
      tableData: '',
      Dkey: 0,
    }
  },
  created() {
    this.updated();
    // console.log(this.tableData)
    if(this.loginUser === undefined || this.loginUser === null) this.$router.push('/login');
  },
  mounted() {
    let _this = this
    _this.$root.$on('update', function () {
      _this.updated()
    })
    if (this.isTeam){
      this.$router.push({
        name: 'teamprojects',
        params:{teamName: tableName}
      })
    }else{
      this.$router.push({
        name: 'mains',
      })
    }
  },

}
</script>

