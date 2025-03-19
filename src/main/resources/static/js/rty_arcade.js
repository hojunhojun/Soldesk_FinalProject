function commonSenseQuizEventController() {
	var quizzes = [];
	var index = 0;
	var correctNum = 0;
	if ($("#quiz-data") == null) return;
	$("#quiz-data div").each(function() {
		q = $(this).data("question").split("/");
		quizzes.push({
			question: q[0],
			answer: $(this).data("answer"),
			choices: q[1].split(",")
		});
	});
	var question = $("#question");
	var choices = $("#choices");
	var nextBtn = $("#CSquiz #next");
	var nextPopUpArea = $("#CSquiz .nextPopUpArea");
	var progress = $("#CSquiz .quiz-progress"); // 추가된 부분

	function updateProgress() {
		progress.text("현재  " + (index + 1) + " / " + quizzes.length + " 문제");
	}
	function showQuiz() {
		if (index < quizzes.length) {
			var quiz = quizzes[index];
			question.text(quiz.question);
			choices.empty();
			$.each(quiz.choices, function(_, choice) {
				var button = $("<button>").text(choice);
				button.click(function() {
					checkAnswer(choice, quiz.answer);
				});
				choices.append(button);
			});
			updateProgress();

		} else {
			$("#CSquiz .quizNextArea").css("display", "none");
			$("#CSquiz .quizResultArea").attr("class", "");
			$("#CSquiz #result").text("맞은 개수: " + correctNum + "개");
			nextPopUpArea.css("top", "0px");
			correctNum = 0;
			choices.empty();
		}
	}
	$("#CSquiz .quizResultArea button").click(function() {
		$("#CSquiz .quizNextArea").css("display", "");
		$("#CSquiz .quizResultArea").css("display", "none");
		nextPopUpArea.css("top", "-100%");
		location.href = "arcade.quiz.go";
	});

	function checkAnswer(choice, answer) {
		if (choice == answer) {
			$("#CSquiz .nextPopUp #ox").text("정답");
			$("#CSquiz #correctAnswer").text(answer);
			correctNum++;
		} else {
			$("#CSquiz .nextPopUp #ox").text("오답");
			$("#CSquiz #correctAnswer").text(answer);
		}
		nextPopUpArea.css("top", "0px");
	}

	nextBtn.click(function() {
		index++;
		nextPopUpArea.css("top", "-100%");
		showQuiz();
	});

	showQuiz();
}

