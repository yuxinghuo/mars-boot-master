<template>
  <div id="userLayout" :class="['user-layout-wrapper', device]">
    <div class="container">
<!--      <canvas id="canvas"></canvas>-->
<!--      <div class="top">-->
<!--        <div class="top_Border">-->
<!--          <div class="header">-->
<!--            <a href="/" class="top_a">-->
<!--              <div class="logo_Box">-->
<!--              </div>-->
<!--              <div class="title">火星计划系统</div>-->
<!--            </a>-->
<!--            <div class="header_rightimg">-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
      <div class="main_Border">
        <div class="main_Border_content">
          <!-- <div class="main_Border_imgTwo"> -->
          <div class="left_img">
          </div>
          <div class="form">
            <route-view></route-view>
          </div>
          <!-- </div> -->

          <!-- <route-view></route-view> -->
        </div>
      </div>
      <div class="footer">
        Copyright &copy; 2022
        <span>火星计划有限公司</span>
      </div>
    </div>
  </div>
</template>

<script>
import RouteView from '@/components/layouts/RouteView'
import { mixinDevice } from '@/utils/mixin.js'

export default {
  name: 'UserLayout',
  components: { RouteView },
  mixins: [mixinDevice],
  data() {
    return {}
  },
  mounted() {
    document.body.classList.add('userLayout')
    this.userCanvas()
  },
  beforeDestroy() {
    document.body.classList.remove('userLayout')
  },
  methods: {
    userCanvas() {
      var canvas = document.getElementById('canvas')
      var ctx = canvas.getContext('2d')
      // cx和cy是屏幕的宽度和高度/2
      var cw = (canvas.width = window.innerWidth),
        cx = cw / 2
      var ch = (canvas.height = window.innerHeight),
        cy = ch / 2

      // ctx.fillStyle = "#FFFF00";
      var linesNum = 16
      var linesRy = []
      var requestId = null

      var arr = [
        // h
        157.7847586814884,
        861.139832862529,
        59.965936038042145,
        71.92944692464965,
        // UserLayout.vue?0feb:169 v
        196.83047145582697,
        520.2470182408763,
        97.48937736364195,
        80.57893531122373,
        // UserLayout.vue?0feb:169 h
        1450.884171356958,
        730.0085626121389,
        51.81497364872102,
        98.53593052335198,
        // UserLayout.vue?0feb:169 v
        286.3045538597878,
        429.05302503200585,
        32.91878281874422,
        43.9837981527018,
        // UserLayout.vue?0feb:169 h
        750.051904266698,
        1008.1607911170701,
        84.53840515638637,
        71.69594191259446,
        // UserLayout.vue?0feb:169 v
        598.2231510529434,
        690.5167207512927,
        41.42079899075139,
        47.52348911993694,
        // UserLayout.vue?0feb:169 h
        1393.4946907499182,
        395.5895707298892,
        71.2063468639345,
        92.26082573830466,
        // UserLayout.vue?0feb:169 v
        493.95746618901137,
        119.37317962937047,
        60.59859898425691,
        83.54497305195143,
        // UserLayout.vue?0feb:169 h
        659.1265700305202,
        190.232969959405,
        97.163469604519,
        78.01855515899055,
        // UserLayout.vue?0feb:169 v
        72.50508892356633,
        97.94643134560656,
        39.55662381415283,
        76.86503316526753,
        // UserLayout.vue?0feb:169 h
        1505.600126651914,
        1385.0117763174844,
        82.88585830630927,
        50.3634645481318,
        // UserLayout.vue?0feb:169 v
        566.5659107445224,
        391.6453195823296,
        81.2197246351916,
        91.65802144669452,
        // UserLayout.vue?0feb:169 h
        1040.5873532413007,
        727.6101529213022,
        26.388111828376225,
        81.8039414678555,
        // UserLayout.vue?0feb:169 v
        107.12540774921878,
        396.84893879930337,
        34.68472730595456,
        57.898872116580485,
        // UserLayout.vue?0feb:169 h
        1024.1812239936078,
        661.2090640123861,
        39.96295991840823,
        61.289337089525816,
        // UserLayout.vue?0feb:169 v
        566.6659006255068,
        184.9325425369931,
        97.20961147360472,
        43.31917127812387,
      ]
      var num = -1

      function Line(flag) {
        this.flag = flag
        this.a = {}
        this.b = {}
        if (flag == 'v') {
          this.a.y = 0
          this.b.y = ch
          this.a.x = randomIntFromInterval(0, ch)
          this.b.x = randomIntFromInterval(0, ch)
        } else if (flag == 'h') {
          this.a.x = 0
          this.b.x = cw
          this.a.y = randomIntFromInterval(0, cw)
          this.b.y = randomIntFromInterval(0, cw)
        }
        this.va = randomIntFromInterval(25, 100) / 100
        this.vb = randomIntFromInterval(25, 100) / 100
        this.draw = function () {
          ctx.strokeStyle = '#2296FD'
          ctx.beginPath()
          ctx.moveTo(this.a.x, this.a.y)
          ctx.lineTo(this.b.x, this.b.y)
          ctx.stroke()
        }

        this.update = function () {
          if (this.flag == 'v') {
            this.a.x += this.va
            this.b.x += this.vb
          } else if (flag == 'h') {
            this.a.y += this.va
            this.b.y += this.vb
          }

          this.edges()
        }

        this.edges = function () {
          if (this.flag == 'v') {
            if (this.a.x < 0 || this.a.x > cw) {
              this.va *= -1
            }
            if (this.b.x < 0 || this.b.x > cw) {
              this.vb *= -1
            }
          } else if (flag == 'h') {
            if (this.a.y < 0 || this.a.y > ch) {
              this.va *= -1
            }
            if (this.b.y < 0 || this.b.y > ch) {
              this.vb *= -1
            }
          }
        }
      }
      console.log(linesRy)
      function Draw() {
        // requestId = window.requestAnimationFrame(Draw);
        ctx.clearRect(0, 0, cw, ch)
        for (var i = 0; i < linesRy.length; i++) {
          var l = linesRy[i]
          l.draw()
          l.update()
        }
        for (var i = 0; i < linesRy.length; i++) {
          var l = linesRy[i]
          for (var j = i + 1; j < linesRy.length; j++) {
            var l1 = linesRy[j]
            Intersect2lines(l, l1)
          }
        }
      }
      function Init() {
        linesRy.length = 0
        for (var i = 0; i < linesNum; i++) {
          var flag = i % 2 == 0 ? 'h' : 'v'
          var l = new Line(flag)
          linesRy.push(l)
        }
        if (requestId) {
          window.cancelAnimationFrame(requestId)
          requestId = null
        }
        ;(cw = canvas.width = window.innerWidth), (cx = cw / 2)
        ;(ch = canvas.height = window.innerHeight), (cy = ch / 2)
        Draw()
      }

      setTimeout(function () {
        console.log('0')
        Init()

        addEventListener('resize', Init, false)
      }, 15)

      function Intersect2lines(l1, l2) {
        var p1 = l1.a,
          p2 = l1.b,
          p3 = l2.a,
          p4 = l2.b
        var denominator = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y)
        var ua = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x)) / denominator
        var ub = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x)) / denominator
        var x = p1.x + ua * (p2.x - p1.x)
        var y = p1.y + ua * (p2.y - p1.y)
        if (ua > 0 && ub > 0) {
          markPoint({
            x: x,
            y: y,
          })
        }
      }

      function markPoint(p) {
        ctx.beginPath()
        ctx.arc(p.x, p.y, 2, 0, 2 * Math.PI)
        ctx.fillStyle = '#FFF'
        ctx.fill()
      }

      function randomIntFromInterval(mn, mx) {
        num++
        if (num === arr.length) {
          num = 0
        }

        // let rember = Math.random() * (mx - mn + 1) + mn
        // return rember
        return arr[num]

        // return ~~(Math.random() * (mx - mn + 1) + mn)
      }
    },
  },
}
</script>
<style lang="less" >
@media (max-height: 1240px) {
  html {
    font-size: 140px;
  }
}
@media (max-height: 1050px) {
  html {
    font-size: 137px;
  }
}
@media (max-height: 1024px) {
  html {
    font-size: 133px;
  }
}
@media (max-height: 960px) {
  html {
    font-size: 125px;
  }
}
@media (max-height: 900px) {
  html {
    font-size: 117px;
  }
}

