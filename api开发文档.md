## 1 规范说明

### 1.1 通信协议

HTTPS协议

### 1.2 请求方法

所有接口只支持POST方法发起请求。

### 1.3 字符编码

HTTP通讯及报文BASE64编码均采用UTF-8字符集编码格式。

### 1.4 格式说明

元素出现要求说明：

| 符号 | 说明                                        |
| ---- | :------------------------------------------ |
| R    | 报文中该元素必须出现（Required）            |
| O    | 报文中该元素可选出现（Optional）            |
| C    | 报文中该元素在一定条件下出现（Conditional） |

### 1.5 请求报文结构

接口接收**POST**方法的请求体作为**RequestData**和URL参数**SignData**，其中**RequestData**为请求内容，**SignData**的值为签名内容。

#### 1.5.1 参数说明

**RequestData（请求内容）：** 其明文为每次请求的具体参数，采用 JSON 格式。  

**SignData（签名内容）：** **RequestData**的MD5加密字符串，用于校验**RequestData**是否合法。

#### 1.5.2 请求内容（RequestData）明文结构说明

采用JSON格式，其中包含header（公有参数）、body（私有参数）节点：

|   名称   |                         描述                         |         备注         |
| :------: | :--------------------------------------------------: | :------------------: |
| 公共参数 | 每个接口都包含的通用参数，以JSON格式存放在Header属性 | 详见以下公共参数说明 |
| 私有参数 |     每个接口特有的参数，以JSON格式存放在Body属性     |   详见每个接口定义   |

**公共参数说明：**

公共参数（Header）是用于标识产品及接口鉴权的参数，每次请求均需要携带这些参数：

| 参数名称  |  类型  | 出现要求 |                 描述                  |
| :-------: | :----: | :------: | :-----------------------------------: |
|   token   | string |    R     | 用户登录后Token，没有登录则为空字符串 |
| timestamp |  long  |    R     |            当前UNIX时间戳             |


#### 1.5.3 校验流程

服务端接收到请求后首先接收请求Body作为**RequestData**，格式为JSON字符串，然后对JSON字符串进行MD5加密，加密后的值与请求中的**SignData**值进行对比，如对比通过，视为合法请求，否则视为非法请求。

#### 1.5.4 请求报文示例

POST请求Body报文示例：

```json
{
    "header":{
        "token":"2366CF921FAD44CCBB07FF9CD02FC90E",
        "timestamp":1502870664
    },
    "body":{
        "id":"18520322032",
      	"name":"张三",
        "dept":"执行部"
    }
}
```

URL报文示例：

```http
https://www.vehicle.com/api/update/verify
```

### 1.6 响应报文结构

#### 1.6.1 结构说明

所有接口响应均采用JSON格式，如无特殊说明，每次请求的返回值中，都包含下列字段：

| 参数名称 |  类型  | 出现要求 |                  描述                  |
| :------: | :----: | :------: | :------------------------------------: |
|   code   |  int   |    R     | 响应码，代码定义请见“附录A 响应吗说明” |
|   msg    | string |    R     |                响应描述                |
|   data   |  Map   |    R     |  每个接口特有的参数，详见每个接口定义  |


#### 1.6.2 响应报文示例

```json
{
    "code":200,
    "msg":"调用成功",
    "data":{
        "channel":"A10086",
        "type":7004
    }
}
```

## 2. 接口定义

### 纲要

- /api
  - /add
    - /report
      - /assign
      - /repair
      - /protect
      - /lend
      - /return
    - /pickVehicle
    - /drive
    
  - /delete
    - /revoke
      - /assgin
      - /repair
      - /protect
      - /lend
    - /banVehicle
    
  - /update
    - /verify
    - /password
    - /vet
      - /assign
      - /repair
      - /protect
      - /lend
  
      - /return
    
  - /get
  
    - /all
      - /assign
      - /drive
      - /punish
      - /repair
      - /protect
      - /lend
      - /vehicleInfo
    - /detail
      - /assign
      - /drive
      - /punish
      - /repair
      - /protect
      - /lend
      - /vehicleInfo
    - /protectTip
    - /employee
    - /boss
  - login


### 2.1 添加

#### 2.1.1 进行申报

- **接口说明：** 对某项具体业务进行申报
- **接口地址：** /api/add/report

