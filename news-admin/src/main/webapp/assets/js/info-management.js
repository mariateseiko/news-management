$(document).ready(function(){
    $("[id^=info-button-]").each(function() {
        $(this).on("click", function(){
            var buttonId = $(this).attr('id');
            var splitted = buttonId.split("-");
            var inputIdIndex = splitted[splitted.length-1];
            var inputId = "#info-input-" + inputIdIndex;
            $("[id^=info-button-]").show();
            $(".info-list input[id^=info-input-]").prop("disabled", true);
            $("[id^=edit-panel-]").hide();
            $(inputId).prop('disabled', false);
            $("#edit-panel-"+inputIdIndex).show()
            $(this).hide();
        });
    });

    $("[id^=cancel-info-button]").each(function() {
        $(this).on("click", function(){
            var buttonId = $(this).attr('id');
            var splitted = buttonId.split("-");
            var inputIdIndex = splitted[splitted.length-1];
            $(".info-list input[id^=info-input-]").prop("disabled", true);
            $("[id^=edit-panel-]").hide();
            $("#info-button-" + inputIdIndex).show();
        });
    });
});

