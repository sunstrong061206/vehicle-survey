# 语法规范

Mock.js 的语法规范包括两部分：

1. 数据模板定义规范（Data Template Definition，DTD）
2. 数据占位符定义规范（Data Placeholder Definition，DPD）

## 数据模板定义规范 DTD

**数据模板中的每个属性由 3 部分构成：属性名、生成规则、属性值：**

```js
// 属性名   name
// 生成规则 rule
// 属性值   value
'name|rule': value
```

**注意：**

- **属性名** 和 **生成规则** 之间用竖线 `|` 分隔。
- **生成规则** 是可选的。
- **生成规则** 有以下几种格式：
  1. `'name|min-max': value`
  1. `'name|count': value`
  1. `'name|min-max.dmin-dmax': value`
  1. `'name|min-max.dcount': value`
  1. `'name|count.dmin-dmax': value`
  1. `'name|count.dcount': value`
  1. `'name|+step': value`
- **生成规则** 的含义需要依赖**属性值**的类型才能确定。
- **属性值** 中可以含有 `@占位符`。
- **属性值** 还指定了最终值的初始值和类型。

**生成规则和示例：**

### 1. 属性值是字符串 **String**

1. `'name|min-max': string`

   通过重复 `string` 生成一个字符串，重复次数大于等于 `min`，小于等于 `max`。

2. `'name|count': string`

   通过重复 `string` 生成一个字符串，重复次数等于 `count`。

### 2. 属性值是数字 **Number**

1. `'name|+1': number`

   属性值自动加 1，初始值为 `number`。

2. `'name|min-max': number`

   生成一个大于等于 `min`、小于等于 `max` 的整数，属性值 `number` 只是用来确定类型。

3. `'name|min-max.dmin-dmax': number`

   生成一个浮点数，整数部分大于等于 `min`、小于等于 `max`，小数部分保留 `dmin` 到 `dmax` 位。

```js
Mock.mock({
    'number1|1-100.1-10': 1,
    'number2|123.1-10': 1,
    'number3|123.3': 1,
    'number4|123.10': 1.123
})
// =>
{
    "number1": 12.92,
    "number2": 123.51,
    "number3": 123.777,
    "number4": 123.1231091814
}
```

### 3. 属性值是布尔型 **Boolean**

1. `'name|1': boolean`

   随机生成一个布尔值，值为 true 的概率是 1/2，值为 false 的概率同样是 1/2。

2. `'name|min-max': value`

   随机生成一个布尔值，值为 `value` 的概率是 `min / (min + max)`，值为 `!value` 的概率是 `max / (min + max)`。

### 4. 属性值是对象 **Object**

1. `'name|count': object`

   从属性值 `object` 中随机选取 `count` 个属性。

2. `'name|min-max': object`

   从属性值 `object` 中随机选取 `min` 到 `max` 个属性。

### 5. 属性值是数组 **Array**

1. `'name|1': array`

   从属性值 `array` 中随机选取 1 个元素，作为最终值。

2. `'name|+1': array`

   从属性值 `array` 中顺序选取 1 个元素，作为最终值。

3. `'name|min-max': array`

   通过重复属性值 `array` 生成一个新数组，重复次数大于等于 `min`，小于等于 `max`。

4. `'name|count': array`

   通过重复属性值 `array` 生成一个新数组，重复次数为 `count`。

### 6. 属性值是函数 **Function**

1. `'name': function`

   执行函数 `function`，取其返回值作为最终的属性值，函数的上下文为属性 `'name'` 所在的对象。

### 7. 属性值是正则表达式 **RegExp**

1. `'name': regexp`

   根据正则表达式 `regexp` 反向生成可以匹配它的字符串。用于生成自定义格式的字符串。

   ```js
   Mock.mock({
       'regexp1': /[a-z][A-Z][0-9]/,
       'regexp2': /\w\W\s\S\d\D/,
       'regexp3': /\d{5,10}/
   })
   // =>
   {
       "regexp1": "pJ7",
       "regexp2": "F)\fp1G",
       "regexp3": "561659409"
   }
   ```

## 数据占位符定义规范 DPD

**占位符** 只是在属性值字符串中占个位置，并不出现在最终的属性值中。

**占位符** 的格式为：

```
@占位符
@占位符(参数 [, 参数])
```

**注意：**

- 用 `@` 来标识其后的字符串是 **占位符**。
- **占位符** 引用的是 `Mock.Random` 中的方法。
- **占位符** 也可以引用 **数据模板** 中的属性。
- **占位符** 会优先引用 **数据模板** 中的属性。
- **占位符** 支持 **相对路径** 和 **绝对路径**。
- 可以使用 `\\` 来转义 `@` 符号。

```javascript
Mock.mock({
  name: {
    first: '@FIRST',
    middle: '@FIRST',
    last: '@LAST',
    email: 'example\\@gmail.com',
    full: '@first @middle @last'
  }
})
// =>
{
  "name": {
    "first": "Charles",
    "middle": "Brenda",
    "last": "Lopez",
    "email": "example@gmail.com",
    "full": "Charles Brenda Lopez"
  }
}
```

## 类型转换器 Transfer

### 支持在 生成规则 后面使用转换器语法：

```js
Mock.mock({
  'name1': '1',
  'name2#number': '1',
  'name3#boolean': '1',
  'name4|3#number': '1',
  'name5|1-3#number': '1',
  'name6|1-3.2-4#string': 1,
  'name7#number': '@PHONE'
})
// =>
{
  "name1": "1",
  "name2": 1,
  "name3": true,
  "name4": 111,
  "name5": 11,
  "name6": "2.112",
  "name7": 14996533237
}
```

**注意:**

- 使用 `#` 符号来定义转换器。
- 内置了以下几个转换器：`boolean`、`string`、`number`。
- 可以使用 `Mock.Transfer.extend` 方法自定义转换器。

### 自定义转换器：

```js
Mock.Transfer.extend({
  json (value) {
    return JSON.parse(value)
  }
})
Mock.mock({
  'name#json': '{}'
})
// =>
{
   name: {}
}
```