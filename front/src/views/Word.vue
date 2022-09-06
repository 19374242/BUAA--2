<template>
  <div>
    <el-dropdown>
      <el-button type="">
        选择模版<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown" style = "margin-left: 30px">
        <el-dropdown-item @click.native="model1">项目计划</el-dropdown-item>
        <el-dropdown-item @click.native="model2">会议纪要</el-dropdown-item>
        <el-dropdown-item @click.native="model3">项目管理</el-dropdown-item>
        <el-dropdown-item @click.native="model4">工作周报</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button type="primary" @click="save" style="position: relative; margin-bottom: 30px;margin-left: 10px">保存</el-button>
    <el-button style="position: relative;" type="success" @click="$router.back()">返回</el-button>
    <el-dropdown>
      <el-button type="warning" style="margin-left: 10px">
        导出<i class="el-icon-arrow-down el-icon--right" ></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="handleSaveToWord">word</el-dropdown-item>
        <el-dropdown-item @click.native="handleSaveToPdf">pdf</el-dropdown-item>
        <!--        <el-dropdown-item>markdown</el-dropdown-item>-->
      </el-dropdown-menu>
    </el-dropdown>
    <!--    <el-button style="position: relative;" type="warning" @click="handleSaveToPdf">导出</el-button>-->
    <vue-html2pdf
        :show-layout="false"
        :float-layout="true"
        :enable-download="true"
        :preview-modal="false"
        :paginate-elements-by-height="1400"
        :filename=this.$route.params.curName
        :pdf-quality="2"
        :manual-pagination="false"
        pdf-format="a4"
        pdf-orientation="landscape"
        pdf-content-width="800px"
        @progress="onProgress($event)"
        @hasStartedGeneration="hasStartedGeneration()"
        @hasGenerated="hasGenerated($event)"
        ref="html2Pdf"
    >
      <section slot="pdf-content">
        <!-- PDF Content Here -->
        <span id="report" v-html="content"></span>
      </section>
    </vue-html2pdf>
    <el-tiptap
        v-model="content"
        :extensions="extensions"
        placeholder="请输入内容 …"
        width="100%"
        height="800"
    ></el-tiptap>

  </div>
</template>

<script>
import axios from 'axios';
import htmlDocx from 'html-docx-js/dist/html-docx';
import saveAs from 'file-saver';
import {
  // 需要的 extensions
  Doc,
  Text,
  Paragraph,
  Heading,
  Bold,
  Underline,
  Italic,
  Strike,
  ListItem,
  BulletList,
  OrderedList,
  Link,
  Image,
  Iframe,
  CodeBlock,
  Blockquote,
  TodoItem,
  TodoList,
  TextAlign,
  FontSize,
  FontType,
  SelectAll,
  Fullscreen,
  Print,
  Preview,
  TextHighlight,
  TextColor,
  FormatClear,
  Table,
  TableHeader,
  TableCell,
  TableRow,
  History,
  TrailingNode,
  HardBreak,
  HorizontalRule,
  LineHeight,
  Indent,
} from "element-tiptap";
import VueHtml2pdf from 'vue-html2pdf'
// 注册

