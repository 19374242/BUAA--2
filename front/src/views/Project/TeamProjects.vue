<template>
  <div>
    <div>
      <el-button
        type="success"
        style="margin-right: 10px; position: relative;margin-left: 10px"
        @click="addVisible = true"
      >
        新增项目
      </el-button>
      <el-dropdown style="float: left" @command="handleCommand">
        <el-button style="float: left; margin-left: 170px"type="primary">排序方式</el-button>
        <el-dropdown-menu>
          <el-dropdown-item command="a">按时间正序</el-dropdown-item>
          <el-dropdown-item command="b">按时间倒序</el-dropdown-item>
          <el-dropdown-item command="c">按名称正序</el-dropdown-item>
          <el-dropdown-item command="d">按名称倒序</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input
        v-model="searchform.word"
        style="width: 30%; float: right;margin-right : 170px;margin-bottom: 50px"
        placeholder="请输入想搜索的项目名"
        clearable
        prefix-icon="el-icon-search"
      >
        <!-- <el-button slot="append" @click="searchVisible = true">搜索</el-button> -->
        <el-button slot="append" @click="startSearch">搜索</el-button>
      </el-input>
      <!-- <el-dialog title="搜索检测" :visible.sync="searchVisible" width="30%">
        <el-form label-width="80px">
          <el-form-item label="你搜索了:">
            <span>{{ input }}</span>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="startSearch">确 定</el-button>
          <el-button @click="searchVisible = false">取 消</el-button>
        </div>
      </el-dialog> -->

      <el-dialog title="输入信息" :visible.sync="addVisible" width="30%">
        <el-form label-width="80px">
          <el-form-item label="项目名称">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="save">确 定</el-button>
          <el-button @click="addVisible = false">取 消</el-button>
        </div>
      </el-dialog>

      <el-button style="position: relative" type="" @click="goToDel">
        回收站
      </el-button>
    </div>

    <el-table
      :data="projects"
      style="margin: 0 auto; width: 75%"
      :row-class-name="tableRowClassName"
    >
      <el-table-column prop="pjctName" label="项目名" width="300px">
      </el-table-column>

      <el-table-column>
        <template slot-scope="scope">
          <el-button
            type="primary"
            style="position: relative"
            @click="
              $router.push({
                name: 'files',
                params: {
                  teamName: teamName,
                  pjctName: projects[scope.$index].pjctName,
                },
              })
            "
          >
            查看项目
          </el-button>
          <a href="https://app.diagrams.net/">
            <el-button
              type="success"
              style="position: relative; margin-left: 7px"
              >绘制图表
            </el-button></a
          >

            <el-button
              type="warning"
              style="position: relative; margin-left: 7px"
              @click="$router.push('/mydesign')"
              >原型设计
            </el-button>
          <el-popconfirm
            style="margin-left: 5px"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗"
            @confirm="handleDel(scope.$index)"
          >
            <el-button type="danger" slot="reference">删除项目</el-button>
          </el-popconfirm>
          <!-- <el-popconfirm
            style="margin-left: 5px"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="请输入复制后的新名字"
            @confirm="copyProject(scope.$index)"
          >
          </el-popconfirm> -->
          <el-button
            style="position: relative; margin-left: 7px"
            type=""
            slot="reference"
            @click="preCopy(scope.$index)"
          >
            复制项目
          </el-button>
          <el-dialog
            title="输入复制后的新名字"
            :visible.sync="changeVisible"
            width="30%"
          >
            <el-form label-width="80px">
              <el-form-item label="副本名称">
                <el-input v-model="copyform.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="saveCopy(copydata)">确 定</el-button>
              <el-button @click="changeVisible = false">取 消</el-button>
            </div>
          </el-dialog>
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
      input: '',
      copydata: '',
      form: {
        "name": '',
      },
      copyform: {
        "name": '',
      },
      searchform: {
        "word": '',
      },
      changeVisible: false,
      addVisible: false,
      searchVisible: false,
      projects: [
        {
          pjctName: "年年有余"
        }
      ]
    }
  },
  methods: {
    jump(e) {
      window.location.href = e
    },
    handleCommand(command) {
      const tempthis = this;
      // http://47.97.158.6:9091/project/teamProjectSort?method=2&teamName=fish
      // http://47.97.158.6:9091/project/allProjectSort?method=2&email=2222
      if (command === 'a') {
        this.orderWay = '0';
      }
      else if (command === 'b') {
        this.orderWay = '1';
      }
      else if (command === 'c') {
        this.orderWay = '2';
      }
      else if (command === 'd') {
        this.orderWay = '3';
      }
      axios.get('http://47.97.158.6:9091/project/teamProjectSort?method=' + this.orderWay + "&teamName=" + this.teamName)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          console.log(JsonResponse.data);
          if (JsonResponse.data.status === 0) {
            tempthis.$message.success("成功排序");
            tempthis.projects=JsonResponse.data.projects;
          }
          else{
            tempthis.$message.error("排序失败");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    goToDel: function () {
      this.$router.push({
        name: 'dustbin',
        params: {
          teamName: this.teamName,
        }
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    startSearch() {
      const tempthis = this;
      // let params;
      // params = {
      //   searchInput: document.getElementById("input").value
      // }
      // localhost:9091/project/searchOnesProjects?word=复习&email=1
      axios.get('http://47.97.158.6:9091/project/searchTeamProjects?teamName=' + this.teamName + "&word=" + this.searchform.word)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          console.log(JsonResponse.data);
          if (JsonResponse.data.status === 0) {
            tempthis.$message.success("成功搜索到");
            tempthis.projects=JsonResponse.data.project;
          }
          else
            tempthis.$message.error("搜索失败");
        })
        .catch(function (error) {
          console.log(error);
        })


    },

    save() {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/createProject?teamName="
        + this.teamName +
        "&pjctName=" + this.form.name)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          if (JsonResponse.data === 0) {
            tempthis.$message.success("创建成功")
            tempthis.update();
          } else
            tempthis.$message.error("失败")
        })
        .catch(function (error) {
          console.log(error);
        })

      this.addVisible = false;
      this.form = {}
    },
    preCopy(id) {
      const tempthis = this;
      this.changeVisible = true;
      this.copydata = id;
    },
    saveCopy(id) {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/projectCopy?teamName="
        + tempthis.teamName +
        "&pjctName=" + tempthis.projects[id].pjctName + "&newName=" + tempthis.copyform.name)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          if (JsonResponse.data === 0) {
            tempthis.$message.success("复制成功")
            tempthis.update();
          } else
            tempthis.$message.error("失败")
        })
        .catch(function (error) {
          console.log(error);
        })

      this.changeVisible = false;
      this.form = {}
    },
    handleDel(id) {
      // fish fish_pjct
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/delProject?teamName="
        + tempthis.teamName
        + "&pjctName=" + tempthis.projects[id].pjctName)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          console.log(JsonResponse.data);
          if (JsonResponse.data === 0) {
            tempthis.$message.success("已放入回收站")
            tempthis.update()
          }
          else
            tempthis.$message.error("失败")
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    update() {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/getProjects?teamName=" + tempthis.teamName)
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
    this.$root.$emit('update')
    this.loginUser = this.$cookies.get('loginUser');
    this.teamName = this.$route.params.teamName;
    this.update();
  }
}
</script>