function connectQuizAreaEvent() {
	var reg = $("#quizRegTbl");
	if (reg == null) return;
	$("#quizReg button").click(function() {
		if (reg.css("right") == "-500px") {
			reg.css("right", "20px")
		} else {
			reg.css("right", "-500px")
		}
	});
}
// 돌림판 컨트롤러
function connectRouletteController() {
	var circleBoard = document.getElementById("circleBoard");
	if (circleBoard == null) return;
	circleBoard.width = circleBoard.closest("div").offsetWidth;
	circleBoard.height = circleBoard.closest("div").offsetWidth;
	var menuInput = document.getElementById("menuInput");
	var pen = circleBoard.getContext("2d");
	var circleHeight = circleBoard.height / 2;
	var circleWidth = circleBoard.width / 2;
	var r = circleWidth - 10;
	drawCircle();
	var arc = 0;
	var menu = [];
	var colors = [];
	var clicked = false;
	var currentAngle = 0;
	var speed = 0;
	var spinning = false;
	var slowing = false;
	function drawCircle() {
		pen.clearRect(0, 0, circleBoard.width, circleBoard.height);
		pen.fillStyle = "#fff0b4";
		pen.arc(circleWidth, circleHeight, r, 0, Math.PI * 2, false);
		pen.fill();
		var text = new Image();
		text.src = "../img/rouletteText.png";
		text.onload = function() {
			pen.drawImage(text, r - 190, r - 190, 400, 400);
		};
	}
	function drawPointer() {
		pen.beginPath();
		// 삼각형 그리기 
		pen.moveTo(circleWidth, 30);
		pen.lineTo(circleWidth - 20, 0);
		pen.lineTo(circleWidth + 20, 0);
		pen.closePath();
		pen.fillStyle = "#fff0b4";
		pen.fill();
		pen.lineWidth = 1;
		pen.stroke();
	}
	function drawWheel() {
		pen.clearRect(0, 0, circleBoard.width, circleBoard.height);
		var angleOffset = currentAngle - Math.PI / 2;
		for (var i = 0; i < menu.length; i++) {
			var startAngle = angleOffset + i * arc;
			var endAngle = startAngle + arc;
			// 각 섹션 그리기
			pen.beginPath();
			pen.moveTo(circleWidth, circleHeight);
			pen.arc(circleWidth, circleHeight, r, startAngle, endAngle);
			pen.closePath();
			pen.fillStyle = colors[i];
			pen.fill();
			pen.lineWidth = 1;
			pen.stroke();

			var fontSize = 40;
			if (menu.length >= 20) fontSize = 20; // 20개 이상이면 20px
			else if (menu.length >= 10) fontSize = 30; // 10개 이상이면 30px

			pen.save();
			pen.translate(circleWidth, circleHeight);
			pen.rotate(startAngle + arc / 2);
			pen.textAlign = "center";
			pen.font = fontSize + "px m";
			pen.fillStyle = "black";
			pen.fillText(menu[i], r / 2, 0);
			pen.restore();
		}
		drawPointer();
	}
	// 룰렛 회전 함수
	function spin() {
		if (spinning) {
			currentAngle += speed;
			drawWheel();
			if (slowing) {
				if (speed > 0.01) {
					speed *= 0.98; // 점점 속도 줄이기
				} else {
					spinning = false;
					slowing = false;
				}
			}
			requestAnimationFrame(spin);
		}
	}

	$("#createRoulette").click(function() {
		input = $(menuInput).val();
		if ((input == "") || (input.endsWith(",")) || 
			(input.endsWith(", ")) || (input.includes(",,"))) {
			alert("항목을 다시 확인하세요!")
			return;
		}
		menu = $(menuInput).val().split(",");
		for (var i = 0; i < menu.length; i++) {
			colors.push(getRandomColor());
		}
		clicked = true;
		arc = (2 * Math.PI) / menu.length;
		currentAngle = 0;
		drawWheel();
	});
	$("#startButton").click(function() {
		if (clicked && !spinning) {
			spinning = true;
			slowing = false;
			speed = 0.3;
			spin();
		}
	});
	$("#stopButton").click(function() {
		if (spinning) {
			slowing = true;
		}
	});
	$("#resetButton").click(function() {
		clicked = false;
		$(menuInput).val("");
		drawCircle();
	});
}

