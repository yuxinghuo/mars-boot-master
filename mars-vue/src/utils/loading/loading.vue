<template>
  <div class="loader" v-if="flag">
    <!-- <div>
             加载中....请稍后
    </div>-->
    <div class="loading-wrapper">
      <!-- loading -->
      <div class="container1">
        <div class="circle circle1"></div>
        <div class="circle circle2"></div>
        <div class="circle circle3"></div>
        <div class="circle circle4"></div>
      </div>
      <div class="container2">
        <div class="circle circle1"></div>
        <div class="circle circle2"></div>
        <div class="circle circle3"></div>
        <div class="circle circle4"></div>
      </div>
    </div>
    <!-- <a-spin /> -->
    <!-- //利用v-if操控插件的显示与消失。 -->
    <!-- <div class="loader-inner"> -->
    <!-- <div class="loader-line-wrap">
                    <div class="loader-line"></div>
    </div>-->
    <!-- <div class="loader-line-wrap">
                    <div class="loader-line"></div>
    </div>-->
    <!-- <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
                </div>
                <div class="loader-line-wrap">
                    <div class="loader-line"></div>
    </div>-->
    <!-- </div> -->
  </div>
</template>
  
<script>
export default {
  name: 'loading',
}
</script>
  
<style scoped lang="less">
.loading-wrapper {
  width: 100px;
  height: 100px;
  background-color: rgba(0, 0, 0, 0.8);
  border-radius: 10px;
  color: #fff;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  position: absolute;
  left: 50%;
  top: 50%;
  z-index: 9999999;
  transform: translate(-50%, -50%);
}
.container1,
.container2 {
  width: 40px;
  height: 40px;
  position: absolute;
  /* border: 1px solid #ff0000; */
  top: 20%;
  left: 50%;
  margin-left: -20px;
  position: absolute;
}

.container2 {
  transform: rotate(45deg);
}

/* 第1种动画和第二种的相同部分，其中的 animation 也相同 */
.circle,
.bounce {
  width: 10px;
  height: 10px;
  background-color: #fff;
  border-radius: 50%;
  /* 设置小球动画 */
  animation: loading 1.2s both infinite;
}

.circle {
  position: absolute;
}

/* 设置小球的相对位置 */
.circle1 {
  top: 0;
  left: 0;
}

.circle2 {
  top: 0;
  right: 0;
}

.circle3 {
  bottom: 0;
  right: 0;
}

.circle4 {
  bottom: 0;
  left: 0;
}

/* 设置延时 */
.container1 .circle1 {
  animation-delay: 0s;
}

.container2 .circle1 {
  animation-delay: 0.2s;
}

.container1 .circle2 {
  animation-delay: 0.3s;
}

.container2 .circle2 {
  animation-delay: 0.4s;
}

.container1 .circle3 {
  animation-delay: 0.5s;
}

.container2 .circle3 {
  animation-delay: 0.6s;
}

.container1 .circle4 {
  animation-delay: 0.7s;
}

.container2 .circle5 {
  animation-delay: 0.8s;
}

@keyframes loading {
  0% {
    transform: scale(0);
  }

  40% {
    transform: scale(1);
  }

  80% {
    transform: scale(0);
  }

  100% {
    transform: scale(0);
  }
}
.loader {
  //   background: #000;
  background-color: rgba(0, 0, 0, 0);
  font-size: 15px;
  color: white;
  //   background: radial-gradient(#222, #000);
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
  z-index: 99999;
  overflow: hidden;
  position: fixed;

  display: flex;
  justify-content: center;
  align-items: center;
}

//  .loader-inner {
//      bottom: 0;
//      height: 60px;
//       left: 0;
//      margin: auto;
//       position: absolute;
//       right: 0;
//       top: 0;
//       width: 100px;
//   }

//   .loader-line-wrap {
//       animation:
//           spin 2000ms cubic-bezier(.175, .885, .32, 1.275) infinite
//       ;
//       box-sizing: border-box;
//      height: 50px;
//      left: 0;
//       overflow: hidden;
//       position: absolute;
//       top: 0;
//      transform-origin: 50% 100%;
//       width: 100px;
//  }
//   .loader-line {
//      border: 4px solid transparent;
//       border-radius: 100%;
//      box-sizing: border-box;
//      height: 100px;
//       left: 0;
//       margin: 0 auto;
//       position: absolute;
//      right: 0;
//       top: 0;
//      width: 100px;
//   }
//  .loader-line-wrap:nth-child(1) { animation-delay: -50ms; }
//   .loader-line-wrap:nth-child(2) { animation-delay: -100ms; }
//  .loader-line-wrap:nth-child(3) { animation-delay: -150ms; }
//   .loader-line-wrap:nth-child(4) { animation-delay: -200ms; }
//   .loader-line-wrap:nth-child(5) { animation-delay: -250ms; }

//   .loader-line-wrap:nth-child(1) .loader-line {
//       border-color: hsl(0, 80%, 60%);
//      height: 90px;
//       width: 90px;
//       top: 7px;
//   }
//   .loader-line-wrap:nth-child(2) .loader-line {
//       border-color: hsl(60, 80%, 60%);
//       height: 76px;
//       width: 76px;
//       top: 14px;
//   }
//   .loader-line-wrap:nth-child(3) .loader-line {
//      border-color: hsl(120, 80%, 60%);
//       height: 62px;
//       width: 62px;
//      top: 21px;
//  }
//  .loader-line-wrap:nth-child(4) .loader-line {
//      border-color: hsl(180, 80%, 60%);
//     height: 48px;
//     width: 48px;
//      top: 28px;
//  }
//  .loader-line-wrap:nth-child(5) .loader-line {
//      border-color: hsl(240, 80%, 60%);
//      height: 34px;
//     width: 34px;
//      top: 35px;
//  }

//  @keyframes spin {
//     0%, 15% {
//         transform: rotate(0);
//      }
//      100% {
//          transform: rotate(360deg);
//      }
//  }
</style>