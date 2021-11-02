<template>
	<!-- 维修详情页 -->
	<view class="content">
		<view class="main">
			<view class="bg-gray"><text class="main-text">申报人信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{name}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">职位</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{position}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{no}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>

			<view class="bg-gray"><text class="main-text">申报信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">申报时间</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{surveyTime}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{repairLicense}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">维修原因</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{repairMsg}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
			<view class="bg-gray"><text class="main-text">审批人信息</text></view>
			<view class="uni-flex uni-row uni-card">
				<text class="word" style="-webkit-flex: 1;flex: 1;">审批人编号</text>
				<text class="word" style="-webkit-flex: 1;flex: 1;">{{vetNo}}</text> <!-- 通过api接口从数据库调取数据自动生成 -->
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		onLoad: function(options) {
			console.log('onload')
			this.repairId = options.repairId;
			this.getRepairDetail();
		},
		data() {
			return {
				repairId: '',
				name: this.$store.state.userInfo.name,
				no: this.$store.state.userInfo.no,
				position: this.$store.state.userInfo.position,
				surveyTime: '',
				repairLicense: '',
				repairMsg: '',
				vetNo: '',
			}
		},

		methods: {
			getRepairDetail() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/repair",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						repairId: _this.repairId,
					},
					success: res => {
						console.log(res.data.data);
						_this.surveyTime = res.data.data.surveyTime;
						_this.repairLicense = res.data.data.repairLicense;
						_this.repairMsg = res.data.data.repairMsg;
						_this.vetNo = res.data.data.vetNo;
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
