<template>
  <div>
    <!-- 地图热点图：查询规定时间段内，经过出口或入口收费站的车辆来源省份分析统计 -->
    <Echart
      id="centreLeft2Chart"
      ref="centreLeft2ChartRef"
      :options="options"
      height="4.3rem"
      width="9rem"
    ></Echart>
  </div>
</template>

<script>
import Echart from "@/common/echart";
//import "@/common/map/bmap.js";
export default {
  data() {
    return {
      options: {},
    };
  },
  components: {
    Echart,
  },
  // 接收调用该组件的其他组件传进来的值
  // index.vue组件调用该chart.vue组件，传进来cdata值，该组件就对传进来的数据进行处理
  // 然后chart组件又调用Echart组件，传入options（该options调用了处理的数据）
  props: {
    cdata: {
      type: Object,
      default: () => ({}),
    },
  },
  watch: {
    cdata: {
      handler(newData) {
        this.options = {
          title: {
            text: "经过收费站的车辆来源",
            left: "center",
          },
          tooltip: {
            trigger: "item",
          },
          /* bmap: {
            center: [114.09, 27.55],//[104.114129, 37.550339],
            zoom: 5,
            roam: true,
            // 设置地图背景
            mapStyle: {
              styleJson: [
                {
                  featureType: "water",
                  elementType: "all",
                  stylers: {
                    color: "#031628",
                  },
                },
                {
                  featureType: "land",
                  elementType: "all",
                  stylers: {
                    color: "#070A17",
                  },
                },
                {
                  featureType: "boundary",
                  elementType: "geometry",
                  stylers: {
                    color: "#064f85",
                  },
                },
                {
                  featureType: "railway",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "highway",
                  elementType: "all",
                  stylers: {
                    visibility: "off"
                  },
                },
                {
                  featureType: "highway",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#005b96",
                    lightness: 1,
                  },
                },
                {
                  featureType: "highway",
                  elementType: "labels",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "arterial",
                  elementType: "geometry",
                  stylers: {
                    color: "#004981",
                  },
                },
                {
                  featureType: "arterial",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#00508b",
                  },
                },
                {
                  featureType: "poi",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "green",
                  elementType: "all",
                  stylers: {
                    color: "#056197",
                    visibility: "off",
                  },
                },
                {
                  featureType: "subway",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "manmade",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "local",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "arterial",
                  elementType: "labels",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "boundary",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#029fd4",
                  },
                },
                {
                  featureType: "building",
                  elementType: "all",
                  stylers: {
                    color: "#1a5787",
                  },
                },
                {
                  featureType: "label",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: 'label',
                  elementType: 'labels.text.fill',
                  stylers: {
                    color: '#2683DE'
                  }
                }
              ],
            },
          }, */
          visualMap: {
            min: 0,
            max: 1,
            show: false,
            seriesIndex: 0,
            // 颜色
            inRange: {
              color: ['rgba(41,166,206, .5)', 'rgba(41, 172, 168, .9)'],//rgb(41, 172, 168)
            },
            realtime: true // 拖拽时，是否实时更新
          },
          // 底部背景
          geo: {
            show: true,
            aspectScale: 0.85, //长宽比
            zoom: 1.25, // 地图大小
            top: '9.9%',
            left: '23%',
            map: 'china', // 显示中国地图
            roam: true, // 是否允许缩放
            itemStyle: {
              normal: {
                areaColor: '#0F1325', // 设置背景颜色
                //areaColor: 'rgba(0,0,0,0)',
                shadowColor: 'rgba(7,114,204, .8)',
                shadowOffsetX: 0,
                shadowOffsetY: 0.5,
              },
              emphasis: {
                areaColor: '#00aeef',
              },
            },
          },
          series: [
            {
              name: "city",
              type: "scatter",
              coordinateSystem: "geo",
              // coordinateSystem: "bmap" // 在使用百度地图时使用这个
              data: newData.resData,
              symbolSize: function (val) {
                return val[2] / 10;
              },
              encode: {
                value: 2,
              },
              label: {
                formatter: "{b}",
                position: "right",
                show: false,
              },
              emphasis: {
                label: {
                  show: true,
                },
              },
              
            },
            {
              name: "Top 5",
              type: "effectScatter",
              coordinateSystem: "geo", // 绿色图标显示出来
              // coordinateSystem: "bmap"
              data: newData.resData.sort(function(a,b){
                return b.value - a.value
              }).slice(0,5),
              // 设置绿色图标
              symbolSize: function (val) {
                return val[2] / 6;
              },
              encode: {
                value: 2,
              },
              showEffectOn: "render",
              rippleEffect: {
                brushType: "stroke",
              },
              hoverAnimation: true, // 鼠标靠近环形圆圈时可以放大
              label: {
                formatter: "{b}",
                position: "right",
                distance: 1,
                show: true,
              },
              itemStyle: {
                //color: '#29ACA8',
                shadowBlur: 10,
                shadowColor: "#333",
              },
              emphasis: {
                scale: true,
              },
              zlevel: 1,
            },
          ],
        };
      },
      immediate: true,
      //deep: true,
    },
  },
};
</script>
<style>
/*将百度地图Logo去掉 */
.anchorBL {
  display: none;
}
</style>