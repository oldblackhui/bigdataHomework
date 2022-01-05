<template>
  <div id="centreRight1">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color: #5cd9e8">
          <icon name="chart-line"></icon>
        </span>
        <div class="d-flex">
          <span class="fs-xl text mx-2">车辆经过收费站排行榜</span>
        </div>
      </div>
      <div class="d-flex jc-center body-box">
        <dv-scroll-board
          :config="config"
          style="width: 3.75rem; height: 4rem"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      config: {},
    };
  },
  components: {},
  created() {
    // this.$nextTick 将回调延迟到下次DOM更新循环之后执行。在修改数据之后立即使用它，然后等待DOM更新。
    this.$nextTick(() => {
      this.getData();
    });
  },
  mounted() {},
  methods: {
    async getData() {
      var resdata = [];
      await this.$axios({
        method: "post",
        url: this.$api + "rk/sfz",
        data: {
          d1: "2020-12-22 00:00",
          d2: "2020-12-23 00:00",
        },
      }).then((res) => {
        const requestData = () => {
          //console.log(res);
          if (res.status == 200) {
            for (let i = 0; i < res.data.length; i++) {
              resdata.push(
                [].concat(res.data[i].sfzname).concat(res.data[i].countnum)
              );
            }
            //console.log(resdata);
            // 由于datav组件封装时，没有设置deep监听，单独更新data没有用
            this.config = {
              header: ["收费站", "数量"],
              data: resdata,
              rowNum: 7, //表格行数
              headerHeight: 35,
              headerBGC: "#0f1325", //表头
              oddRowBGC: "#0f1325", //奇数行
              evenRowBGC: "#171c33", //偶数行
              index: true,
              columnWidth: [42, 124], // 调整收费站和数量板块之间的间隔
              align: "center",
            };
            setTimeout(requestData, 5000)
          }
        };
        // 发送请求
        requestData()
      });
    },
  },
};
</script>

<style lang="scss">
#centreRight1 {
  padding: 0.14rem; //0.2rem;
  height: 5.125rem;
  min-width: 3.75rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    //width: 45%;
    height: 4.9rem;
    border-radius: 0.0625rem;
  }
  .text {
    color: #c3cbde;
  }
  .body-box {
    border-radius: 0.125rem;
    //overflow: hidden;
  }
}
</style>