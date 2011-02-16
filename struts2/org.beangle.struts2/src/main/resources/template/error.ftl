[#ftl]
[@b.head/]
<h3>Server Error! You know what I mean.</h3>
<div>Create or change [/template/error.ftl] in your webapp directory.Using[#noparse]${stack.pop().exceptionStack}[/#noparse] to display exception stack.</div>
<hr>
<div>
${stack.pop().exceptionStack}
</div>
[@b.foot/]