$(function() {
    $(".single-code").click(function() {
        var cid = $(this).attr("cid");
        var uid = $("#uid").val();

        $.post("/admin/url/auth", {cid: cid, uid: uid}, function(res) {
            if(res=='1') {
                alert("操作成功");
            } else {
                alert("操作失败");
            }
        }, "json");
    });

    $(".my-code").each(function() {
        var id = $(this).val();
        $(("#"+id)).attr("checked", true);
    });
});