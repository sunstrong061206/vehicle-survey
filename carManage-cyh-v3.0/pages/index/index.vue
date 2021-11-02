<template>
	<view class='content'>
		<view class='item'>
			<label class='title'>查勘车辆管理系统</label>
			<view class="hr"></view>
			<button class="toLogin" type='default' @click="toLogin" :disabled='!checked'>已验证,去登陆</button>
			<button class="toAuth" type='default' @click="toAuth" :disabled='!checked'>去认证</button>
			<checkbox class="check" value='agree' :checked='checked' @click="change" color="#7db4f2">我同意
			<text @click="show" style="color:  #4389f2;font-weight: 400;">此用户条款</text></checkbox>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				checked: false
			}
		},
		created() {
			let _this = this;
			if (_this.$store.state.userInfo.no != '') {
				_this.checked = true;
				console.log(_this.$store.state.userInfo.position)
				uni.showToast({
					title: '自动登陆中',
					icon: 'loading',
					duration: 1000,
					success() {
						if (_this.$store.state.userInfo.position === 'manage') {
							setTimeout(function() {
								uni.switchTab({
									url: '../manage/index/index'
								})
							}, 1000)
						}
						if (_this.$store.state.userInfo.position === 'survey') {
							setTimeout(function() {
								uni.switchTab({
									url: '../survey/index/index'
								})
							}, 1000)
						}
						if (_this.$store.state.userInfo.position === 'vet') {
							setTimeout(function() {
								uni.switchTab({
									url: '../vet/index/index'
								})
							}, 1000)
						}
					}
				})
			}
		},
		methods: {
			toAuth() {
				uni.navigateTo({
					url: '../authentication/authentication'
				})
			},
			toLogin() {
				uni.navigateTo({
					url: '../login/login'
				})
			},
			change() {
				this.checked = !this.checked;
			},
			show() {
				let _this = this;
				uni.showModal({
					title: '用户条款',
					content: '此微信小程序仅供公司内员工使用，若为查勘员某些功能需要使用用户位置信息',
				})
			}

		}
	}
</script>

<style>
	*{
		padding: 0;
		margin: 0;
	}
	.content {
		display: flex;
		align-items: center;
		justify-content: center;
		background-image: linear-gradient(#4389f2,#7bc4ff);
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: absolute;
	}

	.item {
		position: absolute;
		top: 250rpx;
		background-color: white;
		height: 500rpx;
		width: 600rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: center;
		border-radius: 30rpx;
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.3);
		animation: itemShow 3s;
		z-index: 1;
	}

	.title {
		position: absolute;
		color: #70b4f2;
		font-size: 55rpx;
		top: 30rpx;
		font-weight: 600;
		text-shadow: 5rpx 5rpx 6rpx rgba(112, 163, 217,.6);
	}

	.toLogin {
		position: absolute;
		top: 160rpx;
		width: 380rpx;
		font-weight: 600;
	}

	.toAuth {
		position: absolute;
		top: 275rpx;
		width: 380rpx;
		font-weight: 600;
	}

	.check {
		position: absolute;
		top: 410rpx;
		left: 150rpx;
		font-weight: 600;
	}
	
	.hr{
		position: absolute;
		top: 120rpx;
		width: 430rpx;
		height: 8rpx;
		background-color: #70b4f2;
		border-radius: 50%;
	}
	
	/* 车车 */
	.cheche{
		position: absolute;
		top: 670rpx;
		width: 550rpx;
		height: 390rpx;
		animation: dogShow 4s;
		z-index: 0;
		transform: rotateY(180deg);
	}
	
	button[type=default] {
	    background-color: #f7f7f7;
	    color: #7bc4ff;
		border: 1rpx solid #7bc4ff;
		box-sizing: content-box;
	}
	
	button[disabled][type=default] {
	    background-color: #d8d8d8;
	    color: rgba(0,0,0,.3);
		border: none;
		box-sizing: content-box;
	}
	
	@-webkit-keyframes itemShow{
		0% { opacity: 0;}
		100% { opacity: 100%; }
	}
	
	@keyframes itemShow{
		0% { opacity: 0;}
		100% { opacity: 100%; }
	}
	
	@-webkit-keyframes dogShow{
		0% { opacity: 0; }
		100% { opacity: 100%; }
	}
	
	@keyframes dogShow{
		0% { opacity: 0; }
		100% { opacity: 100%; }
	}
</style>
