[#ftl/]
[#assign url=b.url('/captcha/image')/]
<input name="${tag.name}" class="captcha_response" type="text" style="${tag.inputStyle!}"/>
<img style="${tag.imageStyle!}" src="${url}" alt="看不清,更换一张" onclick="refreshCaptcha(this)"/>
<script>function refreshCaptcha(object){object.src="${url}?d="+(new Date().getTime());}</script>