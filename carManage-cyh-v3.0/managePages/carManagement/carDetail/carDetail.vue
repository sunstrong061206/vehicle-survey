<template>
	<view class="content">
		<view class="uni-flex uni-column">
			<view class="introBody" v-for="(item,index) in productList" :key="index">
				<uni-card style="width: 100%;"><text class="intro">车牌号：{{license}}</text> 
				</uni-card>
				<uni-card style="width: 100%;">
				<view class="uni-flex uni-row">
					<text class="intro">车辆状态：</text>
					<view v-if="item.status==0" class="state" style="background-color: #ffe98f;">
						<text>已派出</text>
					</view>
					<view v-if="item.status==1" class="state" style="background-color: #e3f9ff;">
						<text>未派出</text>
					</view>
					<view v-if="item.status==2" class="state" style="background-color: #debcde;">
						<text>维修中</text>
					</view>
					<view v-if="item.status==3" class="state" style="background-color: #d5ffd5;">
						<text>保养中</text>
					</view>
					<view v-if="item.status==4" class="state" style="background-color: #a29889;">
						<text>出借中</text>
					</view>
				</view>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">开始使用时间：{{item.begin}}</text>
				</uni-card>
				<uni-card style="width: 100%;">
				<view class="uni-flex uni-row">
					<text class="intro">车辆类型:</text>
					<view v-if="item.type==0">
						<text class="intro">自动挡</text>
					</view>
					<view v-if="item.status==1">
						<text class="intro">手动档</text>
					</view>
				</view>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">最大载人数：{{item.peopleNum}}</text>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">排量：{{item.emission}}</text>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">管理员工号：{{item.manageNo}}</text>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">车辆价格：{{item.price}}</text>
				</uni-card>
				<uni-card style="width: 100%;">
				<text class="intro">上次保养时间：{{new Date(parseInt(item.lastProtectTime)).toLocaleString().replace(/:\d{1,2}$/,' ')}}</text>
				</uni-card>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				license: '',
				productList: [],
			}
		},

		onLoad: function(options) {
			this.license = options.license;
			console.log(options);
			this.getList();
		},

		methods: {
			getList() {
				let _this=this;
				uni.request({
					url: _this.apiServer + "/api/get/getDetail/vehicleInfo",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded', 
						 'enctype': "multipart/form-data"
					},
					data:{
						license:_this.license,
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
	
	.intro {
		font-size: large;
		font-weight: 200;
	}

	.introBody {
		display: flex;
		flex-direction: column;
		align-items: center;
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
</style>