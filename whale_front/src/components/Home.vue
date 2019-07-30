<template>
  <div class="home">
	<div class="home_bg">
		<img v-bind:src="imgUrl"/>
		<!-- <img class="whale_img whale_middle" alt="" src="img/blue.png"> -->
<!-- 		<div class="whale_pop">
			<div class="whale_pop_gao"></div>
		</div> -->
	</div>
  </div>
</template>

<script>
	// import img from '../../static/imgs'
	export default {
		name: 'Home',
		data () {
			return {
				imgUrl: '../../static/imgs/home_bg.png'
			}
		},
		components:{}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
