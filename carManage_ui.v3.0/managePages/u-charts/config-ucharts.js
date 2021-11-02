/*
 * uCharts®
 * 高性能跨平台图表库，支持H5、APP、小程序（微信/支付宝/百度/头条/QQ/360）、Vue、Taro等支持canvas的框架平台
 * Copyright (c) 2021 QIUN®秋云 https://www.ucharts.cn All rights reserved.
 * Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
 * 复制使用请保留本段注释，感谢支持开源！
 * 
 * uCharts®官方网站
 * https://www.uCharts.cn
 * 
 * 开源地址:
 * https://gitee.com/uCharts/uCharts
 * 
 * uni-app插件市场地址：
 * http://ext.dcloud.net.cn/plugin?id=271
 * 
 */

// 主题颜色配置：如每个图表类型需要不同主题，请在对应图表类型上更改color属性
const color = ['#e3f9ff', '#ffe98f', '#debcde', '#d5ffd5', '#73C0DE', '#3CA272', '#FC8452', '#9A60B4', '#ea7ccc'];

//事件转换函数，主要用作格式化x轴为时间轴，根据需求自行修改
const formatDateTime = (timeStamp, returnType)=>{
  var date = new Date();
  date.setTime(timeStamp * 1000);
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? ('0' + m) : m;
  var d = date.getDate();
  d = d < 10 ? ('0' + d) : d;
  var h = date.getHours();
  h = h < 10 ? ('0' + h) : h;
  var minute = date.getMinutes();
  var second = date.getSeconds();
  minute = minute < 10 ? ('0' + minute) : minute;
  second = second < 10 ? ('0' + second) : second;
  if(returnType == 'full'){return y + '-' + m + '-' + d + ' '+ h +':' + minute + ':' + second;}
  if(returnType == 'y-m-d'){return y + '-' + m + '-' + d;}
  if(returnType == 'h:m'){return  h +':' + minute;}
  if(returnType == 'h:m:s'){return  h +':' + minute +':' + second;}
  return [y, m, d, h, minute, second];
}

module.exports = {
  //instance为实例变量承载属性，不要删除
  "instance":{},
  //option为opts及eopts承载属性，不要删除
  "option":{},
  
  "pie":{
      "type": "pie",
      "canvasId": "",
      "canvas2d": false,
      "background": "none",
      "animation": true,
      "timing": "easeOut",
      "duration": 1000,
      "color": [
          "#e3f9ff",
          "#ffe98f",
          "#debcde",
          "#d5ffd5",
          "#73C0DE",
          "#3CA272",
          "#FC8452",
          "#9A60B4",
          "#ea7ccc"
      ],
      "padding": [
          5,
          5,
          5,
          5
      ],
      "rotate": false,
      "errorReload": true,
      "fontSize": 13,
      "fontColor": "#666666",
      "enableScroll": false,
      "touchMoveLimit": 60,
      "enableMarkLine": false,
      "dataLabel": true,
      "dataPointShape": true,
      "dataPointShapeType": "solid",
      "tapLegend": true,
      "legend": {
          "show": true,
          "position": "bottom",
          "float": "center",
          "padding": 5,
          "margin": 5,
          "backgroundColor": "rgba(0,0,0,0)",
          "borderColor": "rgba(0,0,0,0)",
          "borderWidth": 0,
          "fontSize": 13,
          "fontColor": "#666666",
          "lineHeight": 11,
          "hiddenColor": "#CECECE",
          "itemGap": 10
      },
      "extra": {
          "pie": {
              "activeOpacity": 0.5,
              "activeRadius": 10,
              "offsetAngle": 0,
              "customRadius": 0,
              "labelWidth": 15,
              "border": true,
              "borderWidth": 3,
              "borderColor": "#FFFFFF",
              "linearType": "none"
          },
          "tooltip": {
              "showBox": true,
              "showArrow": true,
              "showCategory": false,
              "borderWidth": 0,
              "borderRadius": 0,
              "borderColor": "#000000",
              "borderOpacity": 0.7,
              "bgColor": "#000000",
              "bgOpacity": 0.7,
              "gridType": "solid",
              "dashLength": 4,
              "gridColor": "#CCCCCC",
              "fontColor": "#FFFFFF",
              "splitLine": true,
              "horizentalLine": false,
              "xAxisLabel": false,
              "yAxisLabel": false,
              "labelBgColor": "#FFFFFF",
              "labelBgOpacity": 0.7,
              "labelFontColor": "#666666"
          }
      }
  }
}