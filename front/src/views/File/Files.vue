<template>
  <div>
    <el-button style="position: relative;margin-bottom: 50px;margin-left: 170px" type="primary" @click="createFile">
      新建文档
    </el-button>
    <el-dialog title="新建文档" :visible.sync="addFileVision" width="30%">
      <el-form label-width="80px">
        <el-form-item label="文档名称">
          <el-input v-model="newFileName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createFile1">确 定</el-button>
        <el-button @click="addFileVision = false">取 消</el-button>

      </div>
    </el-dialog>
    <el-dialog
        title="文档内容"
        :visible.sync="readFileVision"
        width="30%"
    >
      <span>{{ cccc }}</span>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="readFileVision = false">关闭</el-button>
  </span>
    </el-dialog>
    <el-table
        :data="Files"
        style="
      margin: 0 auto;
      width: 75%"
    >
      <el-table-column
          prop="fileName"
          label="文档名称"
          width="400px">
      </el-table-column>

      <el-table-column>
        <template #default="scope">
          <el-popconfirm
              style="margin-left: 5px"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定要删除文档吗"
              @confirm="deleteFile(scope.row.fileName)"
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
                     @click="editFile(scope)">
            编辑文档
          </el-button>

<!--          <el-button type="primary"-->
<!--                     style="-->
<!--                   margin-right: 20px;-->
<!--                   float: right"-->
<!--                     @click="readFile(scope.row.fileName)">-->
<!--            查看文档-->
<!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>

  </div>

</template>

<script>

import axios from 'axios'

export default {
  name: 'fileList',

  data() {
    return {
      cccc:"",
      loginUser: {},
      addFileVision: false,
      readFileVision: false,
      newFileName: '',
      Files: [],
      pjctName:this.$route.params.pjctName,
      teamName:this.$route.params.teamName,


    }
  },
  methods: {
    sendCode: function(email){
      const tempthis = this;
      axios.post("http://47.97.158.6:9091/user/sendEmail?email="+email).then(function (JsonResponse){
        console.log(JsonResponse);
        if (JsonResponse.data.status === 0)
          tempthis.$message.success("发送成功");
        else
          tempthis.$message.error('发送失败');
      })
    },
    editFile: function (x) {
      this.$router.push({name: 'word' ,params:{curName:this.Files[x.$index].fileName,
                                                pjctName:this.$route.params.pjctName,
                                                teamName:this.$route.params.teamName}})
    },
    createFile: function (index) {
      this.addFileVision=true

    },
    createFile1: function (index) {
      this.addFileVision=false
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/document/upload?fileName="+this.newFileName+"&curcontent="+""+"&pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
        tempthis.getFiles()
      })

    },
    deleteFile: function (index) {
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/document/del?fileName="+index+"&pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
        tempthis.getFiles()
      })
      this.$message.success("删除成功")
    },
    readFile: function (name) {

      const tempthis = this;
      axios.get("http://47.97.158.6:9091/document/download?fileName="+name+"&pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
        tempthis.cccc=JsonResponse.data.curcontent.substring(3,JsonResponse.data.curcontent.length-4)
      })
      // this.cccc=this.cccc.replace('</br>',"genghuan")
      // this.cccc="test"
      // console.log(this.cccc)
      this.readFileVision = true;
      //这里需要返回文档的信息
    },





    getFiles: function(){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/document/showAll?pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
        tempthis.Files=JsonResponse.data;
      })
    }

  },

  created() {
    this.$root.$emit('update')
    this.loginUser = this.$cookies.get('loginUser');
    this.getFiles();
    // 需要返回所有团队的接口


  }
}

</script>

<style>

</style>