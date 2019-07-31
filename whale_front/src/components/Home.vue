<template>

  <div>
    <router-link to='/back'><el-button plain size="mini">1</el-button></router-link>
  </div>
</template>

<script>
  // import img from '../../static/imgs'
  export default {
    name: 'Home',
    data() {
      return {
        imgUrl: 'static/imgs/home_bg.png'
      }
    },
    components: {}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
