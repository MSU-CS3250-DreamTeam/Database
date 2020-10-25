$(document).ready(function() {
  
  var url = "https://formspree.io/f/mnqodrvr"
  var method = "POST"
  var name = document.getElementById("name");
  var email = document.getElementById("email");
  var address = document.getElementById("address");
  var product = document.getElementById("product");
  var quantity = document.getElementById("quantity");
  var regex = ",";
  
  $('#submit').click(function(event) {
    event.preventDefault();
    var today = new Date();
    var date = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate();
    var order = date + regex;
    order += email.value + regex;
    order += address.value + regex;
    order += product.value + regex;
    order += quantity.value;
    
	var data = new FormData(order);
	var xhr = new XMLHttpRequest();
	xhr.open(method, url);
	xhr.send(data);
	
	window.open("confirm.html","_self");
  });
});
