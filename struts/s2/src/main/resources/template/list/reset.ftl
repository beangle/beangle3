[#ftl]
<button type="reset" class="${tag.cssClass!'btn btn-outline-warning btn-sm'}"
onclick="if(confirm('确认重新填写?')) {this.form.reset(); return true;} else{ return false;}" ${tag.parameterString}>
<i class="fa fa-undo fa-sm"></i> ${b.text('action.reset')}
</button>
