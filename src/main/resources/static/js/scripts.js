function get_quote_of_the_day() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
	 if (this.readyState == 4 && this.status == 200) {
	     // Access the result here
        var Data = JSON.parse(this.responseText);
        //console.log(Data);
        console.log(Data.contents.quotes[0].quote);
	     //alert(this.responseText);
         document.getElementById('quote').innerHTML = Data.contents.quotes[0].quote;
         document.getElementById('author').innerHTML = Data.contents.quotes[0].author;
	 }
    };
    xhttp.open("GET", "http://quotes.rest/qod.json?category=art", true);
    // xhttp.setRequestHeader("Content-type", "application/json");
    // xhttp.setRequestHeader("X-Theysaidso-Api-Secret", "YOUR API HERE");
    xhttp.send();
}