<template>
  <div>
    <!-- 路线流图：查询规定时间段内，进入入口收费站或者从出口收费站出去的车流量，进而进行相应的应对措施 -->
    <Echart
      :options="options"
      id="bottomLeftChart"
      height="5.6rem"
      width="100%"
    ></Echart>
  </div>
</template>

<script>
import Echart from "@/common/echart";
import "@/common/map/bmap.js";
export default {
  data() {
    return {
      options: {},
    };
  },
  components: {
    Echart,
  },
  props: {
    cdata: {
      type: Object,
      default: () => ({}),
    },
  },
  // 使用watch进行监听数据
  watch: {
    cdata: {
      handler(newData) {
        this.options = {
          bmap: {
            // 中心点坐标
            center: [113.99, 22.68],//[116.46, 39.92],
            zoom: 10,
            roam: true,
            // 设置地图背景
            mapStyle: {
              //style: 'dark'
              styleJson: [
                {
                  featureType: "water",
                  elementType: "geometry",
                  stylers: {
                    visibility: "on",
                    color: "#113549ff"//"#031628",
                  },
                },
                {
                  featureType: "land",
                  elementType: "geometry",
                  stylers: {
                    visibility: "on",
                    color: "#091220ff"//"#000102",
                  },
                },
                {
                  featureType: "highway",
                  elementType: "all",
                  stylers: {
                    visibility: "off",
                  },
                },
                {
                  featureType: "arterial",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#000000",
                  },
                },
                {
                  featureType: "arterial",
                  elementType: "geometry.stroke",
                  stylers: {
                    color: "#0b3d51",
                  },
                },
                {
                  featureType: "local",
                  elementType: "geometry",
                  stylers: {
                    color: "#000000",
                  },
                },
                {
                  featureType: "railway",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#000000",
                  },
                },
                {
                  featureType: "railway",
                  elementType: "geometry.stroke",
                  stylers: {
                    color: "#08304b",
                  },
                },
                {
                  featureType: "subway",
                  elementType: "geometry",
                  stylers: {
                    lightness: -70,
                  },
                },
                {
                  featureType: "building",
                  elementType: "geometry.fill",
                  stylers: {
                    color: "#000000",
                  },
                },
                {
                  featureType: "all",
                  elementType: "labels.text.fill",
                  stylers: {
                    color: "#857f7f",
                  },
                },
                {
                  featureType: "all",
                  elementType: "labels.text.stroke",
                  stylers: {
                    color: "#000000",
                  },
                },
                {
                  featureType: "building",
                  elementType: "geometry",
                  stylers: {
                    color: "#022338",
                  },
                },
                {
                  featureType: "green",
                  elementType: "geometry",
                  stylers: {
                    color: "#062032",
                  },
                },
                {
                  featureType: "boundary",
                  elementType: "all",
                  stylers: {
                    color: "#465b6c",
                  },
                },
                {
                  featureType: "manmade",
                  elementType: "all",
                  stylers: {
                    color: "#022338",
                  },
                },
                {
                  featureType: 'label',
                  elementType: 'labels.text.fill',
                  stylers: {
                    color: '#29ACA8'
                  } 
                }
              ],
            },
          },
          series: [
            {
              type: "lines",
              coordinateSystem: "bmap",
              polyline: true,
              data: newData.busLines,
              silent: true,
              lineStyle: {
                normal: {
                  //color: '#c23531',
                  //color: 'rgb(200, 35, 45)',
                  opacity: 0.2,
                  width: 1,
                },
              },
              progressiveThreshold: 500,
              progressive: 200,
            },
            {
              type: "lines",
              coordinateSystem: "bmap",
              polyline: true,
              data: newData.busLines,
              lineStyle: {
                normal: {
                  width: 0,
                },
              },
              effect: {
                constantSpeed: 20,
                show: true,
                trailLength: 0.1,
                symbolSize: 1.5,
              },
              zlevel: 1,
            },
          ],
        };
      },
      immediate: true,
      deep: false,
    },
  },
};
</script>