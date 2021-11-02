<template>
	<!-- 员工信息 -->
	<view class="content">
		<!-- 头部区域 -->
		<view class="top" >
			<view class="head">
				<image src="/static/head.png" class="headimg"></image>
				<text class="name">{{name}}</text>
			</view>
		</view>
		
		
	
				<uni-card class="card">
					<text style="margin-left: 10rpx; margin-top: 20rpx;font-size: 40rpx;" >职工号：{{no}}</text>
				</uni-card>
				
				<uni-card class="card">
					<text style="margin-left: 10rpx; margin-top: 20rpx;font-size: 40rpx;" >职位：{{position}}</text>
				</uni-card>
				
				<uni-card class="card">
					<text style="margin-left: 10rpx; margin-top: 20rpx;font-size: 40rpx;" >上司编号：{{bossNo}}</text>
				</uni-card>
	  
	</view>
</template>

<script>
	export default{
		data(){
			return{
				name:this.$store.state.userInfo.name,
				no:this.$store.state.userInfo.no,
				position:this.$store.state.userInfo.position,
				bossNo:this.$store.state.userInfo.bossNo				
			}
		},
		created(){
			
		},
		methods:{

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
	.name{
		color: white;
		font-size: 40rpx;
		font-weight: 600;
	}
	.card{
		box-shadow: 5rpx 5rpx 5rpx -4rpx rgba(0,0,0,.4);
	}
</style>