@media (max-height: 768px) {
  html {
    font-size: 100px;
  }
}
</style>

<style lang="less" scoped>
userLayout {
  margin-right: 0px;
}
.user-layout-wrapper {
  margin-right: 0px;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.container {
  background: cyan;
  background: url(~@/assets/back.png) no-repeat;
  background-size: 100% 100%;
  margin-right: 0px;
  //padding: 110px 0 144px;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  top: 0;
  left: 0;
}
#canvas {
  position: absolute;
  z-index: 0;
  width: 100%;
  height: 100%;
  background-color: #2671e4;
  display: block;
  margin: 0 auto;
}
.top {
  // height: 1.07rem;
  height: 0.8rem;
  width: 100%;
  position: relative;
  left: 0;
  top: 0;
  background-color: #f1f1f1;
}
.top_Border {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.header {
  width: 1366px;
  min-width: 1366px;
  overflow: hidden;
  height: 100%;
  display: flex;
  justify-content: space-between;
  padding-left: 48px;
  padding-right: 65px;
  // padding-left: 0.48rem;
  // padding-right: 0.65rem;
}
.top_a {
  display: flex;
  align-items: center;
}
// .logo_Box {
//   height: 0.28rem;
//   padding-top: 0.43rem;
// }
.logo {
  width: 100%;
}
.title {
  font-size: 28px;
  color: #2c7bff;
  font-family: 'Microsoft YaHei Bold';
  font-weight: 600;
  line-height: 1;
  margin-left: 0.2rem;
  // margin-top: 0.45rem;
}
.header_rightimg {
  // padding-top: 0.39rem;
  display: flex;
  align-items: center;
}

.main_Border {
  position: relative;
  left: 0;
  top: 0;
  flex: 1;
  display: flex;
  justify-content: center;
}
.main_Border_content {
  width: 1366px;

  display: flex;
}
.left_img {
  // width: 6rem;
  width: 676px;
  height: 400px;
  margin-left: 40px;
  padding-top: 1rem;
  // padding-left: 0.83rem;
  > img {
    width: 100%;
  }
}
.form {
  margin-top: 0.5rem;
  padding-top: 1rem;
  // padding-right: 0.83rem;
}
.main {
  // height: 3.4rem;
  // width: 4.5rem;
  background: #fff;
}
.footer {
  height: 0.4rem;
  position: relative;
  left: 0;
  top: 0;
  text-align: center;
  color: #fff;
  font-size: 10px;
}
// .main_Border_content {
//   width: 1366px;
//   max-width: 1366px;
//   display: flex;
//   flex-direction: column;
// }
// .main_Border_imgTwo {
//   flex: 1;
//   display: flex;
//   background-color: rgba(0, 0, 0, 0);
// }
// #userLayout.user-layout-wrapper {
//   height: 100%;
//   overflow-y: hidden;

//   &.mobile {
//     .container {
//       .main {
//         max-width: 368px;
//         width: 98%;
//       }
//     }
//   }

//   .container {
//     width: 100%;
//     min-height: 100%;
//     // background: cyan;
//     // background: url(~@/assets/background_new.png) no-repeat;
//     background-size: 100% 100%;
//     // padding: 110px 0 144px;
//     position: absolute;

//     z-index: 1;

//     display: flex;
//     flex-direction: column;
//     // justify-content: center;
//     // a {
//     //   text-decoration: none;
//     // }
//     .main_Border{
//      flex: 1;
//      display: flex;
//      justify-content: center;
//      position: relative;
//      left: 0;
//      top: 0;
//       // flex: 1;
//       // position: absolute;
//       // left: 0;
//       // right: 0;
//       // top: 0;
//       // bottom: 0;
//       // margin: 0 auto;
//     }

//     .top {
//       // text-align: center;
//       // height: 107px;
//       background-color: #f1f1f1;
//       display: flex;
//       justify-content: center;
//       background-color: #f1f1f1;
//       position: relative;
//       top: 0;
//       left: 0;
//       .top_Border{
//         width: 1366px;
//         max-width: 1366px;
//         // position: absolute;
//         // left: 0;
//         // right: 0;
//         // top: 0;
//         // bottom: 0;
//         // margin: 0 auto;
//       }
//       .header_rightimg {
//         // width: 50px;
//         // height: 50px;
//         // background-color: skyblue;
//         position: absolute;
//         right: 65px;
//         top: 39px;
//         // img {
//         //   width: 100%;
//         //   height: 100%;
//         // }
//       }
//       .header {
//         height: 107px;
//         // line-height: 44px;
//         text-align: left;
//         padding: 0 65px 0 48px;
//         background-color: #f1f1f1;
//         display: flex;
//         position: relative;
//         left: 0;
//         top: 0;
//         .badge {
//           position: absolute;
//           display: inline-block;
//           line-height: 1;
//           vertical-align: middle;
//           margin-left: -12px;
//           margin-top: -10px;
//           opacity: 0.8;
//         }

//         .logo {
//         //   height: 44px;
// 		  vertical-align: top;
// 		  margin-top: 43px;
//         //   margin-right: 16px;
//         //   border-style: none;

//         }

//         .title {
//           font-size: 28px;
//           // color: rgba(0, 0, 0, .85);
//           color: #2C7BFF;
//           font-family: 'Microsoft YaHei Bold'
//           //  'Chinese Quote', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB',
//           //    'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji',
//           //   'Segoe UI Symbol';
//           ;
// 		  font-weight: 600;
//       line-height: 1;
//       margin-left: 20px;
// 		   display: inline-block;
// 		  //  padding: 0;
// 		   margin-top: 45px;
//         //   position: relative;
//         //   top: 45px;
//         //   right: 20px;
//         }
//       }

//       .desc {
//         font-size: 14px;
//         color: rgba(0, 0, 0, 0.45);
//         margin-top: 12px;
//         margin-bottom: 40px;
//       }
//     }

//     .main {
//       // width: 100%;
//       height: 100%;
//       min-width: 330px;
//       // width: 368px;
//       // display: flex;
//       // margin: 0 auto;
//     }
//     .left_img {
//       display: inline-block;
//       width: 676px;
//       height: 400px;
//       // margin-top: 114px;
//       // margin-top: 9%;
//       // margin-top: 1em;
//       margin-top: 1rem;
//       margin-left: 40px;
//       // height: 360px;
//       // width: 600px;
//       // position: absolute;
//       // top: 231px;
//        // right: 831px;
//       //  top: 30%;
//       // right: 650px;

//       // margin-top: -250px;
//       // margin-left: -450px;
//       > img {
//         height: 100%;
//         width: 100%;
//       }
//     }
//     .form {
//       // min-width: 800px;
//       min-width: 260px;
//       width: 540px;
//       height: 400px;
//       background: #fff;
//       // position: absolute;
//       // top: 30%;
//       // top: 231px;
//       // right: 136px;
//       // display: inline-block;
//       // margin-top: 114px;
//       //  margin-top: 9%;
//      margin-top: 1em;
//       // margin-top: -250px;
//       // margin-left: -450px;
//       // border-radius: 12px;
//       // display: flex;
//       // justify-items: center;
//       // align-items: center;
//     }
//     .footer {
//       height: 65px;
//       // position: absolute;
//       // width: 100%;
//       // bottom: 55px;

//       // padding: 0 16px;
//       // margin: 0px 0 55px;

//       text-align: center;
//        color: #fff;
//         font-size: 10px;
//         // position: absolute;
//         // top: -10px;
//         // left: 50%;
//         // transform: translateX(-50%);
//        font-family:'Microsoft YaHei';

//         // background-color:#2671E4;

//       // .links {
//       //   margin-bottom: 8px;
//       //   font-size: 14px;

//       //   a {
//       //     color: rgba(0, 0, 0, 0.45);
//       //     transition: all 0.3s;

//       //     &:not(:last-child) {
//       //       margin-right: 40px;
//       //     }
//       //   }
//       // }

//       // .copyright {
//       //   // color: rgba(0, 0, 0, 0.45);
//       //   color: #fff;
//       //   font-size: 10px;
//       //   position: absolute;
//       //   top: -10px;
//       //   left: 50%;
//       //   transform: translateX(-50%);
//       //  font-family:'Microsoft YaHei'
//       // }
//     }
//   }
// }
</style>