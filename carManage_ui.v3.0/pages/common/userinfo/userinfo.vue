<template>
	<view class="content">
		<!-- 头部区域 -->
		<view class="top" >
				<image src="/static/head.png" class="top-img"></image>
				<text class="top-text">{{name}}</text>
				<text class="top-text"  @click="logout" style="top: 175rpx;right: 20rpx;font-size: 36rpx;">退出登录</text>
		</view>
		<!-- 功能栏 -->
		<view class="wrap">
				<view class="meun-wrap" >
					<view class="meuns" v-for="(item,index) in meunsList" :key="index" @click="goDetailPage(item.id)">
						<image class="iconimg" :src="item.icon" mode=""></image>
						<text>{{item.msg}}</text>
					</view>
				</view>
		</view>
		
		<!-- 详情解释 -->
		<view>
				<view class="uni-flex uni-row card" v-for="(item,index) in meunsList2" :key="index" @click="goDetailPage(item.id)">
					<image class="iconimg" :src="item.icon" mode="" style="margin-left: 20rpx; margin-top: 20rpx;"></image>
					<text style="margin-left: 10rpx; margin-top: 20rpx;">{{item.msg}}</text>
				</view>
		</view>   
				
		<tabBar :pagePath="'pages/common/userinfo/userinfo'"></tabBar>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				name:this.$store.state.userInfo.name,
				meunsList:[
						{
							icon:"/static/noMessage.png",
							msg:"员工信息",
							id:"userdetail"
						},
						{
							icon:"/static/changePwd.png",
							msg:"修改密码",
							id:"changePwd"
						},
					],
					
					meunsList2:[
						{
							icon:"/static/help.png",
							msg:"帮助",
							id:"help"
						},
						{
							icon:"/static/about.png",
							msg:"关于",
							id:"about"
						},
					],
					
					
				}
		},
		methods: {
			logout() {
				let _this = this;
				_this.$store.commit('LOGOUT')
				uni.showToast({
					title: '退出中',
					icon: 'loading',
					duration: 1000,
					success() {
						setTimeout(function() {
							uni.redirectTo({
								url: '../../index/index'
							})
						}, 1000)
					}
				})
			},
		
				goDetailPage(e) {
					console.log("click")
					const url = '../' + e + '/' + e
					console.log(url)
					uni.navigateTo({
						url: url,
						fail(Error) {
							console.log(Error)
						}
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
	.content {
		display: flex;
		/* align-items: center; */
		/* justify-content: center; */
		flex-direction: column;
		width: 100%;
		height: 150%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}
	/* 头部区域 */
	.top {
		position: relative;
		background-color: #62a9f9;
		height: 250rpx;
		border-bottom-left-radius: 20rpx;
		border-bottom-right-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		margin-bottom: 30rpx;
	}
	.top .top-img{
		position: absolute;
		top: 15rpx;
		left: 15rpx;
		width: 200rpx;
		height: 200rpx;
		box-sizing: border-box;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		border-radius: 50%;
	}
	.top .top-text{
		position: absolute;
		top: 80rpx;
		right: 170rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #DCDFE6;
		text-shadow: 5rpx 5rpx 10rpx rgba(255,255,255,.4);
	}
	/* ---- */
	/* 功能区 */
	.wrap{
			height: 262rpx;
			display: flex;
			justify-content: center;
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
			border-radius: 20rpx;
			margin-bottom: 40rpx;
	}
	.meun-wrap{
		width: 600rpx;
		height: 140rpx;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		padding: 40rpx;
		box-shadow: 0px 2px 16px 0px rgba(17, 33, 50, 0.08);
		border-radius: 20rpx;
		margin-top: 20rpx;
		background-color: #fff;
	}
	/* --- */
	/* 详情解释 */
	.meuns{
		width: 168rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 32rpx;
		font-weight: 540;
		border-radius: 20rpx;
		float: left;
		margin: 0 65rpx;
		margin-top: 20rpx;
	}
	.iconimg{
			width: 50rpx;
			height: 50rpx;
			margin-bottom: 15rpx;
	}
	.card{
		background-color: #fff;
		border-radius: 8rpx;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .2);
		height: 80rpx;
		margin: 10rpx 60rpx;
	}
	/* -- */
</style>
