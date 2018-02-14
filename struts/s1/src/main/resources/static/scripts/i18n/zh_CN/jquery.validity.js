jQuery.extend(jQuery.validity.messages, {
	require:"请填写#{field}",
	requireSelect:"请选择#{field}",
	// Format validators:
	match:"#{field}格式无效",
	integer:"#{field}应为自然数",
	date:"#{field}应为日期格式",
	email:"#{field}应为邮件格式",
	usd:"#{field}应为美元格式",
	url:"#{field}应为URL格式",
	number:"#{field}应为数字格式",
	zip:"#{field}应为邮政编码格式 如######",
	phone:"#{field}应为电话号码格式如 ###-########",
	guid:"#{field}应为 guid格式,如{3F2504E0-4F89-11D3-9A0C-0305E82C3301}",
	time24:"#{field} 应为24小时格式,如 23:00",
	time12:"#{field} 应为12小时格式,如 12:00 AM/PM",

	// Value range messages:
	lessThan:"#{field}须小于#{max}.",
	lessThanOrEqualTo:"#{field} 须小于或等于 #{max}",
	greaterThan:"#{field}须大于 #{min}",
	greaterThanOrEqualTo:"#{field} 须大于或等于 #{min}",
	range:"#{field} 应在#{min}～#{max}之间",

	// Value length messages:
	tooLong:"#{field}不应超过#{max}个字符",
	tooShort:"#{field}不能少于#{min}个字符",

	// Composition validators:
	nonHtml:"#{field}不能包含html代码",
	alphabet:"#{field}包含了不允许的字符",

	minCharClass:"#{field} 不能超过 #{min} #{charClass} 字符",
	maxCharClass:"#{field} 不能少于 #{min} #{charClass} 字符",
	
	// Aggregate validator messages:
	equal:"数据不相等",
	distinct:"存在数据重复",
	sum:"数字累加之和不等与#{sum}",
	sumMax:"数字累加之和必须小于#{max}",
	sumMin:"数字累加之和必须大于#{min}",

	// Radio validator messages:
	radioChecked:"选择项无效",
	
	generic:"无效"
});

jQuery.validity.messages.notBlank="请在#{field}中填写非空白字符";
jQuery.validity.messages.chinaIdcard="请在#{field}中输入15位或18位身份证号";
jQuery.validity.messages.yearMonth="请在#{field}中输入格式为yyyy-mm格式的年月";