##### 2.1.1.1 派车申报

- **接口地址：** /api/add/report/assign

###### 2.1.1.1.1 私有参数

| 名称          | 类型   | 出现条件 | 描述 |
| ------------- | ------ | -------- | ---- |
| assignLicense | string | R        |      |
| assignTime    | long   | R        |      |
| assignPoint   | string | R        |      |
| assignMsg     | string | R        |      |
| surveyNo      | string | R        |      |
| manageNo      | string | R        |      |
| manageTime    | long   | R        |      |

###### 2.1.1.1.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

##### 2.1.1.2 维修申报

- **接口地址：** /api/add/report/repair

###### 2.1.1.2.1 私有参数

| 名称          | 类型   | 出现条件 | 描述     |
| ------------- | ------ | -------- | -------- |
| repairLicense | string | R        |          |
| repairTime    | long   | R        |          |
| repairImg     | img    | R        | 照片数组 |
| repairMsg     | string | R        |          |
| position      | string | R        |          |
| time          | long   | R        |          |
| no            | string | R        |          |

###### 返回结果

| 名称     | 类型 | 出现条件 | 描述 |
| -------- | ---- | -------- | ---- |

##### 2.1.1.3 保养申报

- **接口地址：** /api/add/report/protect

###### 私有参数

| 名称           | 类型   | 出现条件 | 描述 |
| -------------- | ------ | -------- | ---- |
| protectLicense | string | R        |      |
| protectTime    | long   | R        |      |
| protectMsg     | string | R        |      |
| manageNo       | string | R        |      |
| manageTime     | long   | R        |      |

###### 返回结果

| 名称      | 类型 | 出现条件 | 描述 |
| --------- | ---- | -------- | ---- |

##### 2.1.1.4 出借申报

- **接口地址：** /api/add/report/lend

###### 2.1.1.4.1 私有参数

| 名称             | 类型   | 出现条件 | 描述 |
| ---------------- | ------ | -------- | ---- |
| lendLicense      | string | R        |      |
| lendName         | string | R        |      |
| lendPhone        | string | R        |      |
| lendDrivecardImg | string | R        |      |
| lendTime         | long   | R        |      |
| lendDays         | int    | R        |      |
| lendMsg          | string | R        |      |
| no               | string | R        |      |
| time             | long   | R        |      |

###### 2.1.1.4.2 返回结果

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |

##### 2.1.1.5 归还申报

- **接口地址：** /api/add/report/return

###### 2.1.1.4.1 私有参数

| 名称       | 类型     | 出现条件 | 描述 |
| ---------- | -------- | -------- | ---- |
| lendId     | int      | R        |      |
| returnTime | long     | R        |      |
| returnImg  | string[] | R        |      |
| time       | long     | R        |      |

###### 2.1.1.4.2 返回结果

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |

#### 2.1.2 启用车辆

- **接口说明：** 管理员添加车辆
- **接口地址：** /api/add/pickVehicle

##### 2.1.2.1 私有参数

| 名称      | 类型   | 出现条件 | 描述 |
| --------- | ------ | -------- | ---- |
| license   | string | R        |      |
| type      | int    | R        |      |
| peopleNum | int    | R        |      |
| emission  | string | R        |      |
| img       | file   | R        |      |
| price     | double | R        |      |
| no        | string | R        |      |

##### 2.1.2.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

> #### 2.1.3 开始行车
>
> - **接口说明：** 勘查员开始行车，进行记录
> - **接口地址：** /api/add/driveBegin
>
> ##### 2.1.3.1 私有参数
>
> | 名称     | 类型   | 出现条件 | 描述 |
> | -------- | ------ | -------- | ---- |
> | assignId | int    | R        |      |
> | no       | string | R        |      |
> | license  | string | R        |      |
> | begin    | long   | R        |      |
>
> ##### 2.1.3.2 返回结果
>
> | 名称    | 类型 | 出现条件 | 描述 |
> | ------- | ---- | -------- | ---- |
> | driveId | int  | R        |      |

### 2.2 删除

#### 2.2.1 撤销

- **接口说明：** 申报的发起者可以撤销该申报
- **接口地址：** /api/delete/revoke

##### 2.2.1.1 撤销派车申报

