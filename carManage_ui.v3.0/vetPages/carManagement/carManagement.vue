<template>
	<!-- vet-车辆管理 -->
	<view class="content" :style="'height:'+contentHeight+'%'">
		<!-- 头部区域 -->
		<view class="top">
			<image src="/static/carbody.png" class="top-img"></image>
			<text class="top-text">好车险 中华行</text>
			<view class="uni-flex uni-row" style="margin-top: 150rpx;">
				<button @click="confirmDialog2" class="top-btn">管理</button>
			</view>
		</view>
		<!-- 搜索区域 -->
		<!-- 但搜索功能似乎没做完 -->
		<view>
			<uni-forms :model="search" ref="search">
				<input name="license" class="search-input search" placeholder="车牌号模糊查询" focus maxlength="15"
					placeholder-style="text-align: center" v-model="search.license">
				</input>
				<button form-type="submit" class="search-btn" @click="searchList">搜索</button>
			</uni-forms>
		</view>
		<!-- ---- -->
		<!-- 车内容列表 -->
		<view style="position: absolute;top: 380rpx;left: 3rpx;width: 740rpx;">
			<view class="uni-flex uni-row list" v-for="(item,index) in productList" :key="index">
				<text class="list-text list-text1">车牌:{{item.license}}</text> <!-- 车牌 -->
				<text class="list-text list-text2">姓名:{{item.manageName}}</text> <!-- 姓名 -->
				<view v-if="item.status===0" class="state list-text list-text3" >
					<text class="list-text4" style="color: #deb85e;">状态:已派出</text>
				</view>
				<view v-if="item.status===1" class="state list-text list-text3" >
					<text class="list-text4" style="color: #ace0ff;">状态:未派出</text>
				</view>
				<view v-if="item.status===2" class="state list-text list-text3" >
					<text class="list-text4" style="color: #de99d9;">状态:维修中</text>
				</view>
				<view v-if="item.status===3" class="state list-text list-text3" >
					<text class="list-text4" style="color: #7ec291;">状态:保养中</text>
				</view>
				<view v-if="item.status===4" class="state list-text list-text3" >
					<text class="list-text4" style="color: #d36f41;">状态:出借中</text>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		created() {
			if (this.contentHeight < 100)
				this.contentHeight = 100
		},
		data() {
			return {
				productList: [],
				result: '',
				formData: {
					license: '',
					img: [],
					peopleNum: '',
					emission: '',
					type: '',
					price: '',
				},
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
					url: _this.apiServer + "/api/get/getAll/vehicleInfo",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					success: res => {

						console.log(res.data.data);
						if (res.data.code === 200) {
							_this.productList = res.data.data;
							_this.result = 'success';
							this.contentHeight = 27 + res.data.data.length * 11.4
						} else {
							_this.result = 'failed';
						}
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			confirmDialog2() {
				this.$refs['dialogInput2'].open()
			},
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
		height: 100%;
		background-image: linear-gradient(#7bc4ff, #4389f2);
		position: absolute;
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

	.top .top-btn {
		position: absolute;
		right: 10rpx;
		bottom: 10rpx;
		width: 90rpx;
		height: 60rpx;
		line-height: 60rpx;
		font-size: 30rpx;
		padding: 0;
		color: #5da8e4;
		font-weight: 600;
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
	.dialog {
		background-color: #7bc4ff;
		width: 500rpx;
		height: 315rpx;
		border-radius: 15rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		padding: 10rpx;
	}

	.dialog button {
		width: 180rpx;
		height: 70rpx;
		background-color: #fff;
		margin-top: 45rpx;
		padding: 0;
		font-size: 35rpx;
		line-height: 70rpx;
		font-weight: 600;
		color: #7bc4ff;
	}


	/* 车内容区 */
	.list {
		background: #fff;
		border-radius: 20rpx;
		margin: 20rpx;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 140rpx;
		background-color: #ffffff;
	}

	.list .list-img {
		width: 250rpx;
		height: 250rpx;
	}

	.list .list-text {
		position: absolute;
		left: 40rpx;
		font-size: 40rpx;
		font-weight: 600;
		color: #6f6f6f;
	}

	.list .list-text1 {
		top: 5rpx;
	}

	.list .list-text2 {
		top: 65rpx;
	}

	.list .list-text3 {
		top: 50rpx;
	}

	.list .list-text4 {
		position: absolute;
		top: -3rpx;
		right: -510rpx;
		font-size: 50rpx;
	}

	.state {
		border-radius: 12rpx;
		width: 130rpx;
		height: 60rpx;
		/* vertical-align: middle; */
		/* text-align: center; */
		line-height: 55rpx;
	}


	/* ---- */
</style>
