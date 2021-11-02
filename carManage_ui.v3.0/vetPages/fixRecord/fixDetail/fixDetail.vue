<template>
	<!-- vet-维修详情页 -->
	<!-- js代码没写完 -->
	<view class="content">
		<view>
				<view class="bg-gray"><text class="main-text">申报人信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
					<text style="-webkit-flex: 1;flex: 1;"></text>  <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">部门</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				
				<view class="bg-gray"><text class="main-text">申报信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">申报时间</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">维修原因</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">图片</text>
					<image style="-webkit-flex: 1;flex: 1;"></image> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="bg-gray"><text class="main-text">审批人信息</text></view>
				<view class="uni-flex uni-row uni-card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">审批人</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
		</view>
		<view>
			<view class="bg-gray"><text>审批流程</text></view>
			<view>
				<!-- 从后端获取审批状态，具体怎么做还没想好 -->
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
				array2:['--请选择--','李四','张三','王五'],
				index2:0,
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
		top: 230rpx;
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
		font-weight: 600;
		font-size: 35rpx;
		color: #999999;
	}
	
	.main-text {
		border-bottom: 4rpx solid #fff;
	}
</style>
