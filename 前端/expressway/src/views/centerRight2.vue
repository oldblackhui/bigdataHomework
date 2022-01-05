<template>
  <div id="centreRight2">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color: #5cd9e8">
          <icon name="align-left"></icon>
        </span>
        <span class="fs-xl text mx-2">来自不同省份的车辆排行榜</span>
      </div>
      <div class="d-flex jc-center body-box" style="margin-top: 0">
        <dv-scroll-ranking-board :config="ranking" style="width: 100%; height: 4.5rem" ></dv-scroll-ranking-board>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ranking: {},
      d1: "2020-12-22 00:00",
      d2: "2020-12-23 00:00"
    };
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
        var resdata = [];
        this.$axios({
          method: "post",
          url: this.$api+"ck/fromcity",
          data: {
            d1: this.d1,
            d2: this.d2,
            sfzname: "广东罗田主线站",
          },
        }).then((res) => {
          //console.log(res);
          if (res.status == 200) {
            for (let i = 0; i < 14; i++) {
              resdata.push({
                name: res.data[i].cityname,
                value: res.data[i].num,
              });
            }
            /* this.config = {
              data: resdata,
            }; */
            this.ranking = {
              data: resdata,
              carousel: "page",
              unit: "辆",
              waitTime: 2500,
              rowNum: 7 // 定义有几行
            }
          }
          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(d2)
          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestData, 5000)
        });
      };
      // 发起请求
      requestData();
    }
  },
};
</script>

<style lang="scss">
#centreRight2 {
  padding: 0.0625rem;
  height: 5rem;
  min-width: 3.75rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    padding: 0.0625rem;
    height: 5.0625rem;
    border-radius: 0.125rem;
  }
  .text {
    color: #c3cbde;
  }
  .body-box {
    border-radius: 0.125rem;
    overflow: hidden;
  }
}
</style>