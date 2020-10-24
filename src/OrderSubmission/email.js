$(document).ready(function() {
    var name = document.getElementById("name");
    var email = document.getElementById("email");
    var location = document.getElementById("location");
    var product = document.getElementById("product");
    var quantity = document.getElementById("quantity");
    var regex = ",";



    $('#submit').click(function(event) {
        event.preventDefault();
        var today = new Date();
        var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
        var order = date + regex;
        order += email.value + regex;
        order += location.value + regex;
        order += product.value + regex;
        order += quantity.value;

        var service_id = 'customer+orders';
        var template_id = 'template_mxp9zmg';
        var template_params = {
            message: order
        };

        emailjs.send(service_id, template_id, template_params);
        window.location = ""
        console.log(order);
    });
        <!-- Place this script at the end of the body tag -->


            window.addEventListener("DOMContentLoaded", function() {

            // get the form elements defined in your form HTML above

            var form = document.getElementById("form");
            var button = document.getElementById("submit");



            // handle the form submission event

            form.addEventListener("submit", function(ev) {
            ev.preventDefault();
            var data = new FormData(form);
            ajax(form.method, form.action);
        });

            // helper function for sending an AJAX request

            function ajax(method, url, data, success, error) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url);
            xhr.setRequestHeader("Accept", "application/json");
            xhr.onreadystatechange = function() {
            if (xhr.readyState !== XMLHttpRequest.DONE) return;
            if (xhr.status === 200) {
            success(xhr.response, xhr.responseType);
        } else {
            error(xhr.status, xhr.response, xhr.responseType);
        }
        };
            xhr.send(data);
        }

    });
}