- **接口地址：** /api/delete/revoke/assign

###### 2.2.1.1.1 私有参数

| 名称       | 类型 | 出现条件 | 描述 |
| ---------- | ---- | -------- | ---- |
| assignId   | int  | R        |      |
| revokeTime | long | R        |      |

###### 2.2.1.1.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

##### 2.2.1.2 撤销维修申报

- **接口地址：** /api/delete/revoke/repair

###### 2.2.1.2.1 私有参数

| 名称       | 类型 | 出现条件 | 描述 |
| ---------- | ---- | -------- | ---- |
| repairId   | int  | R        |      |
| revokeTime | long | R        |      |

###### 2.2.1.2.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

##### 2.2.1.3 撤销保养申报

- **接口地址：** /api/delete/revoke/protect

###### 2.2.1.3.1 私有参数

| 名称       | 类型 | 出现条件 | 描述 |
| ---------- | ---- | -------- | ---- |
| protectId  | int  | R        |      |
| revokeTime | long | R        |      |

###### 2.2.1.3.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

##### 2.2.1.4 撤销出借申报

- **接口地址：** /api/delete/revoke/lend

###### 2.2.1.4.1 私有参数

| 名称      | 类型 | 出现条件 | 描述 |
| --------- | ---- | -------- | ---- |
| lendId    | int  | R        |      |
| revokeMsg | long | R        |      |

###### 2.2.1.4.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

#### 2.2.2 弃用车辆

- **接口说明：** 管理员删除车辆
- **接口地址：** /api/delete/banVehicle

##### 2.2.2.1 私有参数

| 名称    | 类型   | 出现条件 | 描述 |
| ------- | ------ | -------- | ---- |
| license | string | R        |      |
| endMsg  | string | R        |      |

##### 2.2.2.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

### 2.3 修改

#### 2.3.1 认证

- **接口说明：** 员工进行身份验证，将微信和个人信息绑定
- **接口地址：** /api/update/verify

##### 2.3.1.1 私有参数

| 名称     | 类型   | 出现条件 | 描述     |
| -------- | ------ | -------- | -------- |
| no       | string | R        |          |
| name     | string | R        |          |
| position | string | R        |          |
| dept     | string | R        |          |
| facedata | file   | R        | 暂不考虑 |
| openId   | string | R        |          |

##### 2.3.1.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

#### 2.3.2 审批

- **接口说明：** 进行对提交的申报人工审核
- **接口地址：** /api/update/vet

##### 2.3.2.1 派车审批

- **接口地址：** /api/update/vet/assign

###### 2.3.2.1.1 私有参数

| 名称      | 类型   | 出现条件 | 描述            |
| --------- | ------ | -------- | --------------- |
| assignId  | int    | R        |                 |
| vetNo     | string | R        |                 |
| vetTime   | string | R        |                 |
| result    | int    | R        | -1不通过，1通过 |
| resultMsg | string | R        |                 |

###### 2.3.2.1.2 返回结果

| 名称     | 类型 | 出现条件 | 描述 |
| -------- | ---- | -------- | ---- |
| assignId | int  | R        |      |

##### 2.3.2.2 维修审批

- **接口地址：** /api/update/vet/repair

###### 2.3.2.2.1 私有参数

| 名称      | 类型   | 出现条件 | 描述            |
| --------- | ------ | -------- | --------------- |
| repairId  | int    | R        |                 |
| position  | string | R        | manage\|\|vet   |
| no        | string | R        |                 |
| time      | long   | R        |                 |
| result    | int    | R        | -1不通过，1通过 |
| resultMsg | string | R        |                 |

###### 2.3.2.2.2 返回结果

| 名称     | 类型 | 出现条件 | 描述 |
| -------- | ---- | -------- | ---- |
| repairId | int  | R        |      |

##### 2.3.2.3 保养审批

- **接口地址：** /api/update/vet/protect

###### 2.3.2.3.1 私有参数

| 名称      | 类型   | 出现条件 | 描述            |
| --------- | ------ | -------- | --------------- |
| protectId | int    | R        |                 |
| vetNo     | string | R        |                 |
| vetTime   | long   | R        |                 |
| result    | int    | R        | -1不通过，1通过 |
| resultMsg | string | R        |                 |

###### 2.3.2.3.2 返回结果

