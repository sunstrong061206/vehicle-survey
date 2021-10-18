    //界面动画控制方法
    function camera1() {
        let parentW = $("#main-div-camera").width();
        console.log(parentW);
        let w1 = $("#main-div-camera-1").width();
        if (w1 < parentW-10) {
          max()
          $("#main-div-camera-2").css({ "left": "100%" });
          $("#main-div-camera-3").css({ "top": "100%" });
          $("#main-div-camera-4").css({ "left": "100%", "top": "100%" });
        }
        else {
          min()
        }
      }
  
      function camera2() {
        let parentW = $("#main-div-camera").width();
        let w2 = $("#main-div-camera-2").width();
        if (w2 < parentW-10) {
          max()
          $("#main-div-camera-1").css({ "left": "-100%" });
          $("#main-div-camera-2").css({ "left": "0%" });
          $("#main-div-camera-3").css({ "left": "-100%", "top": "100%" });
          $("#main-div-camera-4").css({ "left": "0%", "top": "100%" });
        }
        else {
          min()
        }
      }
  
      function camera3() {
        let parentW = $("#main-div-camera").width();
        let w3 = $("#main-div-camera-3").width();
        if (w3 < parentW-10) {
          max()
          $("#main-div-camera-1").css({ "top": "-100%" });
          $("#main-div-camera-2").css({ "left": "100%", "top": "-100%" });
          $("#main-div-camera-3").css({ "top": "0%" });
          $("#main-div-camera-4").css({ "left": "100%", "top": "0%" });
        }
        else {
          min()
  
        }
      }
  
      function camera4() {
        let parentW = $("#main-div-camera").width();
        let w4 = $("#main-div-camera-4").width();
        if (w4 < parentW-10) {
          max()
          $("#main-div-camera-1").css({ "left": "-100%", "top": "-100%" });
          $("#main-div-camera-2").css({ "left": "0%", "top": "-100%" });
          $("#main-div-camera-3").css({ "left": "-100%", "top": "0%" });
          $("#main-div-camera-4").css({ "left": "0%", "top": "0%" });
        }
        else {
          min()
        }
      }
  
      function max() {
        for (let i = 1; i < 5; i++) {
          $("#main-div-camera-" + i.toString()).css({ "width": "100%", "height": "100%" });
        };
      }
      function min() {
        for (let i = 1; i < 5; i++) {
          $("#main-div-camera-" + i.toString()).css({ "width": "50%", "height": "50%" });
        };
        $("#main-div-camera-1").css({ "left": "0%", "top": "0%" });
        $("#main-div-camera-2").css({ "left": "50%", "top": "0%" });
        $("#main-div-camera-3").css({ "left": "0%", "top": "50%" });
        $("#main-div-camera-4").css({ "left": "50%", "top": "50%" });
      }
  