const Mock = require('better-mock/dist/mock.mp.js')

Mock.mock('http://localhost:8080/api/login', 'POST', () => {
	let Result = {
		code: 200,
		data: {
			token: 'dasdsadasdsdsada',
			employee: {
				position: 'vet',
				name: '陈大哈',
				no: '43423423'
			},
		},
	}
	return Result
})


Mock.mock('http://localhost:8080/api/get/getProtectTip', 'POST', () => {
	let res = {
		msg: '数据不为空，返回成功',
		data: [{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '213dfsdfd',
				lastProtectTime: '1633588479775',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			{
				license: '21321321',
				lastProtectTime: '1633072704000',
			},
			


		],
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/getAll/vehicleInfo', 'POST', () => {
	
let result = {
		data: [{
				license: '21321321',
				lastProtectTime: '2000',
			},
			{
				license: '213dfsdfd',
				lastProtectTime: '2000',
			}
		],
	};
	return result
})



Mock.mock('http://localhost:8080/api/add/report/protect', 'POST', (option) => {
	console.log(option)
	let res = {
		code: 200,
		data: {
			protectId: '11111'
		}
	}
	return res
})



Mock.mock('http://localhost:8080/api/add/report/assign', 'POST', (option) => {
	console.log(option)
	let res = {
		code: 200,
		data: {
			assginId: '11111'
		}
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/employee', 'POST', (option) => {
	console.log(option)
	let res = {
		data: [{
				name: 'dsadsad',
				no: '121211212',
			},
			{
				name: 'dasdsadaa',
				no: '4545646547',
			}
		],
	}
	return res
})




Mock.mock('http://localhost:8080/api/get/getAll/examDone', 'POST', (option) => {
	console.log(option)
	let res = {
		data: [{
				name: '小兵1',
				time: '时间戳1',
				message: '信息1',
				examineId: '123',
				type: '保养申报'
			},
			{
				name: '小兵2',
				time: '时间戳2',
				message: '信息2',
				examineId: '456',
				type: '维修申报'
			}
		],
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/examToDo', 'POST', (option) => {
	console.log(option)
	let res = {
		data: [{
				name: '小兵1',
				time: '时间戳1',
				message: '信息1',
				examineId: '123',
				type: '保养申报'
			},
			{
				name: '小兵2',
				time: '时间戳2',
				message: '信息2',
				examineId: '456',
				type: '维修申报'
			}
		],
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/bossNo', 'POST', (option) => {
	console.log(option)
	let res = {
		data: {
			bossNo: '31234324',
		},
	}
	return res
})



Mock.mock('http://localhost:8080/api/get/getAll/protect', 'POST', (option) => {
	console.log(option)
	let res = {
		data: [{
				name: '111',
				protectLiense: 'protectlicense',
				protectTime: 'time',
				protectMsg: 'dasdaa',
				protectId: '123456'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			},
			{
				name: '222',
				protectLiense: 'protectlicense2',
				protectTime: 'time2',
				protectMsg: 'dasdasd',
				protectId: '654321'
			}
		]
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/getDetail/protect', 'POST', (option) => {
	console.log(option)
	let res = {
		data: {
			protectMsg: '车辆维修信息',
		},

	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/repair', 'POST', (option) => {
	console.log(option)
	let res = {
		data: [{
				repairMsg: 'repairMsg1',
				repairTime: 'time1',
				repairLicense: 'car1',
				name: 'qwe',
				repairId: '111111'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			},
			{
				repairMsg: 'repairMsg2',
				repairTime: 'time2',
				repairLicense: 'car2',
				name: 'asd',
				repairId: '222222'
			}
		],
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getDetail/repair', 'POST', () => {
	let res = {
		data: {
			repairMsg: 'repairMsg1',
			repairTime: 'time1',
			repairLicense: 'car1',
			name: 'qwe',
			repairId: '111111',
			vetNo: 'vetNo11111',
			surveyTime: 'time11111'
		},
	}
	return res
})

Mock.mock('http://localhost:8080/api/update/location', 'POST', (option) => {
	console.log(option)
})

Mock.mock('http://localhost:8080/api/post/location', 'POST', (option) => {
	console.log(option)
})


Mock.mock('http://localhost:8080/api/get/getDetail/examine', 'POST', (option) => {
	console.log(option)
	let res = {
		data: {
			protectId: '123133',
			protectMsg: '23213213',
			result: 1
		}
	}
	return res
})



Mock.mock('http://localhost:8080/api/add/pickVehicle', 'POST', (option) => {
	console.log(option)
let res={
	code:200,
	data:{},
}
return res
})

Mock.mock('http://localhost:8080/api/add/report/fix', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:{}
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/getDetail/vehicleInfo', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:{
			type:1,
			peopleNum:'10'
		}
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/punish', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			accidentMsg:'事故详情======',
			accidentPoint:'location',
			manageNo:'4324324',
			payStatus:1,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		},
		{
			accidentMsg:'事故详情================',
			accidentPoint:'location2',
			manageNo:'32132323',
			payStatus:0,
		}]
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getDetail/punish', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			surveyNo:'321321',
			punishMsg:'321312'
		}],
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/assign', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		},
		{
			assignLicense:'11111',
			assignId: '11111',
			assginTime: '2020-1-1',
			result: '以出车'
		}],
	}
	return res
})


