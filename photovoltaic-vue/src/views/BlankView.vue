<template>
  <h1>正在跳转...</h1>
</template>

<script>
export default {
  data(){
    this.$router.push({
      name:"detail",
      query:{
        id: this.$route.query.id
      }
    })
  }
}
</script>

<style scoped>

</style>