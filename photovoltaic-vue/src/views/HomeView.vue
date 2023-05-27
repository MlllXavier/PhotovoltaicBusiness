<template>
  <div>
    <div style="width:100%;height:50%;text-align: center;">
      <p style="font-size:50px;color: dodgerblue;">欢迎使用！</p>
    </div>
    <div class="block">
      <el-carousel :interval="4000" type="card" height="350px">
        <el-carousel-item v-for="item in urls" :key="item">
          <el-image :src=item.url style="width: 100%;height: 100%"/>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div style="width:100%;height:50%;text-align: center;">
      <router-link to="information" style="text-decoration: none;">
        <el-button type="primary" style="width: 200px;height:100px;margin: 20px; font-size: 20px;">电子档案</el-button>
      </router-link>
      <router-link to="urgent" style="text-decoration: none;">
        <el-button type="success" style="width: 200px;height:100px;margin: 20px; font-size: 20px;">业务管理</el-button>
      </router-link>
      <router-link to="subsidy" style="text-decoration: none;">
        <el-button type="warning" style="width: 200px;height:100px;margin: 20px; font-size: 20px;">补贴结算</el-button>
      </router-link>
      <router-link to="compensation" style="text-decoration: none;">
        <el-button type="danger" style="width: 200px;height:100px;margin: 20px; font-size: 20px;">追退补费用</el-button>
      </router-link>
    </div>
  </div>

  <el-input v-model="label" placeholder="请输入年份" style="width: 120px;" />
  <el-button type="primary" @click="saveYear">确定</el-button>
  <div id="echarts_box" style="height:400px;"></div>
  <div id="echarts_box3" style="height:400px;"></div>
  <div style="width: 200px; padding-left: 30px; color: dodgerblue; font-weight: bold;">所有用户各年总发电量</div>
  <div id="echarts_box2" style="height:400px;"></div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
import { ref } from "vue";

export default {
  setup(props) {
    let label = ref('2022')
    return {
      label,
    }
  },
  data() {
    return {
      mouth: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
      urls: [
        { url: require('../assets/1.jpg') },
        { url: require('../assets/2.jpg') },
        { url: require('../assets/3.jpg') },
        { url: require('../assets/4.jpg') },
        { url: require('../assets/5.jpg') },
        { url: require('../assets/6.jpg') },
      ]
    }
  },
  methods: {
    saveYear() {
      this.getMouthDataFromAllUser(this.label)
    },
    getMouthDataFromAllUser(y) {
      request.get("/subsidy/getMouthDataFromAllUser", {
        params: {
          yearIndex: y
        }
      }).then(res => {
        for (let j = 0; j < res.data.length; j++) {
          for (let i = 0; i < res.data[0].length; i++) {
            res.data[j][i] = parseFloat(res.data[j][i]).toFixed(2);
          }
        }
        var chartDom = document.getElementById('echarts_box');
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          title: {
            text: '所有用户指定年数据'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['发电量', '上网电量']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} kw'
            }
          },
          series: [
            {
              name: '发电量',
              type: 'line',
              smooth: true,
              data: res.data[0],
            },
            {
              name: '上网电量',
              type: 'line',
              smooth: true,
              data: res.data[1],
            },
          ]
        };
        option && myChart.setOption(option);

        var chartDom2 = document.getElementById('echarts_box3');
        var myChart2 = echarts.init(chartDom2);
        var option2;
        option2 = {
          title: {
            text: '用户分析数据'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['应付购电费', '中央补助金额', '总应付金额']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          series: [
            {
              name: '应付购电费',
              type: 'line',
              smooth: true,
              data: res.data[2],
            },
            {
              name: '中央补助金额',
              type: 'line',
              smooth: true,
              data: res.data[3],
            },
            {
              name: '总应付金额',
              type: 'line',
              smooth: true,
              data: res.data[4],
            },
          ]
        };
        option2 && myChart2.setOption(option2);
      })
    },
    getYearDataFromAllUser() {
      request.get("/subsidy/getYearDataFromAllUser", {
        params: {
        }
      }).then(res => {
        var chartDom = document.getElementById('echarts_box2');
        var myChart = echarts.init(chartDom);
        var option;
        var data1 = [];
        var data2 = [];
        for (let d in res.data) {
          data1.push(d);
          data2.push(res.data[d]);
        }
        option = {
          xAxis: {
            type: 'category',
            data: data1
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} kw'
            }
          },
          series: [
            {
              data: data2,
              type: 'line'
            }
          ]
        };
        option && myChart.setOption(option);
      })
    },
  },
  created() {
    this.getYearDataFromAllUser()
    this.getMouthDataFromAllUser(2022)
  },
}
</script>

<style>
.block{
  width: 70%;
  margin: 0 auto;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>