<template>
	<!-- 勘察员筛选违规记录 -->
	<view class="content" :style="'height:'+contentHeight+'%'">
		<!-- 头部区域 -->
		<view class="top">
			<view style="justify-content: space-between;">
				<image src="/static/carbody.png" class="top-img"></image>
				<text class="top-text">违规记录</text>
				<view class="uni-flex uni-row" style="margin-top: 150rpx;">
					<button @click="confirmDialog" class="top-btn">筛选</button>
				</view>
			</view>
			<view>
				<uni-forms :model="search" ref="search">
					<view class="uni-flex uni-row date" v-if="array[index].name==='时间'" style="position: absolute;top:278rpx;">
						<uni-forms-item>
							<view class="uni-list">
								<view class="uni-list-cell">
									<view class="uni-list-cell-left">
										日期范围
									</view>
									<view class="uni-list-cell-db">
										<picker mode="date" :value="startDate" @change="bindStartDateChange">
											<view class="uni-input">{{startDate}}</view>
										</picker>
									</view>
									<text>至</text>
									<view class="uni-list-cell-db">
										<picker mode="date" :value="endDate" @change="bindEndDateChange">
											<view class="uni-input">{{endDate}}</view>
										</picker>
									</view>

								</view>
							</view>
						</uni-forms-item>
					</view>
					<view class="uni-flex uni-row " style="position: absolute;top: 260rpx;width: 100%;"
						v-if="array[index].name==='车牌号'">

						<uni-forms-item style="width: 100%;">
							<input name="license" class="search-input search" placeholder="车牌号模糊查询" focus maxlength="15"
								placeholder-style="text-align: center" v-model="search.license">
							</input>
						</uni-forms-item>
					</view>
					<view class="uni-flex uni-row search" v-if="array[index].name==='结果'">
						<uni-forms-item>
							<view class="uni-list-cell-db">
								<picker @change="resultPickerChange" :value="resultIndex" :range="resultArray"
									range-key="result">
									<view class="uni-input">{{resultArray[resultIndex].result}}</view>
								</picker>
							</view>
						</uni-forms-item>
					</view>
					<view class="uni-flex uni-row" v-if="array[index].name!=='所有'">
						<uni-forms-item>
							<button form-type="submit" class="search-btn" @click="searchList()">搜索</button>
						</uni-forms-item>
					</view>
				</uni-forms>
			</view>
		</view>
		<!-- 内容主体区 -->
		<view style="position: absolute;top: 380rpx;left: 3rpx;width: 740rpx;">
		<view v-for="(item,index) in productList" :key="index" class="uni-flex uni-column card"
			style="margin-top: 40rpx;margin-left: 30rpx;">
			<navigator :url="'/surveyPages/fine/fineDetail/fineDetail?punishId='+ item.punishId">
				<view style="font-weight: bold;margin-left: 30rpx;">
					<text>违规单号:{{item.punishId}}</text>
				</view>
				<view style="color: #999999;margin-left: 30rpx;">
					<text>罚款金额:{{item.punishAmount}}</text>
				</view>
				<view class="uni-flex uni-row"
					style="color: #999999;margin-left: 30rpx;justify-content: space-between; margin-right: 60rpx;">
					<text>违规信息:{{item.accidentMsg}}</text>
				</view>
			</navigator>
		</view>
		</view>
		<!-- --- -->


		<uni-popup ref="dialogInput" type="bottom">
			<view class="popup uni-flex uni-column">
				<view>
					<form>
						<view class="uni-list-cell" style="background-color: #fff;">
							<view class="uni-list-cell-left">
								查询条件
							</view>
							<view class="uni-list-cell-db">
								<picker @change="bindPickerChange" :value="index" :range="array" range-key="name">
									<view class="uni-input">{{array[index].name}}</view>
								</picker>
							</view>
						</view>
						<button form-type="submit" @click="dialogInputConfirm">确定</button>
					</form>
				</view>
			</view>
		</uni-popup>


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
				array: [{
					name: '所有'
				}, {
					name: '时间'
				}, {
					name: '车牌号'
				}, {
					name: '是否缴款'
				}],
				index: 0,
				resultIndex: 0,
				resultArray: [{
						result: '未缴款'
					},
					{
						result: '已缴款'
					}
				],
				startDate: getDate({
					format: true
				}),
				endDate: getDate({
					format: true
				}),
				productList: [],
				search: {
					license: ''
				},
				// content总高度
				contentHeight:0,
			}

		},
		onLoad() {
			this.getList();
		},
		methods: {
			confirmDialog() {
				this.$refs['dialogInput'].open() //打开popup栏
			},
			close() {
				this.$refs['dialogInput'].close()
			},

			dialogInputConfirm() {
				this.$refs['dialogInput'].close();
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为：' + e.detail.value)
				this.index = e.detail.value
			},
			resultPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为：' + e.detail.value)
				this.resultIndex = e.detail.value
			},
			bindStartDateChange: function(e) {
				this.startDate = e.detail.value
			},
			bindEndDateChange: function(e) {
				this.endDate = e.detail.value
			},
			getList() {
				uni.request({
					url: this.apiServer + "/api/get/getAll/punish",
					method: 'POST',
					data: {
						filter: 'all',
						no: this.$store.state.userInfo.no,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					success: res => {
						this.productList = res.data.data;
						this.contentHeight = 27 + res.data.data.length*12.7
						if(this.contentHeight<100)
							{
								this.contentHeight = 100
							}
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			searchList() {
				let _this = this;
				var beginTime = new Date(_this.startDate);
				var endTime = new Date(_this.endDate);
				var begin = Date.parse(beginTime);
				var end = Date.parse(endTime);
				var t = '';
				if (begin > end) {
					t = begin;
					begin = end;
					end = t;
				};
				if (_this.index == 1) {
					uni.request({
						url: _this.apiServer + "/api/get/getAll/punish",
						data: {
							filter: 'time',
							no: _this.$store.state.userInfo.no,
							begin: begin,
							end: end,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype': "multipart/form-data"
						},
						method: 'GET',
						success: res => {
							_this.productList = res.data.data;
						},
						fail: res => {
							console.log("未查询到相关车辆")
						},
					})
				};
				if (_this.index == 2) {
					uni.request({
						url: _this.apiServer + "/api/get/getAll/punish",
						data: {
							filter: 'license',
							no: _this.$store.state.userInfo.no,
							license: _this.search.license,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype': "multipart/form-data"
						},
						method: 'GET',
						success: res => {
							console.log(res.data);
							_this.productList = res.data.data;
						},
						fail: res => {
							console.log("未查询到相关车辆")
						},
					})
				};
				if (_this.index == 3) {
					console.log('click')
					uni.request({
						url: _this.apiServer + "/api/get/getAll/punish",
						data: {
							filter: 'payStatus',
							no: _this.$store.state.userInfo.no,
							payStatus: _this.resultIndex,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype': "multipart/form-data"
						},
						method: 'GET',
						success: res => {
							_this.productList = res.data.data;
						},
						fail: res => {
							console.log("未查询到相关车辆")
						},
					})
				};



			}
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
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}

	/* ---- */
	/* 头部界面 */
	.top {
		position: relative;
		background-color: #7bc4ff;
		height: 250rpx;
		border-bottom-left-radius: 20rpx;
		border-bottom-right-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		margin-bottom: 30rpx;
	}

	.top .top-img {
		position: absolute;
		top: 15rpx;
		left: 15rpx;
		width: 220rpx;
		height: 220rpx;
		box-sizing: border-box;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		border-radius: 50%;
	}

	.top .top-text {
		position: absolute;
		top: 80rpx;
		right: 110rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #fff;
		text-shadow: 5rpx 5rpx 10rpx rgba(92, 163, 248, .4);
	}

	.top .top-btn {
		position: absolute;
		right: 10rpx;
		bottom: 10rpx;
		width: 90rpx;
		height: 60rpx;
		line-height: 60rpx;
		font-size: 30rpx;
		padding: 0;
		color: #5da8e4;
		font-weight: 600;
	}

	/* ---- */
	/* 搜索区 */
	.search-input {
		background: pink;
		border-radius: 10rpx;
		margin: 20rpx 0;
		position: relative;
		height: 65rpx;
		float: left;
		width: 70%;
		background-color: #fff;
		margin-left: 25rpx;
		margin-right: 61rpx;
		border: 2rpx solid #62a9f9;
	}

	.search-input:hover {
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		animation: searchShadow 0.5s;
	}

	.search-btn {
		position: absolute;
		left: 610rpx;
		top: 110rpx;
		width: 120rpx;
		height: 70rpx;
		margin-top: 20rpx;
		line-height: 70rpx;
		background-color: #62a9f9;
		font-size: 40rpx;
		color: #fff;
		padding: 0;
	}

	.button-hover {
		opacity: 60%;
	}

	@-webkit-keyframes searchShadow {
		0% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	@keyframes searchShadow {
		0% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0);
		}

		100% {
			box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		}
	}

	/* ---- */
	/* 内容主体区 */
	.card {
		border-radius: 15rpx;
		margin: 20rpx;
		margin-bottom: 0;
		position: relative;
		box-shadow: 2rpx 2rpx 4rpx rgba(0, 0, 0, .4);
		height: 145rpx;
		background-color: #fff;
	}

	/* ---- */
</style>
