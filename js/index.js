window.onload=function(){
	//登录窗口弹出层
	function openNew(){
		var mHeight = document.documentElement.scrollHeight;
		var mWidth = document.documentElement.scrollWidth;
		var lHeight = document.documentElement.clientHeight;
		var Mask = document.getElementsByClassName('mask')[0];
		Mask.classList.remove('gm');
		Mask.classList.add('gb');
		Mask.style.height = mHeight+"px";
		Mask.style.width = mWidth+"px";
		var Login = document.getElementsByClassName('login')[0];
		var dHeight = Login.offsetHeight;
		var dWidth = Login.offsetWidth;
		Login.classList.remove('gm');
		Login.classList.add('gb');
		Login.style.top = (lHeight-400)/2 + "px";  //如果改变了登录框的高宽，要相应地改变此处的数值
		Login.style.left = (mWidth-350)/2 + "px";
	}
	var bLog = document.getElementsByClassName('t_log')[0];
	bLog.onclick = function(){  //登录按钮单机事件处理程序
		openNew();
	}

	//关闭弹出层
	var Close = document.getElementsByClassName('close')[0];
	var Login = document.getElementsByClassName('login')[0];
	var Mask = document.getElementsByClassName('mask')[0];
	Mask.onclick = Close.onclick = function(){
		Login.classList.remove('gb');
		Login.classList.add('gm');
		Mask.classList.remove('gb');
		Mask.classList.add('gm');
	}
}