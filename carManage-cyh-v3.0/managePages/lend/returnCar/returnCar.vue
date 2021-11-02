<template>
		<!-- 还车信息录入 -->
		<view class="content" >
			<!-- 头部区域 -->
			<view class="top ">
				<image class="topImg" src="/static/work.png"></image>
				<text class="topText">录入还车信息</text>
			</view>
			<!-- 内容主体区 -->
			<view class="main">
			<form>
				<view class="bg-gray"><text class="main-text">归还信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">借车单</text>
					<input class="word" style="-webkit-flex: 1;flex: 1;" placeholder="请输入借车单号"
						v-model="formData.lendId" />
				</view>
				<view class="uni-flex uni-column card" style="position: relative;">
					<text class="word" style="-webkit-flex: 1;flex: 1;width: 50%;">车辆状况</text>
					<text style="position: absolute;right: 130rpx;">{{tempFilePaths.length}}/1</text>
				</view>

				<view class="uni-flex uni-row card" style="height:auto;">
					<view class="uni-uploader-body">
						<view class="uni-uploader__files">
							<block v-for="(image,index) in tempFilePaths " :key="index">
								<view class="uni-uploader__file">
									<image class="uni-uploader__img" :src="image" :data-src="image" @tap="previewImage">
									</image>
								</view>
							</block>
							<view class="uni-uploader__input-box">
								<view class="uni-uploader__input" @tap="chooseImage"></view>
							</view>
						</view>
					</view>
				</view>
				<view class="uni-flex uni-column card" style="height: 60rpx;width: 80rpx;margin-top: 0;margin-left: 520rpx;">
					<text style="color: #DD524D;height: 100%;width: 100%;line-height: 60rpx;font-weight: 600;text-align: center;" @click="remove">清除</text>
				</view>
				<view class="bg-gray"><text class="main-text">借车信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">还车时间</text>
					<picker mode="date" :value="lendDate" @change="bindLendDateChange" class="word" 	style="-webkit-flex: 1;flex: 1;margin-top: 18rpx;"> 
						<view>{{lendDate}}</view>
					</picker>
				</view>
				<view>
					<button form-type="submit" @click="submit" :disabled="isAble" class="btn">申请</button>
				</view>
			</form>
			</view>
		</view>
	</view>
</template>

<script>
	function getDate(type) {
		const date = new Date();

		let year = date.getFullYear();
		let month = date.getMonth() + 1;
		let day = date.getDate();

		month = month > 9 ? month : '0' + month;;
		day = day > 9 ? day : '0' + day;

		return `${year}-${month}-${day}`;
	}
	export default {
		data() {
			return {
				lendDate: getDate({
					format: true
				}),
				tempFilePaths: [],
				isAble: true,
				formData:{
					lendId:'',
				}
			}
		},
		methods: {
			previewImage: function(e) {
				var current = e.target.dataset.src
				uni.previewImage({
					current: current,
					urls: this.tempFilePaths,
				})
			},
			remove: function() {
				this.tempFilePaths = [];
				this.isAble = true;
			},
			bindLendDateChange: function(e) {
				this.lendDate = e.detail.value
			},
			bindTimePickerChange: function(e) {
				this.timeIndex = e.target.value
			},
			bindPickerChange: function(e) {
				this.index = e.target.value
			},
			submit() {
				let _this = this;
				let timestamp = new Date().getTime();
				var lendTime = new Date(_this.lendDate);
				if(_this.formData.lendId!=''){
				uni.uploadFile({
					url: _this.apiServer + "/api/add/report/return",
					filePath: _this.tempFilePaths[0],
					name: "returnImg",
					header: {
						'content-type': 'multipart/form-data'
					},
					formData: {
						lendId: _this.formData.lendId,
						returnTime: Date.parse(lendTime),
						time:timestamp,
					},
					success(res) {
						res.data = JSON.parse(res.data);
						if (res.data.code == 200) {
							uni.showToast({
								title: '申报成功',
								icon: 'success',
								duration: 1000,
								success() {
									setTimeout(function() {
										uni.navigateBack({})
									}, 1000)
								}
							})
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: 'none',
								duration: 1000,
							})
						}
					},
					fail(res) {
						console.log('fail' + res.errMsg)
						uni.showToast({
							title: "接口访问出错",
							icon: 'none',
							duration: 1000,
						})
					}
				})
				}else{
					uni.showToast({
						title: '请检查借车单是否填写',
						icon: 'none',
						duration: 1000,
					})
				}
			},

			isFullImg: function() {
				return new Promise((res) => {
					uni.showModal({
						content: "图片上传个数已上限",
						success: (e) => {
							res(false)
						},
						fail: () => {
							res(false)
						}
					})
				})
			},
			chooseImage: async function() {
				let _this = this;
				if (_this.tempFilePaths.length === 1) {
					let isContinue = await _this.isFullImg();
					if (!isContinue) {
						return;
					}
				}
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					success: (res) => {
						_this.tempFilePaths.push(res.tempFilePaths[0]);
						_this.isAble = false;
						console.log(_this.tempFilePaths)
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
		align-items: center;
		/* justify-content: center; */
		flex-direction: column;
		width: 100%;
		height: 150%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff,#4389f2);
	}
	/* ---- */
	/* 头部界面 */
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
	input{
		height: 100%;
		width: 100%;
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
	.main {
		position: absolute;
		top: 275rpx;
		width: 80%;
	}
	
	.bg-gray {
		color: #ffffff;
		font-weight: 600;
		font-size: 40rpx;
		text-align: center;
		border-radius: 15rpx;
		box-sizing: border-box;
	}
	
	.main-text {
		border-bottom: 4rpx solid #fff;
	}
	
	.word {
		text-align: center;
	}
	
	.picker-view {
		width: 550rpx;
		height: 400rpx;
		margin-top: 20rpx;
	}
	
	.item {
		height: 50px;
		align-items: center;
		justify-content: center;
		text-align: center;
	}
	
	.card {
		background-color: #fff;
		border-radius: 8rpx;
		margin: 20rpx 0;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 80rpx;
		line-height: 80rpx;
	}
	
	.main-input {
		height: 100%;
	}
	
	.btn {
		width: 50%;
		font-size: 40rpx;
		font-weight: 600;
		color: #488ef3;
		border-radius: 20rpx;
	}
	
	.main-textarea {
		margin-left: 60rpx;
		width: 50%;
		height: 90%;
		border-radius: 10rpx;
		box-sizing: border-box;
		margin: 10rpx;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .4);
	}
	.main-textarea:hover{
		box-shadow: 0 2rpx 40rpx rgba(0, 0, 0, .4);
		animation: textareaShadow 0.5s;
	}
	@-webkit-keyframes textareaShadow{
		0% { box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .4);}
		100% { box-shadow: 0 2rpx 40rpx rgba(0, 0, 0, .4);}
	}
	@-webkit-keyframes textareaShadow{
		0% { box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .4);}
		100% { box-shadow: 0 2rpx 40rpx rgba(0, 0, 0, .4);}
	}
</style>
