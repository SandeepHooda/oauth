function getGoogleInfo(id_token) {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
           if (xmlhttp.status == 200) {
        	   var resp =  JSON.parse(xmlhttp.responseText);
        	   if (resp.given_name){
        		   userSignStatus(true);
        		   document.getElementById("googleInfo").innerHTML =resp.given_name + " "+resp.family_name;
                   document.getElementById("googleInfoImg").src=resp.picture;
                  

        	   }else {
        		   userSignStatus(false);
        	   }
               
           }
           else if (xmlhttp.status == 400) {
              alert('There was an error 400');
           }
           else {
               alert('something else other than 200 was returned');
           }
        }
    };
    xmlhttp.open("GET", "/GAuth?id_token="+id_token, true);
    xmlhttp.send(); 
}
   
function userSignStatus(signedIn){
	if(signedIn){
		document.getElementById("signin").style.display = "none";
        document.getElementById("signout").style.display = "block";
        document.getElementById('signout').disabled = false;
	}else {
		  document.getElementById("googleInfo").innerHTML ="Guest";
	        document.getElementById("googleInfoImg").src="images/city.png";
	        document.getElementById('signout').disabled = true;
	        document.getElementById("signin").style.display = "block";
	        document.getElementById("signout").style.display = "none";
	        document.getElementById("googleInfo").innerHTML ="Guest";
	}
	
	document.getElementById("googleInfoImg").style.display = "block";
	document.getElementById("welcomeMsg").style.display = "inline-block";
}

    function onSignIn(googleUser) {
      console.log('Google Auth Response', googleUser);
      if (googleUser && googleUser.Zi && googleUser.Zi.id_token) {
    	  getGoogleInfo(googleUser.Zi.id_token);

          sessionStorage.setItem('id_token',googleUser.Zi.id_token);
      }
  
     
      
    }
    
    /**
     * Handle the sign out button press.
     */
    function handleSignOut() {
      var googleAuth = gapi.auth2.getAuthInstance();
      googleAuth.signOut().then(function() {
        //firebase.auth().signOut();
    	  userSignStatus(false);
        sessionStorage.clear();
      });
    }
    
    function checkIfLoggedIn()
    {
      if(sessionStorage.getItem('id_token') == null){
    	  userSignStatus(false)
      } else {
        //User already logged in
        var id_token = "";
        id_token = sessionStorage.getItem('id_token');
     
        getGoogleInfo(id_token);
      }
    }
    window.onload = function() {
    	//document.getElementById('signout').addEventListener('click', handleSignOut, false);
    	checkIfLoggedIn();
      };