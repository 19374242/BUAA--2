import Vue from 'vue'
import VueRouter from 'vue-router'
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)


//团队有关路由
const members = {
  path: 'members/:teamName?',
  name: 'members',
  component:() =>import('../views/Team/members'),
}
const teams = {
  path: 'teams',
  name: 'teams',
  component:() =>import('../views/Team/Teams'),
}
//文档有关路由
const dustbin = {
  path: 'dustbin/:teamName',
  name: 'dustbin',
  component:() =>import('../views/Project/Dustbin'),
}
const TeamFiles = {
  path: 'TeamFiles/:teamName',
  name: 'TeamFiles',
  component:() =>import('../views/File/TeamFiles'),
}

//项目有关路由
const projects = {
  path: 'projects',
  name: 'projects',
  component:() =>import('../views/Project/Projects'),
}
const mains = {
  path: 'mains',
  name: 'mains',
  component:() =>import('../views/Mains'),
}
const teamprojects = {
  path: 'teamprojects/:teamName',
  name: 'teamprojects',
  component:() =>import('../views/Project/TeamProjects'),
}

const files = {
  path: 'files/:teamName/:pjctName',
  name: 'files',
  component:() =>import('../views/File/Files'),
}
const word = {
  path: 'word',
  name: 'word',
  component:() =>import('../views/Word'),
}

const routes = [
  {
    path: '/',
    redirect: 'login'
  },
  {
    path: '/layout/:info',
    name: 'layout',
    component: () => import('../views/Layout.vue'),
    children:[
        teams,
        members,
        projects,
        teamprojects,
        files,
        word,
        dustbin,
        mains,
        TeamFiles,
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/Project',
    name: 'project',
    component: () => import('../views/Project.vue')
  },
  {
  path: '/mydesign',
    name: 'mydesign',
    component:() =>import('../views/Project/Mydesign.vue')},
  {
    path: '/info',
    name: 'info',
    component: () => import('../views/Info.vue')
  },
  {
    path: '/invite/:teamName',
    name: 'userInvite',
    component:() =>import('../views/User/Invite'),
  }
]


// const userInvite = {
//   path: '/invite/:teamName',
//   name: 'userInvite',
//   component:() =>import('../views/User/Invite'),
// }




const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
