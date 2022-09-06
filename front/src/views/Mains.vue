<template>
  <div>
    <p style="text-align: center;font-size: 70px;margin-top: -10px;color: black">墨&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp书</p>
    <el-carousel :interval="4000" type="card" height="450px" style="margin-top: 0px" >
      <el-carousel-item v-for="(item, index) in robot" :key="index">
        <img :src="item.url" @click="$router.push('/members')" style="height: 450px;width: 720px"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>

export default {
  name: "mains",
  data(){
    return{
      robot:[
        {url: require("../assets/11.jpg")},
        {url: require("../assets/22.jpg")},
        {url: require("../assets/33.jpg")},
      ]
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>