Mock.mock('http://localhost:8080/api/get/getAll/drive', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			driveLicense:'11111',
			driveId: '11111',
			surveyName: '111111',
		},
		{
			driveLicense:'11111',
			driveId: '11111',
			surveyName: '111111',
		},
		],
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/getAll/vehicleInfo', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			license:'浙C66666',
			manageName:'陈中天',
			status:0
		},
		{
			license:'浙A88888',
			manageName:'黄晓烟',
			status:1
		},
		{
			license:'浙A88884',
			manageName:'李博天',
			status:2
		},
		{
			license:'浙A88888',
			manageName:'王汪',
			status:3
		},
		{
			license:'浙A88777',
			manageName:'王旺',
			status:4
		},
		{
			license:'浙A88888',
			manageName:'王往',
			status:4
		},
		{
			license:'浙A88888',
			manageName:'王亡',
			status:3
		},
		{
			license:'浙A88888',
			manageName:'黄游',
			status:2
		}],
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/getDetail/punish', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			accidentMsg:'321321',
			payStatus: '0',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		},
		{
			accidentMsg:'321321',
			payStatus: '1',
			accidentPoint:'444444',
			punishMsg:'111111',
			manageNo:'123456',
			accidentTime:'111111',
			license:'111111',
			doneTime:'111111',
			accidentMsg:'hhhhhhh'
		}
		],
	}
	return res
})

Mock.mock('http://localhost:8080/api/get/count', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			vet: 20,
			notVet: 36
		}],
	}
	return res
})

// 管理员借车申报记录
Mock.mock('http://localhost:8080/api/get/getAll/lendCar', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		},
		{
			lendCarId: '123456',
			lendMsg: '123456',
			name: '陈大哈',
			phone: '19816899999',
			lendTime: '2021-11-7',
			lendLicense: '浙C66666'
		}],
	}
	return res
})
// 管理员借车申报记录详情页
Mock.mock('http://localhost:8080/api/get/getDetail/lendCar', 'POST', () => {
	let res = {
		data: {
			lendCarId: '123456',
			phone:'19816899999',
			imgUrl:'../../../../static/car2.png',
			lendTime: '2020-10-1',
			lendLicense: '浙C58857',
			reason: '现场勘察',
			vetNo: 'vt123456',
			lendDay: 20,
		},
	}
	return res
})
// 管理员保养申报记录
Mock.mock('http://localhost:8080/api/get/getAll/protectDeclare', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		},
		{
			protectId:'123456',
			protectMsg:'123456',
			name:'刘华强',
			no:'vt123456',
			protectTime: '2021-10-9',
			protectLicense: '浙B66666'
		}],
	}
	return res
})
// 管理员保养申报记录详情页
Mock.mock('http://localhost:8080/api/get/getDetail/protectCar', 'POST', () => {
	let res = {
		data: {
			protectId: '123456',
			protectTime: '2021-5-6',
			protectLicense: '浙C29568',
			detail: '擦车窗,换轮胎',
			vetNo: 'vt456789',
		},
	}
	return res
})
// 管理员派车申报记录
Mock.mock('http://localhost:8080/api/get/getAll/sendCarDeclare', 'POST', (option) => {
	console.log(option)
	let res={
		code:200,
		data:[{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		},
		{
			sendId:'123456',
			sendMsg:'123456',
			name:'卢本伟',
			no:'sv123456',
			sendTime:'2021-12-12',
			sendLicense:'浙A123456'
		}],
	}
	return res
})
// 管理员派车申报记录详情页
Mock.mock('http://localhost:8080/api/get/getDetail/sendCar', 'POST', () => {
	let res = {
		data: {
			sendCarId: '123456',
			sendTime: '2021-10-9',
			sendLicense: '浙C85214',
			place: '浙江温州',
			vetNo: 'vt12465',
			situation: '破损严重',
			svNo:'sv123456'
		},
	}
	return res
})