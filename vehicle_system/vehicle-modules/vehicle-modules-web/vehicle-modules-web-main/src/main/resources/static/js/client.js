function stringToUint8Array(str) {
    var arr = [];
    for (var i = 0, j = str.length; i < j; ++i) {
        arr.push(str.charCodeAt(i));
    }
    return arr
}

function Client(_address) {
    if (axios !== undefined) {
        axios.defaults.withCredentials = true;
    }
    let _client = this;
    this.token_url = "task/ws/token";
    this.useAuthCheck = false;
    this.createTime = new Date().getTime();
    this.hooks = new Map();
    this.address = _address;
    this.token = "";
    this.session = "";
    this.sendTryTimes = 0;
    this.MaxSendTryTimes = 10;
//func传入时不要带(...),func原型结构为func(data),data为具体返回的参数，自动传入。
//<br/>如:<br/>func(data){<br/>t=data.id;<br/>tt=data.type;<br/>}
    this.addHook = (type, func) => {
        _client.hooks.set(type, func);
        return _client;
    };
    this.callHook = (type, data) => {
        let func = _client.hooks.get(type);
        if (func !== null && func !== undefined) {
            try {
                func(data);
            } catch (e) {
                console.error(e);
            }
        } else {
            console.error(`NoSuchHook[type=${type}]`);
        }
    };
    this.handle = (data) => {
        if (data instanceof String) {
            console.log(data);
        } else if
        ((data instanceof Blob) || (typeof data === "object")) {
            try {
                let wrapper = _client.protoDecode("Wrapper", data)
                _client.callHook(wrapper.type, wrapper.data)
            } catch (e) {
                console.error(e);
            }
        }
    };
    this.wrapperMessage = (type, data) => {
        return _client.protoGenerate("Wrapper", {type: type, data: data});
    };
    this.TypeMessage = (type) => {
        return _client.protoGenerate("Wrapper", {type: type});
    };
    //TODO: !!!
    this.wrapMessage = (data) => {
        //data = new Uint8Array([1, 2, 3, 4, 5, 6, 7, 8, 9]);
        let l = data.length;
        let bytes = new Uint8Array(6 + l);
        bytes.set([60, 87]);//'<W'
        bytes.set([l & 0xFF000000], 2);
        bytes.set([l & 0x00FF0000], 3);
        bytes.set([l & 0x0000FF00], 4);
        bytes.set([l & 0x000000FF], 5);
        bytes.set(data, 6);
        return bytes;
    }

    this.unpackMessage = (bytes) => {
        let result = bytes.subarray(6);
        return result;
    }
    this.protoDecode = (path, data) => {
        try {
            let lookupType = _client.proto_root.lookupType(path);
            return lookupType.decode(data);
        } catch (e) {
            console.error(e);
            return undefined;
        }
    };
    this.protoGenerate = (path, dataMap) => {
        let PROTO = _client.proto_root.lookupType(path);
        let proto = PROTO.create(dataMap);
        let result;
        try {
            result = PROTO.encode(proto).finish();
        } catch (e) {
            console.error(e);
            console.error(dataMap);
        }
        return result;
    };
    this.setToken = (token) => {
        _client.token = token;
    };
    this.setSession = (session) => {
        _client.session = session;
    };
    this.init = async () => {
        try {
            window.client = _client;
            window.parent.client = _client;
            await _client.loadLib('js/protobuf.js');
            await _client.loadProtoBuf("proto/IVDA_data.proto");
            await _client.initTask();
            await _client.loadListener();
        } catch (e) {
            alert(e);
        }
        return this;
    };
    this.loadListener = () => {

    };
    this.initTask = async () => {
        if (_client.session === undefined) {
            _client.session = getCookie("session");
        }
        if (_client.useAuthCheck) {
            if (_client.token === undefined || _client.session === undefined) {
                if ("undefined" != typeof axios) {
                    await function () {
                        return new Promise(function (resolve, reject) {
                            axios.get(_client.token_url).then((result) => {
                                _client.token = result.data.token;
                                _client.session = result.data.session;
                                setCookie("session", result.data.session, 36000);
                                resolve();
                            })
                        });
                    }();
                }
            }
        }
        _client.websocket = new WebSocket(`ws://${_client.address}?session=${_client.session}&token=${_client.token}`);
        _client.websocket.onopen = function (event) {
            //_client.establish_send({session: _client.session,token: _client.token});
        };
        _client.websocket.onclose = function () {
            alert("连接已断开...");
        };
        _client.websocket.onmessage = function (event) {
            let data = event.data;
            if (typeof data == 'string')
                console.log(data);
            else {
                let reader = new FileReader();
                reader.readAsArrayBuffer(data);
                reader.onload = function (e) {
                    let buf = new Uint8Array(reader.result);
                    buf = _client.unpackMessage(buf)
                    _client.handle(buf);
                };
            }
        };
        _client.websocket.CONNECTING;
    };
    this.queueTask = async (tasks) => {
        let queue = new Queue();
        for (let i = 0; i < tasks.length; ++i) {
            queue.push(tasks[i]);
        }
        while (queue.size() > 0) {
            try {
                let task = queue.pop();
                if (task[1] !== undefined) {
                    await task[0](task[1]);
                } else {
                    await task[0]();
                }
            } catch (e) {
                console.error(e);
                return
            }
        }
        console.log('finish')
    };
    this.isReady = () => {
        return _client.websocket.readyState === 1
    };
    this.isDisconnect = () => {
        return _client.websocket.readyState > 1
    };
    this.send = (data) => {
        if (_client.isDisconnect()) {
            return;
        }
        if (!_client.isReady()) {
            if (_client.sendTryTimes > _client.MaxSendTryTimes) return;
            _client.sendTryTimes += 1;
            setTimeout(() => _client.send(data), 500);
            return;
        }
        try {
            data = _client.wrapMessage(data)
            _client.websocket.send(data);
        } catch (e) {
            console.error(e);
            return false;
        }
        return true;
    };
    this.loadLib = async (url) => {
        let script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = url;
        document.getElementsByTagName('head')[0].appendChild(script);
    };
    this.loadProtoBuf = (url) => {
        return new Promise((resolve, reject) => {
            protobuf.load(url, function (err, root) {
                if (err) throw err;
                _client.proto_root = root;
                console.log(_client.proto_root);
                resolve();
            });
        });

    };
    return _client;
}

