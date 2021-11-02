<template>
	<!-- 保养申报 -->
	<view class="content">
		<!-- 头部区域 -->
		<view class="top">
			<image src="/static/work.png" class="topImg"></image>
			<text class="topText">保养申报</text>
		</view>
		<!-- 内容主体区 -->
		<view class="main">
			<form>
				<view class="bg-gray"><text class="main-text">申报人信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{name}}</text>
				</view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{no}}</text>
				</view>

				<view class="bg-gray"><text class="main-text">申报信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">保养时间</text>
					<picker mode="date" :value="protectDate" @change="bindProtectDateChange" class="word"
						style="-webkit-flex: 1;flex: 1;margin-top: 16rpx;">
						<view >{{protectDate}}</view>
					</picker>
				</view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
					<picker @change="bindPickerChange" :range="licenseArray" :range-key="'license'" class="input word"
						style="-webkit-flex: 1;flex: 1;">
						<label>{{licenseArray[index].license}}</label>
					</picker>
				</view>
				<view class="uni-flex uni-row card" >
					<text class="word" style="-webkit-flex: 1;flex: 1;">保养详情</text>
					<input placeholder="请输入..." class="word main-input" style="-webkit-flex: 1;flex: 1;"
						v-model="protectMsg" />
				</view>
				<view class="bg-gray"><text class="main-text">审批人信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">审批人</text>
					<picker @change="bindPickerChange2" :range="vetArray" :range-key="'name'" class="input word"
						style="-webkit-flex: 1;flex: 1;">
						<label>{{vetArray[index2].name}}</label>
					</picker>
				</view>
				<view>
					<button form-type="submit" @click="submit" class="btn">提交</button>
				</view>
			</form>
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
				protectDate: getDate({
					format: true
				}),
				name: this.$store.state.userInfo.name,
				no: this.$store.state.userInfo.no,
				licenseArray: [],
				index: 0,
				vetArray: [],
				index2: 0,
				protectMsg: '',
			}
		},
		created() {
			this.getLicense();
			this.getVet();
		},
		methods: {
			getVet() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/employee",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data",
					},
					data:{
						filter:'position',
						position:'vet',
					},
					success: res => {
						console.log(res.data.data);
						_this.vetArray = res.data.data;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			bindProtectDateChange: function(e) {
				this.protectDate = e.detail.value
			},
			getLicense() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/vehicleInfo",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data",
					},
					data: {
						no: _this.$store.state.userInfo.no,
						filter: 'all',
						position: _this.$store.state.userInfo.position,
					},
					success: res => {
						console.log(res.data.data);
						_this.licenseArray = res.data.data;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			submit() {
				let _this = this;
				let timestamp = new Date().getTime();
				let protectTime = new Date(_this.protectDate);
				uni.request({
					url: _this.apiServer + "/api/add/report/protect",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						manageNo: _this.$store.state.userInfo.no,
						protectLicense: _this.vetArray[_this.index2],
						protectTime: Date.parse(protectTime),
						protectMsg: _this.protectMsg,
						manageTime: timestamp,
						vetNo:_this.vetArray[_this.index2].no,
					},
					success(res) {
						if (res.data.code === 200) {
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
					}
				})
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},

			bindPickerChange2: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index2 = e.target.value
			},
			bindChange: function(e) {
				const val = e.detail.value
				this.year = this.years[val[0]]
				this.month = this.months[val[1]]
				this.day = this.days[val[2]]
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
		background-color: #fff;
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
	.main-text{
		border-bottom: 4rpx solid #fff;
	}
	.word {
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
	.main-input{
		height: 100%;
	}
	.btn{
		width: 50%;
		font-size: 40rpx;
		font-weight: 600;
		color: #488ef3;
		border-radius: 20rpx;
	}
	/* ---- */
</style>
