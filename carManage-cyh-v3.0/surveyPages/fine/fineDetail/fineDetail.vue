<template>
	<!-- 罚款详情 -->
	<view class="content">
		<!-- 头部区域 -->
		<view class="top">
			<view class="uni-flex uni-row" style="justify-content: space-between;">
				<image src="/static/carbody.png" style="width: 200rpx;height: 200rpx;margin-top: 20rpx;"></image>
			</view>
		</view>
		<!-- 内容主体 -->
		<view class="main">

			<view class="introBody" v-for="(item,index) in productList" :key="index">
				<uni-card style="background-color: #5eb9b2;width: 100%;"><text class="intro">违规单号：{{punishId}}</text>
				</uni-card>
				<uni-card style="background-color: #5eb9b2;width: 100%;">
					<view class="uni-flex uni-row">
						<text class="intro">缴款状态：</text>
						<view v-if="item.payStatus==0" class="state" style="background-color: #ffe98f;">
							<text>未缴款</text>
						</view>
						<view v-if="item.payStatus==1" class="state" style="background-color: #e3f9ff;">
							<text>已缴款</text>
						</view>
					</view>
				</uni-card>
				<uni-card style="background-color: #5eb9b2;width: 100%;">
					<text class="intro">违规时间：{{item.accidentTime}}</text>
				</uni-card>
				<uni-card style="background-color: #5eb9b2;width: 100%;">
				<text class="intro">违规地点：{{item.accidentPoint}}</text>
				</uni-card>
				<uni-card style="background-color: #5eb9b2;width: 100%;">
				<text class="intro">处罚信息：{{item.punishMsg}}</text>
				</uni-card>
				<uni-card style="background-color: #5eb9b2;width: 100%;">
				<text class="intro">处罚金额：{{item.punishAmount}}</text>
				</uni-card>

			</view>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				punishId: '',
				productList: [],
			}
		},

		onLoad: function(options) {
			this.punishId = options.punishId;
			console.log(options);
			this.getList();
		},

		methods: {
			getList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/punish",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						punishId: _this.punishId,
					},
					success: res => {
						console.log(res.data.data.payStatus)
						_this.productList = res.data.data;
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
		top: 230rpx;
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
		font-weight: 600;
		font-size: 35rpx;
		color: #999999;
	}
	
	.main-text {
		border-bottom: 4rpx solid #fff;
	}
</style>