| 名称      | 类型 | 出现条件 | 描述 |
| --------- | ---- | -------- | ---- |
| protectId | int  | R        |      |

##### 2.3.2.4 出借审批

- **接口地址：** /api/update/vet/lend

###### 2.3.2.4.1.1 私有参数

| 名称      | 类型   | 出现条件 | 描述            |
| --------- | ------ | -------- | --------------- |
| lendId    | int    | R        |                 |
| no        | string | R        |                 |
| time      | long   | R        |                 |
| result    | int    | R        | -1不通过，1通过 |
| resultMsg | string | R        |                 |

###### 2.3.2.4.1.2 返回结果

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |
| lendId | int  | R        |      |

##### 2.3.2.5 归还审批

- **接口地址：** /api/update/return

###### 2.3.2.5.1 私有参数

| 名称      | 类型   | 出现条件 | 描述            |
| --------- | ------ | -------- | --------------- |
| lendId    | int    | R        |                 |
| no        | string | R        |                 |
| time      | long   | R        |                 |
| result    | int    | R        | -1不通过，1通过 |
| resultMsg | int    | R        |                 |

###### 2.3.2.5.2 返回结果

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |
| lendId | int  | R        |      |

> #### 2.3.3 结束行车
>
> - **接口说明：** 勘查员行车结束
> - **接口地址：** /api/update/driveEnd
>
> ##### 2.3.3.1 私有参数
>
> | 名称    | 类型 | 出现条件 | 描述 |
> | ------- | ---- | -------- | ---- |
> | driveId | int  | R        |      |
> | end     | long | R        |      |
>
> ##### 2.3.3.2 返回结果
>
> | 名称 | 类型 | 出现条件 | 描述 |
> | ---- | ---- | -------- | ---- |

#### 2.3.4 修改密码

- **接口说明：** 用户修改登录密码
- **接口地址：** /api/update/password

##### 2.3.4.1私有参数

| 名称        | 类型   | 出现条件 | 描述 |
| ----------- | ------ | -------- | ---- |
| no          | string | R        |      |
| oldPassword | string | R        |      |
| newPassword | string | R        |      |

##### 2.3.4.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |

### 2.4 查询

#### 2.4.1 查询多组简要的信息（默认时间排序）

- **接口说明：** 查询业务中多组简要的的信息
- **接口地址：** /api/get/getAll

##### 2.4.1.1 查询多组简要的派车信息

- **接口地址：** /api/get/getAll/assign

###### 2.4.1.1.1 私有参数

| 名称     | 类型   | 出现条件 | 描述                                                         |
| -------- | ------ | -------- | ------------------------------------------------------------ |
| filter   | string | R        | 筛选条件:all,time(区间),license,result(审核过+未撤回)，默认时间排序 |
| no       | string | R        |                                                              |
| position | string | R        |                                                              |
| begin    | long   | R        |                                                              |
| end      | long   | R        |                                                              |
| result   | int    | R        |                                                              |
| license  | string | R        |                                                              |

###### 2.4.1.1.2 返回结果

| 名称          | 类型   | 出现条件 | 描述                       |
| ------------- | ------ | -------- | -------------------------- |
| assignId      | int    | R        |                            |
| assignTime    | long   | R        |                            |
| assignLicense | string | R        |                            |
| result        | int    | R        |                            |
| effectStatus  | int    | R        | 默认为0，失效为-1，有效为1 |

- 勘查员能看到哪些派车信息？只有批准通过的能看到？

##### 2.4.1.2 查看多组简要的行车信息

- **接口地址：** /api/get/getAll/drive

###### 2.4.1.2.1 私有参数

| 名称    | 类型   | 出现条件 | 描述                                         |
| ------- | ------ | -------- | -------------------------------------------- |
| filter  | string | R        | 筛选条件:all,time(区间),license,默认时间排序 |
| no      | string | R        |                                              |
| begin   | long   | O        |                                              |
| end     | long   | O        |                                              |
| license | string | O        |                                              |

###### 2.4.1.2.2 返回结果

| 名称         | 类型   | 出现条件 | 描述 |
| ------------ | ------ | -------- | ---- |
| driveId      | int    | R        |      |
| driveLicense | string | R        |      |
| driveBegin   | long   | R        |      |
| driveEnd     | long   | R        |      |
| surveyPoint  | string | R        |      |

