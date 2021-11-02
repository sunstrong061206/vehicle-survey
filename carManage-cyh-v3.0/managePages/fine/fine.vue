<template>
	<view >
		<view class="top "> 
			<image class="img" src="/static/work.png" style="width: 600rpx;height: 400rpx;"></image>
		</view>
		<view>
			<form :model="fineForm" @submit="formSubmit" ref="fineForm">
				<view class="bg-gray"><text>申报人信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">姓名</text>
					<text style="-webkit-flex: 1;flex: 1;"></text>  <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">部门</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">工号</text>
					<text style="-webkit-flex: 1;flex: 1;"></text> <!-- 通过api接口从数据库调取数据自动生成 -->
				</view>
				
				<view class="bg-gray"><text>申报信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">被罚款人</text>
					<input name="beFinedPeople" v-model="fineForm.beFinePeople" placeholder="请输入..." class="input" style="-webkit-flex: 1;flex: 1;"/>
				</view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">车牌号</text>
					<!-- 下面这个下拉选框的组件要实现通过api接口从数据库调取数据 -->
					<picker @change="bindPickerChange" :range="array" class="input" style="-webkit-flex: 1;flex: 1;">
							<label class="">{{array[index]}}</label>
					</picker>
				</view>
				<view class="uni-flex uni-column card" style="height: 200rpx;">
					<text class="word" style="-webkit-flex: 1;flex: 1;">罚款详情</text>
					<textarea name="fineDetail" placeholder="请输入...(时间.地点.罚款原因)" style="margin-left: 60rpx;"></textarea>
				</view>
	
				<view class="bg-gray"><text>审批人信息</text></view>
				<view class="uni-flex uni-row card">
					<text class="word" style="-webkit-flex: 1;flex: 1;">审批人</text>
					<!-- 下面这个下拉选框的组件要实现通过api接口从数据库调取数据 -->
					<picker @change="bindPickerChange2" :range="array2" class="input" style="-webkit-flex: 1;flex: 1;">
							<label class="">{{array2[index2]}}</label>
					</picker>
				</view>
				<view>
					<button form-type="submit" @click="fineForm">提交</button>
				</view>
			</form>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
				array:['--请选择--','A','B','C','D'],
				index:0,
				
				array2:['--请选择--','李四','张三','王五'],
				index2:0,
				
				fineForm:{
					beFinedPeople:'',
				}
				
				// imageURL: '/static/background2.jpg',
				
			}
		},
		methods:{
			bindPickerChange: function(e) {
			            console.log('picker发送选择改变，携带值为', e.target.value)
			            this.index = e.target.value
			        },
					
			bindPickerChange2: function(e) {
			            console.log('picker发送选择改变，携带值为', e.target.value)
			            this.index2 = e.target.value
			        },
			
			fineForm(){
				uni.request({
					url:'/ ',
					data:{
						beFinedPeople:this.fineForm.beFinePeople,
						
					},
					method:'POST',
					success(res){
						if(res.data.status === '200'){
							uni.showToast({
								title: '提交成功',
								duration: 2000
							})
						}
					}
				})
			},
		},
		
	}
</script>

<style>
	.bg-gray{
		background-color: rgba(112, 170, 214, 0.8);
	}
	.word{
		text-indent: 40rpx;
		margin-top:25rpx;
	}
	.card {
		background-color: rgba(233, 233, 233, 0.8);
		border-radius: 8rpx;
		margin:20rpx 0;
		position: relative;
		box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, .3);
		height: 100rpx;
	}
	.top{
		background-color: rgba(255, 255, 255, 0.5);
		height: 380rpx;
	}
	.img{
		position: relative;
		left: 50%;
		transform: translate(-50%);
	}
</style>
