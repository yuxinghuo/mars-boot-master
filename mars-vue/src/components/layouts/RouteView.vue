<template>
  <div class="main">
  <!-- <keep-alive>
    <router-view v-if="keepAlive" />
  </keep-alive>
  <router-view v-if="!keepAlive" /> -->
   <keep-alive :include="keepAliveList" >
    <router-view />
   </keep-alive>
  </div>
</template>

<script>
import { mapState } from 'vuex'

  export default {
    name: "RouteView",
    computed: {
      keepAlive () {
        return this.$route.meta.keepAlive
      },
      ...mapState({
        keepAliveList: state => state.utils.keepAliveList,
      })
       
    },
  }
</script>