##### 2.4.1.3 查看多组简要的惩罚信息

- **接口地址：** /api/get/getAll/punish

###### 2.4.1.3.1 私有参数

| 名称      | 类型   | 出现条件 | 描述                                      |
| --------- | ------ | -------- | ----------------------------------------- |
| filter    | string | R        | 筛选条件:all,time(区间)，车牌默认时间排序 |
| no        | string | R        |                                           |
| payStatus | int    | O        | 0代表未缴款，1表示已缴款                  |
| begin     | long   | O        |                                           |
| end       | long   | O        |                                           |
| license   | string | O        |                                           |

###### 2.4.1.3.2 返回结果

| 名称          | 类型   | 出现条件 | 描述 |
| ------------- | ------ | -------- | ---- |
| punishId      | int    | R        |      |
| accidentMsg   | string | R        |      |
| accidentPoint | string | R        |      |
| accidentTime  | long   | R        |      |
| punishMsg     | string | R        |      |
| punishAmount  | double | R        |      |
| payStatus     | int    | R        |      |

##### 2.4.1.4 查看多组简要的维修信息

- **接口地址：** /api/get/getAll/repair

###### 2.4.1.4.1 私有参数

| 名称     | 类型   | 出现条件 | 描述                                           |
| -------- | ------ | -------- | ---------------------------------------------- |
| filter   | string | R        | 筛选条件：all,time,license,result(审核过+有效) |
| no       | string | R        |                                                |
| position | string | R        |                                                |
| license  | string | O        |                                                |
| begin    | long   | O        |                                                |
| end      | long   | O        |                                                |
| result   | int    | O        |                                                |

###### 2.4.1.4.2 返回结果

| 名称          | 类型   | 出现条件 | 描述                       |
| ------------- | ------ | -------- | -------------------------- |
| repairId      | int    | R        |                            |
| repairLicense | string | R        |                            |
| repairTime    | long   | R        |                            |
| repairMsg     | string | R        |                            |
| name          | string | R        |                            |
| result        | int    | R        |                            |
| effectStatus  | int    | R        | 默认为0，失效为-1，有效为1 |

##### 2.4.1.5 查看多组简要的保养信息

- **接口地址：** /api/get/getAll/protect

###### 2.4.1.5.1 私有参数

| 名称    | 类型   | 出现条件 | 描述                |
| ------- | ------ | -------- | ------------------- |
| filter  | string | R        | license,time,result |
| no      | string | R        |                     |
| license | string | O        |                     |
| begin   | long   | O        |                     |
| end     | long   | O        |                     |
| result  | int    | O        |                     |

###### 2.4.1.5.2 返回结果

| 名称           | 类型   | 出现条件 | 描述                       |
| -------------- | ------ | -------- | -------------------------- |
| protectId      | int    | R        |                            |
| protectLicense | string | R        |                            |
| protectTime    | long   | R        |                            |
| protectMsg     | string | R        |                            |
| result         | int    | R        |                            |
| effectStatus   | int    | R        | 默认为0，失效为-1，有效为1 |

##### 2.4.1.6 查看多组简要的出借信息

- **接口地址：** /api/get/getAll/lend

###### 2.4.1.6.1 私有参数

| 名称         | 类型   | 出现条件 | 描述                                                         |
| ------------ | ------ | -------- | ------------------------------------------------------------ |
| filter       | string | R        | all,name,license,lendTime,returnTime,lendResult,returnResult |
| no           | string | R        |                                                              |
| name         | string | O        |                                                              |
| license      | string | O        |                                                              |
| lendBegin    | long   | O        |                                                              |
| lendEnd      | long   | O        |                                                              |
| returnBegin  | long   | O        |                                                              |
| returnEnd    | long   | O        |                                                              |
| lendResult   | int    | O        |                                                              |
| returnResult | int    | O        |                                                              |

###### 2.4.1.6.2 返回结果

