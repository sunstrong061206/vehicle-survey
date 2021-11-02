<template>
	<view class="content">
		<!-- 实名认证图片 -->
		<image src='../../static/sfrz.png' class="img-top"></image>
		<uni-forms autoComplete="on" :modelValue="authForm" ref="authForm" label-position="left" >
			<uni-group>
				<uni-forms-item label="姓名" required labelAlign="center" labelWidth="85">
					<uni-easyinput placeholder="请输入您的真实姓名" name="realname" focus v-model="authForm.realname"
						type="text"></uni-easyinput>
				</uni-forms-item>
				<uni-forms-item label="工作证" required labelAlign="center" labelWidth="85">
					<uni-easyinput placeholder="请输入您的职工号" name="no" v-model="authForm.no" maxlength="10">
					</uni-easyinput>
				</uni-forms-item>

				<uni-forms-item name="job">
					<view class="uni-list">
						<view class="uni-list-cell">
							<view class="uni-list-cell-left">
								所处职位
							</view>
							<view class="uni-list-cell-db">
								<picker @change="bindPickerChange" :value="index" :range="job">
									<view class="uni-input">{{job[index]}}</view>
								</picker>
							</view>
						</view>
					</view>
				</uni-forms-item>

				<uni-forms-item name="bumen">
					<view class="uni-list">
						<view class="uni-list-cell">
							<view class="uni-list-cell-left">
								所在部门
							</view>
							<view class="uni-list-cell-db">
								<picker mode="multiSelector" @columnchange="bindMultiPickerColumnChange"
									:value="multiIndex" :range="multiArray" v-model="authForm.bumen" name="bumen">
									<view class="uni-input">
										{{multiArray[0][multiIndex[0]]}}，{{multiArray[1][multiIndex[1]]}}
									</view>
								</picker>
							</view>
						</view>
					</view>
				</uni-forms-item>
				<uni-forms-item label="密码设置" required labelAlign="right" labelWidth="85">

					<uni-easyinput type="password" placeholder="请设置您的密码" name="password" maxlength="20"
						v-model="authForm.password">
					</uni-easyinput>
				</uni-forms-item>

				<uni-forms-item label="重复密码" required labelAlign="right" labelWidth="85">
					<uni-easyinput type="password" placeholder="请重复您的密码" name="repassword" maxlength="20"
						v-model="authForm.repassword">
					</uni-easyinput>
				</uni-forms-item>
			</uni-group>
		</uni-forms>
		<button type="primary" @click="submit" class="submit">提交</button>
		<button type="default" class="reset">重置</button>
		<!-- 狗勾U•ェ•U -->
		<image src="../../static/dogRun.gif" class="dogRun"></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				focus: false,
				authForm: {
					realname: '',
					no: '',
					password: '',
					repassword: ''
				},
				job: ['查勘人', '管理人', '负责人'],
				index: 0,
				multiArray: [
					['管理部', '执行部'],
					['车辆管理部', '审批部'],
				],
				multiIndex: [0, 0],

			}
		},
		onLoad() {
			let _this = this;
			var code = '';
			var appId = 'wx42936ad485f33e67';
			var secret = '8b84771f5074f712daf6bbc53805106c';
			uni.login({
				success: (res) => {
					code = res.code;
					_this.code = code
					uni.request({
						url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + appId +
							'&secret=' + secret +
							'&js_code=' + res.code + '&grant_type=authorization_code',
						header: {
							'content-type': 'json'
						},
						success: function(res) {
							_this.openid = res.data.openid;
						}
					});

				},
				fail: res => {
					console.log("fail");
				}
			});

		},
		methods: {
			bindPickerChange: function(e) {
				this.index = e.target.value
			},
			bindMultiPickerColumnChange: function(e) {
				this.multiIndex[e.detail.column] = e.detail.value
				switch (e.detail.column) {
					case 0:
						switch (this.multiIndex[0]) {
							case 0:
								this.multiArray[1] = ['车辆管理部', '审批部']
								break
							case 1:
								this.multiArray[1] = ['查勘部']
								break
						}
						this.multiIndex.splice(1, 1, 0)
						this.multiIndex.splice(2, 1, 0)
						break

				}
				this.$forceUpdate()
			},

			submit() {
				let _this = this;
				let dept = _this.multiArray[0][_this.multiIndex[0]] + ',' + _this.multiArray[1][_this.multiIndex[
					1]];
				let Enpos=['survey','manage','vet'];
				let pos = Enpos[_this.index];
				if (_this.authForm.realname === '' || _this.authForm.no === '' || _this.authForm.password === '' || _this
					.authForm.repassword === '') {
					uni.showToast({
						title: '请完善表单',
						icon: 'none'
					})
					return
				}
				if (_this.authForm.password != _this.authForm.repassword) {
					uni.showToast({
						title: '两次密码输入错误',
						icon: 'none'
					})
					return
				}
				uni.request({
					//url: "/authentication",
					url:_this.apiServer + "/api/update/verify",
					data: {
						no: _this.authForm.no,
						name: _this.authForm.realname,
						position: pos,
						dept: dept,
						//facedata: 'facedata',
						wechat: _this.openid,
						password: _this.authForm.password
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype':"multipart/form-data"
					},
					method: 'POST',
					success: (res) => {
						console.log(res);
						if (res.data.code === 200) {
							_this.$store.commit('LOGIN', [_this.authForm.realname, _this.authForm.no, pos,res.data.data.employee.bossNo]);
							console.log(_this.$store.state);
							uni.showToast({
								title: '认证成功',
								icon: 'success',
								duration: 1000,
								success() {
									if (pos === 'survey') {
										uni.switchTab({
											url: '../user/index/index'
										})	
									}
									if (pos === "vet") {
										uni.switchTab({
											url: '../director/index/index'
										})
									}
									if (pos === "manage") {
										setTimeout(function() {
											uni.switchTab({
												url: '../manage/index/index' 
											})
										}, 1000)
									}
								}
							})
						} else if(res.data.code === 303){
							uni.showToast({
								title:res.data.msg,
								icon:'none',
								duration:1000,
							})
							}
					},
					fail: (res) => {
						console.log('fail');
					}
				})

			}
		}
	}
