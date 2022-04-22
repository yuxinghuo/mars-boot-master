<template>
    <div class="iconBox">

      <component style="width:12px;height:12px" :is="type"></component>
    </div>
</template>
<script>
// import zktabhome from '@/assets/addicon/tabhome.svg';
// import zkiconContract from '@/assets/addicon/icon-contract.svg';
// import zkzhuanqian from '@/assets/addicon/zhuanqian.svg';


export default {
  name: 'ZKIcons',
  props:{
    type:{}
  },
  data(){
      return{

      }
  },
  components:{
    // zktabhome,
    // zkiconContract,
    // zkzhuanqian,
  },
}
</script>
<style scoped>
.iconBox{
  display: inline-block;
}
</style>
