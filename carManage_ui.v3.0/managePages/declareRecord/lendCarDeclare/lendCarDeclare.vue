<template>
	<!-- 借车申报记录 -->
	<view class="content" :style="'height:'+contentHeight+'%'">
		<!-- 头部区域 -->
		<view class="top">
			<view style="justify-content: space-between;">
				<image src="/static/carbody.png" class="top-img"></image>
				<text class="top-text">好车险 中华行</text>
			</view>
		</view>
		<!-- 搜索框区 -->
		<view >
			<form>
					<input name="keyword" type="text" placeholder="搜索车辆" placeholder-style="text-align: center" v-model="formData.license" class="search-input search"/>
					<button @click="search" class="search-btn">搜索</button>
			</form>
		</view>
		
		<!-- 内容主体区 -->
		<view style="position: absolute;top: 380rpx;left: 3rpx;width: 740rpx;">
		<view v-for="(item,index) in lendList" :key="index" class="card">
			<navigator :url="'lendCarDetail/lendCarDetail?lendId='+item.lendId">
				<view style="font-weight: bold;margin-left: 30rpx;">
					<text>{{item.lendMsg}}</text>
				</view>
				<view style="color: #999999;margin-left: 30rpx;">
					<text>{{item.name}}</text>
					<text>{{item.phone}}</text>
				</view>
				<view class="uni-flex uni-row"
					style="color: #999999;margin-left: 30rpx;justify-content: space-between; margin-right: 60rpx;">
					<text>{{item.lendTime}}</text>
					<text>{{item.lendLicense}}</text>
				</view>
			</navigator>
		</view>
		</view>
	</view>
</template>

<script>
	export default {
		created(){
			if(this.contentHeight<100)
				this.contentHeight = 100
		},
		data() {
			return {
				lendList: [],
				// content总高度
				contentHeight:0,
				formData:{
					license:''
				}
				}
		},
		onLoad() {
			this.getList();
		},
		methods: {
			getList() {
				let _this = this;
				uni.request({
					url: _this.apiServer + "/api/get/getAll/lend",
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						'enctype':"multipart/form-data"
					},
					data:{
						filter:'all',
						no:_this.$store.state.userInfo.no,
						position:_this.$store.state.userInfo.position,
					},
					success: res => {
						console.log(res.data.data);
						this.lendList = res.data.data;
						this.contentHeight = 27 + res.data.data.length*11.4
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
	
			search(){
				let _this=this;
				uni.request({
					url:_this.apiServer+"/api/get/getAll/lendCar",
					method:'POST',
					header: {
					 'content-type': 'application/x-www-form-urlencoded',
					 'enctype': "multipart/form-data"
					},
					data:{
						no:_this.$store.state.userInfo.no,
						filter:'license',
						license:_this.formData.license,
					},
					success:res=>{
						console.log(res.data.data);
						_this.lendList = res.data.data;
					},
					fail:res=>{
					console.log("发生了错误");
					},
				})
			}
			},
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
		/* height: 100%; */
		position: absolute;
		background-image: linear-gradient(#7bc4ff,#4389f2);
	}
	/* ---- */
	/* 搜索区 */
	.search-input{
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
	.search-input:hover{
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		animation:searchShadow 0.5s;
	}
	.search-btn{
		float: left;
		width: 25%;
		height: 69rpx;
		margin-top: 20rpx;
		line-height: 60rpx;
		background-color: #62a9f9;
		font-size: 40rpx;
		color: #fff;
	}
	.button-hover{
		opacity: 60%;
	}
	@-webkit-keyframes searchShadow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0); }
		100% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4); }
	}
	@keyframes searchShadow{
		0% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, 0); }
		100% { box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0, 0, 0, .4); }
	}
	/* ---- */
	/* 头部界面 */
	.top {
		position: relative;
		background-color: #7bc4ff;
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
		width: 220rpx;
		height: 220rpx;
		box-sizing: border-box;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
		border-radius: 50%;
	}
	.top .top-text{
		position: absolute;
		top: 80rpx;
		right: 110rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #fff;
		text-shadow: 5rpx 5rpx 10rpx rgba(92, 163, 248,.4);
	}
	/* ---- */
	
	/* 内容主体区 */
	.card {
		border-radius: 15rpx;
		margin: 20rpx;
		position: relative;
		box-shadow: 2rpx 2rpx 4rpx rgba(0, 0, 0, .4);
		height: 145rpx;
		background-color: #fff;
	}
	/* ---- */
</style>
