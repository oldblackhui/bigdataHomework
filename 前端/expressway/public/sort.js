var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

// 起始时间
let date = "2020-12-22 00:00";
// 间隔多少分钟
let time = '10';
// 结束时间
//let d2 = '';

/* const setRequestTime = (startTime) => {
  date = startTime;
  // 将时间字符串转换为时间戳
  let time1 = new Date(date).getTime();
  // 获取time分钟后的时间戳
  let time2 = new Date((time1 + 1000*60*time));

  let month = time2.getMonth() + 1;
  d2 = time2.getFullYear() + '-' + month + '-' + time2.getDate() + ' ' + time2.getHours() + ":" + time2.getMinutes();
}

// 初始化时间
setRequestTime("2020-12-21 23:55"); */

const requestData = () => {
  $.ajax({
    type: "POST", // 数据的提交方式
    url: "http://localhost:8088/ck/timecar",
    contentType: "application/json;charset=UTF-8",
    data: JSON.stringify({ // 转化为json字符串
      date: date,
      time: time,
      num: '10' // num为数据条数，就是一共有多少组数据
    }),
    dataType: "json", // 用来指定服务器返回的data数据类型必须是JSON类型
    success: function (res) {
      var resdata = [];
      res.forEach(car => {
        resdata.push([].concat(car.car).concat(car.num).concat(car.time))
      })
      resdata.unshift(["car", "num", "time"]);
      //console.log(resdata);
      run(resdata); // 请求有延迟
      /* if(resdata.length != 0) {
        run(resdata);
      } */

      // 每次请求成功之后，刷新下一次请求时间
      //setRequestTime(d2)
      // 请求到数据之后5秒钟再次发起请求
      setTimeout(requestData, 5000)
    }
  });
}
// 发起请求
requestData();

function run(_rawData) {
  const cars = [
    //'一型车', // 一型车数据与其他型车差距过大，呈现效果不好，所以去掉
    '二型车',
    '三型车',
    '四型车',
    '五型车',
    '六型车'
  ];
  const datasetWithFilters = [];
  const seriesList = [];
  echarts.util.each(cars, function (car) {
    var datasetId = 'dataset_' + car;
    datasetWithFilters.push({
      id: datasetId,
      fromDatasetId: 'dataset_raw',
      transform: {
        type: 'filter',
        config: {
          and: [
            { dimension: 'time', gte: 10 }, // gte这里要是number类型的，映射time列
            { dimension: 'car', '=': car }
          ]
        }
      }
    });
    seriesList.push({
      type: 'line',
      datasetId: datasetId,
      showSymbol: false,
      name: car,
      endLabel: {
        show: true,
        formatter: function (params) {
          return params.value[0] + ': ' + params.value[1]; // 线条旁边显示的内容，这里显示car和num
        }
      },
      labelLayout: {
        moveOverlap: 'shiftY'
      },
      emphasis: {
        focus: 'series'
      },
      encode: {
        x: 'time', // 表示维度为time映射到x轴
        y: 'num', // 表示维度为num映射到y轴
        label: ['car', 'num'],
        itemName: 'time',
        tooltip: ['num']
      }
    });
  });
  //console.log(datasetWithFilters)
  //console.log(seriesList)
  option = {
    animationDuration: 5000, // 增长速度
    dataset: [
      {
        id: 'dataset_raw',
        source: _rawData
      },
      ...datasetWithFilters
    ],
    /* title: {
      text: ''
    }, */
    tooltip: {
      order: 'valueDesc',
      trigger: 'axis'
    },
    xAxis: {
      name: 'time interval',
      type: 'category',
      nameLocation: 'end'
    },
    yAxis: {
      name: 'num'
    },
    grid: {
      right: 140
    },
    series: seriesList
  };
  myChart.setOption(option);
}

option && myChart.setOption(option);
