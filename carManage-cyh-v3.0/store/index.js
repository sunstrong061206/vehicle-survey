import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		userInfo: {
			name: uni.getStorageSync('name'),
			no: uni.getStorageSync('no'),
			position: uni.getStorageSync('position'),
			bossNo: uni.getStorageSync('bossNo')
		}
	},
	mutations: {
		LOGIN(state, [name, no, position, bossNo]) {
			state.userInfo.name = name;
			state.userInfo.no = no;
			state.userInfo.position = position;
			state.userInfo.bossNo = bossNo;
			uni.setStorageSync('name', name);
			uni.setStorageSync('no', no);
			uni.setStorageSync('position', position);
			uni.setStorageSync('bossNo', bossNo);

		},
		LOGOUT(state) {
			state.userInfo.name = '';
			state.userInfo.no = '';
			state.userInfo.position = '';
			state.userInfo.bossNo = '';
			uni.clearStorage();
		}
	},
	actions: {},
	modules: {

	},

})
