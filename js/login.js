window.onload=function(){
	var account = document.getElementsByClassName('lo_i_pp')[0];
	var password = document.getElementsByClassName('lo_i_pp')[1];
	var acWarn = document.getElementsByClassName('lw_ac')[0];
	var paWarn = document.getElementsByClassName('lw_pa')[0];
	// var test = document.getElementsByClassName('lo_b')[0];
	// console.log('');
	account.onblur = function () {
		if(account.value == ""){
			acWarn.classList.remove('gn');
			acWarn.classList.add('gb');
		}
		else{
			acWarn.classList.remove('gb');
			acWarn.classList.add('gn');
		}
	}
	password.onblur = function () {
		if(password.value == ""){
			paWarn.classList.remove('gn');
			paWarn.classList.add('gb');
		}
		else{
			paWarn.classList.remove('gb');
			paWarn.classList.add('gn');
		}
	}
}