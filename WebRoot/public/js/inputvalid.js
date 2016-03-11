/*
 * 美家秀自定义Form表单输入验证工具类
 * data-blur="false" 是否需要响应onBlur事件，默认不填是true
 * data-valid="[{rule:'not_empty',value:0,message:'not empty!'}]"
 */
(function($) {
    var InputValid = function InputValid(form, messages) {
        this.init(form, messages);
    }, old = null;
    InputValid.prototype = {
        init:function(form, messages) {
            this.message = $.extend({}, this.message, messages);
            this.$form = $(form);
            this.addEvent();
        },
        addEvent:function() {
            var self = this;
            this.$form.find("input").blur(function() {
                var onBlurFlag = $(this).attr("data-blur");
                if (!Boolean(onBlurFlag) || onBlurFlag == "true") self.validate(this);
            });
            this.$form.find("input").focus(function() {
                $(this).popover("destroy");
                $(this).parents(".has-error").removeClass("has-error");
            });
            this.$form.find("select").blur(function() {
                var onBlurFlag = $(this).attr("data-blur");
                if (!Boolean(onBlurFlag) || onBlurFlag == "true") self.validate(this);
            });
            this.$form.find("select").focus(function() {
                $(this).popover("destroy");
                $(this).parents(".has-error").removeClass("has-error");
            });
            this.$form.find("textarea").blur(function() {
                var onBlurFlag = $(this).attr("data-blur");
                if (!Boolean(onBlurFlag) || onBlurFlag == "true") self.validate(this);
            });
            this.$form.find("textarea").focus(function() {
                $(this).popover("destroy");
                $(this).parents(".has-error").removeClass("has-error");
            });
        },
        validate_all:function() {
            var self = this;
            var flag = true;
            this.$form.find("input").each(function() {
                if (!self.validate(this)) flag = false;
            });
            this.$form.find("select").each(function() {
                if (!self.validate(this)) flag = false;
            });
            this.$form.find("textarea").each(function() {
                if (!self.validate(this)) flag = false;
            });
            return flag;
        },
        validate:function(input) {
            var $input = $(input);
            var result = true;
            var data_valids = eval($input.attr("data-valid"));
            var $parents = $input.parents(".form-group");
            if(typeof $parents === "undefined" || $parents==null) $parents = $input.parents(".control-group");
            if (Boolean(data_valids)) {
                $input.popover("destroy");
                $input.parents(".form-group").removeClass("has-error");
                var self = this;
                $.each(data_valids, function() {
                    if (Boolean(this.rule) && typeof this.rule === "string" && typeof self.methods[this.rule] === "function") {
                        if (this.rule !== "not_empty" && !self.methods.not_empty($input.val())) return true;
                       
                        if (!self.methods[this.rule]($input.val(), this.value, this.relation)) {
                            if (Boolean(this.message)) {
                                self.popover_option.content = this.message.replace(/:value/g, this.value);
                            } else {
                                self.popover_option.content = self.message[this.rule].replace(/:value/g, this.value);
                            }
                            $input.parents(".form-group").addClass("has-error");
                            
                            
                            
                            
                            $input.popover(self.popover_option);
                            $input.popover("show");
                            result = false;
                            return false;
                        }
                    }
                });
            }
            return result;
        },
        methods:{
            not_empty:function(value) {
                return value !== null && $.trim(value).length > 0;
            },
            min_length:function(value, min_len) {
                return $.trim(value).length >= parseInt(min_len, 10);
            },
            max_length:function(value, max_len) {
                if (!this.not_empty(value)) return true;
                return $.trim(value).length <= parseInt(max_len, 10);
            },
            exact_length:function(value, exact_len) {
                return $.trim(value).length === parseInt(exact_len, 10);
            },
            min_value:function(value, min_val) {
                var val = parseInt($.trim(value), 10);
                return !isNaN(val) && val >= parseInt(min_val, 10);
            },
            max_value:function(value, max_val) {
                var val = parseInt($.trim(value), 10);
                return !isNaN(val) && val <= parseInt(max_val, 10);
            },
            equals:function(value, target) {
                return value == target;
            },
            compare:function(value, compareDoc, relation) {
                if (!this.not_empty($(compareDoc).val())) return true;
                var val = parseInt($.trim(value), 10);
                var val2 = parseInt($.trim($(compareDoc).val()), 10);
                if (relation === ">=") return val >= val2; else if (relation === ">") return val > val2; else if (relation === "=") return val = val2; else if (relation === "<") return val < val2; else if (relation === "<=") return val <= val2;
            },
            regex:function(value, regexpStr) {
                var regex = new RegExp(regexpStr);
                return regex.test(value);
            },
            email:function(value) {
                var regex = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                return regex.test($.trim(value));
            },
            url:function(value) {
                var regex = new RegExp("^(http|https|ftp|udp|mop)\\://\\S+$");
                return regex.test(value);
            },
            ip:function(value) {
                var regex = new RegExp("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2}).){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$");
                return regex.test($.trim(value));
            },
            mac:function(value) {
                var regex = new RegExp("^([0-9a-fA-F]{2})(([/s:-][0-9a-fA-F]{2}){5})$");
                return regex.test(value);
            },
            year:function(value) {
                if (!isNaN(parseInt(value, 10)) && parseInt(value, 10) <= 3e3 && parseInt(value, 10) >= 1900) return true; else return false;
            },
            date:function(value) {
                var regex = new RegExp("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
                return regex.test(value);
            },
            id:function(value) {
                var regex = new RegExp("^[0-9a-zA-Z:_-]+$");
                return regex.test(value);
            },
            chinese:function(value) {
                var regex = new RegExp("^[u4e00-u9fa5]+$");
                return regex.test(value);
            },
            version:function(value) {
                var regex = new RegExp("^[0-9.]+$");
                return regex.test(value);
            },
            domain:function(value) {
                var regex = new RegExp("^[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+.?$");
                return regex.test(value);
            },
            path:function(value) {
                var regex = new RegExp("(^\\.|^/|^[a-zA-Z])?:?/.+(/$)?");
                return regex.test(value);
            },
            integer:function(value) {
                var regex = new RegExp("^-?\\d+$");
                return regex.test(value);
            },
            positive:function(value) {
                var regex = new RegExp("^[0-9]*[1-9][0-9]*$");
                return regex.test(value);
            },
            non_negative:function(value) {
                var regex = new RegExp("^\\d+$");
                return regex.test(value);
            },
            first_letter:function(value, first_letter) {
                var regex = new RegExp("^" + first_letter + "[0-9a-zA-Z:_-]+$");
                return regex.test(value);
            },
            //0-1之间的小数
            toFixed:function(value,precision){
            	var pattern = new RegExp("^0\\.{1}\\d{1,"+precision+"}$");
            	
            	return pattern.test(value);
            }
            
        },
        message:{
            not_empty:"",
            min_length:"",
            max_length:"",
            exact_length:"",
            min_value:"",
            max_value:"",
            equals:"",
            regex:"",
            email:"",
            url:"",
            ip:"",
            mac:"",
            year:"",
            date:"",
            id:"",
            chinese:"",
            version:"",
            domain:"",
            path:"",
            integer:"",
            positive:"",
            non_negative:"",
            first_letter:"",
            mobile:""
        },
        popover_option:{
            placement:"bottom",
            trigger:"manual",
            content:""
        }
    };
    old = $.fn.inputValid;
    $.fn.inputValid = function(msg) {
        var result = null;
        $(this).each(function(i, item) {
            var $this = $(item), data = $this.data("inputvalid"), messages = typeof msg !== "object" ? null :msg;
            if (!data) $this.data("inputvalid", data = new InputValid(this, messages));
            result = data;
        });
        return result;
    };
})(jQuery);