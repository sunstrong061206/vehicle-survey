<template>
	<!-- 保养详细信息 -->
	<view class="content">
		<!-- 内容主体区 -->
		<view class="main">
		<view  v-for="(item,index) in protectList" :key="index">
				<view class="bg-gray"><text class="main-text">申报人信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{name}}</text>  
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">职位</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{position}}</text> 
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{no}}</text> 
				</view>
				
				<view class="bg-gray"><text class="main-text">申报信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">申报时间</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{item.manageTime}}</text> 
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{item.protectLicense}}</text> 
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">保养原因</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{item.protectMsg}}</text> 
				</view>
				<view class="bg-gray"><text class="main-text">审批人信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">审批人编号</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{item.vetNo}}</text> 
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">审批人时间</text>
					<text class="word" style="-webkit-flex: 1;flex: 1;">{{item.vetTime}}</text> 
				</view>	
		</view>
		</view>
	</view>
</template>

<script>
	export default{
		onLoad:function(options) {
			this.protectId=options.protectId;
			console.log(options);
			this.getDetail();
		},
		data(){
			return{
				protectId:'',
				name:this.$store.state.userInfo.name,
				no:this.$store.state.userInfo.no,
				position:this.$store.state.userInfo.position,
				protectList:[],
			}
		},
		methods:{
			getDetail(){
				let _this=this;
							uni.request({
								url: _this.apiServer+"/api/get/getDetail/protect",
								method:'POST',
								header: {
								 'content-type': 'application/x-www-form-urlencoded',
								 'enctype': "multipart/form-data"
								},
								data:{
									protectId:_this.protectId,
								},
								success:res=>{
									console.log(res.data.data);
									_this.protectList = res.data.data;
								},
								fail:res=>{
								console.log("发生了错误");
								},
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
	
	/* 背景 */
	.content {
		display: flex;
		align-items: center;
		/* justify-content: center; */
		flex-direction: column;
		width: 100%;
		height: 150%;
		position: absolute;
		background-image: linear-gradient(#7bc4ff, #4389f2);
	}
	
	/* ---- */
	/* 内容主体 */
	.main {
		position: absolute;
		top: 45rpx;
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
	
	.word {
		text-align: center;
		height: 70rpx;
		line-height: 70rpx;
	}
	
	.main-text {
		border-bottom: 4rpx solid #fff;
	}
</style>
