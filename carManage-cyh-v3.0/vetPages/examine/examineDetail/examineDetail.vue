<template>
	<view>
		<view class="uni-flex uni-row top">
			<image src="/static/examTop.png" style="width: 360rpx;height: 210rpx;margin-bottom: 20rpx;"></image>
			<text style="font-size: x-large;margin-top: 120rpx; color: #333333;margin-bottom: 20rpx;">{{type}}</text>
		</view>

		<!-- 借车审批 -->
		<view v-if="type==='借车申报'">
			<view class="uni-flex uni-column forms">
				<text class="intro">出借单编号：{{list.lendId}}</text>
				<text class="intro">出借车辆车牌：{{list.lendLicense}}</text>
				<text class="intro">出借人姓名：{{list.lendName}}</text>
				<text class="intro">出借人手机号：{{list.lendPhone}}</text>
				<text class="intro">出借人身份证照片：</text>
				<image class="img">{{list.lendIdcardImg}}</image>
				<text class="intro">出借人驾驶证照片：</text>
				<image class="img">{{list.lendDrivecardImg}}</image>
				<text class="intro">出借时间：{{list.lendTime}}</text>
				<text class="intro">出借持续天数：{{list.lendDays}}</text>
			</view>
			<view class="uni-flex uni-column forms" v-if="fromPage=='examDone'">
				<text class="intro">审批结果：{{list.result}}</text>
			</view>


			<view class="uni-flex uni-row" style="margin-top: 40rpx;" v-if="fromPage=='examToDo'">
				<button @click="yes" class="yesButton">同意</button>
				<button @click="no" class="noButton">否决</button>
			</view>
		</view>

		<!-- 维修审批 -->
		<view v-if="type==='维修申报'">
			<view class="uni-flex uni-column forms">
				<text class="intro">维修单编号：{{list.repairId}}</text>
				<text class="intro">维修车辆车牌：{{list.repairLicense}}</text>
				<text class="intro">维修说明：{{list.repairMsg}}</text>
				<text class="intro">维修部位照片：</text>
				<image class="img">{{list.repairImg}}</image>
				<text class="intro">维修时间：{{list.repairTime}}</text>
				<text class="intro">勘察员工号：{{list.surveyNo}}</text>
				<text class="intro">勘察员操作时间：{{list.surveyTime}}</text>
			</view>
			<view class="uni-flex uni-column forms" v-if="fromPage=='examDone'">
				<text class="intro">审批结果：{{list.result}}</text>
			</view>


			<view class="uni-flex uni-row" style="margin-top: 40rpx;" v-if="fromPage=='examToDo'">
				<button @click="yes" class="yesButton">同意</button>
				<button @click="no" class="noButton">否决</button>
			</view>
		</view>

		<!-- 保养审批 -->
		<view v-if="type==='保养申报'">
			<view class="uni-flex uni-column forms">
				<text class="intro">保养单编号：{{list.protectId}}</text>
				<text class="intro">保养车辆车牌：{{list.protectLicense}}</text>
				<text class="intro">保养时间：{{list.protectTime}}</text>
				<text class="intro">保养说明：{{list.protectMsg}}</text>
			</view>
			<view class="uni-flex uni-column forms" v-if="fromPage=='examDone'">
				<text class="intro">审批结果：{{list.result}}</text>
			</view>

			<view class="uni-flex uni-row" style="margin-top: 40rpx;" v-if="fromPage=='examToDo'">
				<button @click="yes" class="yesButton">同意</button>
				<button @click="no" class="noButton">否决</button>
			</view>
		</view>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				type: '',
				list: [],
				examineId: '',
				fromPage: '',

			}
		},

		onLoad: function(options) {
			this.type = options.type;
			this.examineId = options.examineId;
			this.fromPage = options.fromPage;
			console.log(options);
			this.getList();

		},

		methods: {
			getList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/examine",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						examineId: _this.examineId,
						type: _this.type,
					},
					success: res => {
						console.log(res.data.data);
						_this.list = res.data.data;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},

			yes() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/yes",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						examineId: _this.examineId,
						type: _this.type,
						result: 1,
					},
					success: res => {
						uni.showToast({
							title: '该申报已同意',
							icon: 'success',
							duration: 1000,
						});
						setTimeout(function() {
							uni.navigateBack({})
						}, 1000);
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			no() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						examineId: _this.examineId,
						type: _this.type,
						result: 0,
					},
					success: res => {
						uni.showToast({
							title: '该审批已拒绝',
							icon: 'success',
							duration: 1000,
						});
						setTimeout(function() {
							uni.navigateBack({})
						}, 1000);
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
		font-size: initial;
		font-weight: 200;
	}

	.introBody {
		display: flex;
		flex-direction: column;
		align-items: center;
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

	.img {
		width: 200rpx;
		height: 200rpx;
		margin-left: 250rpx;
	}

	.forms {
		margin-top: 40rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		background-color: rgb(87, 107, 149, 0.4);
		border-radius: 15rpx;
	}

	.yesButton {
		background-color: rgb(206, 251, 229);
		border-radius: 35rpx;
	}

	.noButton {
		background-color: rgb(255, 202, 204);
		border-radius: 35rpx;
	}
</style>
