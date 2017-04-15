
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '1873385672935431',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.8'
    });
    FB.AppEvents.logPageView();   
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
  
  function statusChangeCallback(response)
  {
	  if(response.status === 'connected'){
		  //Show log out
		
			console.log("access token "+response.authResponse.accessToken);
			console.log("user id "+response.authResponse.userID);
			console.log('exp date '+response.authResponse.expiresIn);
			console.log("signed in status "+response.authResponse.signedRequest);
			getfacebookInfo(response.authResponse.accessToken);
	  }else {
		  userSignStatus(false);
	  }
	
  }
    
  function getfacebookInfo(id_token) {
	    var xmlhttp = new XMLHttpRequest();

	    xmlhttp.onreadystatechange = function() {
	        if (xmlhttp.readyState == XMLHttpRequest.DONE ) {
	           if (xmlhttp.status == 200) {
	        	   var resp =  JSON.parse(xmlhttp.responseText);
	        	   if (resp.name){
	        		   userSignStatus(true);
	        		   document.getElementById("loginInfo").innerHTML =resp.name ;
	                   document.getElementById("InfoImg").src="http://graph.facebook.com/"+resp.id+"/picture?type=large";
	                  

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
	    xmlhttp.open("GET", "/FbookAuth?access_token="+id_token, true);
	    xmlhttp.send(); 
	}

  function userSignStatus(signedIn){
		if(signedIn){
			document.getElementById("signin").style.display = "none";
	        document.getElementById("signout").style.display = "block";
	        document.getElementById('signout').disabled = false;
		}else {
			  document.getElementById("loginInfo").innerHTML ="Guest";
		        document.getElementById("InfoImg").src="images/city.png";
		        document.getElementById('signout').disabled = true;
		        document.getElementById("signin").style.display = "block";
		        document.getElementById("signout").style.display = "none";
		        document.getElementById("loginInfo").innerHTML ="Guest";
		}
		
	}
  function handleSignOut(){
	  FB.logout(function(response) {
		  userSignStatus(false);
		}, function (error){
			 userSignStatus(false);
			console.log(error);
		});
  }
  

  function checkLoginState() {
	  userSignStatus(false);
	    FB.getLoginStatus(function(response) {
	      statusChangeCallback(response);
	    });
	  }
window.onload = function() {
	checkLoginState();
  }
  

  