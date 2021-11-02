<template>
	<view>
		<view class="top">
			<view class="uni-flex uni-row">
				<image src="/static/carbody.png" style="width: 250rpx;height: 220rpx;"></image>
				<text
					style="font-size: x-large;color: #DCDFE6;margin-left: 150rpx;margin-top: 80rpx;">{{punishId}}</text>
			</view>
		</view>

		<view v-for="(item,index) in productList" :key="index">

			<view class=" uni-flex uni-column introBody">
				<text class="intro" style="margin-top: 20rpx;">惩罚日志编号：{{item.punishld}}</text>
				<text class="intro">勘察员工号：{{item.surveyNo}}</text>
				<text class="intro">事故说明：{{item.accidentMsg}}</text>
				<text class="intro">事故时间：{{item.accidentTime}}</text>
				<text class="intro">事故地点：{{item.accidentPoint}}</text>
				<text class="intro">具体处罚说明：{{item.punishMsg}}</text>
				<text class="intro">罚款金额：{{item.punishAmount}}</text>
				<view class="uni-flex uni-row" style="margin-bottom: 20rpx;">
					<text class="intro">缴款状态：</text>
					<view v-if="item.status==='0'">
						<text class="intro">未缴款</text>
					</view>
					<view v-if="item.status==='1'">
						<text class="intro">已缴款</text>
					</view>
				</view>
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
						'enctype': "multipart/form-data",
					},
					data:{
						punishId:_this.punishId,
					},
					success: res => {
						console.log(res.data.data);
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
	.intro {
		font-size: large;
		font-weight: 200;
	}

	.introBody {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 40rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		background-color: rgb(87, 107, 149, 0.4);
		border-radius: 15rpx;
	}

	.carImg {
		width: 650rpx;
		height: 400rpx;
		margin-top: 30rpx;

	}

	.state {
		border-radius: 12rpx;
		width: 130rpx;
		height: 60rpx;
		vertical-align: middle;
		text-align: center;
		line-height: 55rpx;
	}

	.top {
		background-color: rgb(87, 107, 149, 0.6);
		height: auto;
		border-radius: 40rpx;
	}
</style>
