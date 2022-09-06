<template>
  <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
    <el-table-column
        label="用户名"
        prop="order_id">
    </el-table-column>
    <el-table-column
        label="注册日期"
        prop="date">
    </el-table-column>
    <el-table-column
        label="联系方式"
        prop="tele">
    </el-table-column>
    <el-table-column
        align="right">
      <template slot="header" slot-scope="scope">
        <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"/>
      </template>
      <template slot-scope="scope">
        <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)">修改</el-button>
        <EditServiceUser :dialog1="dialog1"></EditServiceUser>
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  components: {
    EditServiceUser,
  },
  data() {
    return {
      tableData: [{
        renterID:'',
        username: '',
        phoneNumber: '',
      }, {
        renterID:'',
        username: '',
        phoneNumber: '',
      }, {
        renterID:'',
        username: '',
        phoneNumber: '',
      }, {
        renterID:'',
        username: '',
        phoneNumber: '',
      }],
      search: '',
      dialog1:{
        show:false,
        title:""
      }
    }
  },
  methods: {
    handleEdit(index, row) {
      this.dialog1={
        show:true,
        title:index
      };
      userApi.PersonalInfo({opera_type: 'CSSearchUser'}).then(
          res => {
            this.tableData[0].renterID = res.data.detail[0].renterID;
            this.tableData[0].username = res.data.detail[0].username;
            this.tableData[0].phoneNumber = res.data.detail[0].phoneNumber;
            this.tableData[1].renterID = res.data.detail[1].renterID;
            this.tableData[1].username = res.data.detail[1].username;
            this.tableData[1].phoneNumber = res.data.detail[1].phoneNumber;
            this.tableData[2].renterID = res.data.detail[2].renterID;
            this.tableData[2].username = res.data.detail[2].username;
            this.tableData[2].phoneNumber = res.data.detail[2].phoneNumber;
            this.tableData[3].renterID = res.data.detail[3].renterID;
            this.tableData[3].username = res.data.detail[3].username;
            this.tableData[3].phoneNumber = res.data.detail[3].phoneNumber;
          }
      )
    },
    handleDelete(index, row) {
      userApi.PersonalInfo({opera_type: 'delUser',username:this.tableData[index].username});
      console.log(index, row);
    }
  },
}
</script>