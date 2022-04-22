<template>
<!-- <a href="#"> -->
  <svg
    :class="iconStyle"
    aria-hidden="true"
  >
    <use :xlink:href="iconName" />
  </svg>
<!-- </a> -->
</template>
<script>
import '@/assets/iconW/iconfont.js'
// import '@/assets/iconW/iconfont_hover.js'
export default {
  name: 'IconSvg',

  props: {
    iconClass: {
      type: String,
      required: true
    },
    iconStyle: {
      type: String,
      default: ''
    }
  },
  computed: {
    iconName() {
      return `#${this.iconClass}`
    }
  }
}
</script>