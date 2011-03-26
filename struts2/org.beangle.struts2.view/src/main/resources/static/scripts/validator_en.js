// Title: Tigra Form Validator
// URL: http://www.softcomplex.com/products/tigra_form_validator_pro/
// Version: 1.1
// Date: 09/14/2004 (mm/dd/yyyy)
// Notes: Registration needed to use this script legally. Visit official site for details.

// regular expressions or function to validate the format

var targetClassName = "";

var re_dt = /^(\d{1,2})\-(\d{1,2})\-(\d{4})$/,
re_tm = /^(\d{1,2})\:(\d{1,2})\:(\d{1,2})$/,
a_formats = {
  'alpha'   : /^[a-zA-Z\.\-]*$/,
  'alphanum': /^\w+$/,
  'unsigned': /^\d+$/,
  'integer' : /^[\+\-]?\d*$/,
  'real'    : /^[\+\-]?\d*\.?\d*$/,
  'unsignedReal': /^\d*\.?\d*$/,
  'ration'  : /^\d*\.?\d*\%?$/,
  'email'   : /^[\w-\.]+\@[\w\.-]+\.[a-z]{2,4}$/,
  'phone'   : /^[\d\.\s\-]+$/,
  'date'    : function (s_date) {
    // check format
    if (!re_dt.test(s_date))
      return false;
    // check allowed ranges
    if (RegExp.$1 > 31 || RegExp.$2 > 12)
      return false;
    // check number of day in month
    var dt_test = new Date(RegExp.$3, Number(RegExp.$2-1), RegExp.$1);
    if (dt_test.getMonth() != Number(RegExp.$2-1))
      return false;
    return true;
  },
  'time'    : function validate_time(s_time) {
    // check format
    if (!re_tm.test(s_time))
      return false;
    // check allowed ranges
    if (RegExp.$1 > 23 || RegExp.$2 > 59 || RegExp.$3 > 59)
      return false;
    return true;
  }
},
a_messages = [
  'No form name passed to validator construction routine',
  'No array of "%form%" form fields passed to validator construction routine',
  'Form "%form%" can not be found in this document',
  'Incomplete "%n%" form field descriptor entry. "l" attribute is missing',
  'Can not find form field "%n%" in the form "%form%"',
  'Can not find label tag (id="%t%")',
  'Can not verify match. Field "%m%" was not found',
  '%l% is a required field',
  'Value for "%l%" must be %mn% characters or more',
  'Value for "%l%" must be no longer than %mx% characters',
  '"%v%" is not valid value for "%l%"',
  '"%l%" must match "%ml%"',
  '"%l%" is not valid value for english characters or ".""-"',
  '%l% is not valid value for english characters or number',
  '%l%is not valid value for number',
  '%l%is not valid value for number',
]


// validator counstruction routine
function validator(s_form, a_fields, o_cfg) {
  this.f_error = validator_error;
  this.f_alert = o_cfg && o_cfg.alert
    ? function(s_msg) { alert(s_msg); return false }
    : function() { return false };

  // check required parameters
  if (!s_form)
    return this.f_alert(this.f_error(0));
  this.s_form = s_form;

  if (!a_fields || typeof(a_fields) != 'object')
    return this.f_alert(this.f_error(1));
  this.a_fields = a_fields;

  this.a_2disable = o_cfg && o_cfg['to_disable'] && typeof(o_cfg['to_disable']) == 'object'
    ? o_cfg['to_disable']
    : [];

  this.exec = validator_exec;
}

