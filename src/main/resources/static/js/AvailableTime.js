var doctorId;
var date;
var times = [];

$( "#date" ).change(function () {
    select = document.getElementById('time');
    while (select.firstChild) {
        select.removeChild(select.firstChild);
    }
    getAvailableTime();
});

$( "#doctor" ).change(function () {
    getAvailableTime();
});

function getAvailableTime() {
    doctorId = $( "#doctor" ).val();
    date = $( "#date" ).val();
    $.ajax({
        type: "POST",
        url: "/staff/free-time",
        dataType: "json",
        data: {
            doctorId: doctorId,
            date: date
        },

        success: function (data) {
            if (data.status === "success") {
                times = data.times;
                times.forEach(function(item, i, arr) {
                    $( "#time" ).append($("<option value='" + item + "'>" + item + "</option>"));
                });
            }
        }
    });
}
