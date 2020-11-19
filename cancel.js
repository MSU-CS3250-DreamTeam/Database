$(document).ready(function() {

    var url = "https://formspree.io/f/xyybpoyy"
    var method = "POST"
    var email = document.getElementById("email");
    var product = document.getElementById("product");
    var regex = ",";

    $('#submit').click(function(event) {
        event.preventDefault();
        var today = new Date();
        var date = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate();
        var order = date + regex;
        order += email.value + regex;
        order += product.value + regex;

        var data = new FormData(order);
        var xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.send(data);

        window.open("confirm.html","_self");
    });
});