class Queue {
    constructor() {
        this.list = [];
    }

    push(data) {
        this.list.unshift(data);
    };

    pop() {
        return this.list.pop();
    };

    peek() {
        return this.list[this.size() - 1];
    };

    size() {
        return this.list.length;
    };
}

function setCookie(cname, cvalue, exsecond) {
    var d = new Date();
    d.setTime(d.getTime() + (exsecond * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return undefined;
}

function imageCompression(baseImage, obj, quality, callback) {
    var img = new Image();
    img.src = baseImage;
    img.onload = function () {
        var that = this;
        var w = that.width, h = that.height;
        //按照h 压缩后的base64 和 width
        var compressionResult, compressionWidth, compressionHeight;
        if (h > obj.height) {
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            //创建属性节点
            var createw = document.createAttribute('width');
            var createh = document.createAttribute('height');
            h = obj.height;
            w = (h / that.height) * w;
            compressionWidth = w;
            compressionHeight = h;
            createw.nodeValue = w;
            createh.nodeValue = h;
            canvas.setAttributeNode(createw);
            canvas.setAttributeNode(createh);
            ctx.drawImage(that, 0, 0, w, h);
            var base64 = canvas.toDataURL('image/png', quality);
            compressionResult = base64;
        } else {
            compressionResult = baseImage;
            compressionWidth = that.width;
            compressionHeight = that.height;
        }
        //width > 默认width  做截取处理
        if (compressionWidth > obj.width) {
            ClippingImage(compressionResult, obj.width, compressionHeight, quality, function (base64Clipping) {
                callback(base64Clipping);
            });
        } else {
            callback(compressionResult);
        }

    }
}

function ClippingImage(base64Codes, width, height, quality, callback) {
    var img = new Image();
    img.src = base64Codes;
    //生成canvas
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    var createw = document.createAttribute('width');
    var createh = document.createAttribute('height');
    createw.nodeValue = width;
    createh.nodeValue = height;
    canvas.setAttributeNode(createh);
    canvas.setAttributeNode(createw);
    img.onload = function () {
        ctx.drawImage(img, 0, 0, width, height, 0, 0, width, height);
        var base64Result = canvas.toDataURL('image/png', quality);
        callback(base64Result)
    }
}