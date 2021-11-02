<template>
	<!-- vet-违规记录 -->
	<view class="content" :style="'height:'+contentHeight+'%'">
		<!-- 头部区域 -->
		<view class="top">
			<view style="justify-content: space-between;">
				<image src="/static/carbody.png" class="top-img"></image>
				<text class="top-text">好车险 中华行</text>
			</view>
		</view>
		<!-- 搜索框区 -->
		<view class="uni-flex uni-row">
			<form @submit="formSubmit">

				<input name="keyword" type="text" class="search-input search" placeholder="搜索车辆"
					placeholder-style="text-align: center" />
				<button form-type="submit" class="search-btn">搜索</button>

			</form>
		</view>

		<view style="position: absolute;top: 380rpx;left: 3rpx;width: 740rpx;">
			<view v-for="(item,index) in productList" :key="index" class="uni-flex uni-column card">
				<view class="uni-flex uni-row"
					style="margin-left: 30rpx;justify-content: space-between; margin-right: 60rpx;">
					<text style="top: 10rpx;left: 10rpx;">违规信息:{{item.accidentMsg}}</text>
					<text v-if="item.payStatus==='1'" style="color: #478df3;top: 140rpx;left: 10rpx;">支付状态:已处理
					</text>
					<text v-if="item.payStatus==='0'" style="color: #f92672;top: 140rpx;left: 10rpx;">支付状态:未处理
					</text>
				</view>
				<view class="uni-flex uni-row"
					style="margin-left: 30rpx;justify-content: space-between; margin-right: 20rpx;">
					<text style="color: #999999;top: 90rpx;left: 10rpx;">事故地点:{{item.accidentPoint}}</text>
					<text style="color: #999999;top: 8rpx;left: 370rpx;">惩罚信息:{{item.punishMsg}}</text>
					<text style="color: #999999;top: 50rpx;left: 370rpx;width: 400rpx;">管理员编号:{{item.manageNo}}</text>
				</view>
				<view class="uni-flex uni-row"
					style="margin-left: 30rpx;justify-content: space-between; margin-right: 20rpx;">
					<text style="color: #999999;top: 90rpx;left: 370rpx;">事发时间:{{item.accidentTime}}</text>
					<text style="color:	 #999999;top: 50rpx;left: 10rpx;">车牌号:{{item.license}}</text>
				</view>
				<view v-if="item.payStatus==='1'" style="margin-left: 300rpx;">
					<text style="top: 150rpx;left: 400rpx;">处理时间：{{item.doneTime}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				productList: [],
				// content总高度
				contentHeight: 0,
			}

		},
		onLoad() {
			this.getList();
		},
		methods: {
			getList() {
				let _this = this;
				uni.request({
					url:_this.apiServer + "/api/get/getDetail/punish",
					method: 'POST',
					data: {
						filter: 'all',
						no: _this.$store.state.userInfo.no,
						position: _this.$store.state.userInfo.position,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded', //自定义请求头信息
						'enctype': "multipart/form-data"
					},
					success: res => {
						console.log(res.data.data);
						_this.productList = res.data.data;
						_this.contentHeight = 27 + res.data.data.length * 16
						console.log(_this.contentHeight)
						if (_this.contentHeight < 100)
								{
									_this.contentHeight = 100
								}
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
	/* 背景 */
	.content {
		display: flex;
		/* align-items: center; */
		/* justify-content: center; */
		background-color: #fff;
		flex-direction: column;
		width: 100%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}

	/* ---- */
	/* 搜索区 */
	.search-input {
		background: pink;
		border-radius: 10rpx;
		margin: 20rpx 0;
		position: relative;
		height: 65rpx;
		float: left;
		width: 60%;
		background-color: #fff;
		margin-left: 25rpx;
		margin-right: 61rpx;
		border: 2rpx solid #62a9f9;
	}

	.search-input:hover {
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		animation: searchShadow 0.5s;
	}

	.search-btn {
		float: left;
		width: 25%;
		height: 69rpx;
		margin-top: 20rpx;
		line-height: 60rpx;
		background-color: #62a9f9;
		font-size: 40rpx;
		color: #fff;
	}

	.button-hover {
		opacity: 60%;
	}

	@-webkit-keyframes searchShadow {
		0% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	@keyframes searchShadow {
		0% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	/* ---- */
	/* 头部界面 */
	.top {
		position: relative;
		background-color: #7bc4ff;
		height: 250rpx;
		border-bottom-left-radius: 20rpx;
		border-bottom-right-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		margin-bottom: 30rpx;
	}

	.top .top-img {
		position: absolute;
		top: 15rpx;
		left: 15rpx;
		width: 220rpx;
		height: 220rpx;
		box-sizing: border-box;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		border-radius: 50%;
	}

	.top .top-text {
		position: absolute;
		top: 80rpx;
		right: 110rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #fff;
		text-shadow: 5rpx 5rpx 10rpx rgba(92, 163, 248, .4);
	}

	/* ---- */
	/* 内容主体区 */
	.card {
		border-radius: 15rpx;
		margin: 20rpx;
		position: relative;
		box-shadow: 2rpx 2rpx 4rpx rgba(0, 0, 0, .4);
		height: 210rpx;
		background-color: #fff;
	}
	.card text{
		font-weight: 600;
		font-size: 35rpx;
		position: absolute;
	}
	/* ---- */
</style>
