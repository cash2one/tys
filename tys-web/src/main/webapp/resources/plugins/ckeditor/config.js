/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {

	config.language = 'zh-cn';
	
	config.height = 300;
	config.fullPage = true;
	config.allowedContent = true;
	
	config.toolbar =
		[
		['Save','NewPage','Preview','-','Templates'],
		['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
		['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
		['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
		'/',
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
		'/',
		['Styles','Format','Font','FontSize'],
		['TextColor','BGColor'],
		['Link','Unlink','Anchor'],
		['Maximize', 'ShowBlocks','-','Source']
		];

	config.enterMode = CKEDITOR.ENTER_P;
	config.shiftEnterMode = CKEDITOR.ENTER_BR;

	config.pasteFromWordRemoveFontStyles = false;
	config.pasteFromWordRemoveStyles = false;
	
	config.ignoreEmptyParagraph = false;
	
	//是否强制复制来的内容去除格式 plugins/pastetext/plugin.js
    config.forcePasteAsPlainText =false //不去除
    //是否强制用“&”来代替“&amp;”plugins/htmldataprocessor/plugin.js
    config.forceSimpleAmpersand = false;
	
	// Make dialogs simpler.
	config.removeDialogTabs = 'image:advanced;link:advanced';

	config.filebrowserUploadUrl = "ckeditorUpload";
};
