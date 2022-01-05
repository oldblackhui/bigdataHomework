<template>
  <div>
    <Chart :cdata="cdata" />
  </div>
</template>

<script>
import Chart from "./chart.vue";
// 解决this.$echarts.color.modifyHSL的报错，再引入一下echarts
import echarts from "echarts";
export default {
  data() {
    return {
      cdata: {
        points: [],
        busLines: [],
        hStep: 0,
      },
      d1: "2020-12-21 23:30",
      d2: "2020-12-22 00:00"
    };
  },
  components: {
    Chart,
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      /* // 起始时间
      let d1 = "";
      // 结束时间
      let d2 = "";

      const setRequestTime = (startTime) => {
        d1 = startTime;
        // 将时间字符串转换为时间戳
        let time = new Date(d1).getTime();
        // 获取5分钟后的时间戳
        let time2 = new Date(time + 1000 * 60 * 5);
        let month = time2.getMonth() + 1;
        d2 =
          time2.getFullYear() +
          "-" +
          month +
          "-" +
          time2.getDate() +
          " " +
          time2.getHours() +
          ":" +
          time2.getMinutes();
      };

      // 初始化时间
      setRequestTime("2020-12-21 23:55"); */

      const requestData = () => {
        this.$axios({
          method: "post",
          url: this.$api+"rk/rktock",
          data: {
            d1: this.d1,
            d2: this.d2,
            sfzname: "广东罗田主线站",
          },
        }).then((res) => {
          console.log(res.data);
          for (let i = 0; i < res.data.length; i++) {
            this.cdata.points.push(
              []
                .concat(parseInt(res.data[i].cklng * 1e5))
                .concat(parseInt(res.data[i].cklat * 1e5))
                .concat(parseInt((res.data[i].rklng - res.data[i].cklng) * 1e5))
                .concat(parseInt((res.data[i].rklat - res.data[i].cklat) * 1e5))
            );
          }
          //console.log(this.cdata.points);
          this.cdata.hStep = 300 / (this.cdata.points.length - 1);
          // 使用map函数，主要是取下标值，用在随机添加线条颜色中
          // 同样是使用到.push为数组添加元素属性：coords和lineStyle属性，lineStyle还包含normal属性
          this.cdata.points.map((busLine, idx) => {
            var prevPt;
            var respoints = [];
            for (var i = 0; i < busLine.length; i += 2) {
              var pt = [busLine[i], busLine[i + 1]];
              if (i > 0) {
                pt = [prevPt[0] + pt[0], prevPt[1] + pt[1]];
              }
              prevPt = pt;

              respoints.push([pt[0] / 1e5, pt[1] / 1e5]);
            }
            //console.log(respoints);
            this.cdata.busLines.push({
              coords: respoints,
              // 随机添加线条颜色
              lineStyle: {
                normal: {
                  color: echarts.color.modifyHSL(
                    "#5A94DF",
                    Math.round(this.cdata.hStep * idx)
                  ),
                },
              },
            });
          });
          //console.log(this.cdata.busLines);
          
          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(d2);
          // 请求到数据之后1秒钟再次发起请求
          //setTimeout(requestData, 30000);
        });
      };
      // 发起请求
      requestData();
    }
  }
};
</script>

<style lang="scss" scoped>
</style>