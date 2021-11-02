<template>
	<!-- 修改密码 -->
	<view class="content">
		<!-- 头部区域 -->
		<view class="top" >
			<view class="head">
				<image src="/static/head.png" class="headimg"></image>
				<text class="name">{{name}}</text>
			</view>
		</view>
		<!-- 更改密码区域 -->
		<uni-card >
			旧密码：<input v-model="oldPwd" type="password" placeholder="请输入旧密码" class="changeInput"></input>
		</uni-card>

		<uni-card >
			新密码：<input v-model="newPwd" type="password" placeholder="请输入新密码" class="changeInput"></input>
		</uni-card>
		
			<button @click="submit" class="btn">修改</button>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				oldPwd:'',
				newPwd:'',
			}
		},
		methods: {
			submit(){
				let _this=this;
				uni.request({
					url:_this.apiServer + "/api/update/password",
					header:{
						'enctype': "multipart/form-data"
					},
					data:{
						oldPassword:_this.oldPwd,
						newPassword:_this.newPwd,
						no:_this.$store.state.userInfo.no
					},
					success(res) {
						if(res.data.code==200){
							_this.$store.commit('LOGOUT')
							uni.showToast({
								title: '修改成功，请重新登录',
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
						}else{
							uni.showModal({
								content:res.data.msg,
								success: (e) => {
									res(false)
								},
								fail: () => {
									res(false)
								}
							})
						}
					}
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
	/* 背景 */
	.content {
		display: flex;
		/* align-items: center; */
		/* justify-content: center; */
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}
	.top{
		height: 430rpx;
	}
	.head{
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 32rpx;
		font-weight: 540;
	}
	.headimg{
		height: 200rpx;
		width: 200rpx;
		margin-top: 110rpx;
	}
	/* 更改密码区域 */
	.uni-card__content--pd.data-v-19622063{
		font-size: 35rpx;
		font-weight: 600;
	}
	.changeInput{
		border-radius: 10rpx;
		padding: 20rpx 0;
	}
	.changeInput:hover{
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		animation:shadowShow 0.5s ;
	}
	@-webkit-keyframes shadowShow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,0); }
		100% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4); }
	}
	@keyframes shadowShow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,0); }
		100% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4); }
	}
	.btn{
		width: 200rpx;
		font-size: 40rpx;
		font-weight: 600;
		color: #89bbfd;
	}
	.button-hover{
		opacity: .6;
	}
</style>