</script>

<style>
	/* @import url("../../common/uni.css"); */
	
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
		
	.title-top {
		//顶部title
		font-size: 50rpx;
	}
	
	/* 实名认证图片 */
	.img-top{
		position: absolute;
		top: 80rpx;
		width:220rpx; 
		height: 200rpx;
		border:4rpx solid rgba(120, 193, 254,.6);
		border-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	/* 表单区 */
	.uni-group.data-v-176b3b64{
		border-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		border: 8rpx solid rgba(120, 193, 254,.6);
		box-sizing: border-box;
	}
	
	/* 狗勾 */
	.dogRun{
		position: absolute;
		bottom: 80rpx;
		width: 530rpx;
		height: 115rpx;
	}
	
	/* 提交按钮 */
	.submit{
		position: absolute;
		bottom: 220rpx;
		left: 180rpx;
		width: 170rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	button[type=primary] {
		background-color: #5ca3f8;
	}
	.button-hover[type=primary] {
		background-color: #aedafe;
	}
	/* 重置按钮 */
	.reset{
		position: absolute;
		bottom: 220rpx;
		right: 180rpx;
		width: 170rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	
	/* 表单中的字体 */
	.uni-forms-item__label .label-text.data-v-61dfc0d0 {
		color: #4b91f4!important;
	}
	
	.uni-list-cell-left {
		color: #4b91f4;
	}
	
	/* input输入框 */
	.is-input-border.data-v-abe12412{
		box-shadow: none;
	}
	
	.is-input-border.data-v-abe12412:hover{
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		animation: shadowShow 1.5s;
	}
	
	/* 动画 */
	@-webkit-keyframes shadowShow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,0); }
		100% { 	box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4); }
	}
	
	@keyframes shadowShow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,0); }
		100% { 	box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4); }
	}
	
</style>
