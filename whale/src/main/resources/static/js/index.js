$(function(){
	function randomColor(){
		   var rgb = Math.random(16);
		   return "#"+String(rgb).substring(2,8);
	}
	function randomFrom(lowerValue,upperValue){
	 return Math.floor(Math.random() * (upperValue - lowerValue + 1) + lowerValue);
	}
	$('.whale_U_li').each(function(){
		$(this).css({"color":"rgba(0,0,0,1)","background":"rgba("+randomFrom(170,256)+","+randomFrom(160,256)+","+randomFrom(160,256)+",1)"});
	});
});
