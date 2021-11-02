<template>
	<view class="content">
		<!-- 头部欢迎界面 -->
		<view class="top">
			<image src="../../../static/carbody.png" class="top-img"></image>
			<text class="top-text">好车险 中华行</text>
			<view class="welcome">{{text}}</view>
			<view class="message" @click="confirmDialog"></view>
			<view class="messageCount" v-if="messageCount !== 0">{{messageCount}}</view>
		</view>
		<!-- 分段器 -->
		<template>
			<view>
				<uni-segmented-control :current="fdq_current" :values="items" @clickItem="onClickItem" styleType="text"
					activeColor="#ffffff"></uni-segmented-control>
				<view class="content">
					<view v-show="fdq_current === 0">
						<view class="wrap">
							<view class="meuns" v-for="(item,index) in meunsList" :key="index"
								@click="goDetailPage(item.id)">
								<image :src="item.icon"></image>
								{{item.msg}}
							</view>
						</view>
					</view>
					<view v-show="fdq_current === 1">
						<view class="wrap">
							<view class="meuns" v-for="(item,index2) in meunsList2" :key="index2"
								@click="goDetailPage(item.id)">
								<image :src="item.icon"></image>
								<text>{{item.msg}}</text>
							</view>
						</view>
					</view>
					<view v-show="fdq_current === 2">
						<view class="wrap">
							<view class="meuns" v-for="(item,index3) in meunsList3" :key="index3"
								@click="goDetailPage(item.id)">
								<image :src="item.icon"></image>
								<text>{{item.msg}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</template>
		<!-- <view class="card">主要功能</view>
		<view class="wrap" style="justify-content:flex-start;height: 280rpx;">
			<view class="meuns" v-for="(item,index) in meunsList" :key="index" @click="goDetailPage(item.id)">
				<image :src="item.icon"></image>
				{{item.msg}}
			</view>
		</view>

		<view class="card">车辆信息</view>

		<view class="wrap" style="justify-content:flex-start;height: 250rpx;">
			<view class="meuns" v-for="(item,index2) in meunsList2" :key="index2" @click="goDetailPage(item.id)">
				<image :src="item.icon"></image>
				<text>{{item.msg}}</text>
			</view>

		</view>
		<view class="card">车辆信息</view>

		<view class="wrap" style="justify-content:flex-start;height: 250rpx;">
			<view class="meuns" v-for="(item,index3) in meunsList3" :key="index3" @click="goDetailPage(item.id)">
				<image :src="item.icon"></image>
				<text>{{item.msg}}</text>
			</view>

		</view>	 -->
		<!-- 显示维修 -->
		<view>
			<uni-popup ref="dialogInput" type="dialog">
				<view class="popup" style="height: auto">
					<view class="repaired">请尽快维护满进度车辆</view>
					<view v-for="(item,index) in msg" :key="index" v-if="index>=min&&index<max">
						<view>
							<uni-card>
								<input disabled :value="'车牌号:'+item.license"></input>
								<input disabled
									:value="'最近保养日期:'+ new Date(parseInt(item.lastProtectTime)).toLocaleString().replace(/:\d{1,2}$/,' ')">
								</input>
								<!-- <uni-rate :max="10" :readonly="true"
									:value="((new Date().getTime()-item.lastProtectTime )/86400000 + 1) > 10 ? 10 : ((new Date().getTime()-item.lastProtectTime )/86400000 + 1) " /> -->
								<!-- 进度条 -->
								<!-- 进度条是写死的，最长是十天，超过十天也是百分百，就得到值之后把inner的width改成对应的百分比就可以了，举个例子：1天为10%。应该是可以改dom元素的style的 -->
								<view class="outer">
									<view class="inner"
										:style="'width:'+ (((new Date().getTime()-item.lastProtectTime )/86400000)*10)>100 ? 100 :(((new Date().getTime()-item.lastProtectTime )/86400000)*10)+'%'">
									</view>
								</view>
							</uni-card>
						</view>
					</view>
					<uni-pagination @change="handlePage" show-icon="true" :total="total" :current="current"
						pageSize="3"></uni-pagination>
				</view>
				<view>

					<!-- <button form-type="submit" @click="dialogInputConfirm"
						style="width: 580rpx;height:70rpx; font-size: small;background-color: #5eb9b2; ">我已知晓</button> -->
				</view>


			</uni-popup>
		</view>

		<tabBar :pagePath="'pages/manage/index/index'"></tabBar>
		<!-- 狗勾 -->
		<image src="../../../static/dogRun.gif" class="dogRun"></image>
	</view>
</template>

<script>
	export default {
		created() {
			let _this = this;
			uni.showToast({
				title: '获取消息中',
				icon: 'loading',
				duration: 1500,
				success() {
					setTimeout(function() {
						uni.request({
							url: _this.apiServer + "/api/get/getProtectTip",
							method: 'POST',
							data: {
								no: _this.$store.state.userInfo.no
							},
							header: {
								'content-type': 'application/x-www-form-urlencoded',
								'enctype': "multipart/form-data"
							},
							success: (res) => {
								if (res.data.msg === '数据不为空，返回成功') {
									// _this.confirmDialog();
									_this.msg = res.data.data;
									_this.total = _this.msg.length;
									_this.messageCount = _this.total
									console.log(_this.messageCount)
								}
							}
						})
					}, 1000)
				}

			})
		},
		data() {
			return {
				msg: [],
				max: 3,
				min: 0,
				total: '',
				current: 1,
				text: '欢迎管理员：' + this.$store.state.userInfo.name + "!",
				meunsList: [{
						icon: "/static/data.png",
						msg: "申报",
						id: "declare"
					},
					{
						icon: "/static/exam.png",
						msg: "维修审批",
						id: "examine"
					},
					{
						icon: "/static/changePwd.png",
						msg: "出借",
						id: "lend"
					},
					{
						icon: "/static/car3.png",
						msg: "车辆管理",
						id: "carManagement"
					},
				],
				meunsList2: [{
						icon: "/static/weixiu.png",
						msg: "维修记录",
						id: "fixRecord"
					},
					{
						icon: "/static/baoyang.png",
						msg: "保养记录",
						id: "protectRecord"
					},
				],
				meunsList3: [{
					icon: "/static/exam.png",
					msg: "申报记录",
					id: "declareRecord"
				}, ],
				// 分段器《-----
				fdq_current: 0,
				items: ['主要功能', '车辆数据', '信息查询'],
				//----》到这
				// 保养车量信息条数
				messageCount:0,
			}
		},
		methods: {
			handlePage(params) {
				let _this = this;
				console.log(params)
				_this.current = params.current;
				_this.max = _this.current * 3;
				_this.min = _this.max - 3;

			},
			confirmDialog() {
				this.$refs['dialogInput'].open()
			},
			dialogInputConfirm() {
				this.$refs['dialogInput'].close();
			},
			goDetailPage(e) {
				const url = '/managePages/' + e + '/' + e
				uni.navigateTo({
					url: url
				})
			},
			// 分段器方法
			onClickItem(e) {
				this.fdq_current = e.currentIndex;
			}
		}
	}
</script>

<style>
	* {
		margin: 0;
		padding: 0;
	}

	.content {
		display: flex;
		/* align-items: center; */
		/* justify-content: center; */
		background-image: linear-gradient(#7bc4ff, #4389f2);
		flex-direction: column;
		width: 100%;
		height: 100%;
		position: absolute;
	}

	/* 分段器<--- */
	.wrap {
		margin-top: 20rpx;
		height: 235rpx;
		display: flex;
		justify-content: space-between;
		flex-wrap: wrap;
		border-radius: 10rpx;
		background-color: #fff;
		padding-top: 75rpx;
		box-shadow: 0 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
	}

	.meuns {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 26rpx;
		width: 180rpx;
	}

	.segmented-control__text.data-v-064e9cd1 {
		color: white !important;
	}

	/* --->到这 */
	image {
		width: 80rpx;
		height: 80rpx;
		margin-bottom: 15rpx;
	}

	.card {
		position: relative;
		display: flex;
		background-color: #488df2;
		border-radius: 15rpx;
		margin: 20rpx 0;
		box-shadow: 0 10rpx 10rpx -4rpx rgba(0, 0, 0, .4);
		height: 50rpx;
		justify-content: center;
		/* 水平居中 */
		align-items: center;
		/* 垂直居中 */
		color: #808080;
	}

	/* 头部欢迎界面《----- */
	.top {
		position: relative;
		background-color: #62a9f9;
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
		top: 30rpx;
		right: 110rpx;
		font-size: 55rpx;
		font-weight: 600;
		color: #DCDFE6;
		text-shadow: 5rpx 5rpx 10rpx rgba(255, 255, 255, .4);
	}

	.top .welcome {
		position: absolute;
		bottom: 40rpx;
		right: 80rpx;
		width: auto;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 40rpx;
		font-weight: 600;
		border-radius: 20rpx;
		color: #DCDFE6;
		animation: welcomeShow 0.5s;
		animation-timing-function: ease-out;

	}
	
	.top .message{
		position: absolute;
		top: 25rpx;
		right: 15rpx;
		background: url(../../../static/消息.png);
		background-size: 100% 100%;
		width: 70rpx;
		height: 70rpx;
		background-color: #FFFFFF;
		border-radius: 50%;
		box-shadow: 10rpx 10rpx 10rpx -4rpx rgba(0,0,0,.4);
	}
	.top .messageCount{
		position: absolute;
		top: 4rpx;
		right: 2rpx;
		width: 45rpx;
		height: 45rpx;
		background-color: #ff3755;
		line-height: 45rpx;
		text-align: center;
		font-size: 35rpx;
		font-weight: 600;
		border-radius: 50%;
		color: #FFFFFF;
	}
	/* 动画 */
	@-webkit-keyframes welcomeShow {
		0% {
			bottom: 300rpx;
		}

		100% {
			bottom: 40rpx;
		}
	}

	@keyframes welcomeShow {
		0% {
			bottom: 300rpx;
		}

		50% {
			bottom: 40rpx;
		}

		70% {
			bottom: 80rpx;
		}

		80% {
			bottom: 40rpx;
		}

		90% {
			bottom: 60rpx;
		}

		100% {
			bottom: 40rpx;
		}
	}

	/* -----》到这 */
	.line {
		background-color: #ffffff;
		border-radius: 50rpx;
	}


	.popup {
		border-radius: 20rpx;
		background-image: linear-gradient(135deg, #66a6ff 0%, #89f7fe 100%);
		width: 580rpx;
		box-shadow: 20rpx 20rpx 20rpx -4rpx rgba(0, 0, 0, .4);
	}

	.repaired {
		width: 100%;
		height: 80rpx;
		border-radius: 80rpx;
		line-height: 80rpx;
		text-align: center;
		color: #fff;
		font-size: 45rpx;
		font-weight: 600;
		text-shadow: 5rpx 5rpx 5rpx rgba(255, 255, 255, .4);
	}

	.outer {
		width: 475rpx;
		height: 25rpx;
		border-radius: 15rpx;
		border: 4rpx solid #4facfe;
	}

	.inner {
		height: 100%;
		border-radius: 15rpx;
		background-image: linear-gradient(to right, #76cbff 0%, #4facfe 100%);
	}

	.uni-pagination__btn.data-v-a276fa4e {
		border-radius: 20rpx;
		font-weight: 600;
	}

	/* 狗狗 */
	.dogRun {
		position: absolute;
		top: 800rpx;
		left: 100rpx;
		width: 530rpx;
		height: 115rpx;
	}
</style>
