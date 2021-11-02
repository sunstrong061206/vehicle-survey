<template>
	<view>
		<view class="top">
			<view class="uni-flex uni-row" style="justify-content: space-between;">
				<image src="/static/carbody.png" style="width: 220rpx;height: 180rpx;"></image>
				<text
					style="font-size: larger;margin-top: 120rpx;color: #DCDFE6;">好车险
					中华行</text>
				<view class="uni-flex uni-row" style="margin-top: 150rpx;">
					<button @click="confirmDialog"
						style="width: 130rpx; height: 60rpx; vertical-align:middle; text-align: center; line-height: 55rpx;">筛选</button>
				</view>
			</view>
			<view>
				<uni-forms :model="search" ref="search">
					<view class="uni-flex uni-row date" v-if="array[index].name==='时间'">
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
					<view class="uni-flex uni-row search" v-if="array[index].name==='车牌号'">
			
						<uni-forms-item>
							<input name="license"
								style="width: 200rpx; text-align: center; margin-top: 15rpx; margin-left: 50rpx;"
								placeholder="车牌号模糊查询" focus maxlength="15" placeholder-style="text-align: center"
								v-model="search.license">
							</input>
						</uni-forms-item>
					</view>
					<view class="uni-flex uni-row" v-if="array[index].name!=='所有'">
						<uni-forms-item>
							<button form-type="submit"
								style="width: 130rpx; height: 60rpx; vertical-align:middle; text-align: center; line-height: 55rpx; left:620rpx"
								@click="searchList">搜索</button>
						</uni-forms-item>
					</view>
				</uni-forms>
			</view>
			</view>
<view  style="margin-top: 100rpx;">
		<view v-for="(item,index) in productList" :key="index" class="uni-flex uni-column card">
			<navigator
				:url="'/managePages/violationsRecord/violationsDetail/violationsDetail?=punishId'+ item.punishId">
			<view class="uni-flex uni-row"
				style="margin-left: 30rpx;justify-content: space-between; margin-right: 60rpx;">
				<text style="font-weight: bold;">{{item.accidentMsg}}</text>
				<view v-if="item.payStatus===1" style="background-color: #a6ffff;border-radius: 12rpx; ">已处理</view>
				<view v-if="item.payStatus===0" style="background-color: #ffb84d;border-radius: 12rpx; ">未处理</view>
			</view>
			<view class="uni-flex uni-row"
				style="margin-left: 30rpx;justify-content: space-between; margin-right: 20rpx;">
				<text style="color: #999999;">{{item.accidentPoint}}</text>
				<view style="background-color: #fff3f3; color: #FF0000;"><text>{{item.punishMsg}}</text></view>
				<text style="color: #999999;">{{item.manageNo}}</text>
			</view>
			<view class="uni-flex uni-row"
				style="margin-left: 30rpx;justify-content: space-between; margin-right: 20rpx;">
				<text style="color: #999999;">{{item.accidentTime}}</text>
				<text style="color: #999999;">{{item.license}}</text>
			</view>
			<view v-if="item.payStatus===1" style="margin-left: 300rpx;">
				<text style="font-size: small;">处理时间：{{item.doneTime}}</text>
			</view>
			</navigator>
		</view>
		</view>
		<uni-popup ref="dialogInput" type="bottom">
			<view class="popup uni-flex uni-column">
				<view>
					<form>
						<view class="uni-list-cell">
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
				},],
				index: 0,
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
			bindStartDateChange: function(e) {
				this.startDate = e.detail.value
			},
			bindEndDateChange: function(e) {
				this.endDate = e.detail.value
			},
			getList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/punish",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype': "multipart/form-data",
					},
					data: {
						no: _this.$store.state.userInfo.no,
						filter: 'all',
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
				var beginTime = new Date(_this.startDate);
				var endTime = new Date(_this.endDate);
				var begin =  Date.parse(beginTime);
				var end =  Date.parse(endTime);
				var t = '';
				if(begin>end){
					t=begin;
					begin=end;
					end= t;
				};
				
				if(_this.index==1)
				{
					
					uni.request({
						url: _this.apiServer + "/api/get/getAll/punish",
						data: {
							filter:'time',
							no: _this.$store.state.userInfo.no,
							begin: begin,
							end: end,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype':"multipart/form-data"
						},
						method: 'POST',
						success: res => {
							console.log(res.data.data);
							_this.productList = res.data.data;
						},
						fail: res => {
							console.log("未查询到相关车辆")
						},
					})
				};
				if(_this.index==2)
				{
					uni.request({
						url:_this.apiServer +  "/api/get/getAll/punish",
						data: {
							filter:'license',
							no: _this.$store.state.userInfo.no,
							license: _this.search.license,
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
							'enctype':"multipart/form-data"
						},
						method: 'POST',
						success: res => {
							console.log(res.data.data);
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
.search {
		background: #fff;
		border-radius: 8rpx;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 80rpx;
	}

	.top {
		background-color: #5eb9b2;
		border-radius: 30rpx;
		height: 280rpx;
	}

	.card {
		background: #f4f4f4;
		border-radius: 8rpx;
		margin: 20rpx 0;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 180rpx;
	}
	.add-round {
		position: fixed;
		z-index: 999;
		right: 30rpx;
		bottom: 30rpx;
		border-radius: 50%;
		width: 120rpx;
		height: 120rpx;
		color: #fff;
		text-align: center;
		line-height: 120rpx;
		font-weight: 100;
		font-size: 80rpx;
	}
	.image{
			width: 100rpx;
			height: 100rpx;
			background-color: #5eb9b2;
			border-radius: 50rpx;
		}
		.date {
			background: #fff;
			border-radius: 8rpx;
			position: relative;
			box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
			height: 80rpx;
		}
</style>
