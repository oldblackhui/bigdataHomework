<template>
  <div>
    <!-- 父组件向子组件传递数据以属性的方式进行传递，如以下该形式 -->
    <Chart :cdata="cdata" />
  </div>
</template>

<script>
import Chart from "./chart.vue";
export default {
  data() {
    return {
      cdata: {
        // 处理后的排好序的数据数组
        resData: []
      },
      d1: "2020-12-22 06:00",
      d2: "2020-12-22 07:00"
    };
  },
  components: {
    Chart,
  },
  mounted() {   
    this.getResData();
  },
  methods: {
    getResData() {
      /* const setRequestTime = (startTime) => {
        this.d1 = startTime
        // 将时间字符串转换为时间戳
        let time = new Date(this.d1).getTime();
        // 获取5分钟后的时间
        let time2 = new Date((time + 1000*60*5));
        let month = time2.getMonth() + 1;
        this.d2 = time2.getFullYear() + '-' + month + '-' + time2.getDate() + ' ' + time2.getHours() + ":" + time2.getMinutes();
      }
      // 初始化时间
      setRequestTime(this.d1); */

      const requestData = () => {
        this.$axios({
          // 注意是get还是post请求，一般有body的是post请求，否则会出错
          method: "post",
          url: this.$api+"ck/fromcity",
          data: {
            d1: this.d1,
            d2: this.d2,
            sfzname: "广东罗田主线站",
          },
        })
        .then((res) => {
          console.log(res);
          let setData = []; // 定义一个空数组
          for(let i=0; i<res.data.length; i++) {
            this.cdata.resData.push({
              name: res.data[i].cityname,
              // 将num和lng，lat拼接到一个数组里面，相当于convertData函数处理返回的数据
              value: setData.concat(res.data[i].lng).concat(res.data[i].lat).concat(Math.log(res.data[i].num+1)*50) // 使用log函数，使得差异大的数据差异减小
            })
          }

          // 每次请求成功之后，刷新下一次请求时间
          //setRequestTime(this.d2)

          // this.$emit()第一个参数是虚拟事件，相当于把父组件传递过来的方法在这里注册了
          // 第二个参数是要传递的参数，也就是子组件向父组件传递的参数
          //this.$emit('event1',this.d1);

          // 请求到数据之后5秒钟再次发起请求
          setTimeout(requestData, 5000)
        })
      } 
      // 发起请求
      requestData();
    }
  },
};
</script>

<style lang="scss" scoped>
</style>