<template>
	<view class="content">
		<!-- 内容主体 -->
		<view class="box">
			<!-- 上传图片《----- -->
			<view class="text1">
				上传照片
			</view>
			<view class="documentUp">
				<view class="uni-uploader">
					<view class="uni-uploader-head">
						<view class="uni-uploader-title">点击可预览选好的图片</view>
						<view class="uni-uploader-info">{{imageList.length}}/9</view>
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
				</view>
				<view class="add-delete">清除</view>
			</view>
			<!-- -----》上传图片 -->
			
			
			<!-- 照片回显《------- -->
				<!-- 可编辑的，为了防止识别后车牌不对，可以手动更改 -->
				<view class="text2">车牌号:</view>
				<input type="text" class="returnMessage"/>
			<!-- -----》 -->
			
			<!-- 原因说明《----- -->
				<view class="text2">
					原因说明：
				</view>
				<textarea value="" placeholder="请输入原因" class="season"/>
			<!-- ---- -->
			
			<!-- 提交按钮 -->
				<button type="primary">提交</button>
		</view>
	</view>
</template>

<script>
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
		justify-content: center;
		background-image: linear-gradient(#4389f2,#7bc4ff);
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: absolute;
	}
	
	/* 内容主题 */
	.box{
		width: 610rpx;
		height: 1180rpx;
		background-color: #fff;
		border-radius: 15rpx;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4) ;	
	}
	
	/* 文件上传《----- */
	.text1{
		width: 100%;
		font-size: 45rpx;
		font-weight: 600;
		color: #5299f6;
		text-align: center;
	}
	
	.documentUp{
		box-shadow: 0rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4) ;
		margin: 20rpx;
	}
	.add-delete{
		width: 80rpx;
		text-align: center;
		font-size: 30rpx;
		font-weight: 600;
		margin-left: 475rpx;
		color: #d02926;
	}
	/* -----》 */
	
	/* 照片回显《----- */
	.text2{
		font-size: 45rpx;
		font-weight: 600;
		margin-bottom: 20rpx;
		color: #5299f6;
	}
	
	.returnMessage{
		width: 80%;
		height: 50rpx;
		border: 4rpx solid #5299f6;
		margin: 0 auto;
		border-radius: 15rpx;
		margin-bottom: 20rpx;
	}
	
	.returnMessage:hover{
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4);
		animation: messageShow 1s;
	}
	
	@-webkit-keyframes messageShow{
		0% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,0); }
		100% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4); }
	}
	@keyframes messageShow{
		0% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,0); }
		100% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4); }
	}

	/* ----》 */
	
	/* 原因说明《--- */
	.season{
		width: 94%;
		height: 300rpx;
		box-shadow: 0rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4) ;
		border-radius: 20rpx;
		margin: 20rpx;
	}
	.season:hover{
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4) ;
		animation: seasonShadow 0.5s;
	}
	@-webkit-keyframes seasonShadow{
		0% { box-shadow: 0rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4) ; }
		100% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4) ; }
	}
	@keyframes seasonShadow{
		0% { box-shadow: 0rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4) ; }
		100% { box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0,0,0,.4) ; }
	}
	/* ----》 */
	
	/* 按钮 */
	button[type=primary] {
		background-color: #468cf3;
		width: 200rpx;
		font-size: 40rpx;
	}
	.button-hover[type=primary] {
		background-color:#aedafe;
	}
</style>