export default {
  components: {
    VueHtml2pdf,
  },

  created() {
    const tempthis = this;
    this.$root.$emit('update')
    axios.get("http://47.97.158.6:9091/document/download?fileName="+this.$route.params.curName+"&pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
      tempthis.content=JsonResponse.data.curcontent
    })

  },
  methods: {
    handleSaveToPdf() {
      this.$refs.html2Pdf.generatePdf()
    },
    onProgress() {

    },
    hasStartedGeneration() {

    },
    hasGenerated() {

    },
    handleSaveToWord(rec){

      let htmlStr = this.content.replace('</br>','\n');
      let page = `<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body>${htmlStr}
        </body></html>`
      // console.log(page);return
      saveAs(
          htmlDocx.asBlob(page, {
            orientation: "landscape"//跨域设置
          }),
          //文件名
          this.$route.params.curName+".doc"
      )
    },
    model1(){
      this.content = "一、 项目的简要介绍</br>二、 项目的内容</br>  1、立项依据</br>  2、项目意义</br>  3、项目的内容及目标</br>  4、项目可行性分析</br>   5、需求预测及分析</br>     ⑴市场定位及市场分析</br>     ⑵用户分析</br>    ⑶市场环境及前景</br>  6、完成项目采用的方法。"

    },
    model2(){
      this.content = "会议时间：</br>" +
          "     xx年xx月xx日</br>" +
          "  会议地点：</br>" +
          "     公司三楼会议室</br>" +
          "  主持人：</br>" +
          "     xxx</br>" +
          "  参加会议人员：</br>" +
          "     公司主管领导、各科室及各项目部负责人</br>" +
          "  会议记录资料：</br>"
    },
    model3(){
      this.content = "一、项口基本情况</br>" +
          "项目名称：</br>" +
          "项目编号：</br>" +
          "客户联系人：</br>" +
          "电话：</br>" +
          "项目经理：</br>" +
          "制作时间：</br>" +
          "二、项目组成员</br>" +
          "成员姓名：</br>" +
          "项目角色：</br>" +
          "所在部门：</br>" +
          "职责：</br>" +
          "项目开始日期：</br>" +
          "项日结束日期：</br>" +
          "三、功能分解</br>" +
          "模块名称：</br>" +
          "负责人：</br>" +
          "开工日期：</br>" +
          "工期(小时)：</br>" +
          "完工日期：</br>" +
          "是否超期：</br>" +
          "工作补充：</br>" +
          "四、功能调整</br>" +
          "调整项目：</br>" +
          "变更要点：</br>" +
          "变更理由：</br>" +
          "申请人：</br>" +
          "日期：</br>" +
          "审核人：</br>" +
          "日期："
    },
    model4(){
      this.content = "工作周报</br>" +
          "报告人：</br>" +
          "报告日期：</br>" +
          "一、本周工作内容</br>" +
          "工作内容：</br>" +
          "完成状况：</br>" +
          "问题困难及解决方法：</br>" +
          "二、下周工作计划</br>" +
          "工作内容：</br>" +
          "工时：</br>" +
          "计划时间：</br>" +
          "三、项目风险</br>" +
          "风险描述：</br>" +
          "发生的可能性：</br>" +
          "风险值：</br>" +
          "缓解或避免措施：</br>" +
          "四、意见及建议</br>" +
          "遇到的主要问题和困难：</br>"
    },
    save(){
      const tempthis = this;
      axios.get("http://47.97.158.6:9091/document/upload?fileName="+this.$route.params.curName+"&curcontent="+this.content+"&pjctName="+this.$route.params.pjctName+"&teamName="+this.$route.params.teamName).then(function (JsonResponse){
        tempthis.$message.success("保存成功")
      })
    }
  },
  name: "",
  props: {},
  data() {
    return {
      fileName:this.$cookies.get("fileName"),
      extensions: [
        new Doc(),
        new Text(),
        new Paragraph(),
        new Heading({ level: 6 }),
        new Bold({ bubble: true }), // 在气泡菜单中渲染菜单按钮
        new Underline({ bubble: true, menubar: false }), // 在气泡菜单而不在菜单栏中渲染菜单按钮
        new Italic(),
        // new Strike(),
        new ListItem(),
        new BulletList(),
        new OrderedList(),
        new Link(),
        new Image(
            // {
            // 默认会把图片生成 base64 字符串和内容存储在一起，如果需要自定义图片上传
            // uploadRequest(file) {
            //   如果接口要求 Content-Type 是 multipart/form-data，则请求体必须使用 FormData
            //   const fd = new FormData()
            //   fd.append('image', file)
            //   第1个 return 是返回 Promise 对象
            //   为什么？因为 axios 本身就是返回 Promise 对象
            //   return uploadImage(fd).then(res => {
            //     // 这个 return 是返回最后的结果
            //     return res.data.data.url
            //   })
            // } // 图片的上传方法，返回一个 Promise<url>
            // }
        ),
        new Iframe(),
        new CodeBlock(),
        new Blockquote(),
        new TodoItem(),
        new TodoList(),
        new TextAlign(),
        new FontSize(),
        new FontType(),
        new SelectAll(),
        new Fullscreen(),
        new Print(),
        new Preview(),
        new TextHighlight(),
        new TextColor(),
        new FormatClear(),
        new Table({ resizable: true }),
        new TableHeader(),
        new TableCell(),
        new TableRow(),
        new History(),
        new TrailingNode(),
        new HardBreak(),
        new HorizontalRule(),
        new LineHeight(),
        new Indent(),
      ],
      content: "",
    };
  },

};
</script>

<style scoped lang="less">
.el-dropdown {
  vertical-align: top;
}
.el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>


