<template>
  <div id="center">
    <div class="up">
      <div
        class="bg-color-black item"
        v-for="item in titleItem"
        :key="item.title"
      >
        <p class="ml-3 colorBlue fw-b">{{ item.title }}</p>
        <div>
          <dv-digital-flop :config="item.number" style="width:1.25rem;height:.625rem;" />
        </div>
      </div>
    </div>
    <div class="down">
      <div class="ranking bg-color-black">
        <span style="color: #5cd9e8">
          <icon name="align-left"></icon>
        </span>
        <span class="fs-xl text mx-2 mb-1">今日截至当前时刻车流量排名前五的收费站</span>
        <dv-conical-column-chart :config="config" style="width:400px;height:155px;" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      titleItem: [
        {},{}
      ],
      config: {},
      d1: "2020-12-22 00:00",
      d2: "2020-12-23 00:00"
    };
  },
  components: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      /* // 起始时间
      let d1 = '';
      // 结束时间（也就是现在的时间，截至目前）
      let d2 = '';
      // 每次加5分钟的时间，代替d2
      let d3 = '';

      const setRequestTime = (endTime) => {
        //d2 = endTime;
        // 将时间字符串转换为时间戳
        let time = new Date(this.d2).getTime();

        // 既然求截至d2时间为止的数量，那么就要确定d1为何时，看d2处于哪一天来判断
        if(time >= new Date("2020-12-20 00:00") && time <= new Date("2020-12-20 23:59")) {
          this.d1 = "2020-12-20 00:00"
        } else if(time >= new Date("2020-12-21 00:00") && time <= new Date("2020-12-21 23:59")) {
          this.d1 = "2020-12-21 00:00"
        } else if(time >= new Date("2020-12-22 00:00") && time <= new Date("2020-12-22 23:59")) {
          this.d1 = "2020-12-22 00:00"
        }

        // 获取5分钟后的时间
        let time2 = new Date((time + 1000*60*5));
        let month = time2.getMonth() + 1;
        d3 = time2.getFullYear() + '-' + month + '-' + time2.getDate() + ' ' + time2.getHours() + ":" + time2.getMinutes();
      }
      // 初始化时间
      setRequestTime(this.d2); */
      
      const requestrkSfz = () => {
        this.$axios({
          method: "post",
          url: this.$api+"rk/daysfznum",
          data: {
            d1: this.d1,
            d2: this.d2,
          },
        }).then((res) => {
          let numbers = [];
          if (res.status == 200) {
            numbers.push(res.data);
            this.titleItem[0] = {
              title: "今日已使用入口收费站数量",
              number: {
                number: numbers,
                toFixed: 0,
                content: "{nt}",
                style: {
                  fontSize: 25
                }
              }
            }
          }
          // 每次请求成功之后，刷新下一次请求时间，相当于不断变换结束时间
          //setRequestTime(d3);
          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestrkSfz, 5000);
        });
      };
      // 发起请求
      requestrkSfz();

      const requestckSfz = () => {
        this.$axios({
          method: "post",
          url: this.$api+"ck/daysfznum",
          data: {
            d1: this.d1,
            d2: this.d2,
          },
        }).then((res) => {
          let numbers = [];
          if (res.status == 200) {
            numbers.push(res.data);
            this.titleItem[1] = {
              title: "今日已使用出口收费站数量",
              number: {
                number: numbers,
                toFixed: 0,
                content: "{nt}",
                style: {
                  fontSize: 25
                }
              }
            }
          }
          // 每次请求成功之后，刷新下一次请求时间，相当于不断变换结束时间
          //setRequestTime(d3);
          // 请求到数据之后10秒钟再次发起请求
          setTimeout(requestrkSfz, 10000);
        });
      };
      // 发起请求
      requestckSfz();

      const requestrkCar = () => {
        this.$axios({
          method: "post",
          url: this.$api+"rk/daycarnum",
          data: {
            d1: this.d1,
            d2: this.d2,
          },
        }).then((res) => {
          let numbers = [];
          if (res.status == 200) {
            numbers.push(res.data);
            this.titleItem[2] = {
              title: "今日入口收费站已经过车辆数量",
              number: {
                number: numbers,
                toFixed: 0,
                content: "{nt}",
                style: {
                  fontSize: 25
                }
              }
            }
          }
          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(d3);
          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestrkCar, 5000);
        });
      };
      requestrkCar();

      const requestckCar = () => {
        this.$axios({
          method: "post",
          url: this.$api+"ck/daycarnum",
          data: {
            d1: this.d1,
            d2: this.d2,
          },
        }).then((res) => {
          let numbers = [];
          if (res.status == 200) {
            numbers.push(res.data);
            this.titleItem[3] = {
              title: "今日出口收费站已经过车辆数量",
              number: {
                number: numbers,
                toFixed: 0,
                content: "{nt}",
                style: {
                  fontSize: 25
                }
              }
            }
          }
          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(d3);
          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestckCar, 5000);
        });
      };
      requestckCar();

      const requestRank = () => {
        this.$axios({
          method: "post",
          url: this.$api+"all/topsfz",
          data: {
            d1: this.d1,
            d2: this.d2,
          },
        }).then((res) => {
          let sfzdata = [];
          if (res.status == 200) {
            for(let key in res.data) {
              //console.log(key,sfz[key]);
              sfzdata.push({
                name: key,
                value: res.data[key]
              })
            }
          }
          this.config = {
            data: sfzdata.slice(0,5),
            showValue: true,
            fontSize: 10,
            columnColor: 'rgba(15, 194, 255, 0.6)'
            //columnColor: 'rgba(159, 230, 184, 0.4)'
          }
          //console.log(this.config);
          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(d3);
          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestRank, 5000);
        });
      };
      // 发起请求
      requestRank();
    }
  },
};
</script>

<style lang="scss" scoped>
#center {
  display: flex;
  flex-direction: column;
  .up {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    .item {      
      border-radius: 0.0625rem;
      padding-top: 0.15rem;
      margin-top: 0.2rem;
      width: 46%;
      height: 0.875rem;
    }
  }
  .down {
    padding: 0.07rem 0.05rem;
    padding-bottom: 0;
    width: 100%;
    display: flex;
    height: 3.1875rem;
    justify-content: space-between;
    .bg-color-black {
      height: 2.8rem;
      border-radius: 0.0625rem;
    }
    .ranking {
      //padding-left: 0;
      width: 100%;
      margin-left: 0.08rem;
      margin-right: 0.08rem;
      border-radius: 0.0625rem;
    }
    .percent {
      width: 40%;
      display: flex;
      flex-wrap: wrap;
      .item {
        width: 50%;
        height: 1.5rem;
        span {
          margin-top: 0.0875rem;
          display: flex;
          justify-content: center;
        }
      }
    }
  }
}
</style>