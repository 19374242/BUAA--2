<template>
  <div :key="Dkey">
    <el-button style="position: relative;" type="primary" @click="addFileVision=true">
      新建团队文档
    </el-button>

    <el-dialog title="新建团队文档" :visible.sync="addFileVision" width="30%">
      <el-form label-width="80px">
        <el-form-item label="文档名称">
          <el-input v-model="newFileName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createFile">确 定</el-button>
        <el-button @click="addFileVision = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="提示"
        :visible.sync="readFileVision"
        width="30%"
    >
      <span>{{ cccc }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="readFileVision = false">关闭</el-button>
      </span>
    </el-dialog>

    <div style="
      margin: 0 auto;
      width: 75%">

      <h1>团队文档</h1>
      <el-table
          :data="teamFiles.filter(
               (data) =>
      !searchTeamfiles ||
      data.fileName.toLowerCase().includes(searchTeamfiles.toLowerCase())
          )"
      >

        <el-table-column
            prop="fileName"
            sortable label="文档名称"
            width="200px">
        </el-table-column>

        <el-table-column>
          <template #header>
            <el-input v-model="searchTeamfiles"
                      size="small"
                      placeholder="搜索团队文档" />
          </template>

          <template #default="scope">
            <el-popconfirm
                style="margin-left: 5px"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定要删除文档吗"
                @confirm="deleteFile(0, scope.$index)"
            >
              <el-button type="danger"
                         style="
                   float: right"
                         slot="reference"
              >
                删除文档
              </el-button>
            </el-popconfirm>
            <el-button type="success"
                       style="
                   margin-right: 20px;
                   float: right"
                       @click="editFile(0, scope.$index)">
              编辑文档
            </el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="
      margin: 0 auto;
      width: 75%">

      <h1>项目文档</h1>
      <el-table
          :data="pjctFiles.filter(
               (data) =>
      !search ||
      data.pjctName.toLowerCase().includes(search.toLowerCase()) ||
      data.fileName.toLowerCase().includes(search.toLowerCase())
          )"
      >

        <el-table-column
            prop="pjctName"
            sortable label="项目名称"
            width="200px">
        </el-table-column>

        <el-table-column
            prop="fileName"
            sortable label="文档名称"
            width="200px">
        </el-table-column>

        <el-table-column>
          <template #header>
            <el-input v-model="search"
                      size="small"
                      placeholder="搜索项目文档" />
          </template>

          <template #default="scope">
            <el-popconfirm
                style="margin-left: 5px"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定要删除文档吗"
                @confirm="deleteFile(1, scope.$index)"
            >
              <el-button type="danger"
                         style="
                   float: right"
                         slot="reference"
              >
                删除文档
              </el-button>
            </el-popconfirm>
            <el-button type="success"
                       style="
                   margin-right: 20px;
                   float: right"
                       @click="editFile(1, scope.$index)">
              编辑文档
            </el-button>

          </template>
        </el-table-column>
      </el-table>

    </div>

  </div>

</template>

<script>

import axios from 'axios'

export default {
  name: 'fileList',
  computed: {
    teamName(){
      return this.$route.params.teamName;
    },
  },

  data() {
    return {
      search: '',
      searchTeamfiles: '',
      cccc:"",
      loginUser: {},
      addFileVision: false,
      readFileVision: false,
      newFileName: '',
      teamFiles: [],
      pjctFiles: [],
      Dkey: 0,
    }
  },
  methods: {

    editFile: function (kind, id) {
      var tmp = this.pjctFiles;
      if (kind === 0){
        tmp = this.teamFiles
        this.$router.push({name: 'word' ,params:{
            pjctName: "团队文档",
            file: tmp[id]}})
      }else{
        this.$router.push({name: 'word' ,params:{curName: tmp[id].fileName,
            pjctName: tmp[id].pjctName,
            teamName: tmp[id].teamName}})
      }
    },
    createFile: function (index) {
      // http://localhost:9091/team/createTeamFile加参数
      this.addFileVision=false
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/team/createTeamFile?fileName="+this.newFileName
         +"&teamName="+this.teamName).then(function (JsonResponse){
        tempthis.getFiles()
      })
    },
    deleteFile: function (kind, id) {
      // http://localhost:9091/team/delTeamFile/?teamName=烤漆总动员&fileName=成员介绍/
      const tempthis = this;
      var tmp = this.pjctFiles;
      if (kind === 0) tmp = this.teamFiles;
      axios.get("http://47.97.158.6:9091/team/delTeamFile/?teamName="+tmp[id].teamName+
          "&fileName="+tmp[id].fileName).then(function (JsonResponse){
        tempthis.getFiles()
      })
      this.$message.success("删除成功")
    },

    getFiles: function(){
      // http://localhost:9091/team/returnFile
      const tempthis = this;
      console.log("teamName: ")
      console.log(this.teamName)
      axios.get("http://47.97.158.6:9091/team/returnFile?teamName="+this.teamName).then(function (JsonResponse){
        tempthis.teamFiles=JsonResponse.data.teamFiles;
        tempthis.pjctFiles = JsonResponse.data.pjctFiles;
        tempthis.Dkey++;
        console.log("update teamFiles")
        console.log(tempthis.teamFiles);
        console.log(tempthis.pjctFiles)
      })
    }
  },

  created() {
    this.$root.$emit('update')
    this.loginUser = this.$cookies.get('loginUser');
    this.getFiles();
  },

}

</script>

<style>

</style>