| 名称                | 类型   | 出现条件 | 描述                       |
| ------------------- | ------ | -------- | -------------------------- |
| lendId              | int    | R        |                            |
| license             | string | R        |                            |
| name                | string | R        |                            |
| lendTime            | long   | R        |                            |
| lendDays            | int    | R        |                            |
| manage2LendResult   | int    | R        |                            |
| vetLendResult       | int    | R        |                            |
| returnTime          | long   | R        |                            |
| manage2ReturnResult | int    | R        |                            |
| vetReturnResult     | int    | R        |                            |
| effectStatus        | int    | R        | 默认为0，失效为-1，有效为1 |

##### 2.4.1.7 查看多组简要的车辆信息

- **接口地址：** /api/get/getAll/vehicleInfo

###### 2.4.1.7.1 私有参数

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |
| filter | string | R | all,manageName,status,peopleNum,lastProtectTime |
| no | string | R |  |
| position | string | R | |
| status | int | O |  |
| peopleNumMin | int | O |  |
| peopleNumMax | int | O |  |
| lastProtectTimeBegin | long | O |  |
| lastProtectTimeEnd | long | O |  |

###### 2.4.1.7.2 返回结果

| 名称            | 类型   | 出现条件 | 描述 |
| --------------- | ------ | -------- | ---- |
| license         | string | R        |      |
| manageName      | string | R        |      |
| status          | int    | R        |      |
| peopleNum       | int    | R        |      |
| emission        | string | R        |      |
| lastProtectTime | long   | R        |      |

#### 2.4.2 查看详细信息

- **接口说明：** 查看业务中的详细信息
- **接口地址：** /api/get/getDetail

##### 2.4.2.1 查看派车详情

- **接口地址：** /api/get/getDetail/assign

###### 2.4.2.1.1 私有参数

| 名称     | 类型 | 出现条件 | 描述 |
| -------- | ---- | -------- | ---- |
| assignId | int  | R        |      |

###### 2.4.2.1.2 返回结果

| 名称          | 类型   | 出现条件 | 描述                         |
| ------------- | ------ | -------- | ---------------------------- |
| assignLicense | string | R        |                              |
| assignTime    | long   | R        |                              |
| assignPoint   | string | R        |                              |
| assignMsg     | string | R        |                              |
| surveyNo      | string | R        |                              |
| manageNo      | string | R        |                              |
| manageTime    | long   | R        |                              |
| vetNo         | string | R        |                              |
| vetTime       | long   | R        |                              |
| result        | int    | R        | 0表示未通过，1表示通过       |
| resultMsg     | string | R        |                              |
| effectStatus  | int    | R        | 0表示此单失效，1表示此单生效 |

##### 2.4.2.2 查看行车详情

- **接口地址：** /api/get/getDetail/drive

###### 2.4.2.2.1 私有参数

| 名称    | 类型 | 出现条件 | 描述 |
| ------- | ---- | -------- | ---- |
| driveId | int  | R        |      |

###### 2.4.2.2.2 返回结果

| 名称         | 类型   | 出现条件 | 描述 |
| ------------ | ------ | -------- | ---- |
| driveLicense | string | R        |      |
| driveBegin   | long   | R        |      |
| driveEnd     | long   | R        |      |
| surveyNo     | string | R        |      |
| surveyPoint  | string | R        |      |
| surveyRoute  | string | R        |      |

##### 2.4.2.3 查看惩罚详情

- **接口地址：** /api/get/getDetail/punish

###### 2.4.2.3.1 私有参数

| 名称     | 类型 | 出现条件 | 描述 |
| -------- | ---- | -------- | ---- |
| punishId | int  | R        |      |

###### 2.4.2.3.2 返回结果

| 名称          | 类型   | 出现条件 | 描述                     |
| ------------- | ------ | -------- | ------------------------ |
| surveyNo      | string | R        |                          |
| accidentMsg   | string | R        |                          |
| accidentTime  | long   | R        |                          |
| accidentPoint | string | R        |                          |
| punishMsg     | string | R        |                          |
| punishAmount  | double | R        |                          |
| payStatus     | int    | R        | 0表示未缴款，1表示已缴款 |
| manageNo      | string | R        |                          |

##### 2.4.2.4 查看维修详情

- **接口地址：** /api/get/getDetail/repair

###### 2.4.2.4.1 私有参数

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |
| pairId | int  | R        |      |

###### 2.4.2.4.2 返回结果

