<template>
  <el-menu default-active="1-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="#545c64"
    text-color="#fff" active-text-color="#ffd04b">
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>Ticket</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Work</template>
        <el-menu-item index="1-1">Ticket</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Other">
        <el-menu-item index="1-3" disabled>选项3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="1-4" disabled>
        <template slot="title">选项4</template>
        <el-menu-item index="1-4-1">选项1</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="4">
      <i class="el-icon-setting"></i>
      <span slot="title">设置</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<style>
</style>
