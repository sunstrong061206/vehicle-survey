<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			if (uni.getStorageSync('token')) {
				this.$store.state.token =  uni.getStorageSync('token');
				this.$store.state.userInfo.name=uni.getStorageSync('name');
				this.$store.state.userInfo.no=uni.getStorageSync('no');
				this.$store.state.userInfo.position=uni.getStorageSync('position');
				this.$store.state.hasLogin=true
			}
			console.log(this.$store.state)
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>


<style>
	/*每个页面公共css */
	/*还需要写   进入小程序时要将 内存里的信息再赋值给vuex进行存储，因为小程序刷新store里的信息都会不见*/
	@import './common/uni.css';

</style>
