function joinCheck() {
	var idField = document.joinForm.id;
	var pwField = document.joinForm.pw;
	var pwcField = document.joinForm.pwChk;
	var photoField = document.joinForm.photoTemp;
	var nickField = document.joinForm.nick;
	var nameField = document.joinForm.name;
	var addr1Field = document.joinForm.addr1;
	var addr2Field = document.joinForm.addr2;
	var addr3Field = document.joinForm.addr3;

	if (isEmpty(idField) || containsHS(idField)) {
		alert("ID를 다시 입력하세요");
		idField.value="";
		return false;
	}
	
	if (lessThan(idField, 6)) {
		alert("ID는 6글자 이상 입력해주세요")
		idField.value="";
		return false;
	}
	
	if ($("#joinIdInput").css("color") == "rgb(255, 0, 0)") {
		alert("이미 가입된 아이디 입니다.");
		idField.value="";
		return false;
	}
	
	if (isEmpty(pwField) || notEqual(pwField, pwcField)) {
		alert("PW를 다시 입력하세요");
		pwField.value="";
		return false;
	}
	
	if (notContains(pwField, "1234567890")) {
		alert("비밀번호에 숫자를 포함시켜주세요");
		pwField.value="";
		return false;
	}
	
	if (lessThan(pwField, 6)) {
		alert("비밀번호는 8글자 이상 입력해주세요")
		pwField.value="";
		return false;
	}
	
	if (isEmpty(nickField)) {
		alert("닉네임을 입력하세요.");
		return false;
	}
	
	if ($("#joinNickInput").css("color") == "rgb(255, 0, 0)") {
		alert("이미 사용중인 닉네임 입니다.");
		nickField.value="";
		return false;
	}
	
	if (isEmpty(nameField)) {
		alert("이름을 입력하세요.");
		return false;
	}

	if (isEmpty(photoField) || (isNotType(photoField, "jpg") && isNotType(photoField, "png") && isNotType(photoField, "gif"))) {
		alert("프로필사진을 다시 선택하세요");
		return false;
	}

	if (isEmpty(addr1Field) || isEmpty(addr2Field) || isEmpty(addr3Field)) {
		alert("주소를 입력해주세요");
		addr1Field.value = "";
		addr2Field.value = "";
		addr3Field.value = "";
		return false;
	}
	return true;
}

function loginCheck(f) {
	var idField = f.id; var pwField = f.pw;
	if (isEmpty(idField) || isEmpty(pwField)) {
		alert("다시 입력하세요");
		idField.value = "";
		pwField.value = "";
		idField.focus();
		return false;
	}
	return true;
}

function memberUpdateCheck(f) {
	var pwField = f.pw; var pwChkField = f.pwChk;
	var nickField = f.nick; var addr1Field = f.addr1;
	var addr2Field = f.addr2; var addr3Field = f.addr3;
	var photoField = f.photo2;

	if (isEmpty(pwField) || notEqual(pwField, pwChkField)
		|| notContains(pwField, "1234567890")) {
		alert("비밀번호를 다시 입력해주세요");
		pwField.value = "";
		pwChkField.value = "";
		pwField.focus();
		return false;
	}
	if (isEmpty(nickField)) {
		alert("이름을 입력해주세요");
		nameField.value = "";
		nameField.focus();
		return false;
	}
	
	if ($("#updateNickInput").css("color") == "rgb(255, 0, 0)") {
		alert("이미 사용중인 닉네임 입니다.");
		idField.value="";
		return false;
	}
		
	if (isEmpty(addr1Field) || isEmpty(addr2Field) || isEmpty(addr3Field)) {
		alert("주소를 입력해주세요");
		addr1Field.value = "";
		addr2Field.value = "";
		addr3Field.value = "";
		return false;
	}
	if (isEmpty(photoField)) {
		return true;
	}
	if (isNotType(photoField, "png")
		&& isNotType(photoField, "gif")
		&& isNotType(photoField, "jpg")) {
		alert("사진 형식을 확인해주세요");
		photoField.value = "";
		return false;
	}
	return true;
}

