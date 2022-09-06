<template>

  <div id="app">
    <el-button
        type="primary"
        style="position: relative; margin-left: 7px"
        @click="$router.back()"
    >返回
    </el-button>
    <el-button
        type="success"
        style="position: relative; margin-left: 7px"
        @click="printOut('main')"
    >导出jpg
    </el-button>
    <div id="main">
      <ng-form-design  />
      此区域的所有代码渲染的效果都会导出
    </div>


  </div>
</template>



<script>
import html2canvas from "html2canvas"
export default {
  methods: {
    dataURLToBlob(dataurl) {
      var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
          bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new Blob([u8arr], {type: mime})
    },
    //name 为div的id 名称，会把id 下面的所有标签渲染出的页面效果导出
    downloadResult(name) {
      let canvasID = document.body
      let a = document.createElement('a');
      html2canvas(canvasID).then(canvas => {
        let dom = document.body.appendChild(canvas);
        dom.style.display = "none";
        a.style.display = "none";
        document.body.removeChild(dom);
        let blob = this.dataURLToBlob(dom.toDataURL("image/png"));
        a.setAttribute("href", URL.createObjectURL(blob));
        a.setAttribute("download", name + ".png")
        document.body.appendChild(a);
        a.click();
        URL.revokeObjectURL(blob);
        document.body.removeChild(a);
      });
    },
    printOut(name) {
      // 滚动条置顶，
      $(window).scrollTop(0);
      document.body.scrollTop = 0;
      document.documentElement.scrollTop = 0;
      this.downloadResult(name)
    },
  },
  name: "Mydesign"
}
</script>

<style scoped>

</style>