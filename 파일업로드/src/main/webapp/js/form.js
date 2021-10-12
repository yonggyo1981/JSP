$(function() {
	/** 에디터 로드 */
	CKEDITOR.replace("content");
	CKEDITOR.config.height = 300;
	
	/** 이미지 업로드 팝업 */
	$("#addImage").click(function() {
		/**
		* 1. 팝업 백그라운드 
		* 2. 팝업 컨텐츠 영역 
		* 3. 팝업 컨텐츠 영역 -> 업로드 양식 HTML 
		 */
	
		/** 팝업 백그라운드 S */
		if ($("#layer_dim").length == 0) { // layer_dim이 없는 경우는 동적 추가  
			$("body").append("<div id='layer_dim'></div>");
		}	
		
		$("#layer_dim").css({
			position: "fixed",
			width: "100%",
			height: "100%",
			top: 0,
			left: 0,
			background : "rgba(0, 0, 0, 0.6)",
			zIndex : 10,
		});
		/** 팝업 백그라운드 E */
		
		/** 팝업 컨텐츠 영역 S  */
		if ($("#layer_popup").length == 0) { // layer_popup이 없으면 -> 추가 
			$("body").append("<div id='layer_popup'></div>");
		}
		
		const width = 320;
		const height = 200;
		
		const xpos = Math.round(($(window).width() - width) / 2);
		const ypos = Math.round(($(window).height() - height) / 2);
		
		$("#layer_popup").css({
			position: "fixed",
			width: width + "px",
			height: height + "px",
			backgroundColor : "#ffffff",
			borderRadius : "20px",
			zIndex :  11,
			top: ypos + "px",
			left: xpos + "px",
			padding: "20px",
		});
		/** 팝업 컨텐츠 영역 E */
		
		/** 업로드 양식 처리 S */
		$("#layer_popup").html($("#uploadForm").html());
		/** 업로드 양식 처리 E */
		
	});
	
	/** 레이어 팝업 닫기 */
	$("body").on("click", "#layer_dim", function() {
		$("#layer_dim, #layer_popup").remove();
	});
	
	/** 파일 선택시 자동 submit */
	$("body").on("change", "#uploadFrm input[type='file']", function() {
		$("#uploadFrm").submit();
	});
});

/** 이미지 파일 업로드시 에디터 추가 함수 */
function callbackImageUpload(images) {
	if (!images) 
		return;
		
	images = images.split("||");
	images.forEach(function(image) {
		CKEDITOR.instances.content.insertHtml(`<img src='${image}'>`);
	});
	
	$("#layer_dim, #layer_popup").remove();
}