function challengeRegCheck(f) {
	var titleField = f.title;
	var file1Field = f.imgTemp2;
	var file2Field = f.imgTemp3;
	var file3Field = f.imgTemp4;
	var file4Field = f.imgTemp5;
	var file5Field = f.imgTemp6;
	var textField = f.text;

	if (isEmpty(titleField)) {
		alert("제목을 입력해주세요");
		titleField.value = "";
		titleField.focus();
		return false;
	}
	if (isEmpty(file1Field)) {
		alert("사진을 1장 이상 등록해주세요");
		file1Field.value = "";
		return false;
	}
	if (isEmpty(textField)) {
		alert("내용을 입력해주세요");
		textField.value = "";
		return false;
	}

	if (isEmpty(file2Field) || isEmpty(file3Field) || isEmpty(file4Field) || isEmpty(file5Field)) {
		return true;
	}

	if ((isNotType(file1Field, "png") && isNotType(file1Field, "gif") && isNotType(file1Field, "jpg")) ||
		(isNotType(file2Field, "png") && isNotType(file2Field, "gif") && isNotType(file2Field, "jpg")) ||
		(isNotType(file3Field, "png") && isNotType(file3Field, "gif") && isNotType(file3Field, "jpg")) ||
		(isNotType(file4Field, "png") && isNotType(file4Field, "gif") && isNotType(file4Field, "jpg")) ||
		(isNotType(file5Field, "png") && isNotType(file5Field, "gif") && isNotType(file5Field, "jpg"))) {

		alert("사진 형식을 확인해주세요");
		file1Field.value = "";
		file2Field.value = "";
		file3Field.value = "";
		file4Field.value = "";
		file5Field.value = "";
		return false;
	}
	return true;
}

function foodGuideRegCheck(f) {
   var titleField = f.title;
   var file1Field = f.file1;
   var file2Field = f.file2;
   var file3Field = f.file3;
   var file4Field = f.file4;
   var file5Field = f.file5;
   var textField = f.text;

   if (isEmpty(titleField)) {
      alert("제목을 입력해주세요");
      titleField.value = "";
      titleField.focus();
      return false;
   }
   if (isEmpty(textField)) {
      alert("설명을 입력해주세요");
      textField.value = "";
      return false;
   }

   if (isEmpty(file1Field)) {
      alert("메인 이미지을 등록해주세요");
      return false;
   }

   var fileFields = [file1Field, file2Field, file3Field, file4Field, file5Field];
   for (var i = 0; i < fileFields.length; i++) {
      if (!isEmpty(fileFields[i]) &&
         isNotType(fileFields[i], "png") &&
         isNotType(fileFields[i], "gif") &&
         isNotType(fileFields[i], "jpg")) {
         alert("사진 형식을 확인해주세요 (PNG, GIF, JPG만 가능)");
         return false;
      }
   }
   return true;
}

function challengeUpdateCheck(f) {
    var titleField = f.title;
    var file1Field = f.imgTemp2;
    var file2Field = f.imgTemp3;
    var file3Field = f.imgTemp4;
    var file4Field = f.imgTemp5;
    var file5Field = f.imgTemp6;
    var textField = f.text;

    // 제목 필수 입력
    if (isEmpty(titleField)) {
        alert("제목을 입력해주세요");
        titleField.focus();
        return false;
    }

    // 내용 필수 입력
    if (isEmpty(textField)) {
        alert("내용을 입력해주세요");
        textField.focus();
        return false;
    }

    // 파일 형식 검사 (파일이 있을 경우만 검사)
    var fileFields = [file1Field, file2Field, file3Field, file4Field, file5Field];
    for (var i = 0; i < fileFields.length; i++) {
        if (!isEmpty(fileFields[i]) && 
            isNotType(fileFields[i], "png") && 
            isNotType(fileFields[i], "gif") && 
            isNotType(fileFields[i], "jpg")) {
            alert("사진 형식을 확인해주세요 (PNG, GIF, JPG만 가능)");
            return false;
        }
    }

    return true;
}

