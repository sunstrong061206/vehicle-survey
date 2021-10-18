let box_info_number = 0;
/**
 * showInfoBox方法
 * @param {*} 相对窗口上部的百分比位置 
 * @param {*} 提示信息 
 * @param {*} 信息类型 (0：信息提示, 1：错误提示)
 */
function showInfoBox(position, message,type) {
    let title = "info";
    let color = "danger";
    if(type === 0){
        title = "提示,";
        color = "info";
    }
    else{
        title = "错误,";
        color = "danger";
    }
    let number = box_info_number;
    if(number !== 0){
        let id = "#box-info-" + (number-1).toString();
        $(id).remove();
    }
    box_info_number = box_info_number + 1;
    let span = 'span-info-' + number.toString();
    let span_id = 'id="' + span + '"';
    let box = 'box-info-' + number.toString();
    let box_id = 'id="' + box + '"';
    let box_info = $('<div class="alert alert-'+ color +' message-box" ' + box_id + '><strong>'+ '<i class="bi bi-info-circle-fill mr-1"></i>' + title +'</strong><span ' + span_id + '></span></div>');
    $("body").append(box_info);
    setTimeout(function () {
        $("#" + span).text(message);
        $("#" + box).css("opacity", "100");
        $("#" + box).css("top", position + "%").delay(1500).fadeOut(500);
    },1);
}