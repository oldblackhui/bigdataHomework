<template>
  <div id="centreLeft2">
    <div class="bg-color-black">
      <div class="d-flex pt-2 pl-2">
        <span style="color:#5cd9e8">
          <icon name="chart-pie"></icon>
        </span>
        <div class="d-flex">
          <span class="fs-xl text mx-2">地图热点图</span>
          <dv-decoration-1 style="width:1.25rem;height:.25rem; position:relative;top:-.0375rem;" />
        </div>
      </div>
      <div class="d-flex jc-center">
        <!-- 通过事件绑定的形式向子组件传递change()方法，event1可以理解为虚拟事件，装载着父组件传递过来的方法 -->
        <CentreLeft2Chart />
      </div>
    </div>
  </div>
</template>

<script>
// 调用组件，渲染效果
import CentreLeft2Chart from "@/components/echart/centerLeft/centerLeft2Chart";
export default {
  data() {
    return {};
  },
  components: {
    CentreLeft2Chart
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss">
#centreLeft2 {
  //padding: 0.2rem;
  padding: 0.0625rem;
  height: 5.125rem;
  min-width: 3.75rem;
  border-radius: 0.0625rem;
  .bg-color-black {
    // 背景蒙层设置
    //height: 0rem;
    height: 5.0625rem;
    border-radius: 0.125rem;
  }
  .text {
    color: #c3cbde;
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