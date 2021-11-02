<template>
	<view class="content">
		<view v-for="(item,index) in homePosts" :key="index" class="card">
			<uni-card>
				<navigator
					:url="'/managePages/examine/examineDetail/examineDetail?repairId='+ item.repairId+'&fromPage=examToDo'">

					<view class="uni-flex uni-row">
						<view style="margin-top: 10rpx;">
							<image src="/static/time.png" style="width: 50rpx;height: 50rpx;"></image>
						</view>
						<view class="uni-flex uni-column">
							<view style="margin-top: 10rpx;">
								<text>{{item.repairLicense}}</text>
							</view>
							<view class="uni-flex uni-column">
								<text>申报人：{{item.name}}</text>
								<text>申报时间：{{new Date(parseInt(item.repairTime)).toLocaleString().replace(/:\d{1,2}$/,' ')}}</text>
								<text>申报编号：{{item.repairId}}</text>
								<text style="color: #808080;">点击查看详情</text>
							</view>
						</view>
					</view>

				</navigator>
			</uni-card>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				homePosts: [],
			}
		},

		onLoad() {
			this.getHomePosts();
		},
		methods: {
			getHomePosts() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/repair",
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					method: 'POST',
					data: {
						filter: 'isVet', //filter 过滤器 是否审批
						isVet: false,
						no: _this.$store.state.userInfo.no,
						position: _this.$store.state.userInfo.position,
					},
					success: (res) => {
						console.log(res.data.data);
						_this.homePosts = res.data.data;
					}
				});
			}
		}
	}
</script>

<style>
	*{
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
		position: absolute;
		background-image: linear-gradient(#7bc4ff,#4389f2);
	}
	.card{
		width: 90%;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		margin: 20rpx 0;
		border-radius: 15rpx;
	}
</style>
