<template>
	<view>
		<view class="page-body">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 300px;" :latitude="latitude" :longitude="longitude" :markers="covers"
					:polyline="polylines">
				</map>
			</view>
		</view>
		<view v-if='status===1'><button @click="start">开始行车</button></view>
		<view v-if='status===0'><button @click="end">结束任务</button></view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				id: 0,
				latitude: '',
				longitude: '',
				covers: [],
				status: 1,
				timer: null,
				time: '',
				nowLatitude:'',
				nowLongtitude:'',
				polylines: [{
					points: [{
							latitude: 30.26555,
							longitude: 120.1536
						},
						{
							latitude: 30.26555,
							longitude: 120.160704
						},
					],
					color: "#0000AA", //线的颜色
					width: 2, //线的宽度
					dottedLine: true, //是否虚线

				}],
			}
		},
		mounted: function() {
			this.getAuthorizeInfo();
		},
		methods: {

			start() {
				let _this = this;
				_this.status = 0;
				_this.timer = setInterval(_this.getLocation, 5000);
			},
			getLocation() {
				var _this = this;
				let timestamp = new Date().getTime();
				_this.time = timestamp;
				uni.getLocation({
					type: 'wgs84',
					success: function(res) {
						_this.latitude = res.latitude;
						_this.longitude = res.longitude;
						_this.covers = [{
							id: 0,
							latitude: res.latitude,
							longitude: res.longitude,
							iconPath: '/static/car.png',
							title: '当前位置',
							content: '当前位置',
							width: 40,
							height: 40
						}]
						_this.nowLatitude=res.latitude;
						_this.nowLongitude=res.longitude;
						_this.polylines[0].points.push({
							latitude: res.latitude,
							longitude: res.longitude,
						});
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
					}
				});
				uni.request({
					url: _this.apiServer + "/api/post/location",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						//派车单编号、车牌号
						no: _this.$store.state.userInfo.no,
						time:_this.time,
						latitude:_this.nowLatitude,
						longtitude:_this.nowLongitude,
					},
					success: res => {
						console.log('success');

					},
					fail: res => {
						console.log("发生了错误");
					},
				})

			},


			end() {
				let _this = this;
				_this.status = 1;
				clearTimeout(this.timer);
				_this.getLocation();
				uni.showToast({
					title: '出车任务结束',
					icon: 'success',
					duration: 1000,
				});
				setTimeout(function() {
					uni.navigateBack({
					})
				}, 1000);

			},
			getAuthorizeInfo(a = "scope.userLocation") {
				var _this = this;
				uni.authorize({
					scope: a,
					success() {
						uni.getLocation({
							type: 'wgs84',
							success: function(res) {
								_this.latitude = res.latitude;
								_this.longitude = res.longitude;
								_this.covers = [{
									id: 0,
									latitude: res.latitude,
									longitude: res.longitude,
									iconPath: '/static/car.png',
									title: '当前位置',
									content: '当前位置',
									width: 40,
									height: 40
								}]

								console.log('当前位置的经度：' + res.longitude);
								console.log('当前位置的纬度：' + res.latitude);
							}
						});


					},
					fail() {
						console.log("你拒绝了授权，无法获得位置信息")
						uni.navigateBack({

						});
					}
				})
			},

		}
	}
</script>

<style>
	page {
		font-size: 30upx;
		font-family: "gray";
		margin-top: 15upx;
		margin-left: 20upx;
	}

	.current-location {
		width: 22%;
		height: 35upx;
		float: left;
		color: dimgray;
	}

	.address {
		width: 69%;
		height: 35upx;
		float: left;
		text-overflow: ellipsis;
		/* 显示省略号 */
		white-space: nowrap;
		/* 强制字体不换行 */
		overflow: hidden;
		/* 隐藏 */
		color: dimgray;
	}

	image {
		height: 30upx;
		width: 30upx;
	}
</style>
