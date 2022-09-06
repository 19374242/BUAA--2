<!--<template>-->
<!--&lt;!&ndash; 顶栏 &ndash;&gt;-->
<!--    <div class="home-container">-->
<!--        <div class="home-header">-->
<!--            <div id="home-left">-->
<!--                图标-->
<!--            </div>-->
<!--            <div id="index-header">-->
<!--                标题-->
<!--            </div>-->
<!--            <div id="search-container">-->
<!--                <el-input-->
<!--                placeholder="在个人项目搜索"-->
<!--                v-model="searchBarInput">-->
<!--                </el-input>-->
<!--            </div>-->
<!--            <div id="Project-add">添加图标</div>-->
<!--            <div id="home-right">-->
<!--                用户/消息/成员-->
<!--            </div>-->
<!--        </div>-->
<!--    </div>-->

<!--&lt;!&ndash; 主内容 &ndash;&gt;-->
<!--    <div class="Project-container">-->
<!--        <div class="Project-header" ref="choosePage">-->
<!--            <div id="Project-header-left" class="Project-header-item"></div>-->
<!--            <div class="change-current-page Project-header-item">-->

<!--                <div class="change-current-page-item" :class='{currentpage: currentPage==="tasks"}'-->
<!--                     @click="currentPage = 'tasks'">任务-->
<!--                </div>-->
<!--                <div class="change-current-page-item" :class='{currentpage: currentPage==="files"}'-->
<!--                     @click="currentPage = 'files'">文件-->
<!--                </div>-->
<!--                <div class="change-current-page-item" :class='{currentpage: currentPage==="calenders"}'-->
<!--                     @click="currentPage = 'calenders'">日程-->
<!--                </div>-->

<!--            </div>-->

<!--            <div class="Project-menu Project-header-item">-->
<!--                <div class="Project-menu-item" :class='{currentmenu: currentMenu === "member"}'-->
<!--                     @click="currentMenu='member'">-->

<!--                </div>-->
<!--                <transition name="el-zoom-in-center">-->
<!--                    <div class="Project-menu-item" :class='{currentmenu: currentMenu === "view"}'-->
<!--                         @click="currentMenu='view'" v-if="currentPage==='tasks'">-->
<!--                        成员-->
<!--                    </div>-->
<!--                </transition>-->
<!--                <div class="Project-menu-item" :class='{currentmenu: currentMenu === "setting"}'-->
<!--                     @click="currentMenu='setting'">-->
<!--                    设置-->
<!--                </div>-->
<!--            </div>-->

<!--        </div>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--     name: "Login",-->
<!--  data() {-->
<!--    return {-->
<!--      email: null,-->
<!--      pwd: '',-->
<!--    }-->
<!--  },-->
<!--  mounted() {-->
<!--    let _this = this-->
<!--    _this.initPage()-->
<!--  },-->
<!--  methods: {-->
<!--    initPage() {-->
<!--    }-->
<!--  }-->

<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.home-container {-->
<!--  height: 100%;-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--}-->

<!--img {-->
<!--  width: 20px;-->
<!--  height: 20px;-->
<!--  margin: 15px 10px 15px 10px;-->
<!--}-->
<!--#Project-add {-->
<!--  height: 100%;-->
<!--}-->
<!--#home-left img {-->
<!--}-->
<!--#Project-add img {-->
<!--  width: 20px;-->
<!--  height: 20px;-->
<!--}-->
<!--.home-header {-->
<!--  padding-left: 30px;-->
<!--  height: 49px;-->
<!--  background: #ffffff;-->
<!--  line-height: 49px;-->
<!--  font-size: 18px;-->
<!--  color: #3E5568;-->
<!--  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5);-->
<!--  z-index: 10;-->
<!--  position: relative;-->
<!--  display: flex;-->
<!--  justify-content: space-between;-->
<!--  align-items: center;-->
<!--}-->
<!--#home-left {-->
<!--  height: 49px;-->
<!--  display: flex;-->
<!--  flex-direction: row;-->
<!--  align-items: center;-->
<!--}-->
<!--#home-left div {-->
<!--  display: inline-flex;-->
<!--  margin-right: 8px;-->
<!--}-->
<!--#home-right {-->
<!--  height: 49px;-->
<!--  display: flex;-->
<!--  flex-direction: row;-->
<!--  align-items: center;-->
<!--}-->
<!--#home-right div {-->
<!--  display: inline-flex;-->
<!--}-->
<!--#Project-search {-->
<!--  width: 300px;-->
<!--  padding: 0px 0 0 20px;-->
<!--}-->

<!--.Project-container {-->
<!--    flex: 1;-->
<!--    display: flex;-->
<!--    flex-direction: column;-->
<!--}-->
<!--.Project-header {-->
<!--    height: 48px;-->
<!--    background: #E7F1F0;-->
<!--    line-height: 48px;-->
<!--    font-size: 16px;-->
<!--    color: #97A4B1;-->
<!--    border-bottom: #A1BDBF 1px solid;-->
<!--    display: flex;-->
<!--    flex-direction: row;-->
<!--    justify-content: space-between;-->
<!--}-->
<!--.Project-header-item {-->
<!--}-->
<!--.change-current-page-item {-->
<!--    display: inline-block;-->
<!--    width: 80px;-->
<!--    line-height: 40px;-->
<!--    height: 40px;-->
<!--    border-bottom: 4px transparent;-->
<!--    padding-top: 4px;-->
<!--    text-align: center;-->
<!--}-->
<!--.change-current-page-item :hover {-->
<!--    border-bottom: 4px #33CCCC solid;-->
<!--}-->
<!--.currentpage {-->
<!--    font-weight: bold;-->
<!--    color: #33CCCC;-->
<!--    border-bottom: 4px #33CCCC solid;-->
<!--}-->
<!--.currentmenu {-->
<!--    background: #A1BDBF;-->
<!--}-->
<!--.Project-menu {-->
<!--    text-align: center;-->
<!--    float: right;-->
<!--    margin-right: 5px;-->
<!--    display: flex;-->
<!--    justify-content: flex-end;-->
<!--}-->
<!--.Project-menu-item {-->
<!--    width: 38px;-->
<!--    display: inline-block;-->
<!--    font-size: 24px;-->
<!--    margin-right: 0px;-->
<!--    padding: 0 5px 0 5px;-->
<!--}-->
<!--.Project-menu-item :hover {-->
<!--}-->
<!--.Project-menu > i {-->
<!--    color: #33CCCC;-->
<!--}-->
<!--.Project-menu > i:hover {-->
<!--}-->
<!--#Project-header-left, .Project-menu {-->
<!--    width: 300px;-->
<!--}-->

<!--</style>-->
