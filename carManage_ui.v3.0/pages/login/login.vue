<template>
	<view class='content'>
		<view class="item-top">
			<view class="item-top-text" v-if="topShow">
				\(^▽^ )/
			</view>
			<view class="item-top-text" else>
				(*/ω＼*)
			</view>
		</view>
		<view class='item'>
			<uni-forms autoComplete="on" v-model="loginForm" ref="loginForm" :rules="loginRules">
				<uni-forms-item labelAlign="center" name="no">
					<input placeholder-style="color:#4389f2" placeholder="请输入您的职工号" maxlength="20"
						v-model="loginForm.no" @focus="eyeOpen"></input>
				</uni-forms-item>
				<uni-forms-item labelAlign="center" name="password">
					<input placeholder-style="color:#4389f2" placeholder="请输入您的密码" type="password" 
						maxlength="20" v-model="loginForm.password" @focus="eyeClose()"></input>
				</uni-forms-item>
				<button type="primary" @click="login">登录</button>
			</uni-forms>
			<view class="forget" @click="open">忘记密码</view>
		</view>

		<image src="../../static/dogRun.gif" class="dogRun"></image>
		<!-- 微信登录 -->
		<image src="../../static/vx-icon.png" class="vx-icon"></image>
		<!-- 忘记密码dialog -->
		<uni-popup ref="popup" type="bottom">
			<view class="forgetItem">
				<uni-forms ref="forgetForm" v-model="forgetFormData" :rules="forgetRules" style="width: 500rpx;">
					<uni-forms-item name="phone" >
						<uni-easyinput type="text" v-model="forgetFormData.phone" placeholder="请输入手机号"/>
					</uni-forms-item>
					<uni-forms-item name="securityCode">
						<uni-easyinput type="text" v-model="forgetFormData.securityCode" placeholder="请输入验证码" />
					</uni-forms-item>
					<uni-forms-item name="newPwd">
						<uni-easyinput type="password" v-model="forgetFormData.newPwd" placeholder="请输入新密码" />
					</uni-forms-item>
					<uni-forms-item name="rePwd">
						<uni-easyinput type="password" v-model="forgetFormData.rePwd" placeholder="请确认新密码" />
					</uni-forms-item>
				</uni-forms>
				<button type="primary" class="btn btn1" @click="sendCode" :disabled="btn1_crl">{{btn1_text}}</button>
				<button type="primary" class="btn btn2" @click="submitForget">点击提交</button>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	const pwdReg = /^[^\s]{6,16}$/
	export default {
		onReady() {
			// 需要在onReady中设置规则
			this.$refs.forgetForm.setRules(this.forgetRules)
			this.$refs.loginForm.setRules(this.loginRules)
			},
		data() {
			return {
				// 登录表单
				loginForm: {
					no: '',
					password: ''
				},
				// 登录表单校验
				loginRules:{
					no:{
						rules:[
							{
								required: true,
								errorMessage: '请输入职工号'
							},
							{
								pattern: "^(vt|sv|ad)[0-9]{6}$",
								errorMessage: "职工号格式错误"
							},
							{
								maxLength: 8,
								errorMessage: "长度最长为8位"
							}
						]
					},
					password:{
						rules:[
							{
								required: true,
								errorMessage: '请输入密码'
							},
							{
								minLength: 6,
								maxLength: 16,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
							},
							{
								pattern: "^[^\\s]{6,16}$",
								errorMessage: '密码中不能带有空格'
							}
						]
					}
				},
				// 忘记密码表单
				forgetFormData: {
					phone: '',
					// 验证码
					securityCode: '',
					// 新密码
					newPwd: '',
					// 确认密码
					rePwd: ''
				},
				// 忘记密码表单的校验规则
				forgetRules: {
					phone: {
						rules: [{
								required: true,
								errorMessage: '请输入手机号'
							},
							{
								pattern: "^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$",
								errorMessage: '手机号码格式不正确'
							}
						]
					},
					securityCode: {
						rules: [{
							required: true,
							errorMessage: '请输入验证码'
						}]
					},
					newPwd: {
						rules: [{
								required: true,
								errorMessage: '请输入新密码'
							},
							{
								minLength: 6,
								maxLength: 16,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
							},
							{
								pattern: "^[^\\s]{6,16}$",
								errorMessage: '密码中不能带有空格'
							}
						]
					},
					rePwd: {
						rules: [{
								required: true,
								errorMessage: '请确认新密码'
							},
							{
								minLength: 6,
								maxLength: 16,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
							},
						]
					}
				},
				// 顶部显示颜文字
				topShow: true,
				btn1_text: '发送验证码',
				totaltime: 10,
				//定时器
				timer: null,
				//节流阀
				canClick: true,
				//控制按钮是否可用
				btn1_crl: false,
				
			}
		},
		methods: {
			// 登录
			login(loginForm) {
				this.$refs.loginForm.validate().then(res => {
					console.log('表单数据信息：', res);
					let _this = this;
					uni.request({
						url: _this.apiServer + '/api/login',
						data: {
							no: _this.loginForm.no,
							password: _this.loginForm.password
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype': "multipart/form-data"
						},
						method: 'POST',
						success(res) {
							if (res.data.code === 200) {
								uni.showToast({
									title: '登录成功',
									icon: 'success',
									duration: 1000,
									success() {
										console.log('success')
					
										_this.$store.commit('LOGIN', [res.data.data.employee.name, res.data
											.data.employee.no, res.data.data.employee.position, res
											.data.data.employee.bossNo
										]);
					
										console.log(res.data.data.employee.position)
										if (res.data.data.employee.position === 'survey') {
											uni.switchTab({
												url: '../survey/index/index'
											})
										}
										if (res.data.data.employee.position === "vet") {
											uni.switchTab({
												url: '../vet/index/index'
											})
										}
										if (res.data.data.employee.position == "manage") {
											setTimeout(function() {
												uni.switchTab({
													url: '../manage/index/index'
												})
											}, 1000)
										}
									}
								})
							} else {
								uni.showToast({
									title: res.data.msg,
									icon: 'none',
									duration: 1000,
								})
							}
						}
					})
				}).catch(err => {
					console.log('表单错误信息：', err);
					
				})
			},
			//改变颜文字表情
			eyeOpen() {
				this.topShow = true
			},
			eyeClose() {
				this.topShow = false
			},
			// 忘记密码对话框弹出
			open() {
				this.$refs.popup.open('center')
				// 重置表单
				// this.forgetFormData.phone = ''
				// this.forgetFormData.securityCode = ''
				// this.forgetFormData.newPwd = ''
				// this.forgetFormData.rePwd = ''
			},
			//发送验证码
			sendCode() {
				if (!this.canClick) return
				this.canClick = false
				this.btn1_crl = true
				this.timer = setInterval(() => {
					this.totaltime--
					this.btn1_text = this.totaltime + 's'
					// 清除定时器
					if (this.totaltime < 0) {
						clearTimeout(this.timer)
						this.timer = null
						this.btn1_text = '发送验证码'
						this.totaltime = 10
						this.canClick = true
						this.btn1_crl = false
					}
				}, 1000)
				//写死了
				uni.showToast({
					title: '发送成功！',
					icon: "none"
				})
			},
			// 提交忘记密码，写死了
			submitForget(forgetForm) {
				this.$refs.forgetForm.validate().then(res => {
					console.log('表单数据信息：', res);
					
					if(this.forgetFormData.newPwd === this.forgetFormData.rePwd)
					{
						// 在这写与数据库连接的代码
						uni.showToast({
							title: '修改成功！',
							icon: "none"
						})
						this.$refs.popup.close()
					}
					else{
						uni.showToast({
							title: '两次密码不一致！',
							icon: "none"
						})
					}
				}).catch(err => {
					console.log('表单错误信息：', err);
					uni.showToast({
						title: '修改失败！',
						icon: "none"
					})
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

	.content {
		display: flex;
		align-items: center;
		justify-content: center;
		background-image: linear-gradient(#4389f2, #7bc4ff);
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
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0, 0, 0, .3);
		animation: itemShow 2s;
		/* z-index: 2; */
	}

	.item-top {
		position: absolute;
		top: 100rpx;
		width: 300rpx;
		height: 300rpx;
		border-radius: 50%;
		background-color: white;
		box-sizing: border-box;
		border: 8rpx solid rgba(120, 193, 254, .6);
		/* z-index: 1; */
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0, 0, 0, .3);
		animation: item-topShow 2s;
	}

	.item-top-text {
		color: #aedafe;
		font-size: 60rpx;
		font-weight: 600;
		text-align: center;
		line-height: 180rpx;
	}

	/* 输入框 */
	input {
		color: #4389f2;
		border: 4rpx solid #4389f2;
		width: 460rpx;
		height: 85rpx;
		border-radius: 10rpx;
	}

	input:hover {
		animation: shadowShow 0.5s;
		box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0, 0, 0, .4);
	}

	/* 按钮 */
	button[type=primary] {
		background-color: #468cf3;
		width: 200rpx;
		font-size: 40rpx;
	}

	.button-hover[type=primary] {
		background-color: #aedafe;
	}

	/* 忘记密码 */
	.forget {
		position: absolute;
		top: 320rpx;
		right: 20rpx;
		font-weight: 600;
		color: #468cf3;
	}

	.forgetItem {
		position: relative;
		background-color: white;
		height: 750rpx;
		width: 600rpx;
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: center;
		border-radius: 30rpx;
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0, 0, 0, .3);
	}

	.btn {
		position: absolute;
		bottom: 25rpx;
		font-size: 30rpx !important;
		padding: 0;
		text-align: center;
	}

	.btn1 {
		left: 65rpx;
	}

	.btn2 {
		right: 65rpx;
	}

	button[disabled][type=primary] {
		background-color: #999999;
	}

	/* 微信 */
	.vx-icon {
		position: absolute;
		bottom: 80rpx;
		width: 300rpx;
		height: 300rpx;
		border-radius: 50%;
		border: 8rpx solid rgba(120, 193, 254, .6);
		animation: vxShow 2s;
	}

	/* 狗狗 */
	.dogRun {
		position: absolute;
		top: 800rpx;
		width: 530rpx;
		height: 115rpx;
		animation: dogShow 4s;

	}

	/* 动画 */
	@-webkit-keyframes itemShow {
		0% {
			top: 0rpx;
			opacity: 0;
		}

		20% {
			top: 0rpx;
			opacity: 0;
		}

		60% {
			top: 200rpx;
			opacity: 30%;
		}

		70% {
			top: 250rpx;
			opacity: 100%;
		}

		80% {
			top: 220rpx;
		}

		100% {
			top: 250rpx;
		}
	}

	@keyframes itemShow {
		0% {
			top: 0rpx;
			opacity: 0;
		}

		20% {
			top: 0rpx;
			opacity: 0%;
		}

		60% {
			top: 200rpx;
			opacity: 30%;
		}

		70% {
			top: 250rpx;
			opacity: 100%;
		}

		80% {
			top: 220rpx;
		}

		100% {
			top: 250rpx;
		}
	}

	@-webkit-keyframes shadowShow {
		0% {
			box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	@keyframes shadowShow {
		0% {
			box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	@-webkit-keyframes item-topShow {
		0% {
			top: 0rpx;
			opacity: 0;
		}

		20% {
			top: 0rpx;
			opacity: 0;
		}

		60% {
			top: 50rpx;
			opacity: 0%;
		}

		70% {
			top: 100rpx;
			opacity: 100%;
		}

		80% {
			top: 70rpx;
		}

		100% {
			top: 100rpx;
		}
	}

	@keyframes item-topShow {
		0% {
			top: 0rpx;
			opacity: 0;
		}

		20% {
			top: 0rpx;
			opacity: 0%;
		}

		60% {
			top: 50rpx;
			opacity: 0%;
		}

		70% {
			top: 100rpx;
			opacity: 100%;
		}

		80% {
			top: 70rpx;
		}

		100% {
			top: 100rpx;
		}
	}

	@-webkit-keyframes vxShow {
		0% {
			bottom: -350rpx;
			opacity: 0;
		}

		20% {
			bottom: 0rpx;
			opacity: 50%;
		}

		60% {
			bottom: 30rpx;
			opacity: 100%;
		}

		70% {
			bottom: 80rpx;
		}

		80% {
			bottom: 50rpx;
		}

		100% {
			bottom: 80rpx;
		}
	}

	@keyframes vxShow {
		0% {
			bottom: -350rpx;
			opacity: 0;
		}

		20% {
			bottom: 0rpx;
			opacity: 50%;
		}

		60% {
			bottom: 30rpx;
			opacity: 100%;
		}

		70% {
			bottom: 80rpx;
		}

		80% {
			bottom: 50rpx;
		}

		100% {
			bottom: 80rpx;
		}
	}

	@-webkit-keyframes dogShow {
		0% {
			opacity: 0;
		}

		100% {
			opacity: 100%;
		}
	}

	@keyframes dogShow {
		0% {
			opacity: 0;
		}

		100% {
			opacity: 100%;
		}
	}

	/* @-webkit-keyframes shadowHide{
		0% { box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0,0,0,.4); }
		100% { box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0,0,0,0); }
	}
	
	@keyframes shadowHide{
		0% { box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0,0,0,.4); }
		100% { box-shadow: 8rpx 8rpx 8rpx -4rpx rgba(0,0,0,0); }
	} */
</style>
