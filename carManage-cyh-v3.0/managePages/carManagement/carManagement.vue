<template>
	<!--manage- 车辆管理 -->
	<view class="content" :style="'position:'+contentPosition">
		<!-- 头部区域 -->
		<view class="top">
			<image src="/static/carbody.png" class="top-img"></image>
			<text class="top-text">好车险 中华行</text>
			<view class="uni-flex uni-row" style="margin-top: 150rpx;">
				<button @click="confirmDialog2" class="top-btn">管理</button>
			</view>
		</view>
		<!-- 搜索区域 -->
		<view>
			<uni-forms :model="search" ref="search">
				<input name="license" class="search-input search" placeholder="车牌号模糊查询" maxlength="15"
					placeholder-style="text-align: center" v-model="search.license">
				</input>
				<button form-type="submit" class="search-btn" @click="searchList">搜索</button>
			</uni-forms>
		</view>
		<!-- ---- -->
		<!-- 图表 -->
		<view style="margin-top: 60rpx;" :style="'display:'+chartsBlock">
			<view class="charts-box">
				<qiun-data-charts type="pie" :chartData="chartData" background="none" />
			</view>
		</view>
		<!-- ---- -->
		<!-- 车内容列表 -->
		<view v-for="(item,index) in productList" :key="index">
			<navigator :url="'carDetail/carDetail?license='+item.license">
				<view view class="uni-flex uni-row list">
					<view class="uni-flex uni-column">
						<image src="/static/carbody.png" class="list-img"></image>
						<!-- 先暂时用一个固定的代替 -->
						<text class="list-text1 list-text">{{item.license}}</text> <!-- 车牌 -->
					</view>
					<text class="list-text2 list-text">
						{{item.manageName}}
					</text> <!-- 姓名 -->
					<view v-if="item.status===0" class="state list-text list-text3" style="color: #ffe98f;">
						<text>已派出</text>
					</view>
					<view v-if="item.status===1" class="state list-text list-text3" style="color: #e3f9ff;">
						<text>未派出</text>
					</view>
					<view v-if="item.status===2" class="state list-text list-text3" style="color: #debcde;">
						<text>维修中</text>
					</view>
					<view v-if="item.status===3" class="state list-text list-text3" style="color: #d5ffd5;">
						<text>保养中</text>
					</view>
					<view v-if="item.status===4" class="state list-text list-text3" style="color: #a25d54;">
						<text>出借中</text>
					</view>
					<text class="list-text4">点击查看详情</text>
				</view>
			</navigator>
		</view>
		<!-- ---- -->


		<!-- 添加车辆 -->
		<view class="uni-flex uni-row">
			<uni-popup ref="dialogInput" type="dialog" @maskClick="show">
				<view class="popup uni-flex uni-row ">
					<form class="table">
						<view style="margin-left:10rpx;">
							<input v-model="formData.license" placeholder=" 请输入车牌号" class="card" />
							<!-- <input v-model="formData.img" placeholder=" 请拍照" class="card" /> -->
							<input v-model="formData.peopleNum" placeholder=" 请输入承载人数" class="card" />
							<input v-model="formData.emission" placeholder=" 请输入排量" class="card" />
							<input v-model="formData.type" placeholder=" 请输入车辆类型" class="card" />
							<input v-model="formData.price" placeholder=" 请输入价格" class="card" />
							<!-- 上传照片 《-->
							<view class="documentUp">
								<view class="uni-uploader">
									<view class="uni-uploader-head">
										<view class="uni-uploader-title" style="color: #999999;margin-left: 20rpx;">上传图片</view>
										<view class="uni-uploader-info">{{imageList.length}}/1</view>
									</view>
									<view class="uni-uploader-body">
										<view class="uni-uploader__files">
											<block v-for="(image,index) in imageList" :key="index">
												<view class="uni-uploader__file">
													<image class="uni-uploader__img" :src="image" :data-src="image" @tap="previewImage"></image>
												</view>
											</block>
											<view class="uni-uploader__input-box">
												<view class="uni-uploader__input" @tap="chooseImage"></view>
											</view>
										</view>
									</view>
									<view class="add-delete">清除</view>
								</view>
							</view>
							<!-- -----》 -->
						</view>
						<view>

							<button form-type="submit" @click="dialogInputConfirm" class="add-btn">确认添加</button>
						</view>
					</form>
				</view>
			</uni-popup>

			<!-- 废弃车辆 -->
			<uni-popup ref="dialogInput2" type="dialog" @maskClick="show">
				<view class="dialog">
					<form>
						<view>
							<input v-model="licence" placeholder=" 请输入车牌号" class="card" />
							<input v-model="reason" placeholder=" 输入废弃原因" class="card" />
						</view>
						<view>
							<button form-type="submit" @click="dialogInputConfirm2">确认废弃</button>
						</view>
					</form>
				</view>
			</uni-popup>
			<!-- ---- -->
		</view>

		<view>
			<uni-popup ref="dialogInput3" type="dialog">
				<view class="popup">
					<view class="uni-flex uni-column" style="text-align: center">
						<text style="color: #FF3333;font-size: large;">添加失败！</text>
						<text style="color: #999999;">车牌号重复</text>
						<text style="color: #999999;">请输入正确的车牌号</text>
					</view>
					<view class="uni-flex uni-row">
						<button @click="redown"
							style="width: 180rpx;height:70rpx; font-size: small;background-color: #F0AD4E; margin-top: 90rpx;">重新填写</button>
						<button @click="cancel"
							style="width: 180rpx;height:70rpx; font-size: small;background-color: #aaffff; margin-top: 90rpx;">取消</button>
					</view>
				</view>
			</uni-popup>
		</view>
		<!-- 添加车辆按钮 -->
		<view class="add-round" @click="confirmDialog" :style="'display:'+addVisible">
			+
		</view>

	</view>