// validator execution method
function validator_exec() {
  //var o_form = document.forms[this.s_form];
  var o_form = this.s_form;
  if (!o_form)
    return this.f_alert(this.f_error(2));

  b_dom = document.body && document.body.innerHTML;

  // check integrity of the form fields description structure
  
  for (var n_key in this.a_fields) {
    // check input description entry
    this.a_fields[n_key]['n'] = n_key;
    if (!this.a_fields[n_key]['l'])
      return this.f_alert(this.f_error(3, this.a_fields[n_key]));
    o_input = o_form.elements[n_key];
    if (!o_input)
      return this.f_alert(this.f_error(4, this.a_fields[n_key]));
    this.a_fields[n_key].o_input = o_input;
  }

  // reset labels highlight
  if (b_dom)
    for (var n_key in this.a_fields)
      if (this.a_fields[n_key]['t']) {
        var s_labeltag = this.a_fields[n_key]['t'], e_labeltag = get_element(s_labeltag);
        if (!e_labeltag)
          return this.f_alert(this.f_error(5, this.a_fields[n_key]));
        this.a_fields[n_key].o_tag = e_labeltag;

        // normal state parameters assigned here
        e_labeltag.className = 'tfvNormal';
      }

  // collect values depending on the type of the input
  for (var n_key in this.a_fields) {
    o_input = this.a_fields[n_key].o_input;
    if (o_input.value || o_input.checked) // text, password, hidden, checkbox
      this.a_fields[n_key]['v'] = o_input.value;
    else if (o_input.options) // select
      this.a_fields[n_key]['v'] = o_input.selectedIndex > -1
        ? o_input.options[o_input.selectedIndex].value
        : null;
    else if (o_input.length > 0) // radiobuton
      for (var n_index = 0; n_index < o_input.length; n_index++)
        if (o_input[n_index].checked) {
          this.a_fields[n_key]['v'] = o_input[n_index].value;
          break;
        }
  }

  // check for errors
  var n_errors_count = 0,
    n_another, o_format_check;
  for (var n_key in this.a_fields) {
    o_format_check = this.a_fields[n_key]['f'] && a_formats[this.a_fields[n_key]['f']]
      ? a_formats[this.a_fields[n_key]['f']]
      : null;

    // reset previous error if any
    this.a_fields[n_key].n_error = null;

    // check reqired fields
    if (this.a_fields[n_key]['r'] && !this.a_fields[n_key]['v']) {
      this.a_fields[n_key].n_error = 1;
      n_errors_count++;
    }
    // check length
    else if (this.a_fields[n_key]['mn'] && String(this.a_fields[n_key]['v']).length < this.a_fields[n_key]['mn']) {
      this.a_fields[n_key].n_error = 2;
      n_errors_count++;
    }
    else if (this.a_fields[n_key]['mx'] && String(this.a_fields[n_key]['v']).length > this.a_fields[n_key]['mx']) {
      this.a_fields[n_key].n_error = 3;
      n_errors_count++;
    }
    // check format
    else if (this.a_fields[n_key]['v'] && this.a_fields[n_key]['f'] && (
      (typeof(o_format_check) == 'function'
      && !o_format_check(this.a_fields[n_key]['v']))
      || (typeof(o_format_check) != 'function'
      && !o_format_check.test(this.a_fields[n_key]['v'])))
      ) {
        switch (this.a_fields[n_key]['f']) {
             case "alpha":
                 this.a_fields[n_key].n_error = 6;
                 break;
             case "alphanum":
                 this.a_fields[n_key].n_error = 7;
                 break;
             case "unsigned":
                 this.a_fields[n_key].n_error = 8;
                 break;
             case "integer":
                 this.a_fields[n_key].n_error = 9;
                 break;
             case "real":
                 this.a_fields[n_key].n_error = 10;
                 break;
             case "unsignedReal":
                 this.a_fields[n_key].n_error = 11;
                 break;
             case "ration":
                 this.a_fields[n_key].n_error = 12;
                 break;
             default:
                 this.a_fields[n_key].n_error = 4;
        }

        n_errors_count++;
    }
    // check match
    else if (this.a_fields[n_key]['m']) {
      for (var n_key2 in this.a_fields)
        if (n_key2 == this.a_fields[n_key]['m']) {
          n_another = n_key2;
          break;
        }
      if (n_another == null)
        return this.f_alert(this.f_error(6, this.a_fields[n_key]));
      if (this.a_fields[n_another]['v'] != this.a_fields[n_key]['v']) {
        this.a_fields[n_key]['ml'] = this.a_fields[n_another]['l'];
        this.a_fields[n_key].n_error = 5;
        n_errors_count++;
      }
    }
  }

  // collect error messages and highlight captions for errorneous fields
  var s_alert_message = '',
    e_first_error;

  if (n_errors_count) {
    for (var n_key in this.a_fields) {
      var n_error_type = this.a_fields[n_key].n_error,
        s_message = '';

      if (n_error_type)
        s_message = this.f_error(n_error_type + 6, this.a_fields[n_key]);

      if (s_message) {
        if (!e_first_error)
          e_first_error = o_form.elements[n_key];
        s_alert_message += "！" + s_message + "\n";
        // highlighted state parameters assigned here
        if (b_dom && this.a_fields[n_key].o_tag)
          targetClassName = this.a_fields[n_key].o_tag.className;
          this.a_fields[n_key].o_tag.className = 'tfvHighlight';
      }
    }
    alert(s_alert_message);
    // set focus to first errorneous field
    if (e_first_error.focus)
      e_first_error.focus();
    // cancel form submission if errors detected
    return false;
  }

  for (n_key in this.a_2disable)
    if (o_form.elements[this.a_2disable[n_key]])
      o_form.elements[this.a_2disable[n_key]].disabled = true;

  return true;
}

function validator_error(n_index) {
  var s_ = a_messages[n_index], n_i = 1, s_key;
  for (; n_i < arguments.length; n_i ++)
    for (s_key in arguments[n_i])
      s_ = s_.replace('%' + s_key + '%', arguments[n_i][s_key]);
  s_ = s_.replace('%form%', this.s_form);
  return s_
}

function get_element (s_id) {
  return (document.all ? document.all[s_id] : (document.getElementById ? document.getElementById(s_id) : null));
}

function validDateInterval(startDate,endDate){
		var arrayBeginDate=startDate.split('-');
   		var beginDate=new Date();
   		beginDate.setFullYear(arrayBeginDate[0]);   
  		beginDate.setMonth(arrayBeginDate[1]-1);   
  		beginDate.setDate(arrayBeginDate[2]);
  		
   		var arrayEndDate=endDate.split('-');
   		var endDate=new Date();
   		endDate.setFullYear(arrayEndDate[0]);   
  		endDate.setMonth(arrayEndDate[1]-1);   
  		endDate.setDate(arrayEndDate[2]);
  		
  		if(beginDate.getTime()>endDate.getTime()){
  			return false;
  		} 
  		else{
  			return true;
  		}
   }
