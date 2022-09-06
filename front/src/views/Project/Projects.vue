<template>
  <div>
    <div class="upperpart">
      <el-dropdown style="float: left" @command="handleCommand">
        <el-button style="float: left;margin-left: 170px" type="primary">排序方式</el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">按时间正序</el-dropdown-item>
          <el-dropdown-item command="b">按时间倒序</el-dropdown-item>
          <el-dropdown-item command="c">按名称正序</el-dropdown-item>
          <el-dropdown-item command="d">按名称倒序</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input
        v-model="searchform.word"
        style="width: 30%; float: right;margin-right: 170px;margin-bottom: 50px"
        placeholder="请输入想搜索的项目名"
        clearable
        prefix-icon="el-icon-search"
      >
        <el-button slot="append" @click="startSearch">搜索</el-button>
        <!-- <el-button slot="append" @click="searchVisible = true">搜索</el-button> -->
      </el-input>
      <el-dialog title="搜索检测" :visible.sync="searchVisible" width="30%">
        <el-form label-width="80px">
          <el-form-item label="你搜索了:">
            <span>{{ searchform.word }}</span>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="startSearch">确 定</el-button>
          <el-button @click="searchVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <el-table
      :data="projects"
      style="margin: 0 auto; width: 75%"
      :row-class-name="tableRowClassName"
    >
      <el-table-column prop="teamName" label="团队名称" width="180">
      </el-table-column>
      <el-table-column prop="pjctName" label="项目" width="380">
      </el-table-column>

      <el-table-column>
        <template slot-scope="scope">
          <!--                数据传入-->

          <el-button
            style="position: relative"
            type="primary"
            @click="
              $router.push({
                name: 'files',
                params: {
                  teamName: projects[scope.$index].teamName,
                  pjctName: projects[scope.$index].pjctName,
                },
              })
            "
          >
            查看文档
          </el-button>
          <!--                popconfirm-->
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
            <el-button
              style="position: relative"
              type="danger"
              slot="reference"
            >
              删除项目
            </el-button>
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
            <el-button
              style="position: relative"
              type="primary"
              slot="reference"
            >
              复制项目
            </el-button>
          </el-popconfirm> -->
          <!-- <el-button
            style="position: relative"
            type="primary"
            slot="reference"
            @click="changeVisible = true"
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
              <el-button type="primary" @click="saveCopy">确 定</el-button>
              <el-button @click="changeVisible = false">取 消</el-button>
            </div>
          </el-dialog> -->
        </template>
      </el-table-column>
    </el-table>

    <!--          弹窗-->
    <el-dialog title="输入信息" :visible.sync="dialogFormVisible2" width="30%">
      <el-form label-width="80px">
        <el-form-item label="文档名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建者">
          <el-input v-model="form.user" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建日期">
          <el-input v-model="form.date" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="dialogFormVisible2 = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import axios from 'axios'

export default {
  methods: {
    goToDel() {

    },
    handleCommand(command) {
      const tempthis = this;
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
      axios.get('http://47.97.158.6:9091/project/allProjectSort?method=' + this.orderWay + "&email=" + this.loginUser.email)
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
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    handleAdd() {
      this.dialogFormVisible2 = true
      //表单置空
      this.form = {}
    },
    startSearch() {
      const tempthis = this;
      axios.get('http://47.97.158.6:9091/project/searchOnesProjects?word=' + this.searchform.word + "&email=" + this.loginUser.email)
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
      this.dialogFormVisible2 = true
    },
    handleDel(id) {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/project/delProject?teamName="
        + tempthis.projects[id].teamName
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
      axios.get("http://47.97.158.6:9091/project/oneForProjects?email=" + tempthis.loginUser.email)
        .then(function (JsonResponse) {
          console.log(JsonResponse);
          console.log(JsonResponse.data);
          if (JsonResponse.data.status === 0) {
            tempthis.projects = JsonResponse.data.project;
          } else
            tempthis.$message.error("失败")
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
  data() {
    return {
      loginUser: '',
      input: '',
      orderWay: '',
      isTeam: false,
      tableName: 'TEST',
      addVisible: false,
      changeVisible: false,
      searchVisible: false,
      form: {
        "name": '操作文档',
        "user": '王小虎',
        "date": '2000-12-18'
      },
      copyform: {
        "name": '操作文档',
        "user": '王小虎',
        "date": '2000-12-18'
      },
      searchform: {
        "word": '',
        "email": 'ybb@xxx.com'
      },
      dialogFormVisible: false,
      dialogFormVisible2: false,
      projects: [],
    }
  },
  created() {
    this.$root.$emit('update')
    this.loginUser = this.$cookies.get('loginUser');
    this.teamName = this.$route.params.teamName;
    this.update();
  }
}
</script>

<style scoped>
.upperpart {
  width: 100%;
  height: 20%;
  top: 0;
  background-color: aqua;
}
</style>