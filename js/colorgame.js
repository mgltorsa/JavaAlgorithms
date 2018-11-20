	var pickedColor;
	var squares;
	var wonGame=false;
	var time;
	var timerID;

	function colors(){

		getHeader().style.backgroundColor="steelblue";
		squares = document.querySelectorAll(".square");
		var colors =[];
		setupColors(colors,squares);		
		pickedColor = getPickedColor(colors);		
		document.getElementById("displayColor").innerHTML=pickedColor;
		wonGame=false;
		chronoStart();

	}

	function chronoStart(){
		time= new Date();
		chrono();
	}

	function chrono(){
		var end = new Date();
		var diff = end-time;
		diff = new Date(diff);
		var sec =diff.getSeconds();
		var min = diff.getMinutes();
		if(min<10){
			min ="0"+min;
		}
		if(sec<10){
			sec="0"+sec;
		}

		$("#clock").text(min+":"+sec);
		timerID=setTimeout("chrono()",10);
	}

	function setupColors(colors,squares){
		for (i=0;i<squares.length; i++){
			var color = generateColor(colors);
			colors[i]=color;
			squares[i].style.backgroundColor=colors[i];
			squares[i].addEventListener("click", pickColor()	);
		}
	}

	function getPickedColor(colors){

		var index = Math.floor(Math.random()*colors.length);
		return colors[index];
	}
	function generateColor(colors){
		var color = getRandomColor();
		while(existColor(colors,color))
		{
			color = getRandomColor();
		}

		return color;
	}

	function getRandomColor(){
		var r = Math.floor(Math.random() * 256);
		var g = Math.floor(Math.random() * 256);
		var b = Math.floor(Math.random() * 256);
		return 'rgb(' + r + ', ' + g + ', ' + b + ')';
	}

	function existColor(colors, color){
		var exist=false;
		for (i = 0; i<colors.length && !exist; i++) {
			if(colors[i]===color){
				exist=true;
			}
		}
		return exist;
	}
	
	function pickColor(){
			return function isSelectedColor(div){	
				if(!wonGame){
					var backgroundColor = this.style.backgroundColor;				
					if(backgroundColor==pickedColor){
						console.log("correct");
						alert("Congrats!");
						changeColor(backgroundColor);
						wonGame=true;

					}
				}
			}
	}


	function getHeader(){
		return document.querySelectorAll("h1")[0];
			
	}
	function changeColor(color){
		if(!wonGame){
			getHeader().style.backgroundColor=color;
			for (i = 0; i <squares.length; i++) {
				squares[i].style.backgroundColor=color;	
			}
		}
		
	}