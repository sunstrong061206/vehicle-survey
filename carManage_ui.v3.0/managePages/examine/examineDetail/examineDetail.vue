<template>
	<view class="content">
		<!-- 头部区域 -->
		<view class="top">
			<image src="/static/examTop.png" class="topImg"></image>
			<text class="topText">维修审批</text>
			<!-- 当前选中的申报类型 -->
		</view>
		
		<!-- 内容主体区 -->
		<!-- 维修审批 -->
		<view class="main">
			<view class="uni-flex uni-column forms">
				<text class="intro">维修单编号：{{list.repairId}}</text>
				<text class="intro">维修车辆车牌：{{list.repairLicense}}</text>
				<text class="intro">维修说明：{{list.repairMsg}}</text>
				<text class="intro">维修部位照片：</text>
				<image class="img">{{list.repairImg}}</image>
				<text class="intro">维修时间：{{new Date(parseInt(list.repairTime)).toLocaleString().replace(/:\d{1,2}$/,' ')}}</text>
				<text class="intro">勘察员工号：{{list.surveyNo}}</text>
				<text class="intro">勘察员操作时间：{{new Date(parseInt(list.surveyTime)).toLocaleString().replace(/:\d{1,2}$/,' ')}}</text>
			</view>
			
			<view class="uni-flex uni-column forms" v-if="fromPage=='examDone'" style="width: 30%;
    margin: 30rpx auto;">
				<text class="intro pass" v-if="list.vetResult==1">通过</text>
				<text class="intro rejeced" v-else>未通过</text>
			</view>


			<view class="uni-flex uni-row" style="margin-top: 40rpx;" v-if="fromPage=='examToDo'">
				<button @click="no" class="noButton">否决</button>
				<button @click="yes" class="yesButton">同意</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				repairId:'',
				list: [],
				fromPage: '',

			}
		},

		onLoad: function(options) {
			this.fromPage = options.fromPage;
			this.repairId = options.repairId;
			console.log(options);
			this.getList();

		},

		methods: {
			getList() {
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
						_this.list = res.data.data[0];
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},

			yes() {
				let _this = this;
				let timestamp = new Date().getTime();
				uni.request({
					url: _this.apiServer + "/api/update/vet/repair",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						repairId: _this.repairId,
						result: 1,
						time: timestamp,
						position:_this.$store.state.userInfo.position,
						no:_this.$store.state.userInfo.no,
						resultMsg:'',
					},
					success: res => {
						if(res.data.code==200){
							uni.showToast({
							title: '该申报已同意',
							icon: 'success',
							duration: 1000,
						});
						setTimeout(function() {
							uni.navigateBack({})
						}, 1000);
						}else{
							uni.showToast({
								title: '提交失败',
								icon: 'none',
								duration: 1000,
							});
						}
						
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			no() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/update/vet/repair",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						repairId: _this.repairId,
						result: 0,
						time: timestamp,
						position:_this.$store.state.userInfo.position,
						no:_this.$store.state.userInfo.no,
						resultMsg:'',
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
	*{
		padding: 0;
		margin: 0;
	}
	/* 背景 */
	.content{
		display: flex;
		align-items: center;
		justify-content: center;
		background-image: linear-gradient(#4389f2,#7bc4ff);
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: absolute;
	}
	/* 头部区域 */
	.top {
		position: absolute;
		top: 0;
		background-color: #fff;
		width: 100%;
		height: 220rpx;
		border-bottom-left-radius: 20rpx;
		border-bottom-right-radius: 20rpx;
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4);
		animation: topShow 1s;
	}
	.topImg{
		width: 340rpx;
		height: 180rpx;
	}
	.topText{
		position: absolute;
		top: 45rpx;
		right: 110rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #4b91f1;
		text-shadow: 10rpx 5rpx 10rpx #4b91f1;
	}
	
	@-webkit-keyframes topShow{
		0%{
			top: -220rpx;
		}
		60%{
			top: 0rpx;
		}
		70%{
			top: -30rpx;
		}
		80%{
			top: 0rpx;
		}
		90%{
			top:-10rpx;
		}
		100%{
			top: 0rpx;
		}
	}
	@keyframes topShow{
		0%{
			top: -220rpx;
		}
		60%{
			top: 0rpx;
		}
		70%{
			top: -30rpx;
		}
		80%{
			top: 0rpx;
		}
		90%{
			top:-10rpx;
		}
		100%{
			top: 0rpx;
		}
	}
	/* ---- */
	/* 内容主体区 */
	.main{
		position: absolute;
		top: 230rpx;
	}
	.forms {
		margin-top: 40rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		background-color: #fff;
		border-radius: 15rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	.intro {
		font-weight: 600;
		font-size: 40rpx;
		margin-inline: 40rpx;
		color: #545353;
		
	}
	.pass{
		color: #83c3f6;
	}
	.rejeced{
		color: #e79399;
	}
	/* ---- */
	/* 按钮 */
	.yesButton {
		background-color: #83c3f6;
		border-radius: 15rpx;
		width: 40%;
		color: #fff;
		font-size: 40rpx;
		font-weight: 600;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}

	.noButton {
		background-color: #e79399;
		border-radius: 15rpx;
		width: 40%;
		color: #fff;
		font-size: 40rpx;
		font-weight: 600;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	.button-hover{
		opacity: 60%;
	}
</style>
