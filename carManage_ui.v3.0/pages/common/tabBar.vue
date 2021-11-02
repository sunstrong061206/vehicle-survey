<template>
	<view class="uni-tabbar">
		<view class="uni-tabbar__item" v-for="(item,index) in tabbar" :key="index" @tap="changeTab(item)">
			<view class="icon" :class="[item.fontIcon , item.pagePath == pagePath?'uni-active':'']"></view>
			<view class="uni-tabbar__label" :class="{'active': item.pagePath == pagePath}">
				{{item.text}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			pagePath: null
		},
		
		data() {
			return {
				page: 'contact',
				showPage: false,
				containerHeight: 400,
				tabbar: [{
					"pagePath": 'pages/'+ this.$store.state.userInfo.position +'/index/index',
					"fontIcon": "icon-index",
					"text": "首页"
				}, {
					"pagePath": "pages/common/userinfo/userinfo",

					"fontIcon": "icon-wode",
					"text": "我的"
				}]
			};
		},
		methods: {
			changeTab(item) {
				let page = item.pagePath;
				uni.switchTab({
					url: "../../../" + page,
					fail(error) {
						console.log(error)
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	@import url("../../common/font-icon.css");

	.uni-tabbar {
		position: fixed;
		bottom: 0;
		z-index: 999;
		width: 100%;
		display: flex;
		justify-content: space-around;
		height: 98upx;
		padding: 16upx 0;
		box-sizing: border-box;
		border-top: solid 1upx #ccc;
		background-color: #fff;
		box-shadow: 0px 0px 17upx 1upx rgba(206, 206, 206, 0.32);

		.uni-tabbar__item {
			display: block;
			line-height: 24upx;
			font-size: 20upx;
			text-align: center;
		}

		.uni-tabbar__icon {
			height: 42upx;
			line-height: 42upx;
			text-align: center;
		}

		.icon {
			display: inline-block;
		}

		.uni-tabbar__label {
			line-height: 24upx;
			font-size: 24upx;
			color: #999;

			&.active {
				color: #1ca6ec;
			}
		}
	}
</style>
