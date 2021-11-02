<template>
	<!-- 管理员借车记录详情页 -->
	<view class="content">
		<view class="main">
			<view class="bg-gray"><text class="main-text">申报人信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{name}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{no}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="bg-gray"><text class="main-text">派车信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">派车信息</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{sendTime}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{sendLicense}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">勘察人编号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{svNo}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">地点</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{place}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">事故情况</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{situation}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="bg-gray"><text class="main-text">审批人信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">审批人编号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{vetNo}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="bg-gray"><text class="main-text">审批流程</text></view>
		</view>

	</view>
</template>

<script>
	export default {
		onLoad: function(options) {
			console.log('onload')
			this.sendCarId = options.sendCarId;
			this.getRepairDetail();
		},
		data() {
			return {
				sendCarId: '',
				name: this.$store.state.userInfo.name,
				no: this.$store.state.userInfo.no,
				sendTime: '',
				sendLicense: '',
				place: '',
				vetNo: '',
				situation: '',
				svNo:''
			}
		},

		methods: {
			getRepairDetail() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/sendCar",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						sendCarId: _this.sendCarId,
					},
					success: res => {
						console.log(res.data.data);
						_this.sendCarId = res.data.data.sendCarId;
						_this.sendTime = res.data.data.sendTime;
						_this.sendLicense = res.data.data.sendLicense;
						_this.place = res.data.data.place;
						_this.vetNo = res.data.data.vetNo;
						_this.situation = res.data.data.situation;
						_this.svNo = res.data.data.svNo;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			}
		}

	}
</script>

<style>
	* {
		padding: 0;
		margin: 0;
	}

	/* 背景 */
	.content {
		display: flex;
		align-items: center;
		/* justify-content: center; */
		flex-direction: column;
		width: 100%;
		height: 150%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}

	/* ---- */
	/* 内容主体 */
	.main {
		position: absolute;
		top: 45rpx;
		width: 80%;
	}

	.bg-gray {
		color: #ffffff;
		font-weight: 600;
		font-size: 40rpx;
		text-align: center;
		border-radius: 15rpx;
		box-sizing: border-box;
	}

	.word {
		text-align: center;
		height: 70rpx;
		line-height: 70rpx;
	}

	.main-text {
		border-bottom: 4rpx solid #fff;
	}
</style>