| 名称            | 类型   | 出现条件 | 描述                         |
| --------------- | ------ | -------- | ---------------------------- |
| repairLicense   | string | R        |                              |
| repairTime      | long   | R        |                              |
| repairImg       | img    | R        |                              |
| repairMsg       | string | R        |                              |
| surveyNo        | string | R        |                              |
| surveyTime      | long   | R        |                              |
| manageNo        | string | R        |                              |
| manageTime      | long   | R        |                              |
| manageResult    | int    | R        | 0表示未通过，1表示通过       |
| manageResultMsg | string | R        |                              |
| vetNo           | string | R        |                              |
| vetTime         | long   | R        |                              |
| vetResult       | int    | R        | 0表示未通过，1表示通过       |
| vetResultMsg    | string | R        |                              |
| effectStatus    | int    | R        | 0表示此单失效，1表示此单生效 |

##### 2.4.2.5 查看保养详情

- **接口地址：** /api/get/getDetail/protect

###### 2.4.2.5.1 私有参数

| 名称      | 类型 | 出现条件 | 描述 |
| --------- | ---- | -------- | ---- |
| protectId | int  | R        |      |

###### 2.4.2.5.2 返回结果

| 名称           | 类型   | 出现条件 | 描述                         |
| -------------- | ------ | -------- | ---------------------------- |
| peotectLicense | string | R        |                              |
| protectTime    | long   | R        |                              |
| protectMsg     | string | R        |                              |
| manageNo       | string | R        |                              |
| manageTime     | long   | R        |                              |
| vetNo          | string | R        |                              |
| vetTime        | long   | R        |                              |
| result         | int    | R        | 0表示未通过，1表示通过       |
| resultMsg      | string | R        |                              |
| effectStatus   | int    | R        | 0表示此单失效，1表示此单生效 |

##### 2.4.2.6 查看出借详情

- **接口地址：** /api/get/getDetail/lend

###### 2.4.2.6.1 私有参数

| 名称   | 类型 | 出现条件 | 描述 |
| ------ | ---- | -------- | ---- |
| lendId | int  | R        |      |

###### 2.4.2.6.2 返回结果

| 名称                   | 类型   | 出现条件 | 描述                         |
| ---------------------- | ------ | -------- | ---------------------------- |
| lendLicense            | string | R        |                              |
| lendName               | string | R        |                              |
| lendPhone              | string | R        |                              |
| lendIdcardImg          | img    | R        |                              |
| lendDrivecardImg       | img    | R        |                              |
| lendTime               | long   | R        |                              |
| lendDays               | int    | R        |                              |
| lendMsg                | string | R        |                              |
| manage1No              | String | R        |                              |
| manage1LendTime        | long   | R        |                              |
| manage2No              | string | R        |                              |
| manage2LendTime        | long   | R        |                              |
| manage2LendResult      | int    | R        | 0表示未通过，1表示通过       |
| manage2LendResultMsg   | string | R        |                              |
| vetNo                  | string | R        |                              |
| vetLendTime            | long   | R        |                              |
| vetLendResult          | int    | R        | 0表示未通过，1表示通过       |
| vetLendResultMsg       | string | R        |                              |
| returnTime             | long   | R        |                              |
| returnImg              | img    | R        | 照片数组                     |
| manage1ReturnTime      | long   | R        |                              |
| manage2ReturnTime      | long   | R        |                              |
| manage2ReturnResult    | int    | R        | 0表示未通过，1表示通过       |
| manage2ReturnResultMsg | string | R        |                              |
| vetReturnTime          | long   | R        |                              |
| vetReturnResult        | int    | R        | 0表示未通过，1表示通过       |
| vetReturnResultMsg     | string | R        |                              |
| effectStatus           | int    | R        | 0表示此单失效，1表示此单生效 |

##### 2.4.2.7 查看车辆信息详情

- **接口地址：** /api/get/getDetail/vehicleInfo

###### 2.4.2.7.1 私有参数

| 名称    | 类型   | 出现条件 | 描述 |
| ------- | ------ | -------- | ---- |
| license | string | R        |      |

###### 2.4.7.2 返回结果

