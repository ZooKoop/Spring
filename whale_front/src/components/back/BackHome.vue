<template>
  <el-row>
    <el-col :span="24">
     <Header></Header>
    </el-col>
    <el-col :span="4">
      <BodyLeft />
    </el-col>
    <el-col :span="20">
      <BodyRight/>
    </el-col>
  </el-row>
</template>

<script>
  import Header from '@/components/back/Header.vue'
  import BodyLeft from '@/components/back/BodyLeft.vue'
  import BodyRight from '@/components/back/BodyRight.vue'
  export default {
    components: {
      Header,
      BodyLeft,
      BodyRight
    }
  }
</script>

<style>
</style>
