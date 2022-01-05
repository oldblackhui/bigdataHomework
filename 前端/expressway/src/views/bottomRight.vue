<template>
  <div id="bottomRight">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color:#5cd9e8">
          <icon name="chart-area"></icon>
        </span>
        <div class="d-flex">
          <span class="fs-xl text mx-2">五种车型车流量累积变化展示图</span>
          <div class="decoration2">
            <dv-decoration-2 :reverse="true" style="width:5px;height:6rem;" />
          </div>
        </div>
      </div>
      <div style="display: flex;">
        <!-- <BottomRightChart /> -->
        <!-- 这里使用打包后的路径 -->
        <iframe src="./sort.html" frameborder="0" style="flex: 1; height: 6.5rem"></iframe>
      </div>
    </div>
  </div>
</template>

<script>
// 由于echarts版本问题，动态折线图需要5.几版本，但目前不会修改，所以不引用组件，采用iframe,将html写好的页面显示出来
//import BottomRightChart from "@/components/echart/bottom/bottomRightChart";
export default {
  data() {
    return {
    };
  },
  /* components: {
    BottomRightChart
  }, */
  mounted() {},
  methods: {}
};
</script>

<style lang="scss">
#bottomRight {
  padding: 0.2rem 0.2rem 0;
  height: 6.5rem;
  min-width: 3.75rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    height: 6.1rem;
    border-radius: 0.125rem;
  }
  .text {
    color: #c3cbde;
  } //下滑线动态
  .decoration2 {
    position: absolute;
    right: 0.125rem;
  }
  .chart-box {
    margin-top: 0.2rem;
    width: 2.125rem;
    height: 2.125rem;
    .active-ring-name {
      padding-top: 0.125rem;
    }
  }
}
</style>