| 名称            | 类型   | 出现条件 | 描述                                                         |
| --------------- | ------ | -------- | ------------------------------------------------------------ |
| status          | int    | R        | 1表示待用，0表示出行中，2表示维修中，3表示保养中，4表示出借中 |
| begin           | long   | R        |                                                              |
| end             | long   | R        |                                                              |
| type            | int    | R        | 0表示自动挡，1表示手动挡                                     |
| peopleNum       | int    | R        |                                                              |
| emission        | string | R        | 带单位                                                       |
| manageNo        | string | R        |                                                              |
| img             | img    | R        |                                                              |
| price           | double | R        |                                                              |
| lastProtectTime | long   | R        |                                                              |

#### 2.4.3 获取定位

- **接口说明：** 获得当前的定位坐标
- **接口地址：** /api/get/getLocation

##### 2.4.3.1 私有参数

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |
|      |      |          |      |
|      |      |          |      |
|      |      |          |      |

###### 2.4.3.1.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |
|      |      |          |      |
|      |      |          |      |
|      |      |          |      |

#### 2.4.4 获取路径

- **接口说明：** 记录行车轨迹
- **接口地址：** /api/get/getRoute

##### 2.4.4.1 私有参数

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |
|      |      |          |      |
|      |      |          |      |
|      |      |          |      |

##### 2.4.4.2 返回结果

| 名称 | 类型 | 出现条件 | 描述 |
| ---- | ---- | -------- | ---- |
|      |      |          |      |
|      |      |          |      |
|      |      |          |      |

#### 2.4.5 获取保养提示

- **接口说明：** 车辆距离最近一次保养时间每到具体时长，管理员会收到车辆保养提示
- **接口地址：** /api/get/getProtectTip

##### 2.4.5.1 私有参数

| 名称 | 类型   | 出现条件 | 描述 |
| ---- | ------ | -------- | ---- |
| no   | string | R        |      |

##### 2.4.5.2 返回结果

| 名称            | 类型   | 出现条件 | 描述 |
| --------------- | ------ | -------- | ---- |
| protectLicense  | string | R        |      |
| lastProtectTime | long   | R        |      |

#### 2.4.6 获取上级领导

- **接口说明：** 获取员工的上级领导
- **接口地址：** /api/get/boss

##### 2.4.6.1 私有参数

| 名称 | 类型   | 出现条件 | 描述 |
| ---- | ------ | -------- | ---- |
| no   | string | R        |      |

##### 2.4.6.2 返回结果

| 名称  | 类型   | 出现条件 | 描述 |
| ----- | ------ | -------- | ---- |
| no    | string | R        |      |
| name  | string | R        |      |
| phone | string | R        |      |
| pos   | string | R        |      |
| dept  | string | R        |      |
| age   | string | R        |      |

#### 2.4.7 获取员工信息

- **接口说明：** 依据条件获取员工信息
- **接口地址：** /api/get/employee

##### 2.4.7.1 私有参数

| 名称     | 类型   | 出现条件 | 描述        |
| -------- | ------ | -------- | ----------- |
| filter   | string | R        | no,position |
| no       | string | O        |             |
| position | string | O        |             |

##### 2.4.7.2 返回结果

| 名称  | 类型   | 出现条件 | 描述 |
| ----- | ------ | -------- | ---- |
| no    | string | R        |      |
| name  | string | R        |      |
| phone | string | R        |      |
| pos   | string | R        |      |
| dept  | string | R        |      |
| age   | string | R        |      |

### 2.5 登录

- **接口说明：**通过密码进行登录
- **接口地址：**/api/login

#### 2.5.1 私有参数

| 名称     | 类型   | 出现条件 | 描述 |
| -------- | ------ | -------- | ---- |
| no       | string | R        |      |
| password | string | R        |      |

#### 2.5.2 返回结果

| 名称     | 类型   | 出现条件 | 描述 |
| -------- | ------ | -------- | ---- |
| token    | string | R        |      |
| name     | string | R        |      |
| no       | string | R        |      |
| position | string | R        |      |
| bossId   | string | R        |      |
| phone    | string | R        |      |
| dept     | string | R        |      |

### 详细员工信息



## 4 附录B 响应码说明

| 响应码 | 说明         |
| ------ | :----------- |
| 200    | 处理成功     |
| 301    | 解析报文错误 |
| 302    | 无效调用凭证 |
| 303    | 参数不正确   |
| 500    | 系统内部错误 |
| 999    | 处理失败     |