function foodGuideRegCheck(f) {
	var titleField = f.title;
	var file1Field = f.file1;
	var file2Field = f.file2;
	var file3Field = f.file3;
	var file4Field = f.file4;
	var file5Field = f.file5;
	var textField = f.text;

	if (isEmpty(titleField)) {
		alert("제목을 입력해주세요");
		titleField.value = "";
		titleField.focus();
		return false;
	}
	if (isEmpty(textField)) {
		alert("설명을 입력해주세요");
		textField.value = "";
		return false;
	}
	if (isEmpty(file1Field)) {
		alert("메인 이미지을 등록해주세요");
		return false;
	}

	var fileFields = [file1Field, file2Field, file3Field, file4Field, file5Field];
	for (var i = 0; i < fileFields.length; i++) {
		if (!isEmpty(fileFields[i]) &&
			isNotType(fileFields[i], "png") &&
			isNotType(fileFields[i], "gif") &&
			isNotType(fileFields[i], "jpg")) {
			alert("사진 형식을 확인해주세요 (PNG, GIF, JPG만 가능)");
			return false;
		}
	}
	return true;
}

function foodGuideUpdateCheck(f) {
    var titleField = f.title;
    var file1Field = f.file1;
    var file2Field = f.file2;
    var file3Field = f.file3;
    var file4Field = f.file4;
    var file5Field = f.file5;
    var textField = f.text;

    // 제목 필수 입력
    if (isEmpty(titleField)) {
        alert("제목을 입력해주세요");
        titleField.focus();
        return false;
    }

    // 설명 필수 입력
    if (isEmpty(textField)) {
        alert("설명을 입력해주세요");
        textField.focus();
        return false;
    }

    // 파일 형식 검사 (파일이 있을 경우만 검사)
    var fileFields = [file1Field, file2Field, file3Field, file4Field, file5Field];
    for (var i = 0; i < fileFields.length; i++) {
        if (!isEmpty(fileFields[i]) && 
            isNotType(fileFields[i], "png") && 
            isNotType(fileFields[i], "gif") && 
            isNotType(fileFields[i], "jpg")) {
            alert("사진 형식을 확인해주세요 (PNG, GIF, JPG만 가능)");
            return false;
        }
    }

    return true;
}

function freeRegOrUpdateCheck(f) {
    var titleField = f.title;
    var file1Field = f.file1;
    var file2Field = f.file2;
    var file3Field = f.file3;
    var file4Field = f.file4;
    var file5Field = f.file5;
    var textField = f.text;

    // 제목 필수 입력
    if (isEmpty(titleField)) {
        alert("제목을 입력해주세요");
        titleField.focus();
        return false;
    }

    // 설명 필수 입력
    if (isEmpty(textField)) {
        alert("설명을 입력해주세요");
        textField.focus();
        return false;
    }

    // 파일 형식 검사 (파일이 있을 경우만 체크)
    var fileFields = [file1Field, file2Field, file3Field, file4Field, file5Field];
    for (var i = 0; i < fileFields.length; i++) {
        if (!isEmpty(fileFields[i]) && 
            isNotType(fileFields[i], "png") && 
            isNotType(fileFields[i], "gif") && 
            isNotType(fileFields[i], "jpg")) {
            alert("사진 형식을 확인해주세요 (PNG, GIF, JPG만 가능)");
            return false;
        }
    }

    return true;
}

function CSQuizRegCheck(f) {
	var quizField = f.quiz;
	var choice1Field = f.choice1;
	var choice2Field = f.choice2;
	var choice3Field = f.choice3;
	var choice4Field = f.choice4;
	var corField = f.cor;

	if (isEmpty(quizField)) {
		alert("퀴즈을 입력해주세요");
		quizField.value = "";
		quizField.focus();
		return false;
	}
	if (isEmpty(choice1Field)) {
		alert("선택지1을 등록해주세요");
		choice1Field.value = "";
		return false;
	}
	if (isEmpty(choice2Field)) {
		alert("선택지2을 입력해주세요");
		choice2Field.value = "";
		return false;
	}
	if (isEmpty(choice3Field)) {
		alert("선택지3을 입력해주세요");
		choice2Field.value = "";
		return false;
	}
	if (isEmpty(choice4Field)) {
		alert("선택지4을 입력해주세요");
		choice4Field.value = "";
		return false;
	}
	if (isEmpty(corField)) {
		alert("정답을 입력해주세요");
		corField.value = "";
		return false;
	}

	return true;
}

function foodQuizRegCheck(f) {
	var fileField = f.file2;
	var corField = f.cor;

	if (isEmpty(fileField)) {
		alert("파일을 등록해주세요");
		fileField.value = "";
		return false;
	}

	if (isEmpty(corField)) {
		alert("정답을 입력해주세요");
		corField.value = "";
		return false;
	}
	return true;
}