</template>

<script>
	import uCharts from '../u-charts/config-ucharts.js';
	export default {
		data() {
			return {
				// 控制添加图标是否可见
				addVisible: 'block',
				chartData: {
					series: [{
						"data": [{
								"name": "未派出",
								"value": 50,
								"color": '#e3f9ff'
							},
							{
								"name": "已派出",
								"value": 30,
								"color": '#ffe98f'
							},
							{
								"name": "维修中",
								"value": 20,
								"color": '#debcde'
							},
							{
								"name": "保养中",
								"value": 18,
								"color": '#d5ffd5'
							}
						]
					}],
				},
				search: {
					license: '',
				},
				productList: [],
				formData: {
					license: '',
					img: [],
					peopleNum: '',
					emission: '',
					type: '',
					price: '',
				},
				// 控制图表显示
				chartsBlock: 'block',
				contentPosition: 'relative'
			}

		},
		onLoad() {
			this.getList();
		},
		methods: {
			getList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/vehicleInfo",
					method: 'POST',
					data: {
						filter: 'all',
						no: _this.$store.state.userInfo.no,
						position: _this.$store.state.userInfo.position,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					success: res => {
						console.log(res.data.data);
						_this.productList = res.data.data;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},
			searchList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/vehicleInfo",
					method: 'POST',
					data: {
						filter: 'license',
						no: _this.$store.state.userInfo.no,
						position: _this.$store.state.userInfo.position,
						license: _this.search.license,
					},
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					success: res => {
						console.log(res.data.data);
						_this.productList = res.data.data;
					},
					fail: res => {
						console.log("发生了错误");
					},
				})
			},

			confirmDialog() {
				this.$refs['dialogInput'].open()
				this.addVisible = 'none'
				this.chartsBlock = 'none'
				this.contentPosition = 'absolute'
			},
			confirmDialog2() {
				this.$refs['dialogInput2'].open()
				this.addVisible = 'none'
				this.chartsBlock = 'none'
				this.contentPosition = 'absolute'
			},

			dialogInputConfirm() {
				let _this = this;
				_this.$refs['dialogInput'].close();
				_this.addVisible = 'block'
				_this.chartsBlock = 'block'
				this.contentPosition = 'relative'
				// 提交表单
				uni.request({
					url: _this.apiServer + "/api/add/pickVehicle",
					method: 'POST',
					header: {
						'content-type': 'multipart/form-data; boundary=XXX'
					},
					data: '\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="license"' +
						'\r\n' +
						'\r\n' + _this.formData.license +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="peopleNum"' +
						'\r\n' +
						'\r\n' + _this.formData.peopleNum +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="emission"' +
						'\r\n' +
						'\r\n' + _this.formData.emission +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="manageNo"' +
						'\r\n' +
						'\r\n' + _this.$store.state.userInfo.no +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="type"' +
						'\r\n' +
						'\r\n' + _this.formData.type +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="price"' +
						'\r\n' +
						'\r\n' + _this.formData.price +
						'\r\n--XXX' +
						'\r\nContent-Disposition: form-data; name="img"' +
						'\r\n' +
						'\r\n' + _this.formData.img +
						'\r\n--XXX--',

					success: res => {

						uni.showToast({
							title: '添加中',
							icon: 'loading',
							duration: 1000,
							success() {
								setTimeout(function() {
									if (res.data.code === 200) {
										uni.showToast({
											title: '添加成功',
											icon: 'success',
											duration: 1000,
											success() {
												setTimeout(function() {
													_this.getList();
												}, 1000)
												
											}
											
										
										})

									} else {
										uni.showToast({
											title: '添加失败，请重新填写',
											icon: 'none',

											duration: 1000,

											success() {
												setTimeout(function() {
													_this.$refs['dialogInput']
														.open()
												}, 1000)
											}
										})
									}
								}, 1000)
							}
						})

					},
					fail: () => {
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "none"
						});
					}

				})
			},

			// 废弃车辆,这里采用的方式是直接提交
			dialogInputConfirm2() {
				let _this = this;
				_this.$refs['dialogInput2'].close();
				_this.addVisible = 'block'
				_this.chartsBlock = 'block'
				this.contentPosition = 'relative'
				// 提交表单
				uni.request({
					url: _this.apiServer + "/api/delete/banVehicle",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data"
					},
					data: {
						licence: _self.licence,
						reason: _self.reason
					},
					success: res => {
						console.log(res.data.data);
						if (res.data.code === 200) {
							console.log("success");
							var carouselList = res.data.data;
							_this.carouselList = carouselList;
						}
						_this.text = 'request success';
					},
					fail: () => {
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "none"
						});
					}
				})

			},

			redown() {
				this.$refs['dialogInput'].open();
			},
			cancel() {
				this.$refs['dialogInput3'].close();
			},
			// 点击遮罩层触发事件
			show(){
				this.addVisible = 'block'
				this.chartsBlock = 'block'
				this.contentPosition = 'relative'
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
		width: 60%;
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
		float: left;
		width: 25%;
		height: 69rpx;
		margin-top: 20rpx;
		line-height: 60rpx;
		background-color: #62a9f9;
		font-size: 40rpx;
		color: #fff;
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
	.dialog {
		background-color: #7bc4ff;
		width: 500rpx;
		height: 315rpx;
		border-radius: 15rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		padding: 10rpx;
	}

	.dialog button {
		width: 180rpx;
		height: 70rpx;
		background-color: #fff;
		margin-top: 45rpx;
		padding: 0;
		font-size: 35rpx;
		line-height: 70rpx;
		font-weight: 600;
		color: #7bc4ff;
	}

	
	/* 车内容区 */
	.list {
		background: #fff;
		border-radius: 50rpx;
		margin: 20rpx;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: auto;
		background-color: #61a8f9;
		justify-content: space-between;
		align-items: center;
	}

	.list .list-img {
		width: 250rpx;
		height: 250rpx;
	}

	.list .list-text {
		position: absolute;
		left: 320rpx;
		font-size: 40rpx;
		font-weight: 600;
		color: #fff;
	}

	.list .list-text1 {
		top: 25rpx;
	}

	.list .list-text2 {
		top: 90rpx;
	}
	
	.list .list-text3{
		top: 170rpx;
	}
	.state {
		border-radius: 12rpx;
		width: 130rpx;
		height: 60rpx;
		/* vertical-align: middle; */
		/* text-align: center; */
		line-height: 55rpx;
	}

	.list-text4{
		position: absolute;
		left: 525rpx;
		font-size: 25rpx;
		color: #fff;
		top: 200rpx;
	}
	/* ---- */

	/* 添加车辆 */
	.add-round {
		position: fixed;
		z-index: 2;
		right: 30rpx;
		bottom: 30rpx;
		border-radius: 50%;
		width: 120rpx;
		height: 120rpx;
		color: #4d93f4;
		text-align: center;
		line-height: 111rpx;
		font-weight: 600;
		font-size: 80rpx;
		background-color: #fff;
	}
	.card {
		background: #fff;
		border-radius: 8rpx;
		margin: 15rpx 0;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 75rpx;
		width: 500rpx;
	}
	.table{
		height: 1030rpx;
		background-image: linear-gradient(#4389f2, #7bc4ff);
		width: 600rpx;
		border-radius: 20rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		padding-left: 40rpx;
		padding-top: 40rpx;
		box-sizing: border-box;
	}
	.documentUp{
		background-color: #fff;
		width: 500rpx;
		border-radius: 10rpx;
	}
	.add-delete{
		width: 80rpx;
		text-align: center;
		font-size: 30rpx;
		font-weight: 600;
		margin-left: 410rpx;
		color: #d02926;
	}
	.add-btn{
		width: 200rpx;
		background-color: #FFFFFF;
		font-weight: 600;
		color: #70b8fd;
		margin-top: 40rpx;
		margin-left: 160rpx;
	}
	/* ---- */
	/* 图表 */
	.charts-box {
		width: 100%;
		height: 300px;
	}

</style>
