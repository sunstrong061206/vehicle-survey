<template>
	<view style="position: absolute;top: 0;left: 0;">
		<view class="page-body">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 1200rpx;" :latitude="latitude" :longitude="longitude" :markers="covers"
					:polyline="polylines">
				</map>
			</view>
		</view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				driveId:'',
				id: 0,
				latitude: '',
				longitude: '',
				covers: [],
				status: 1,
				timer: null,
				time: '',
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
			this.getLocation();
		},
		onLoad:function(options) {
			this.driveId=options.driveId;
			console.log(options)
		},
		methods: {
			getLocation() {
				var _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/drive",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						driveId:_this.driveId,
					},
					success: res => {
						console.log(res.data.data[0].route)
						let length=res.data.data[0].route.length-1;
						_this.polylines[0].points=res.data.data[0].route;
						_this.latitude=res.data.data[0].route[0].latitude;
						_this.longitude=res.data.data[0].route[0].longitude;
						_this.covers = [{
							id: 0,
							latitude: res.data.data[0].route[0].latitude,
							longitude:  res.data.data[0].route[0].longitude,
							iconPath: '/static/car.png',
							title: '出发位置',
							content: '出发位置',
							width: 40,
							height: 40
						},{
							id: 1,
							latitude: res.data.data[0].route[length].latitude,
							longitude:  res.data.data[0].route[length].longitude,
							iconPath: '/static/car.png',
							title: '结束位置',
							content: '结束位置',
							width: 40,
							height: 40
						}]
						console.log(_this.polylines[0])
					},
					fail: res => {
						console.log("发生了错误");
					},
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