function foodQuizEventController() {
	var quizzes = [];
	var index = 0;
	var correctNum = 0;
	if ($("#foodQuizData") == null) return;

	$("#foodQuizData div").each(function() {
		quizzes.push({
			question: $(this).attr("data-question"),
			answer: $(this).data("answer")
		});
	});

	var img = $("#foodImg");
	var submitBtn = $("#foodanswer button");
	var answerInput = $("#answerInput");
	var nextBtn = $("#foodquiz #next");
	var nextPopUpArea = $("#foodquiz .nextPopUpArea");
	var progress = $("#foodquiz .quiz-progress");

	function updateProgress() {
		progress.text("현재  " + (index + 1) + " / " + quizzes.length + " 문제");
	}
	function showQuiz() {
		if (index < quizzes.length) {
			var quiz = quizzes[index];

			console.log("이미지 경로:", quiz.question);
			img.attr("src", quiz.question);
			answerInput.val("");
			submitBtn.off("click").on("click", function() {
				var userAnswer = answerInput.val().trim();
				checkAnswer(userAnswer, quiz.answer, quiz.question);
			});
			nextBtn.off("click").on("click", function() {
				index++;
				nextPopUpArea.css("top", "-100%");
				showQuiz();
			});
			updateProgress();
		} else {
			$("#foodquiz .quizNextArea").css("display", "none");
			$("#foodquiz .quizResultArea").attr("class", "");
			$("#foodquiz #result").text("맞은 개수: " + correctNum + "개");
			nextPopUpArea.css("top", "0px");
			correctNum = 0;
		}
	}
	$("#foodquiz .quizResultArea button").click(function() {
		$("#foodquiz .quizNextArea").css("display", "");
		$("#foodquiz .quizResultArea").css("display", "none");
		nextPopUpArea.css("top", "-100%");
		location.href = "arcade.quiz.go";
	});

	function checkAnswer(input, answer, img) {
		if (input.includes(answer)) {
			$("#foodquiz .nextPopUp #ox").text("정답");
			$("#foodquiz #correctAnswer").text(answer);
			correctNum++;
		} else {
			$("#foodquiz .nextPopUp #ox").text("오답");
			$("#foodquiz #correctAnswer").text(answer);
		}
		$("#foodquiz #correctImg img").attr("src", img);
		nextPopUpArea.css("top", "0px");
	}
	// 첫 번째 퀴즈 시작
	showQuiz();
}

// 색 랜덤뽑기
function getRandomColor() {
	var r = Math.random() * 255;
	var g = Math.random() * 255;
	var b = Math.random() * 255;
	return "rgb(" + r + "," + g + "," + b + ")";
}

function quizRegToggleInput() {
	$("#quizRegTbl input[name=type]").change(function() {
		var textInput = $("#textInput");
		var fileInput = $("#fileInput");
		var selectedValue = $("#quizRegTbl input[name=type]:checked").val();
		if (selectedValue === "1") {
			textInput.removeClass("hidden");
			fileInput.addClass("hidden");
		} else {
			textInput.addClass("hidden");
			fileInput.removeClass("hidden");
		}
	}).trigger("change");
}

function regQuizPost() {
	var socket = io.connect("http://ip:port");

	$("#quizRegForm").submit(function(event) {
		event.preventDefault(); // 기본 폼 제출 방지
		var formData = new FormData(this);
		var f = $("#quizRegTbl #quizRegForm")[0];
		var type = $("input[name=type]:checked").val();
		var url = (type == "2") ? "arcade.food.quiz.reg" : "arcade.commonsense.quiz.reg";
		var vaildCheck =  (type == "2") ? foodQuizRegCheck(f) : CSQuizRegCheck(f);
		
		if(vaildCheck) {
			$.ajax({
				url: url,
				type: "POST",
				data: formData,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					socket.emit("foodGuideMsg", "success");
				},
				error: function(xhr) {
					alert("업로드 실패: " + xhr.responseText);
				}
			});
		}

		socket.on("srvFoodGuideMsg", function(_) {
			location.reload();
		});
	});
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload();
	});
}

function quizAllToggleInput() {
	var typeInputs = $("#quizAll #toggle input[name=type]");
	var CSQuizArea = $("#quizAll #CSQuizArea");
	var FQuizArea = $("#quizAll #FQuizArea");

	typeInputs.off("change").on("change", function() {
		var selectedValue = typeInputs.filter(":checked").val();

		if (selectedValue === "1") {
			CSQuizArea.removeClass("hidden");
			FQuizArea.addClass("hidden");
		} else if (selectedValue === "2") {
			CSQuizArea.addClass("hidden");
			FQuizArea.removeClass("hidden");
		}
	}).trigger("change"); // 초기 실행
}


function deleteQuiz(no) {
	var socket = io.connect("http://ip:port");
	var q = confirm("삭제하시겠습니까?");
	if (q) {
		$.getJSON("arcade.quiz.delete?no=" + no, function(result) {
			alert(result.result);
			socket.emit("foodGuideMsg", "success");
		});
	}
	socket.on("srvFoodGuideMsg", function(_) {
		location.href = "arcade.quiz.check.go";
	});
}
