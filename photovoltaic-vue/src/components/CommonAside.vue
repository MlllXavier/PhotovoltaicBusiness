<template>
  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
    <el-radio-button :label="false">开</el-radio-button>
    <el-radio-button :label="true">关</el-radio-button>
  </el-radio-group>
  <el-menu :default-active="this.$route.path.substr(1)" class="el-menu-vertical-demo" :collapse="isCollapse"
    @open="handleOpen" @close="handleClose" router>
    <el-menu-item index="home">
      <el-icon>
        <HomeFilled />
      </el-icon>
      <template #title>首页</template>
    </el-menu-item>
    <el-sub-menu index="1">
      <template #title>
        <el-icon>
          <Menu />
        </el-icon>
        <span>业务管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="urgent" style="color: red">
          <el-icon>
            <AlarmClock />
          </el-icon>
          <span>加急处理</span>
        </el-menu-item>
        <el-menu-item index="unfinished" style="color: indianred">
          <el-icon>
            <Aim />
          </el-icon>
          <span>未完成</span>
        </el-menu-item>
        <el-menu-item index="finished" style="color: green">
          <el-icon>
            <Finished />
          </el-icon>
          <span>已完成</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-menu-item index="information">
      <el-icon>
        <List />
      </el-icon>
      <template #title>用户电子档案</template>
    </el-menu-item>
    <el-menu-item index="subsidy">
      <el-icon>
        <Ticket />
      </el-icon>
      <template #title>补贴结算</template>
    </el-menu-item>
    <el-menu-item index="documents">
      <el-icon>
        <Folder />
      </el-icon>
      <template #title>政策文件</template>
    </el-menu-item>
    <el-menu-item index="compensation">
      <el-icon>
        <Management />
      </el-icon>
      <template #title>追退补费用</template>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'
import {
  HomeFilled,
  Menu,
  Finished,
  AlarmClock,
  Ticket,
  Management,
  List,
  Aim,
  Folder,
} from '@element-plus/icons-vue'

const isCollapse = ref(true)
const handleOpen = (key, keyPath) => {
  //console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  //console.log(key, keyPath)
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>